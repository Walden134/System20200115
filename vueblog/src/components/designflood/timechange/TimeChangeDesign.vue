<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs type="card">
        <div class="pane">
          <inputData></inputData>
        </div>
        <div class="time_content">
          <div class="one_chart">
            <areaChart class="chart"></areaChart>
          </div>
          <div class="one_chart">
            <designCharts class="chart"></designCharts>
          </div>
          <div class="one_table" style="height:190px;">
            <eightTable class="table"></eightTable>
          </div>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import EightTable from "@/components/designflood/timechange/EightTable";
import DesignCharts from "@/components/designflood/timechange/DesignCharts";
import AreaChart from "@/components/designflood/timechange/AreaChart";
import InputData from "@/components/designflood/timechange/InputData";
export default {
  data() {
    return {
      activeName: "post",
      isAdmin: false
    };
  },
  components: {
    inputData: InputData,
    designCharts: DesignCharts,
    areaChart: AreaChart,
    eightTable: EightTable
  },
  methods: {
    handleClick(tab, event) {}
  }
};
</script>
<style>
</style>
