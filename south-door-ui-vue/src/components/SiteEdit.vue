<template>
  <el-row>
    <el-col v-loading="loading" :xs="24" :sm="24" :md="24" :lg="12" :xl="12" :span="24">
      <div class="grid-content bg-purple-dark">
        <div style="display: block">
          <el-page-header
            @back="goBack"
            title="Back"
            :content="siteInfo.siteName"
            class="page-header-wrapper"
          ></el-page-header>
          <div class="info-wrapper">
            <!-- 网站首页上传 -->
            <el-form label-position="right" label-width="110px">
              <el-form-item label="Site Image">
                <el-upload
                  class="avatar-uploader"
                  name="siteImage"
                  action="/api/site/saveSiteImage"
                  :show-file-list="false"
                  :on-success="handleAvatarSuccess"
                  :on-error="handleImageError"
                  :before-upload="beforeAvatarUpload"
                  :on-exceed="handleChange"
                  :data="siteInfo"
                  :disabled="disabledUpload"
                >
                  <el-image v-if="image.url" :src="image.url" class="avatar" fit="cover"></el-image>
                  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                </el-upload>
              </el-form-item>
            </el-form>
            <el-form
              label-position="right"
              :rules="rules"
              ref="siteInfo"
              :model="siteInfo"
              label-width="110px"
            >
              <el-form-item label="Site Name" prop="siteName">
                <el-input v-model="siteInfo.siteName" maxlength="25" show-word-limit></el-input>
              </el-form-item>
              <el-form-item label="Group" prop="groupId">
                <el-select
                  v-model="siteInfo.groupId"
                  @change="changeGroup($event)"
                  placeholder="Please check one group"
                >
                  <el-option
                    v-for="oneGroup in groupList"
                    :key="oneGroup.groupId"
                    :index="oneGroup.groupId"
                    :label="oneGroup.groupName"
                    :value="oneGroup.groupId"
                  ></el-option>
                </el-select>
              </el-form-item>

              <el-form-item label="Address" prop="address">
                <el-input v-model="siteInfo.address"></el-input>
              </el-form-item>
              <el-form-item label="Is Proxy">
                <el-switch v-model="siteInfo.isProxy" :change="changeProxyStatus()"></el-switch>
              </el-form-item>

              <el-form-item label="Proxy Port" prop="proxyPort">
                <el-input
                  v-model.number="siteInfo.proxyPort"
                  :disabled="disabledProxyPort"
                  maxlength="6"
                  show-word-limit
                ></el-input>
              </el-form-item>
              <el-form-item label="Description">
                <el-input v-model="siteInfo.description" maxlength="50" show-word-limit></el-input>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="submitSiteInfo('siteInfo')">Save</el-button>
                <el-button @click="handleCancel()">Cancel</el-button>
              </el-form-item>
            </el-form>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { store } from "../vuex/store.js";
