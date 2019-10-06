<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <InputData></InputData>
        </div>
        <div class="time_top">
          <div style=" width: calc((100% - 290px));">
            <areaChart style="width:100%;height:100%"></areaChart>
          </div>
        </div>
        <div class="time_bottom" align='center'>
          <div style=" width: calc((100% - 300px)*2 /3 + 80px);">
            <designCharts style="width:100%;height:100%"></designCharts>
          </div>
          <div style=" width: calc((100% - 300px) /3 - 80px);">
            <eightTable style="width:100%;height:100%"></eightTable>
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
      isAdmin: false
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
    }
  },
  mounted() {}
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
