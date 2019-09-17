<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <InputData></InputData>
        </div>
        <div class="flood_top">
          <div style=" width: calc((100% - 300px)  / 2);">
            <shejicharts :title="shejiTitle" :request="outputRequest" style="width:100%;height:100%"></shejicharts>
          </div>
          <div style=" width: calc((100% - 300px)  / 2);">
            <badingcharts :title="badingTitle" :request="powerRequest" style="width:100%;height:100%"></badingcharts>
          </div>
        </div>
        <div class="flood_bottom">
          <div style=" width: calc((100% - 300px)  / 2);">
            <jiaohecharts :title="jiaoheTitle" :request="powerRequest" style="width:100%;height:100%"></jiaohecharts>
          </div>
          <div style=" width: calc((100% - 300px)  / 2);">
            <threeTable :title="huizongTitle" style="width:100%;height:100%"></threeTable>
          </div>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import ThreeTable from "@/components/floodrisk/ThreeTable";
import ShejiCharts from "@/components/floodrisk/ShejiCharts";
import JiaoheCharts from "@/components/floodrisk/JiaoheCharts";
import BadingCharts from "@/components/floodrisk/BadingCharts";
import InputData from "@/components/floodrisk/FloodInputData";
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
    InputData: InputData,
    shejicharts: ShejiCharts,
    jiaohecharts: JiaoheCharts,
    badingcharts: BadingCharts,
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
.flood_top > div {
  height: 380px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
.flood_bottom > div {
  height: 330px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
</style>
