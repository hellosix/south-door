<template>
  <el-container>
    <div class="header-fixed">
      <el-header>
        <a :underline="false" class="logo" href="/">
          <img src="./assets/logo.svg" class="logo-icon" />South Door
        </a>
        <el-menu
          :default-active="activeIndex"
          mode="horizontal"
          active-text-color="#007fff"
          @select="handleSelect"
          menu-trigger="click"
        >
          <el-menu-item class="no-active">
            <el-input
              size="small"
              placeholder="Search by site name"
              prefix-icon="el-icon-search"
              v-model="keywords"
            ></el-input>
          </el-menu-item>
          <el-submenu index="0" class="nav-item">
            <template slot="title">{{ currentGroupName }}</template>
            <el-menu-item
              v-for="group in groupList"
              :key="group.groupId"
              :index="group.groupId"
              class="nav-item"
              @click="handleSelectGroup(group)"
            >{{ group.groupName }}</el-menu-item>
          </el-submenu>
          <el-menu-item index="1" @click="toGroupManage" class="nav-item">Group Manage</el-menu-item>
          <el-menu-item index="2" @click="toSiteManage" class="nav-item">Site Manage</el-menu-item>

          <el-menu-item index="-1" class="user-info no-active">
            <el-dropdown trigger="hover" v-if="isLogin">
              <span class="el-dropdown-link userinfo-inner">
               <img :src="userInfo.headPic" class="head-pic" />
                {{ userInfo.userName }}
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="logout()">Sign out</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
            <span class="nav-item" @click="userLogin()" v-else>Sign In</span>
          </el-menu-item>
        </el-menu>
      </el-header>
    </div>
    <el-main class="main-body">
      <router-view></router-view>
    </el-main>

    <el-dialog title="Sign In" :visible.sync="showLogin" width="25%" custom-class="login-wrapper">
      <el-form ref="userInfo" :model="userInfo" label-position="top">
        <el-form-item
          prop="userName"
          :rules="[
            { required: true, message: 'Please enter user name'}
          ]"
        >
          <el-input
            placeholder="Please enter user name"
            prefix-icon="el-icon-user"
            v-model="userInfo.userName"
          ></el-input>
        </el-form-item>
        <el-form-item
          prop="password"
          :rules="[
            { required: true, message: 'Please enter password'}
          ]"
        >
          <el-input
            placeholder="Please enter password"
            prefix-icon="el-icon-lock"
            v-model="userInfo.password"
            show-password
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="loginSubmit('userInfo')" class="login-button">Login</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
import { store } from "./vuex/store.js";

