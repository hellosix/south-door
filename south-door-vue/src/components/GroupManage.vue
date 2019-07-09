<template>
  <div>
    <div class="create-wrapper">
      <el-button
        icon="el-icon-plus"
        @click="showEditGroup()"
        title="Create group"
        size="small"
        class="right"
      >CREAT</el-button>
    </div>

    <el-table
      :data="groupList.filter(group => !search || group.groupName.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%"
      stripe
    >
      <el-table-column label="Group Name" prop="groupName" sortable></el-table-column>
      <el-table-column label="Description" prop="description"></el-table-column>
      <el-table-column label="Date" prop="updateTime" sortable></el-table-column>
      <el-table-column align="right" width="250px">
        <template slot="header" slot-scope="scope">
          <el-input v-model="search" size="mini" placeholder="search by keyworld" />
        </template>
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            circle
            @click="clickEdit(scope.$index, scope.row)"
          ></el-button>
          <el-button
            type="danger"
            size="mini"
            icon="el-icon-delete"
            circle
            @click="clickDelete(scope.$index, scope.row)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- new group -->
    <el-dialog
      title="Edit Group"
      :visible.sync="showEditGroupDialog"
      width="25%"
      custom-class="new-group-wrapper"
    >
      <el-form label-position="top" :rules="rules" :model="operationGroup" ref="operationGroup">
        <el-form-item prop="groupName">
          <el-input
            placeholder="Please enter new group name"
            prefix-icon="el-icon-s-flag"
            v-model="operationGroup.groupName"
            class="group-input"
            maxlength="25"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item prop="description">
          <el-input
            placeholder="Please enter description"
            prefix-icon="el-icon-s-flag"
            v-model="operationGroup.description"
            class="group-input"
            maxlength="25"
            show-word-limit
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          @click="submitGroup('operationGroup')"
          class="new-group-button"
        >Confirm</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="Delete Group"
      :visible.sync="showDeleteDialog"
      :center="true"
      custom-class="delete-site-wrapper"
    >
      <span>
       If there are sites in this group, then <b>{{ operationGroup.groupName }}</b> will not be deleted.
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showDeleteDialog = false">Cancel</el-button>
        <el-button type="danger" @click="confirmDeleteGroup()">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { store } from "../vuex/store.js";
import { isEmpty, isNotEmpty } from "../utils/validate.js";
export default {
  data() {
    var validateGroupName = (rule, value, callback) => {
      if (isEmpty(value) || isEmpty(value.trim())) {
        this.disabledUpload = true;
        return callback(new Error("Please enter group name."));
      } else {
        this.$axios
          .post("/validate/groupName", this.operationGroup)
          .then(response => {
            if (response.data.code == 0) {
              callback();
            } else {
              return callback(
                new Error("This group name " + value + " already exists.")
              );
            }
          })
          .catch(err => {
            this.$message.error("Network error.");
            return callback(new Error("Network error."));
          });
      }
    };
    return {
      search: "",
      showEditGroupDialog: false,
      showDeleteDialog: false,
      operationGroup: {},
      rules: {
        groupName: [
          { required: true, validator: validateGroupName, trigger: "change" }
        ]
      }
    };
  },
  methods: {
    clickEdit(index, row) {
      this.operationGroup = row;
      this.showEditGroupDialog = true;
    },
    clickDelete(index, row) {
      this.operationGroup = row;
      this.showDeleteDialog = true;
    },
    confirmDeleteGroup() {
      let operationGroup = this.operationGroup;
      if (isNotEmpty(operationGroup.groupId)) {
        this.$axios
          .post("/group/deleteGroupById", operationGroup.groupId)
          .then(response => {
            if (response.data.code == 0) {
              this.groupList.forEach(function(group, index, arr) {
                if (group.groupId == operationGroup.groupId) {
                  arr.splice(index, 1);
                }
              });
              store.dispatch("setGroupList", this.groupList);
            }
          })
          .catch(err => {
            this.$message.error("Network error.");
          });
      }

      this.showDeleteDialog = false;
    },
    showEditGroup() {
      this.operationGroup = {};
      this.showEditGroupDialog = true;
    },
    submitGroup(newSiteGroup) {
      this.$refs[newSiteGroup].validate(valid => {
        if (valid) {
          let groupList = store.getters.getGroupList;
          let operationGroup = this.operationGroup;
          if (isEmpty(operationGroup.groupId)) {
            this.$axios
              .post("/group/addGroup", operationGroup)
              .then(response => {
                if (response.data.code == 0) {
                  groupList.push(response.data.data);
                  store.commit("setGroupList", groupList);
                  this.showEditGroupDialog = false;
                } else {
                  this.$message.error("create group failed.");
                }
              })
              .catch(err => {
                this.$message.error("Network error.");
              });
          } else {
            this.$axios
              .post("/group/updateGroup", operationGroup)
              .then(response => {
                if (response.data.code == 0) {
                  groupList.forEach(function(group, index) {
                    if (group.groupId == operationGroup.groupId) {
                      group = operationGroup;
                    }
                  });
                  store.commit("setGroupList", groupList);
                  this.showEditGroupDialog = false;
                } else {
                  this.$message.error("update group failed.");
                }
              })
              .catch(err => {
                this.$message.error("Network error.");
              });
          }
        }
      });
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
  }
};
</script>
<style>
.create-wrapper {
  padding: 0 20px;
}

.right {
  float: right;
}
</style>