import {
  isEmpty,
  isNotEmpty,
  validateURL,
  validateNumber
} from "../utils/validate.js";
export default {
  data() {
    var validateSiteName = (rule, value, callback) => {
      if (isEmpty(value) || isEmpty(value.trim())) {
        this.disabledUpload = true;
        return callback(new Error("Please enter site name."));
      } else {
        this.$axios
          .post("/api/validate/siteName", this.siteInfo)
          .then(response => {
            if (response.data.code == 0) {
              this.disabledUpload = false;
              callback();
            } else {
              this.disabledUpload = true;
              return callback(
                new Error("This site name " + value + " already exists.")
              );
            }
          })
          .catch(err => {
            this.$message.error("Network error.");
          });
      }
    };
    var validateGroup = (rule, value, callback) => {
      if (isEmpty(value)) {
        return callback(new Error("Please select one group!"));
      }
      callback();
    };
    var validateAddress = (rule, value, callback) => {
      if (isEmpty(value)) {
        return callback(new Error("Please enter address!"));
      } else {
        if (!validateURL(value)) {
          return callback(new Error("Please enter right address!"));
        } else {
          // 验证此网站是否可访问
          this.$axios
            .post("/api/validate/address", value)
            .then(response => {
              if (response.data.code == 0) {
                callback();
              } else {
                return callback(new Error("connection refused!"));
              }
            })
            .catch(err => {
              this.$message.error("Network error.");
            });
        }
      }
    };

    var validateProxyPort = (rule, value, callback) => {
      if (this.siteInfo.isProxy) {
        if (isEmpty(value)) {
          return callback(new Error("Please enter port!"));
        } else {
          if (!validateNumber(value)) {
            return callback(new Error("Please enter integer for port!"));
          }
          this.$axios
            .post("/api/validate/proxyPort", value)
            .then(response => {
              if (response.data.code == 0) {
                callback();
              } else {
                return callback(
                  new Error("This port " + value + " has already been used!")
                );
              }
            })
            .catch(err => {
              this.$message.error("Network error.");
            });
        }
      } else {
        callback();
      }
    };
    return {
      rules: {
        siteName: [
          { required: true, validator: validateSiteName, trigger: "change" }
        ],
        address: [
          { required: true, validator: validateAddress, trigger: "blur" }
        ],
        proxyPort: [
          {
            validator: validateProxyPort,
            trigger: "blur"
          }
        ],
        groupId: [
          {
            required: true,
            validator: validateGroup,
            trigger: "blur"
          }
        ]
      },
      siteInfo: {
        siteId: "",
        groupId: "",
        siteName: "",
        isProxy: false,
        proxyPort: ""
      },
      disabledProxyPort: true,
      groupId: "",
      fileList: [],
      image: {
        name: "",
        url: ""
      },
      disabledUpload: true,
      loading: false
    };
  },
  methods: {
    goBack() {
      this.$router.go(-1);
    },
    handleCancel() {
      this.$router.go(-1);
    },
    submitSiteInfo(siteInfo) {
      if (this.disabledProxyPort) {
        this.siteInfo.proxyPort = null;
      }

      this.$refs[siteInfo].validate(valid => {
        if (valid) {
          this.loading = true;
          this.siteInfo.groupId = this.groupId;
          this.$axios
            .post("/api/site/saveSiteInfo", this.siteInfo)
            .then(response => {
              this.loading = true;
              if (response.data.code == 0) {
                this.$router.push({ name: "site-manage" });
              } else {
                this.$message.error("Save site info failed.");
              }
            })
            .catch(err => {
              this.loading = true;
              this.$message.error("Network error.");
            });
        }
      });
    },
    changeProxyStatus() {
      this.disabledProxyPort = !this.siteInfo.isProxy;
    },
    changeGroup(groupId) {
      this.groupId = groupId;
    },
    getSiteInfo(siteId) {
      this.$axios
        .get("/api/site/getSiteInfoById?siteId=" + siteId)
        .then(response => {
          this.siteInfo = response.data.data;
          this.groupId = this.siteInfo.groupId;
          this.image.name = this.siteInfo.siteName;
          this.image.url = "api" + this.siteInfo.imagePath;
        })
        .catch(err => {
          this.$message.error("Network error.");
        });
    },
    handleAvatarSuccess(res, file, fileList) {
      this.image.url = URL.createObjectURL(file.raw);
    },
    handleImageError(err, file, fileList) {
      this.$message.error("Upload image failed.");
    },
    beforeAvatarUpload(file) {
      if (isEmpty(this.siteInfo.siteName)) {
        this.$message.error("Please enter site name!");
        return false;
      }
      if (this.disabledUpload) {
        return false;
      }
      const isJPGOrPNG =
        file.type === "image/jpeg" || file.type === "image/png";
      const isLt2M = file.size / 1024 / 1024 < 10;

      if (!isJPGOrPNG) {
        this.$message.error(
          "Uploading images can only be in JPG or PNG format!"
        );
      }
      if (!isLt2M) {
        this.$message.error("Upload image size can't exceed 10MB!");
      }
      return isJPGOrPNG && isLt2M;
    },
    handleChange(file, fileList) {
      this.$message.info("Please delete current image.");
    }
  },
  computed: {
    groupList() {
      return store.getters.getGroupList;
    }
  },
  mounted() {
    if (!sessionStorage.getItem("user")) {
      this.$router.push("/");
    }
    let siteId = this.$route.params.siteId;
    if (isNotEmpty(siteId)) {
      this.getSiteInfo(siteId);
    }
  }
};
</script>

<style>
.avatar-uploader .el-upload {
  width: 50%;
  max-height: 50%;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  flex: 1;
  overflow: hidden;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  line-height: 250px;
  text-align: center;
}

.avatar {
  max-height: 350px;
  display: block;
}

.page-header-wrapper {
  padding-bottom: 20px;
  border-bottom: 1px solid #dcdfe6;
}

.info-wrapper {
  height: auto;
  text-align: left;
  margin-top: 20px;
}

.el-form-item label {
  padding-right: 18px;
  font-weight: 500px;
}
</style>
