<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane">
          <inputData></inputData>
        </div>
        <div class="genFlood_content">
          <div class="one_chart">
            <pChart class="chart"></pChart>
          </div>
          <div class="two_table">
            <shiceTable class="table"></shiceTable>
          </div>
          <div class="two_table">
            <lilunTable class="table"></lilunTable>
          </div>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import ShiceTable from "@/components/designflood/general/ShiceTable";
import LilunTable from "@/components/designflood/general/LilunTable";
import PChart from "@/components/designflood/general/PChart";
import InputData from "@/components/designflood/general/GenInputData";
export default {
  data() {
    return {
      activeName: "post",
      isAdmin: false
    };
  },
  components: {
    inputData: InputData,
    pChart: PChart,
    shiceTable: ShiceTable,
    lilunTable: LilunTable
  },
  methods: {
    handleClick() {}
  },
  mounted() {}
};
</script>
<style>
</style>
