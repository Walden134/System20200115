<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <inputData></inputData>
        </div>
        <div class="box">
          <div class="runoff_top">
            <div style=" width: calc((100% - 15px));">
              <pfCharts style="width:100%;height:100%"></pfCharts>
            </div>
          </div>
          <div class="runoff_mid">
            <div style=" width: calc((100% - 30px)/2);">
              <lineCharts style="width:100%;height:100%"></lineCharts>
            </div>
            <div style=" width: calc((100% - 30px)/2);">
              <barCharts style="width:100%;height:100%"></barCharts>
            </div>
          </div>
          <div class="runoff_bottom">
            <div style=" width: calc((100% - 15px));">
              <areaChart style="width:100%;height:100%"></areaChart>
            </div>
          </div>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import PFCharts from "@/components/RunoffPredict/PFCharts";
import LineCharts from "@/components/RunoffPredict/LineCharts";
import BarCharts from "@/components/RunoffPredict/BarCharts";
import InputData from "@/components/RunoffPredict/RunOffInputData";
import AreaChart from "@/components/RunoffPredict/AreaChart";
import { getRequest } from "../../utils/api";
export default {
  data() {
    return {
      activeName: "post",
      isAdmin: false
    };
  },

  components: {
    pfCharts: PFCharts,
    lineCharts: LineCharts,
    barCharts: BarCharts,
    inputData: InputData,
    areaChart: AreaChart
  },
  methods: {
    handleClick(tab, event) {}
  },
  mounted: function() {}
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
.box {
  height: 700px;
  width: calc((100% - 290px));
  overflow: auto;
  padding-left: 10px;
}

.box::-webkit-scrollbar {
  display: none;
}
</style>
