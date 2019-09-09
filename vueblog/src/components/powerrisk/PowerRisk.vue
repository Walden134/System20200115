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

          <charts :title="chuliquxian"
                  :request="powerRequest"
                  style="width:1260px;height:350px"></charts>
        </div>
        <div class="bottom_right">
          <powerTable :title="outputTitle"
                      style="width:600px;height:330px"></powerTable>
        </div>
        <div class="bottom_right">
          <chuliTable :title="powerTitle"
                      style="width:600px;height:330px"></chuliTable>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import PowerTable from "@/components/powerrisk/PowerTable";
import ChuliTable from "@/components/powerrisk/ChuliTable";
import UploadExcel from "@/components/powerrisk/UploadExcel";
import Charts from "@/components/powerrisk/Charts";
import InputData from "@/components/powerrisk/InputData";
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
      chuliquxian: {
        title: "出力保证率曲线图"
      },
      outputTitle: {
        title: "发电量风险率",
        label1: "情景",
        label2: "相对典型枯水年风险率",
        label3: "相对设计多年平均风险率"
      },
      powerTitle: {
        title: "保证出力风险率",
        label1: "情景",
        label2: "176.0MW对应保证率",
        label3: "风险率"
      },
      outputRequest: "/article/dataStatistics",
      powerRequest: "/article/dataStatistics"
    };
  },
  components: {
    upload_excel: UploadExcel,
    InputData: InputData,
    charts: Charts,
    chart: ECharts,
    powerTable: PowerTable,
    chuliTable: ChuliTable
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
