<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <InputData></InputData>
        </div>
        <div class="runoff_top">
          <div style=" width: calc((100% - 290px));">
            <Charts style="width:100%;height:100%"></Charts>
          </div>
        </div>
        <div class="runoff_mid">
          <div style=" width: calc((100% - 300px)/2);">
            <lineCharts style="width:100%;height:100%"></lineCharts>
          </div>
          <div style=" width: calc((100% - 300px)/2);">
            <barCharts style="width:100%;height:100%"></barCharts>
          </div>
        </div>
        <div class="runoff_bottom">
          <div style=" width: calc((100% - 290px));">
            <AreaChart style="width:100%;height:100%"></AreaChart>
          </div>
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
    handleClick(tab, event) {}
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
.runoff_top > div {
  height: 380px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
.runoff_mid > div {
  height: 330px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
.runoff_bottom > div {
  height: 380px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
</style>
