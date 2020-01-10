<template>
  <el-container class="home_container">
    <el-header style="height: 130px;width:100%;padding: 0;">
      <div class="head-img"></div>
      <!-- <img id="u66_img" src="../assets/u66.png"> -->
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
          <div id="mapDiv" ref="mapDiv" v-show="!this.$router.currentRoute.name">
          </div>

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
  data() {
    return {
      currentUserName: ""
    };
  },
  components: {},
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
    },
    onLoad() {
      var map = new T.Map("mapDiv");
      map.centerAndZoom(new T.LngLat(100.5, 29.5), 7);
    }
  },
  mounted: function() {
    this.onLoad();
  }
};
</script>
<style>
.two_chart,
.one_chart,
.two_table,
.one_table {
  height: 380px;
  border: 1px solid rgba(74, 136, 220, 0.996078431372549);
  background-color: aliceblue;
  float: left;
  margin: 10px 5px 0px 5px;
}

.two_chart {
  width: calc((100% - 300px) / 2 - 2px);
  border-radius: 25px;
}
.two_table {
  width: calc((100% - 300px) / 2 - 2px);
}
.one_chart {
  width: calc(100% - 292px);
  border-radius: 25px;
}
.one_table {
  width: calc(100% - 292px);
}

.chart,
.table {
  width: 100%;
  height: 100%;
}
.pane {
  width: 270px;
}

::-webkit-scrollbar {
  width: 7px; /*滚动条宽度*/
  height: 7px; /*滚动条高度*/
  background-color: #f0f8ff;
}

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  background-color: rgba(221, 222, 224); /*滚动条的背景颜色*/
}

.home_container {
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
}
.el-header {
  padding: 0;
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
.pane {
  float: right;
  margin-left: 10px;
}

.article_list .el-tabs__header {
  margin: 0;
}
.article_list .main {
  /*justify-content: flex-start;*/
  display: flex;
  flex-direction: column;
  background-color: #fff;
  padding-left: 0px;
  padding-top: 0px;
  padding-bottom: 0px;
  margin-top: 8px;
}
.input > div {
  height: 40px;
}
.input,
.params,
.pattern {
  border: 2px solid rgba(74, 136, 220, 0.996078431372549);
  padding: 16px 8px 8px 8px;
}
.el-checkbox + .el-checkbox {
  margin: 0px;
}
.el-checkbox.is-bordered + .el-checkbox.is-bordered {
  margin-right: 10px;
}
.el-checkbox {
  width: 100px;
  margin: 10px 10px 2px 10px;
}
.head-img {
  height: 130px;
  background-image: url(../assets/logo.png);
  background-size: 100% 100%;
}

#u66_img {
  border-width: 0px;
  position: absolute;
  left: 79.375%;
  top: 63px;
  width: 46px;
  height: 21px;
}

#mapDiv {
  margin: 10px;
  width: calc(100% - 20px);
  height: calc(100% - 35px);
  /* background-image: url(../assets/head_img.jpg); */
  /* background-size: 100% 100%; */
}
</style>
