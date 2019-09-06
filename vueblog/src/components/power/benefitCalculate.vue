<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="left" style=" width:230px">
          <InputData></InputData>
        </div>
        <div class="top_right">
          <!-- <charts style="width:600px;height:350px" ></charts> -->
          <chart ref="dschart" :options="chartdata" style="width:600px;height:350px"></chart>
          <threeTable style="width:600px;height:330px"></threeTable>
        </div>
        <div class="bottom_right">
          <!-- <charts style="width:600px;height:350px"></charts> -->
          <chart ref="dschart" :options="chartdata" style="width:600px;height:350px"></chart>
          <threeTable style="width:600px;height:330px"></threeTable>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import ThreeTable from "@/components/ThreeTable";
import UploadExcel from "@/components/UploadExcel";
import Charts from "@/components/Charts";
import InputData from "@/components/InputData";
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
      chartdata: {
        title: {
          text: ""
        },
        toolbox: {
          show: true,

          feature: {
            magicType: {
              type: ["bar", "line"]
            },
            restore: {},
            dataView: { show: true },
            saveAsImage: {}
          }
        },
        xAxis: {
          data: []
        },
        yAxis: {},
        grid: {
          left: "5%", // 与容器左侧的距离
          right: "5%", // 与容器右侧的距离
          top: "10%", // 与容器顶部的距离
          bottom: "10%" // 与容器底部的距离
        },
        series: [
          {
            type: "bar",
            data: []
          }
        ],
        animationDuration: 3000
      }
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
    }
  },
  mounted: function() {
    var _this = this;
    getRequest("/isAdmin").then(resp => {
      if (resp.status == 200) {
        _this.isAdmin = resp.data;
      }
    });
    getRequest("/article/dataStatistics").then(
      resp => {
        if (resp.status == 200) {
          _this.$refs.dschart.options.xAxis.data = resp.data.categories;
          _this.$refs.dschart.options.series[0].data = resp.data.ds;
        } else {
          _this.$message({ type: "error", message: "数据加载失败!" });
        }
      },
      resp => {
        _this.$message({ type: "error", message: "数据加载失败!" });
      }
    );
  }
};
</script>
<style>
.left {
  float: left;
  margin: 5px 50px 5px 5px;
}
.top_right {
  float: left;
  margin: 5px 30px;
}
.bottom_right {
  float: left;
  margin: 5px 30px;
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
