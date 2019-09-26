<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <InputData></InputData>
        </div>
        <div class="time_top">
          <div style=" width: calc((100% - 290px));">
            <areaChart :title="leftChart" :request="outputRequest" style="width:100%;height:100%"></areaChart>
          </div>
        </div>
        <div class="time_bottom" align='center'>
          <div style=" width: calc((100% - 300px)*2 /3 + 80px);">
            <designCharts :title="rightChart" :request="powerRequest" style="width:100%;height:100%"></designCharts>
          </div>
          <div style=" width: calc((100% - 300px) /3 - 80px);">
            <eightTable :title="powerTitle" style="width:100%;height:100%"></eightTable>
          </div>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import EightTable from "@/components/timechangedesign/EightTable";
import DesignCharts from "@/components/timechangedesign/DesignCharts";
import AreaChart from "@/components/timechangedesign/AreaChart";
import InputData from "@/components/timechangedesign/InputData";
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
    InputData: InputData,
    designCharts: DesignCharts,
    areaChart: AreaChart,
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
.time_top > div {
  height: 380px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
.time_bottom > div {
  height: 330px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
</style>
