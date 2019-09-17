<template>
  <el-container class="power">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <inputData @situations="getSituations" @patterns="getPatterns"></inputData>
          <!-- <inputData></inputData> -->
        </div>
        <div class="power_top">
          <div style=" width: calc((100% - 310px)  / 3);">
            <powerChart :request="powerRequest" :situations="situations" :patterns="patterns"
              style="width:100%;height:100%"></powerChart>
          </div>
          <div style=" width: calc((100% - 310px)  / 3);">
            <outputChart :request="outputRequest" :situations="situations" :patterns="patterns"
              style="width:100%;height:100%"></outputChart>
          </div>
          <div style=" width: calc((100% - 310px)  / 3);">
            <powerRiskChart :request="outputRequest" :situations="situations" :patterns="patterns"
              style="width:100%;height:100%"></powerRiskChart>
          </div>
        </div>
        <div class="power_bottom">
          <!-- <div style=" width: calc((100% - 300px) *2 / 3);"> -->
          <div style=" width: calc(100% - 290px) ;">
            <powerTable :title="powerTitle" style="width:100%;height:100%"></powerTable>
          </div>
          <!-- <div style=" width: calc((100% - 300px)  / 3);">
            <outputTable :title="outputTitle" style="width:100%;height:100%"></outputTable>
          </div> -->

        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import OutputTable from "@/components/power/OutputTable";
import PowerTable from "@/components/power/PowerTable";
import PowerChart from "@/components/power/PowerChart";
import OutputChart from "@/components/power/OutputChart";
import PowerRiskChart from "@/components/power/PowerRiskChart";
import InputData from "@/components/power/PowerInputData";
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
    powerChart: PowerChart,
    outputChart: OutputChart,
    powerRiskChart: PowerRiskChart,
    powerTable: PowerTable,
    outputTable: OutputTable
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    getSituations(data) {
      this.situations = data;
    },
    getPatterns(data) {
      this.patterns = data;
    }
  },
  mounted() {}
};
</script>
<style>
.power_top > div {
  /* width: 661px; */
  height: 380px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
.power_bottom > div {
  /* width: 661px; */
  /* width: calc((100% - 300px) / 2); */
  height: 330px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
</style>
