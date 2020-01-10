<template>
  <el-row class="designfloodtable">
    <el-col :span="24"
            class="mtable">
      <div class="table_name">{{title}}
        <div class="toexcel">
          <el-button @click="exportExcel"
                     type="primary"
                     plain
                     class="button"
                     style="width:70px;position:absolute;top:0;right:10px">导出</el-button>
        </div>
      </div>
      <el-table :data="tableData"
                fit
                id="floodriskTable"
                cell-class-name="dyg"
                height="300"
                max-height="300"
                style="width:calc(100% - 5px);height:300px;border:2px;"
                :row-style="{height:'20px'}"
                :cell-style="{padding:'0px'}">
        <el-table-column prop="year"
                         :label="year">
        </el-table-column>
        <el-table-column prop="levelDesign"
                         :label="levelDesign">
        </el-table-column>
        <el-table-column prop="levelCheck"
                         :label="levelCheck">
        </el-table-column>
        <el-table-column prop="levelDam"
                         :label="levelDam">
        </el-table-column>
      </el-table>
      <div style="background-color:#f0f8ff;height:5px"></div>
    </el-col>
  </el-row>
</template>

<script>
import storageUtils from "@utils/storageUtils";
import FileSaver from "file-saver";
import XLSX from "xlsx";

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
    },
    exportExcel() {
      /* generate workbook object from table */
      let wb = XLSX.utils.table_to_book(
        document.querySelector("#floodriskTable")
      );
      /* get binary string as output */
      let wbout = XLSX.write(wb, {
        bookType: "xlsx",
        bookSST: true,
        type: "array"
      });
      try {
        FileSaver.saveAs(
          new Blob([wbout], { type: "application/octet-stream" }),
          "防洪风险统计表.xlsx"
        );
      } catch (e) {
        if (typeof console !== "undefined") console.log(e, wbout);
      }
      return wbout;
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
.mtable .el-table__header th,
.el-table__header tr {
  background-color: #f0f8ff;
  color: #333;
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
  color: #333;
  background-color: #f0f8ff;
  border-radius: 4px;
  font-size: 18px;
  font-weight: 700;
  height: 40px;
  line-height: 40px;
}

.mtable {
  padding-left: 5px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  background-color: #f0f8ff;
}

.toexcel {
  cursor: pointer;
  cursor: hand;
}

.dyg {
  background-color: aliceblue;
  text-align: center;
}
</style>


function newFunction() {
  return 2;
}
