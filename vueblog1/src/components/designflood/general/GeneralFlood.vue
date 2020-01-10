<template>

  <el-container class="article_list">
    <el-main class="main">

      <collapsetransition>
        <div class="collapse-wrap"
             v-show="isActive">
          <el-tabs v-model="activeName"
                   @tab-click="handleClick"
                   type="card">

            <div class="pane">
              <inputData></inputData>
            </div>
            <div class="genFlood_content">
              <div class="one_chart">
                <pChart class="chart"></pChart>
              </div>
              <div class="two_table"
                   style="height:320px">
                <shiceTable class="table"></shiceTable>
              </div>
              <div class="two_table"
                   style="height:320px">
                <lilunTable class="table"></lilunTable>
              </div>
            </div>
          </el-tabs>
        </div>
      </collapsetransition>

    </el-main>
  </el-container>

</template>
<script>
import ShiceTable from "@/components/designflood/general/ShiceTable";
import LilunTable from "@/components/designflood/general/LilunTable";
import PChart from "@/components/designflood/general/PChart";
import InputData from "@/components/designflood/general/GenInputData";
import collapseTransition from "@/utils/shousuo";
export default {
  data() {
    return {
      activeName: "post",
      isAdmin: false,
      isActive: true
    };
  },
  components: {
    inputData: InputData,
    pChart: PChart,
    shiceTable: ShiceTable,
    lilunTable: LilunTable,
    collapsetransition: collapseTransition
  },
  methods: {
    handleClick() {}
  },
  mounted() {}
};
</script>
<style>
.box {
  height: 200px;
  width: 100%;
  background-color: black;
  transition: all 0.5s ease-in-out;
}
.boxHidden {
  transition: all 1s ease-in-out;
  height: 0;
  overflow: hidden;
}
</style>
