<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <inputData></inputData>
        </div>
        <div class="genFlood_top">
          <div style=" width: calc((100% - 290px));">
            <pChart style="width:100%;height:100%"></pChart>
          </div>
        </div>
        <div class="genFlood_bottom">
          <div style=" width: calc((100% - 300px)  / 2)">
            <shiceTable style="width:100%;height:100%"></shiceTable>
          </div>
          <div style=" width: calc((100% - 300px)  / 2)">
            <lilunTable style="width:100%;height:100%"></lilunTable>
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
      isAdmin: false
    };
  },
  components: {
    inputData: InputData,
    pChart: PChart,
    shiceTable: ShiceTable,
    lilunTable: LilunTable
  },
  methods: {
    handleClick() {}
  },
  mounted() {}
};
</script>
<style>
.genFlood_top > div {
  height: 380px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
.genFlood_bottom > div {
  height: 330px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
</style>
