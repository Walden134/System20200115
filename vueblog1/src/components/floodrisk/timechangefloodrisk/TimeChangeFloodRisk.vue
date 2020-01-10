<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs type="card">
        <div class="pane">
          <div style="width:100%">
            <el-form :inline="true" class="demo-form-inline" label-width="140px" :label-position="labelPosition">
              <div class="mark">数据导入</div>
              <div class="input">
                <label class="floodrisk_label" for="">典型洪水过程线</label>
                <uploadExcel @func="getTypicalFloods"> </uploadExcel>
                <label class="floodrisk_label" for="">水位库容关系&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <uploadExcel @func="getLevelCapacityCurve"> </uploadExcel>
                <label class="floodrisk_label" for="">水位泄流能力&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <uploadExcel @func="getLeveldownOutflowCurve"> </uploadExcel>
              </div>

              <div class="mark">参数设置</div>
              <div class="params">
                <el-form-item label="设计洪水位(m)" style="margin-bottom: 1px; ">
                  <el-input style=" width:100px" v-model="floodRisk.levelDesign"></el-input>
                </el-form-item>
                <el-form-item label="校核洪水位(m)" style="margin-bottom: 1px; ">
                  <el-input style=" width:100px" v-model="floodRisk.levelCheck"></el-input>
                </el-form-item>
                <el-form-item label="坝顶高程(m)" style="margin-bottom: 1px; ">
                  <el-input style=" width:100px" v-model="floodRisk.levelDam"></el-input>
                </el-form-item>
              </div>
              <el-form-item style="margin-top: 10px;margin-bottom: 10px">
                <el-button type="primary" @click.native.prevent="submitClick">开始计算</el-button>
                <el-button type="warning" @click.native.prevent="resetData">重置</el-button>
              </el-form-item>

            </el-form>
          </div>
        </div>
        <div class="flood_content">
          <div class="three_chart">
            <chart :options="designData" style="height:100%;width:100%" id="chart"></chart>
          </div>
          <div class="three_chart">
            <chart :options="checkData" style="height:100%;width:100%" id="chart"></chart>
          </div>
          <div class="three_chart">
            <chart :options="damData" style="height:100%;width:100%" id="chart"></chart>
          </div>
          <div class="one_table" style="height:180px">
            <el-row class="designfloodtable">
              <el-col :span="24" class="mtable">
                <div class="table_name">设计水位表
                  <div class="toexcel">
                    <el-button @click="exportExcel" type="primary" plain class="button"
                      style="width:70px;position:absolute;top:0;right:10px">导出</el-button>
                  </div>
                </div>
                <el-table :data="tableData" id="riskTable" border cell-class-name="dyg"
                  style="width:calc(100% - 5px);border:1px solid #EBEEF5;background-color:#f0f8ff"
                  :row-style="{height:'20px'}" :cell-style="{padding:'0px'}" height="150" max-height="150">
                  <el-table-column prop="number" label="水位"> </el-table-column>
                  <el-table-column label="RCP2.6">
                    <el-table-column label="2030S" prop="rcp263"></el-table-column>
                    <el-table-column label="2060S" prop="rcp266"></el-table-column>
                    <el-table-column label="2090S" prop="rcp269"></el-table-column>
                  </el-table-column>
                  <el-table-column label="RCP4.5">
                    <el-table-column label="2030S" prop="rcp453"></el-table-column>
                    <el-table-column label="2060S" prop="rcp456"></el-table-column>
                    <el-table-column label="2090S" prop="rcp459"></el-table-column>
                  </el-table-column>
                  <el-table-column label="RCP8.5">
                    <el-table-column label="2030S" prop="rcp853"></el-table-column>
                    <el-table-column label="2060S" prop="rcp856"></el-table-column>
                    <el-table-column label="2090S" prop="rcp859"></el-table-column>
                  </el-table-column>
                </el-table>
                <!-- <div style="background-color:#f0f8ff;height:5px"></div> -->
              </el-col>
            </el-row>
          </div>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import FloodRiskChart from "@/components/floodrisk/timechangefloodrisk/FloodRiskChart";
