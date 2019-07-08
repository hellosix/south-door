<template>
  <div>
    <div class="create-wrapper">
      <el-button
        icon="el-icon-plus"
        @click="createSite()"
        title="Create group"
        size="small"
        class="right"
      >CREAT</el-button>
    </div>
    <el-table
      :data="siteList.filter(site => !search || site.siteName.toLowerCase().includes(search.toLowerCase()) || site.groupName.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%"
      :default-sort="{prop: 'siteName', order: 'descending'}"
      stripe
    >
      <el-table-column label="Group" prop="groupName" sortable></el-table-column>
      <el-table-column label="Site Name" prop="siteName" sortable></el-table-column>
      <el-table-column label="Address" prop="address" sortable></el-table-column>
      <el-table-column label="Proxy Address" prop="proxyAddress"></el-table-column>
      <el-table-column label="Proxy Port" prop="proxyPort" sortable width="150px"></el-table-column>
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

    <el-dialog
      title="Delete Site"
      :visible.sync="showDeleteDialog"
      width="25%"
      :center="true"
      custom-class="delete-site-wrapper"
    >
      <span>
        Confirm to delete
        <b>{{ deletingSite.siteName }}</b>
      </span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="showDeleteDialog = false">Cancel</el-button>
        <el-button type="danger" @click="confirmDeleteSite()">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script >
import { store } from "../vuex/store.js";
import { isEmpty, isNotEmpty } from "../utils/validate.js";
export default {
  data() {
    return {
      siteList: [],
      search: "",
      isShowDeleteDialog: false,
      showDeleteDialog: false,
      deletingSite: {}
    };
  },
  methods: {
    clickEdit(index, row) {
      this.$router.push({ name: "site-edit", params: { siteId: row.siteId } });
    },
    clickDelete(index, row) {
      this.deletingSite = row;
      this.showDeleteDialog = true;
    },
    confirmDeleteSite() {
      let deletingSite = this.deletingSite;
      if (isNotEmpty(deletingSite.siteId)) {
        this.$axios
          .post("/api/site/deleteSiteById", deletingSite.siteId)
          .then(response => {
            if (response.data.code == 0) {
              this.siteList.forEach(function(site, index, arr) {
                if (site.siteId == deletingSite.siteId) {
                  arr.splice(index, 1);
                }
              });
            }
          })
          .catch(err => {
            this.$message.error("Network error.");
          });
        this.showDeleteDialog = false;
      }
    },
    createSite() {
      this.$router.push({ name: "site-add" });
    }
  },
  mounted() {
    let user = sessionStorage.getItem("user");
    if (user) {
      this.$axios
        .get("/api/site/getAllSite")
        .then(response => {
          this.siteList = response.data.data;
        })
        .catch(err => {
          this.$message.error("Network error.");
        });
    } else {
      this.$router.push("/");
    }
  }
};
</script>

<style>
.delete-site-wrapper {
  min-width: 400px;
  max-width: 400px;
}

.create-wrapper {
  padding: 0 20px;
}

.right {
  float: right;
}
</style>
