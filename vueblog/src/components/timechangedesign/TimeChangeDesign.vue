<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="left" style=" width:230px">
          <InputData></InputData>
        </div>
        <div class="top_right">
          <areaChart :title="leftChart" :request="outputRequest" style="width:1260px;height:300px"></areaChart>

          <!-- <threeTable :title="outputTitle"
                      style="width:600px;height:330px"></threeTable> -->
        </div>
        <div class="bottom_right" align='center'>
          <designCharts :title="rightChart" :request="powerRequest" style="width:1260px;height:430px"></designCharts>

          <eightTable :title="powerTitle" style="width:1050px;height:330px"></eightTable>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import ThreeTable from "@/components/timechangedesign/ThreeTable";
import EightTable from "@/components/timechangedesign/EightTable";
import UploadExcel from "@/components/timechangedesign/UploadExcel";
import UncertainCharts from "@/components/timechangedesign/UncertainCharts";
import DesignCharts from "@/components/timechangedesign/DesignCharts";
import AreaChart from "@/components/timechangedesign/AreaChart";
import InputData from "@/components/timechangedesign/InputData";
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
      leftChart: {
        title: "年降水量及不确定性区间"
      },
      rightChart: {
        title: "设计洪水频率曲线"
      },
      outputTitle: {
        title: "最优模型选取",
        label1: "时变参数",
        label2: "GD",
        label3: "AIC",
        label4: "SBC"
      },
      powerTitle: {
        title: "设计洪水计算值",
        label1: "未来降水",
        label2: "均值",
        label3: "Cv",
        label4: "Cs",
        label5: "0.1%",
        label6: "0.2%",
        label7: "0.5%",
        label8: "1%",
        label9: "5%",
        label10: "10%"
      },
      outputRequest: "/article/dataStatistics",
      powerRequest: "/article/dataStatistics"
    };
  },
  components: {
    upload_excel: UploadExcel,
    InputData: InputData,
    uncertainCharts: UncertainCharts,
    designCharts: DesignCharts,
    areaChart: AreaChart,
    chart: ECharts,
    threeTable: ThreeTable,
    eightTable: EightTable
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    },
    getRequestData() {
      var _this = this;
      getRequest("/isAdmin").then(resp => {
        if (resp.status == 200) {
          _this.isAdmin = resp.data;
        }
      });
      getRequest("/article/dataStatistics").then(
        resp => {
          if (resp.status == 200) {
            _this.output = resp.data;
          } else {
            _this.$message({ type: "error", message: "数据加载失败!" });
          }
        },
        resp => {
          _this.$message({ type: "error", message: "数据加载失败!" });
        }
      );
      getRequest("/article/dataStatistics").then(
        resp => {
          if (resp.status == 200) {
            _this.power = resp.data;
          } else {
            _this.$message({ type: "error", message: "数据加载失败!" });
          }
        },
        resp => {
          _this.$message({ type: "error", message: "数据加载失败!" });
        }
      );
    }
  },
  mounted() {
    this.getRequestData();
  }
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
