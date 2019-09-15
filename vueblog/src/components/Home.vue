<template>
  <el-container class="home_container">
    <el-header style="height: 70px;">
      <img src="../assets/head_img.jpg">
    </el-header>
    <el-container>
      <el-aside width="200px">
        <el-menu default-active="0" class="el-menu-vertical-demo" style="background-color: #ECECEC" router>
          <template v-for="(item,index) in this.$router.options.routes" v-if="!item.hidden">
            <el-submenu :index="index+''" v-if="item.children.length>1" :key="index">
              <template slot="title">
                <i :class="item.iconCls"></i>
                <span>{{item.name}}</span>
              </template>
              <el-menu-item v-for="child in item.children" v-if="!child.hidden" :index="child.path" :key="child.path">
                {{child.name}}</el-menu-item>
            </el-submenu>
            <template v-else>
              <el-menu-item :index="item.children[0].path">
                <i :class="item.children[0].iconCls"></i>
                <span slot="title">{{item.children[0].name}}</span>
              </el-menu-item>
            </template>
          </template>
        </el-menu>
      </el-aside>

      <el-container>
        <el-main>
          <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item v-text="this.$router.currentRoute.name"></el-breadcrumb-item>
          </el-breadcrumb>
          <keep-alive>
            <router-view v-if="this.$route.meta.keepAlive"></router-view>
          </keep-alive>
          <router-view v-if="!this.$route.meta.keepAlive"></router-view>
        </el-main>
      </el-container>
    </el-container>
  </el-container>
</template>
<script>
import { getRequest } from "../utils/api";
export default {
  methods: {
    handleCommand(command) {
      var _this = this;
      if (command == "logout") {
        this.$confirm("注销登录吗?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then(
          function() {
            getRequest("/logout");
            _this.currentUserName = "游客";
            _this.$router.replace({ path: "/" });
          },
          function() {
            //取消
          }
        );
      }
    }
  },
  mounted: function() {},
  data() {
    return {
      currentUserName: ""
    };
  }
};
</script>
<style>
/* .nav_tab {
  top: 130px;
  position: absolute;
}
.el-tabs--border-card > .el-tabs__header .el-tabs__item {
  font-family: "黑体 Bold", "黑体 Regular", "黑体";
  font-weight: 700;
  font-style: normal;
  font-size: 16px;
  color: #ffffff;
}
.el-tabs--border-card {
  background: #fff;
  border: 0px solid rgba(74, 136, 220, 0.996078431372549);
  -webkit-box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12),
    0 0 6px 0 rgba(0, 0, 0, 0.04);
  box-shadow: 0 2px 4px 0 rgba(0, 0, 0, 0.12), 0 0 6px 0 rgba(0, 0, 0, 0.04);
}
.el-tabs--border-card > .el-tabs__header {
  background-color: rgba(74, 136, 220, 0.996078431372549);
  margin: 0;
}
.el-tabs--border-card > .el-tabs__content {
  padding: 0px;
} */
.home_container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
}

.el-header {
  background-color: rgba(74, 136, 220, 0.996078431372549);
  color: #333;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.el-aside {
  background-color: #ececec;
}

.el-main {
  background-color: #fff;
  color: #000;
  text-align: center;
}

.home_title {
  color: #fff;
  font-size: 22px;
  display: inline;
}

.home_userinfo {
  color: #fff;
  cursor: pointer;
}

.home_userinfoContainer {
  position: absolute;
  right: 10px;
  top: 142px;
}
</style>
