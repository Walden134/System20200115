<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName"
               @tab-click="handleClick"
               type="card">
        <div class="left"
             style=" width:230px">
          <InputData></InputData>
        </div>
        <div class="top">

          <pChart :title="pinluquxian"
                  :request="powerRequest"
                  style="width:1260px;height:380px"></pChart>
        </div>
        <div class="bottom_right">
          <shiceTable :title="shiceTitle"
                      style="width:600px;height:330px"></shiceTable>
        </div>
        <div class="bottom_right">
          <lilunTable :title="lilunTitle"
                      style="width:640px;height:330px"></lilunTable>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import ShiceTable from "@/components/designflood/ShiceTable";
import LilunTable from "@/components/designflood/LilunTable";
import UploadExcel from "@/components/designflood/UploadExcel";
import PChart from "@/components/designflood/PChart";
import InputData from "@/components/designflood/InputData";
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
      pinluquxian: {
        title: "设计洪水频率曲线"
      },
      shiceTitle: {
        title: "实测点距信息",
        label1: "序号",
        label2: "经验频率（%）",
        label3: "实测流量(m³/s)"
      },
      lilunTitle: {
        title: "设计洪水计算值",
        label1: "序号",
        label2: "理论频率（%）",
        label3: "理论流量(m³/s)"
      },
      outputRequest: "/article/dataStatistics",
      powerRequest: "/article/dataStatistics"
    };
  },
  components: {
    upload_excel: UploadExcel,
    InputData: InputData,
    pChart: PChart,
    chart: ECharts,
    shiceTable: ShiceTable,
    lilunTable: LilunTable
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
.top {
  float: left;
  margin: 30px 30px 0px 30px;
}
.bottom_right {
  float: left;
  margin: 30px 30px 0px 30px;
}
.bottom_left {
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
