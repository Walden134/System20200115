<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane">
          <inputData></inputData>
        </div>
        <div class="power_content">
          <div class="two_chart">
            <powerChart class="chart"></powerChart>
          </div>
          <div class="two_chart">
            <powerONEChart class="chart"></powerONEChart>
          </div>
          <div class="two_chart">
            <outputChart class="chart"></outputChart>
          </div>
          <div class="two_chart">
            <outputRateChart class="chart"></outputRateChart>
          </div>
          <div class="one_table">
            <powerTable class="chart"></powerTable>
          </div>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import PowerTable from "@/components/power/PowerTable";
import PowerChart from "@/components/power/PowerChart";
import PowerONEChart from "@/components/power/PowerONEChart";
import OutputChart from "@/components/power/OutputChart";
import OutputRateChart from "@/components/power/OutputRateChart";
import InputData from "@/components/power/PowerInputData";

export default {
  data() {
    return {
      activeName: "post",
      isAdmin: false
    };
  },
  components: {
    inputData: InputData,
    powerChart: PowerChart,
    powerONEChart: PowerONEChart,
    outputChart: OutputChart,
    outputRateChart: OutputRateChart,
    powerTable: PowerTable
  },
  methods: {
    handleClick(tab, event) {}
  }
};
</script>
<style>
</style>