export default {
  name: "Index",
  components: {},
  data() {
    return {
      activeIndex: "",
      keywords: store.getters.getKeywords,
      currentGroupName: "Group List",
      groupId: "",
      isLogin: false,
      showLogin: false,
      userInfo: {
        userName: "",
        password: "",
        headPic: ""
      },
      groupList: store.getters.getGroupList
    };
  },
  methods: {
    notifyToSignIn() {
      this.$notify({
        title: "Not Logged In",
        message: "Please log in first",
        offset: 100,
        duration: 2000
      });
      this.activeIndex = "";
    },
    userLogin() {
      sessionStorage.removeItem("user");
      this.showLogin = true;
      this.isLogin = false;
    },
    logout() {
      sessionStorage.removeItem("user");
      this.isLogin = false;
      this.$router.go(0);
    },
    loginSubmit(userInfo) {
      this.$refs[userInfo].validate(valid => {
        if (valid) {
          this.$axios
            .post("/user/login", this.userInfo)
            .then(response => {
              if (response.data.code == 0) {
                this.userInfo = response.data.data;

                sessionStorage.setItem(
                  "user",
                  JSON.stringify({
                    userName: this.userInfo.userName,
                    headPic: this.userInfo.headPic
                  })
                );
                this.showLogin = false;
                this.isLogin = true;
                this.$router.go(0);
              } else {
                this.$message.error("user name or password wrong");
              }
            })
            .catch(err => {
              this.$message.error("Network error");
            });
        }
      });
    },
    handleSelect(key, keyPath) {
      let user = sessionStorage.getItem("user");
      if (user) {
        localStorage.setItem("activeIndex", key);
      } else {
        localStorage.setItem("activeIndex", "");
      }
    },
    toGroupManage() {
      let user = sessionStorage.getItem("user");
      if (user) {
        this.$router.push({ name: "group-manage" });
      } else {
        this.notifyToSignIn();
      }
    },
    toSiteManage() {
      let user = sessionStorage.getItem("user");
      if (user) {
        this.$router.push({ name: "site-manage" });
      } else {
        this.notifyToSignIn();
      }
    },
    handleSelectGroup(group) {
      this.groupId = group.groupId;
      this.currentGroupName = group.groupName;
      localStorage.setItem(
        "lastSelectedGroup",
        JSON.stringify({
          groupId: group.groupId,
          groupName: group.groupName
        })
      );
      this.$router.push({
        name: "site-group",
        params: { groupId: group.groupId }
      });
    },
    getGroupList() {
      this.$axios
        .get("/group/getGroupList")
        .then(response => {
          this.groupList = response.data.sort(function(group1, group2) {
            return group1.groupName.localeCompare(group2.groupName, "zh-CN");
          });
          store.dispatch("setGroupList", this.groupList);
        })
        .catch(err => {
          this.$message.error("Network error");
        });
    }
  },
  watch: {
    keywords() {
      store.dispatch("setKeywords", this.keywords);
    }
  },
  mounted() {
    let user = sessionStorage.getItem("user");
    if (user) {
      user = JSON.parse(user);
      this.userInfo.userName = user.userName;
      this.userInfo.headPic = user.headPic;
      this.isLogin = true;
    }
    let currentPath = this.$route.path;
    if (currentPath.indexOf("manage") === -1) {
      let lastSelectedGroup = JSON.parse(
        localStorage.getItem("lastSelectedGroup")
      );
      if (lastSelectedGroup) {
        this.groupId = lastSelectedGroup.groupId;
        if (this.groupId) {
          this.currentGroupName = lastSelectedGroup.groupName;
          localStorage.setItem("activeIndex", this.groupId);
          this.$router.push({
            name: "site-group",
            params: { groupId: this.groupId }
          });
        }
      } else {
        this.$router.push({ name: "main-page" });
      }
    }
    this.getGroupList();
    this.activeIndex = localStorage.getItem("activeIndex");
  }
};
</script>

<style>
.el-header {
  background-color: #fff;
  color: #007fff;
  text-align: center;
  line-height: 60px;
  display: flex;
  justify-content: space-between;
}

/*页面下拉，header固定不动*/
.header-fixed {
  position: fixed;
  top: 0;
  left: 0;
  height: 60px;
  width: 100%;
  border-bottom: 1px solid #dcdfe6;
  text-align: center;
  z-index: 100;
  font-size: 16px;
  -webkit-box-shadow: 0 0 5px rgba(102, 102, 102, 0.05);
  -moz-box-shadow: 0 0 5px rgba(102, 102, 102, 0.05);
  box-shadow: 0 0 5px rgba(102, 102, 102, 0.05);
  overflow: hidden;
}

.el-menu {
  height: 100%;
  line-height: 60px;
  padding: 0;
  margin: 0;
}

.nav-item:hover {
  color: #409eff !important;
}

.logo {
  color: #007fff !important;
  font-size: 24px;
  min-width: 155px;
  line-height: 60px;
  text-decoration: none;
  display: flex;
}

.logo-icon {
  margin-right: 5px;
  width: 28px;
}

.user-info {
  border-left: 1px solid #dcdfe6;
  padding-right: 0px;
}

.userinfo-inner {
  cursor: pointer;
  color: #000;
}

.head-pic {
  width: 40px;
  height: 40px;
  border-radius: 20px;
  margin: 10px 0px 10px 10px;
  float: right;
}

.el-main {
  background-color: #ffffff;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-container:nth-child(5) .el-aside,
.el-container:nth-child(6) .el-aside {
  line-height: 260px;
}

.el-container:nth-child(7) .el-aside {
  line-height: 320px;
}

.el-col {
  margin-bottom: 20px;
}

.main-body {
  margin-top: 60px;
}

.login-wrapper {
  min-width: 320px;
  max-width: 320px;
}

.el-dialog__body {
  padding-bottom: 0;
}

.login-button {
  width: 100%;
}
.no-active {
  border-bottom: none !important;
}
</style> 
