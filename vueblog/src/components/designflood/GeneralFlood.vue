<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <InputData></InputData>
        </div>
        <div class="genFlood_top">
          <div style=" width: calc((100% - 290px));">
            <pChart :title="pinluquxian" :request="powerRequest" style="width:100%;height:100%"></pChart>
          </div>
        </div>
        <div class="genFlood_bottom">

          <div style=" width: calc((100% - 300px)  / 2)">
            <shiceTable :title="shiceTitle" style="width:100%;height:100%"></shiceTable>
          </div>
          <div style=" width: calc((100% - 300px)  / 2)">
            <lilunTable :title="lilunTitle" style="width:100%;height:100%"></lilunTable>
          </div>
        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import ShiceTable from "@/components/designflood/ShiceTable";
import LilunTable from "@/components/designflood/LilunTable";
import PChart from "@/components/designflood/PChart";
import InputData from "@/components/designflood/GenInputData";
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
    InputData: InputData,
    pChart: PChart,
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
.pane {
  float: right;
  margin-left: 10px;
}

.genFlood_top > div {
  /* width: 661px; */
  height: 380px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
.genFlood_bottom > div {
  /* width: 661px; */
  /* width: calc((100% - 300px) / 2); */
  height: 330px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
</style>
