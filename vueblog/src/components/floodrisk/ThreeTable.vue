<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="mtable">
      <div class="table_name">{{title}}</div>
      <el-table :data="tableData" stripe fit height="300" max-height="300"
        style="width:calc(100% - 5px);height:300px;border:2px;" :row-style="{height:'20px'}"
        :cell-style="{padding:'0px'}">
        <el-table-column prop="year" :label="year">
        </el-table-column>
        <el-table-column prop="levelDesign" :label="levelDesign">
        </el-table-column>
        <el-table-column prop="levelCheck" :label="levelCheck">
        </el-table-column>
        <el-table-column prop="levelDam" :label="levelDam">
        </el-table-column>
      </el-table>
      <div style="background-color:#20a0ff;height:5px"></div>
    </el-col>
  </el-row>
</template>

<script>
import storageUtils from "../../utils/storageUtils";

export default {
  data() {
    return {
      title: "防洪风险统计表",
      year: "年份",
      levelDesign: "超设计洪水风险率(%)",
      levelCheck: "超校核洪水风险率(%)",
      levelDam: "超坝顶洪水风险率(%)",
      riskRes: [],
      tableData: []
    };
  },
  methods: {
    setTableData() {
      this.tableData = [];
      let start = 2021;
      if (this.riskRes.length > 0) {
        for (let j = 0; j < this.riskRes[0].length; j++) {
          let tmp = {};
          tmp.year = start + j;
          tmp.levelDesign = this.riskRes[0][j];
          tmp.levelCheck = this.riskRes[1][j];
          tmp.levelDam = this.riskRes[2][j];
          this.tableData.push(tmp);
        }
      }
    },
    inintChartData() {
      this.riskRes = storageUtils.readRiskRes();
    }
  },
  mounted() {
    this.inintChartData();
  },
  created() {
    bus.$on("riskRes", data => {
      this.riskRes = data;
    });
  },
  beforeDestroy() {
    bus.$off("riskRes");
  },
  computed: {},
  watch: {
    riskRes() {
      this.setTableData();
    }
  }
};
</script>


<style>
::-webkit-scrollbar {
  width: 7px; /*滚动条宽度*/
  height: 7px; /*滚动条高度*/
  background-color: white;
}

/*定义滑块 内阴影+圆角*/
::-webkit-scrollbar-thumb {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  background-color: rgba(221, 222, 224); /*滚动条的背景颜色*/
}

.el-table__header th,
.el-table__header tr {
  color: black;
  text-align: center;
  padding: 0;
  height: 20px;
}
.el-table--border,
.el-table--group {
  border: none;
}
.el-table__header-wrapper th:nth-last-of-type(2) {
  border-right: none;
}
.el-table--border td:nth-last-of-type(1) {
  border-right: none;
}
.el-table--border::after,
.el-table--group::after {
  width: 0;
}

.table_name {
  color: white;
  background-color: #20a0ff;
  border-radius: 4px;
}

.mtable {
  padding-left: 5px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  background-color: #20a0ff;
}

.designfloodtable {
  width: 100%;
  height: 100%;
}

.firstcolumn {
  background: #d9e4ec;
}
</style>


function newFunction() {
  return 2;
}
