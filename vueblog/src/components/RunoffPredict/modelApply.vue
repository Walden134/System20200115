<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <!-- <el-tab-pane label="输入降尺度气温、降水" name="all">

        </el-tab-pane> -->
        <!-- <el-tab-pane label="输出未来径流" name="post"> -->
        <div id="aside" style=" width:230px">
          <InputData></InputData>
        </div>

        <div id="top" style="height:450px;width:1300px;">
          <!-- <Charts></Charts> -->
        </div>
        <div id="left-aside" style="height:345px;width:650px;">
          <lineCharts></lineCharts>
        </div>
        <div id="left-aside" style="height:345px;width:650px;">
          <barCharts></barCharts>
        </div>
        <div id="bottom">
          <!-- <AreaChart style="height:450px;width:1300px"></AreaChart> -->
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import Charts from "@/components/RunoffPredict/Charts";
import lineCharts from "@/components/RunoffPredict/lineCharts";
import barCharts from "@/components/RunoffPredict/barCharts";
import InputData from "@/components/RunoffPredict/InputData";
import AreaChart from "@/components/RunoffPredict/AreaChart";
import { postRequest } from "../../utils/api";
import { putRequest } from "../../utils/api";
import { deleteRequest } from "../../utils/api";
import { getRequest } from "../../utils/api";
export default {
  mounted: function() {
    var _this = this;
    getRequest("/isAdmin").then(resp => {
      if (resp.status == 200) {
        _this.isAdmin = resp.data;
      }
    });
  },
  data() {
    return {
      activeName: "post",
      isAdmin: false
    };
  },
  methods: {
    handleClick(tab, event) {
      //        console.log(tab, event);
    }
  },
  components: {
    Charts: Charts,
    lineCharts: lineCharts,
    barCharts: barCharts,
    InputData: InputData,
    AreaChart: AreaChart
  }
};
</script>
<style>
.article_list > .header {
  background-color: #ececec;
  margin-top: 10px;
  padding-left: 5px;
  display: flex;
  justify-content: flex-start;
}

.article_list > .main {
  /*justify-content: flex-start;*/
  display: flex;
  flex-direction: column;
  padding-left: 0px;
  background-color: #fff;
  padding-top: 0px;
  margin-top: 8px;
}
#aside {
  float: right;
  margin-top: 1px;
  margin-right: 50px;
}
#top {
  float: left;
  margin-top: 1px;
}
#left-aside {
  float: left;
  margin-top: 20px;
}
#bottom {
  float: left;
  margin-top: 20px;
}
</style>
