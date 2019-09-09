<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="left" style=" width:230px">
          <InputData @situations="getSituations" @patterns="getPatterns"></InputData>
        </div>
        <div class="top_right">
          <charts :title="outputTitle" :request="outputRequest" :situations="situations" :patterns="patterns"
            style="width:600px;height:350px"></charts>
          <!-- <chart ref="dschart1" :options="chartdata" style="width:600px;height:350px"></chart> -->
          <threeTable :title="outputTitle" style="width:600px;height:330px"></threeTable>
        </div>
        <div class="bottom_right">
          <charts :title="powerTitle" :request="powerRequest" :situations="situations" :patterns="patterns"
            style="width:600px;height:350px"></charts>
          <!-- <chart ref="dschart2" :options="chartdata" style="width:600px;height:350px"></chart> -->
          <threeTable :title="powerTitle" style="width:600px;height:330px"></threeTable>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import ThreeTable from "@/components/power/ThreeTable";
import UploadExcel from "@/components/power/UploadExcel";
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
      outputTitle: {
        title: "发电量",
        label1: "情景",
        label2: "发电量(亿KW·h)",
        label3: "增幅(%)"
      },
      powerTitle: {
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
    upload_excel: UploadExcel,
    InputData: InputData,
    charts: Charts,
    chart: ECharts,
    threeTable: ThreeTable
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
.left {
  float: right;
  margin: 30px 30px 5px 5px;
}
.top_right {
  float: left;
  margin: 30px 30px 0px 30px;
}
.bottom_right {
  float: left;
  margin: 30px 30px 0px 30px;
}

.article_list > .header {
  background-color: #ececec;
  margin-top: 10px;
  padding-left: 5px;
  display: flex;
  justify-content: flex-start;
}
.el-tabs__header {
  margin: 0;
}
.article_list > .main {
  /*justify-content: flex-start;*/
  display: flex;
  flex-direction: column;
  padding-left: 0px;
  background-color: #fff;
  padding-top: 0px;
  margin-top: 8px;
}
.form {
  border: 2px solid rgba(74, 136, 220, 0.996078431372549);
  padding: 8px;
}
.label {
  text-align: right;
  vertical-align: middle;
  float: left;
  margin-left: -128px;
  font-size: 14px;
  color: #606266;
  line-height: 40px;
  padding: 0 12px 0 0;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
.upload {
  /* float: left; */
  line-height: 40px;
  position: relative;
  font-size: 14px;
}
</style>
