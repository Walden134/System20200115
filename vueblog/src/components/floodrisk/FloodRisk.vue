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

        <div class="top_right">
          <shejicharts :title="shejiTitle"
                       :request="outputRequest"
                       style="width:600px;height:350px"></shejicharts>
          <badingcharts :title="badingTitle"
                        :request="powerRequest"
                        style="width:600px;height:350px"></badingcharts>
        </div>

        <div class="bottom_right">
          <jiaohecharts :title="jiaoheTitle"
                        :request="powerRequest"
                        style="width:600px;height:350px"></jiaohecharts>

          <threeTable :title="huizongTitle"
                      style="width:600px;height:350px"></threeTable>
        </div>

      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import ThreeTable from "@/components/floodrisk/ThreeTable";
import UploadExcel from "@/components/floodrisk/UploadExcel";
import ShejiCharts from "@/components/floodrisk/ShejiCharts";
import JiaoheCharts from "@/components/floodrisk/JiaoheCharts";
import BadingCharts from "@/components/floodrisk/BadingCharts";
import InputData from "@/components/floodrisk/InputData";
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
      shejiTitle: {
        title: "超设计洪水风险率"
      },
      jiaoheTitle: {
        title: "超校核洪水风险率"
      },
      badingTitle: {
        title: "超坝顶高程洪水风险率"
      },
      huizongTitle: {
        title: "防洪风险统计表",
        label1: "序号",
        label2: "年份",
        label3: "风险率"
      },
      outputRequest: "/article/dataStatistics",
      powerRequest: "/article/dataStatistics"
    };
  },
  components: {
    upload_excel: UploadExcel,
    InputData: InputData,
    shejicharts: ShejiCharts,
    jiaohecharts: JiaoheCharts,
    badingcharts: BadingCharts,
    chart: ECharts,
    threeTable: ThreeTable
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
