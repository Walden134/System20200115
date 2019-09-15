<template>
  <el-container class="power">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <!-- <inputData @situations="getSituations" @patterns="getPatterns"></inputData> -->
          <inputData></inputData>
        </div>
        <div class="top">
          <div>
            <charts :title="powerTitle" :request="powerRequest" :situations="situations" :patterns="patterns"
              style="width:100%;height:100%"></charts>
          </div>
          <div>
            <charts :title="outputTitle" :request="outputRequest" :situations="situations" :patterns="patterns"
              style="width:100%;height:100%"></charts>
          </div>
        </div>
        <div class="bottom">
          <div>
            <powerTable :title="powerTitle" style="width:100%;height:100%"></powerTable>
          </div>
          <div>
            <outputTable :title="outputTitle" style="width:100%;height:100%"></outputTable>
          </div>

        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import OutputTable from "@/components/power/OutputTable";
import PowerTable from "@/components/power/PowerTable";
import Charts from "@/components/power/Charts";
import InputData from "@/components/power/InputData";

import ECharts from "vue-echarts/components/ECharts.vue";
import "echarts/lib/chart/line";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/polar";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import "echarts/theme/dark";
import "echarts/lib/chart/bar";
import { postRequest } from "../../utils/api";
import { putRequest } from "../../utils/api";
import { deleteRequest } from "../../utils/api";
import { getRequest } from "../../utils/api";
export default {
  data() {
    return {
      activeName: "post",
      isAdmin: false,
      powerTitle: {
        title: "发电量",
        label1: "情景",
        label2: "发电量(亿KW·h)",
        label3: "增幅(%)"
      },
      outputTitle: {
        title: "保证出力",
        label1: "情景",
        label2: "95%保证出力(MW)",
        label3: "增幅(%)"
      },
      outputRequest: "/power/outputStatistics",
      powerRequest: "/power/powerStatistics",
      situations: ["GFDL", "CNRM", "CanESM", "MIROC", "BMA"],
      patterns: ["Base"]
    };
  },
  components: {
    inputData: InputData,
    charts: Charts,
    powerTable: PowerTable,
    outputTable: OutputTable
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
    // getSituations(data) {
    //   this.situations = data;
    // },
    // getPatterns(data) {
    //   this.patterns = data;
    // }
  },
  mounted() {}
};
</script>
<style>
.pane {
  float: right;
}

.top > div {
  width: calc((100% - 290px) / 2);
  /* width: 661px; */
  height: 430px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
.bottom > div {
  /* width: 661px; */
  width: calc((100% - 290px) / 2);
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}

.power > .header {
  background-color: #ececec;
  margin-top: 10px;
  padding-left: 5px;
  display: flex;
  justify-content: flex-start;
}
.el-tabs__header {
  margin: 0;
}
.power > .main {
  display: flex;
  flex-direction: column;
  padding-left: 0px;
  background-color: #fff;
  padding-top: 0px;
  margin-top: 8px;
}
</style>
