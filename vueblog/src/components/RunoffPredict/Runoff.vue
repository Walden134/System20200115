<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane">
          <inputData></inputData>
        </div>
        <div class="runoff_content">
          <div class="one_chart">
            <pfCharts class="chart"></pfCharts>
          </div>
          <div class="two_chart">
            <lineCharts class="chart"></lineCharts>
          </div>
          <div class="two_chart">
            <barCharts class="chart"></barCharts>
          </div>
          <div class="one_chart">
            <areaChart class="chart"></areaChart>
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
  }
};
</script>
<style>
</style>
