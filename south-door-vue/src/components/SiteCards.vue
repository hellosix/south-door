<template>
  <el-row :gutter="20" id="site-list">
    <el-col
      :xl="4"
      :lg="6"
      :md="8"
      :sm="12"
      v-for="site in filterSiteList"
      :key="site.siteId"
      :site-id="site.siteId"
    >
      <div class="card-wrapper">
        <div class="image-wrapper">
          <a :href="site.proxyAddress" target="_blank">
            <el-image fit="cover" :src="site.imagePath" class="image site-image"></el-image>
          </a>
        </div>
        <div class="content-wrapper">
          <div class="content-item between">
            <div class="name-wrapper">{{ site.siteName }}</div>
            <el-rate
              v-model="site.rate"
              :colors="colors"
              class="rate-font"
              @change="rateChange(site.siteId, site.rate)"
            ></el-rate>
          </div>
          <div class="content-item between">
            <div class="color-text-secondary description">{{ site.description }}</div>
            <i class="el-icon-edit operation-icon" v-if="canEdit" @click="handleEdit(site.siteId)"></i>
          </div>
        </div>
      </div>
    </el-col>
  </el-row>
</template>

<script>
import { mapGetters } from "vuex";
import { store } from "../vuex/store.js";
import { isNotEmpty, isEmpty } from "../utils/validate.js";
export default {
  props: ["groupId"],
  name: "site-list",
  data() {
    return {
      colors: ["#99A9BF", "#F7BA2A", "#FF9900"],
      siteList: [],
      canEdit: false
    };
  },
  methods: {
    getSiteList(groupId) {
      
      this.$axios
        .get("/site/getSiteByGroupId?groupId=" + groupId)
        .then(response => {
          let siteList = response.data.data;
          let siteRateMap = store.getters.getSiteRateMap;
          for (let site of siteList) {
            //site.imagePath = "/api/" + site.imagePath;
            let rate = siteRateMap[site.siteId];
            if (isNotEmpty(rate)) {
              site.rate = rate;
            }
          }
          this.siteList = siteList;
          store.dispatch("setSiteList", this.siteList);
        })
        .catch(err => {
          console.log(err);
          this.$message.error("Network error");
        });
    },
    handleEdit(siteId) {
      this.$router.push({ name: "site-edit", params: { siteId: siteId } });
    },
    rateChange(siteId, rate) {
      let siteRate = { siteId: siteId, rate: rate };
      store.commit("setSiteRateMap", siteRate);
    }
  },
  watch: {
    groupId(groupId) {
      this.getSiteList(groupId);
    }
  },
  computed: {
    filterSiteList() {
      return store.getters.filterSiteList;
    }
  },
  mounted() {
    let groupId = this.$route.params.groupId;
    let groupList = store.getters.getGroupList;
    for (var group in groupList) {
      if (group.groupId == groupId) {
        localStorage.setItem(
          "lastSelectedGroup",
          JSON.stringify({
            groupId: group.groupId,
            groupName: group.groupName
          })
        );
      }
    }
    if (groupId) {
      this.getSiteList(groupId);
    }
    let user = sessionStorage.getItem("user");
    if (user) {
      this.canEdit = true;
    }
  }
};
</script>


<style>
/** CARD */
.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.card-wrapper {
  height: 268px;
  display: flex;
  flex-direction: column;
  border: 1px solid #e4e7ed;
  background-color: #ffffff;
  border-radius: 18px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.card-wrapper:hover {
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.2);
}

.image-wrapper {
  flex: 1;
  overflow: hidden;
  border-top-left-radius: 18px;
  border-top-right-radius: 18px;
}

.image {
  width: 100%;
  height: 100%;
}

.content-wrapper {
  padding: 10px;
  height: 75px;
}

.content-item {
  text-align: left;
  line-height: 20px;
  margin: 3px 0 0 0;
  padding: 5px;
}

.clearfix:before,
.clearfix:after {
  display: flex;
  content: "";
}

.clearfix:after {
  clear: both;
}

.operation-icon {
  color: #c0c4cc;
  cursor: pointer;
  padding: 5px 10px;
}

.operation-icon:hover {
  color: #007fff;
}

.name-wrapper {
  font-size: 14px;
  max-width: 50%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.description {
  max-width: 80%;
  white-space: nowrap;
  text-overflow: ellipsis;
  -o-text-overflow: ellipsis;
  overflow: hidden;
}

.color-text-secondary {
  font-size: 14px;
  color: #909399;
}

.between {
  display: flex;
  justify-content: space-between;
}

.rate-font i {
  font-size: 14px !important;
}
</style>
