<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <inputData></inputData>
        </div>
        <div class="flood_top">
          <div style=" width: calc((100% - 300px)  / 2);">
            <shejicharts style="width:100%;height:100%"></shejicharts>
          </div>
          <div style=" width: calc((100% - 300px)  / 2);">
            <jiaohecharts style="width:100%;height:100%"></jiaohecharts>
          </div>
        </div>
        <div class="flood_bottom">
          <div style=" width: calc((100% - 300px)  / 2);">
            <badingcharts style="width:100%;height:100%"></badingcharts>
          </div>
          <div style=" width: calc((100% - 300px)  / 2);">
            <threeTable style="width:100%;height:100%"></threeTable>
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
      isAdmin: false
    };
  },
  components: {
    inputData: InputData,
    shejicharts: ShejiCharts,
    jiaohecharts: JiaoheCharts,
    badingcharts: BadingCharts,
    threeTable: ThreeTable
  },
  methods: {
    handleClick(tab, event) {
      console.log(tab, event);
    }
  },
  mounted() {}
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
