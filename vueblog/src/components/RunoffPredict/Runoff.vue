<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <inputData></inputData>
        </div>
        <div class="runoff_top">
          <div style=" width: calc((100% - 290px));">
            <charts style="width:100%;height:100%"></charts>
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
            <!-- <areaChart style="width:100%;height:100%"></areaChart> -->
          </div>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import Charts from "@/components/RunoffPredict/Charts";
import LineCharts from "@/components/RunoffPredict/LineCharts";
import BarCharts from "@/components/RunoffPredict/BarCharts";
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
    handleClick(tab, event) {}
  },
  components: {
    charts: Charts,
    lineCharts: LineCharts,
    barCharts: BarCharts,
    inputData: InputData,
    areaChart: AreaChart
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
