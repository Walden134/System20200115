<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs type="card">
        <div class="pane">
          <inputData></inputData>
        </div>
        <div class="flood_content">
          <div class="two_chart">
            <riskChart class="chart" :title_text="designtext" :data="designData"></riskChart>
          </div>
          <div class="two_chart">
            <riskChart class="chart" :title_text="checktext" :data="checkData"></riskChart>
          </div>
          <div class="two_chart">
            <riskChart class="chart" :title_text="damtext" :data="damData"></riskChart>
          </div>
          <div class="two_chart">
            <levalChart class="table"></levalChart>
          </div>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import FloodRiskChart from "@/components/floodrisk/FloodRiskChart";
import LevalChart from "@/components/floodrisk/LevalChart";
import InputData from "@/components/floodrisk/FloodInputData";
import storageUtils from "@/utils/storageUtils";
export default {
  data() {
    return {
      activeName: "post",
      isAdmin: false,
      designtext: "超设计洪水风险率",
      checktext: "超校核洪水风险率",
      damtext: "超坝顶洪水风险率",
      designData: [],
      checkData: [],
      damData: []
    };
  },
  components: {
    riskChart: FloodRiskChart,
    inputData: InputData,
    levalChart: LevalChart
  },
  methods: {
    handleClick(tab, event) {}
  },
  methods: {
    setChartData() {
      bus.$on("riskRes", data => {
        this.designData = data[0];
        this.checkData = data[1];
        this.damData = data[2];
      });
    },
    inintChartData() {
      let data2 = storageUtils.readRiskRes();
      if (data2.length > 0) {
        this.designData = data2[0];
        this.checkData = data2[1];
        this.damData = data2[2];
      }
    }
  },
  mounted() {
    this.inintChartData();
  },
  created() {
    this.setChartData();
  },
  beforeDestroy() {
    bus.$off("riskRes");
  }
};
</script>
<style>
</style>
