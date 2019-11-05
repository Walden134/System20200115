<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs type="card">
        <div class="pane">
          <inputData></inputData>
        </div>
        <div class="flood_content">
          <div class="panel-group">
            <span>超设计洪水风险率</span>
            <div class="card-panel">{{design}}%</div>
            <span>超校核洪水风险率</span>
            <div class="card-panel">{{check}}%</div>
            <span>超坝顶洪水风险率</span>
            <div class="card-panel">{{dam}}%</div>
          </div>
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
      damData: [],
      design: 0,
      check: 0,
      dam: 0
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
        if (data.length == 0) {
          this.designData = [];
          this.checkData = [];
          this.damData = [];
          this.design = 0;
          this.check = 0;
          this.dam = 0;
          return;
        }
        this.designData = data[0];
        this.checkData = data[1];
        this.damData = data[2];
        let design = 0;
        let check = 0;
        let dam = 0;
        for (let i = 0; i < this.designData.length; i++) {
          design += parseFloat(this.designData[i]);
          check += parseFloat(this.checkData[i]);
          dam += parseFloat(this.damData[i]);
        }
        this.design = (design / 80).toFixed(2);
        this.check = (check / 80).toFixed(2);
        this.dam = (dam / 80).toFixed(2);
      });
    },
    inintChartData() {
      let data2 = storageUtils.readRiskRes();
      if (data2.length == 0) return;
      this.designData = data2[0];
      this.checkData = data2[1];
      this.damData = data2[2];
      for (let i = 0; i < this.designData.length; i++) {
        this.design += data2[0][i];
        this.check += data2[1][i];
        this.dam += data2[2][i];
      }
      this.design = (this.design / 80).toFixed(2);
      this.check = (this.check / 80).toFixed(2);
      this.dam = (this.dam / 80).toFixed(2);
    }
  },
  mounted() {
    this.inintChartData();
  },
  beforeDestroy() {
    bus.$off("riskRes");
  },
  created() {
    this.setChartData();
  }
};
</script>
<style >
.panel-group {
  float: left;
  display: flex;
  justify-content: space-around;
  align-items: center;
  margin-top: 18px;
  width: calc(100% - 292px);
}
.panel-group > span {
  font-size: 20px;
}
.card-panel-col {
  margin-bottom: 32px;
}

.card-panel {
  height: 50px;
  width: calc(100% / 7);
  cursor: pointer;
  font-size: 25px;
  float: left;
  line-height: 50px;
  color: white;
  background: #409eff;
  border-radius: 10px;
  box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.05);
  border-color: rgba(0, 0, 0, 0.05);
}
</style>