import LevalChart from "@/components/floodrisk/timechangefloodrisk/LevalChart";
import RiskTable from "@/components/floodrisk/timechangefloodrisk/RiskTable";
import InputData from "@/components/floodrisk/timechangefloodrisk/FloodInputData";
import storageUtils from "@/utils/storageUtils";
import UploadExcel from "@/components/UploadExcel";
import ECharts from "vue-echarts/components/ECharts.vue";
import "echarts/lib/chart/line";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import "echarts/lib/chart/bar";
import echarts from "echarts";
import FileSaver from "file-saver";
import XLSX from "xlsx";
import { getRequest } from "@/utils/api";
export default {
  data() {
    return {
      activeName: "post",
      isAdmin: false,
      labelPosition: "left",
      designData: {
        title: {
          text: "超设计洪水风险率",
          left: "center"
        },
        toolbox: {
          show: true,
          feature: {
            // magicType: {
            //   type: ["bar", "line"]
            // },
            // restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          name: "时间(年)",
          nameGap: 25,
          nameLocation: "center",
          type: "category",
          data: ["2030S", "2060S", "2090S"]
        },
        yAxis: {
          name: "风险率",
          nameLocation: "center",
          nameGap: 40,
          axisTick: {
            inside: true
          },
          splitLine: {
            show: false
          }
        },
        legend: {
          y: "bottom",
          data: [
            { name: "RCP2.6", icon: "bar" },
            { name: "RCP4.5", icon: "bar" },
            { name: "RCP8.5", icon: "bar" }
          ]
        },
        grid: {
          left: "60px",
          right: "20px",
          top: "40px"
        },
        series: [
          {
            name: "RCP2.6",
            // data: [0.5, 0.84, 0.86],
            data: [],
            type: "bar"
          },
          {
            name: "RCP4.5",
            // data: [0.6, 0.86, 0.97],
            data: [],
            type: "bar"
          },
          {
            name: "RCP8.5",
            // data: [0.42, 0.75, 0.97],
            data: [],
            type: "bar"
          }
        ]
      },
      checkData: {
        title: {
          text: "超校核洪水风险率",
          left: "center"
        },
        toolbox: {
          show: true,
          feature: {
            // magicType: {
            //   type: ["bar", "line"]
            // },
            // restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          name: "时间(年)",
          nameGap: 25,
          nameLocation: "center",
          type: "category",
          data: ["2030S", "2060S", "2090S"]
        },
        yAxis: {
          name: "风险率",
          nameLocation: "center",
          nameGap: 40,
          axisTick: {
            inside: true
          },
          splitLine: {
            show: false
          }
        },
        legend: {
          y: "bottom",
          data: [
            { name: "RCP2.6", icon: "bar" },
            { name: "RCP4.5", icon: "bar" },
            { name: "RCP8.5", icon: "bar" }
          ]
        },
        grid: {
          left: "60px",
          right: "20px",
          top: "40px"
        },
        series: [
          {
            name: "RCP2.6",
            // data: [0.24, 0.6, 0.58],
            data: [],
            type: "bar"
          },
          {
            name: "RCP4.5",
            // data: [0.3, 0.59, 0.84],
            data: [],
            type: "bar"
          },
          {
            name: "RCP8.5",
            // data: [0.07, 0.42, 0.89],
            data: [],
            type: "bar"
          }
        ]
      },
      damData: {
        title: {
          text: "超坝顶高程风险率",
          left: "center"
        },
        toolbox: {
          show: true,
          feature: {
            // magicType: {
            //   type: ["bar", "line"]
            // },
            // restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          name: "时间(年)",
          nameGap: 25,
          nameLocation: "center",
          type: "category",
          data: ["2030S", "2060S", "2090S"]
        },
        yAxis: {
          name: "风险率",
          nameLocation: "center",
          nameGap: 40,
          axisTick: {
            inside: true
          },
          splitLine: {
            show: false
          }
        },
        legend: {
          y: "bottom",
          data: [
            { name: "RCP2.6", icon: "bar" },
            { name: "RCP4.5", icon: "bar" },
            { name: "RCP8.5", icon: "bar" }
          ]
        },
        grid: {
          left: "60px",
          right: "20px",
          top: "40px"
        },
        series: [
          {
            name: "RCP2.6",
            // data: [0.18, 0.39, 0.46],
            data: [],
            type: "bar"
          },
          {
            name: "RCP4.5",
            // data: [0.14, 0.45, 0.65],
            data: [],
            type: "bar"
          },
          {
            name: "RCP8.5",
            // data: [0.05, 0.29, 0.84],
            data: [],
            type: "bar"
          }
        ]
      },
      floodRisk: {
        typicalFloods: [],
        levelCapacityCurve: { curveData: [] },
        leveldownOutflowCurve: { curveData: [] },
        levelDesign: "2096.27",
        levelCheck: "2099.91",
        levelDam: "2102",
        pattern: "26",
        design: "0",
        check: 0,
        dam: 0
      },
      tableData: [],
      respdata: {}
    };
  },
  components: {
    riskChart: FloodRiskChart,
    inputData: InputData,
    levalChart: LevalChart,
    riskTable: RiskTable,
    uploadExcel: UploadExcel,
    chart: ECharts
  },
  methods: {
    resetData() {
      this.respdata = {};
      this.tableData = [];
      bus.$emit("respdata", {});
      this.setChartData({});
    },
    getTypicalFloods(data) {
      let i = 0;
      this.floodRisk.typicalFloods[0] = [];
      this.floodRisk.typicalFloods[1] = [];
      data.map(val => {
        this.floodRisk.typicalFloods[0][i] = val["典型洪水过程"];
        this.floodRisk.typicalFloods[1][i] = val["典型洪水"];
        i++;
      });
    },

    getLeveldownOutflowCurve(data) {
      let i = 0;
      data.map(val => {
        this.floodRisk.leveldownOutflowCurve.curveData[i] = [];
        this.floodRisk.leveldownOutflowCurve.curveData[i] = [];
        this.floodRisk.leveldownOutflowCurve.curveData[i][0] = val["水位"];
        this.floodRisk.leveldownOutflowCurve.curveData[i][1] = val["下泄流量"];
        i++;
      });
    },
    getLevelCapacityCurve(data) {
      let i = 0;
      data.map(val => {
        this.floodRisk.levelCapacityCurve.curveData[i] = [];
        this.floodRisk.levelCapacityCurve.curveData[i] = [];
        this.floodRisk.levelCapacityCurve.curveData[i][0] = val["水位"];
        this.floodRisk.levelCapacityCurve.curveData[i][1] = val["库容"];
        i++;
      });
    },
    submitClick() {
      var _this = this;
      if (
        _this.floodRisk.typicalFloods.length == 0 ||
        _this.floodRisk.levelCapacityCurve.curveData.length == 0 ||
        _this.floodRisk.leveldownOutflowCurve.curveData.length == 0 ||
        _this.floodRisk.levelDesign === "" ||
        _this.floodRisk.levelDam === "" ||
        _this.floodRisk.levelCheck === "" ||
        _this.floodRisk.pattern === ""
      ) {
        _this.$alert("请先导入数据或设置参数值!", "失败!");
        return;
      }
      getRequest(
        "/flood/calcRisk" + "?floodRisk=" + JSON.stringify(_this.floodRisk)
      ).then(
        resp => {
          if (resp.status == 200) {
            console.log(resp.data);
            _this.respdata = resp.data;
            _this.setChartData(_this.respdata);
            bus.$emit("respdata", resp.data);
          } else {
            _this.$alert("计算失败!", "失败!");
          }
        },
        resp => {
          _this.$alert("请重新登陆⊙﹏⊙∥!", "失败!");
        }
      );
    },

    setChartData(respdata) {
      console.log("respdata", respdata);
      if (JSON.stringify(respdata) != "{}") {
        this.designData.series[0].data = respdata.rcp26[0]; //
        this.checkData.series[0].data = respdata.rcp26[1]; //
        this.damData.series[0].data = respdata.rcp26[2]; //

        this.designData.series[1].data = respdata.rcp45[0]; //
        this.checkData.series[1].data = respdata.rcp45[1]; //
        this.damData.series[1].data = respdata.rcp45[2]; //

        this.designData.series[2].data = respdata.rcp85[0]; //
        this.checkData.series[2].data = respdata.rcp85[1]; //
        this.damData.series[2].data = respdata.rcp85[2]; //
        this.tableData = [];
        let tmp = {};
        tmp.number = "设计水位";
        tmp.rcp263 = respdata.rcp261[0][0];
        tmp.rcp266 = respdata.rcp261[0][1];
        tmp.rcp269 = respdata.rcp261[0][2];

        tmp.rcp453 = respdata.rcp451[0][0];
        tmp.rcp456 = respdata.rcp451[0][1];
        tmp.rcp459 = respdata.rcp451[0][2];

        tmp.rcp853 = respdata.rcp851[0][0];
        tmp.rcp856 = respdata.rcp851[0][1];
        tmp.rcp859 = respdata.rcp851[0][2];
        this.tableData.push(tmp);
        tmp = {};
        tmp.number = "校核水位";
        tmp.rcp263 = respdata.rcp262[0][0];
        tmp.rcp266 = respdata.rcp262[0][1];
        tmp.rcp269 = respdata.rcp262[0][2];
        tmp.rcp453 = respdata.rcp452[0][0];
        tmp.rcp456 = respdata.rcp452[0][1];
        tmp.rcp459 = respdata.rcp452[0][2];
        tmp.rcp853 = respdata.rcp852[0][0];
        tmp.rcp856 = respdata.rcp852[0][1];
        tmp.rcp859 = respdata.rcp852[0][2];
        this.tableData.push(tmp);
      } else {
        for (let i = 0; i < 3; i++) {
          this.designData.series[i].data = []; //
          this.checkData.series[i].data = []; //
          this.damData.series[i].data = []; //
        }
      }
    },
    inintChartData() {
      let respdata = storageUtils.readRespdata();
      this.setChartData(respdata);
    },
    exportExcel() {
      /* generate workbook object from table */
      let wb = XLSX.utils.table_to_book(document.querySelector("#lilunTable"));
      /* get binary string as output */
      let wbout = XLSX.write(wb, {
        bookType: "xlsx",
        bookSST: true,
        type: "array"
      });
      try {
        FileSaver.saveAs(
          new Blob([wbout], { type: "application/octet-stream" }),
          "设计洪水计算值.xlsx"
        );
      } catch (e) {
        if (typeof console !== "undefined") console.log(e, wbout);
      }
      return wbout;
    }
  },
  mounted() {
    this.inintChartData();
  },
  watch: {
    respdata: {
      handler: storageUtils.saveRespdata,
      deep: true
    }
  }
};
</script>
<style >
.mark {
  font: 19px "Microsoft YaHei";
  position: relative;
  top: 10px;
  right: -13px;
  width: 100px;
  z-index: 10;
  border-radius: 5px;
  background-color: #3366ff;
  color: white;
}

.floodrisk_label {
  text-align: right;
  vertical-align: middle;
  float: left;
  font-size: 14px;
  color: #606266;
  line-height: 40px;
  padding: 0 12px 0 0;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
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
