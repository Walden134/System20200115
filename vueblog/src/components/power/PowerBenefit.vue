<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <div class="pane" style=" width:270px">
          <inputData></inputData>
        </div>
        <div class="power_top">
          <div style=" width: calc((100% - 310px)  / 3);">
            <powerChart style="width:100%;height:100%"></powerChart>
          </div>
          <div style=" width: calc((100% - 310px)  / 3);">
            <outputChart style="width:100%;height:100%"></outputChart>
          </div>
          <div style=" width: calc((100% - 310px)  / 3);">
            <outputRateChart style="width:100%;height:100%"></outputRateChart>
          </div>
        </div>
        <div class="power_bottom">
          <!-- <div style=" width: calc((100% - 300px) *2 / 3);"> -->
          <div style=" width: calc(100% - 290px) ;">
            <powerTable style="width:100%;height:100%"></powerTable>
          </div>
          <!-- <div style=" width: calc((100% - 300px)  / 3);">
            <outputTable :title="outputTitle" style="width:100%;height:100%"></outputTable>
          </div> -->

        </div>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import PowerTable from "@/components/power/PowerTable";
import PowerChart from "@/components/power/PowerChart";
import OutputChart from "@/components/power/OutputChart";
import OutputRateChart from "@/components/power/OutputRateChart";
import InputData from "@/components/power/PowerInputData";
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
    powerChart: PowerChart,
    outputChart: OutputChart,
    outputRateChart: OutputRateChart,
    powerTable: PowerTable
  },
  methods: {
    handleClick(tab, event) {}
  },
  mounted() {}
};
</script>
<style>
.power_top > div {
  /* width: 661px; */
  height: 380px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
.power_bottom > div {
  /* width: 661px; */
  /* width: calc((100% - 300px) / 2); */
  height: 330px;
  background-color: aliceblue;
  float: left;
  margin: 10px 5px;
}
</style>
