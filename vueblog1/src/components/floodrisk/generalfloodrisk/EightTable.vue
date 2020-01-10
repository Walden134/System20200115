<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="Xtable">
      <div class="table_name">洪水风险结果表
        <div class="toexcel">
          <el-button @click="exportExcel" type="primary" plain class="button"
            style="width:70px;position:absolute;top:0;right:10px">导出</el-button>
        </div>
      </div>
      <el-table :data="tableData" id="dfResultTable" cell-class-name="dyg" border
        style="width:calc(100% - 5px);border:1px solid #EBEEF5;background-color:#f0f8ff" :row-style="{height:'37px'}"
        :cell-style="{padding:'0px'}">
        <el-table-column prop="title" label="设计频率" align="center" width="130">
        </el-table-column>

        <el-table-column align="center" label="0.2%" prop="prop1" width="100"> </el-table-column>
        <el-table-column align="center" label="0.5%" prop="prop2" width="100"> </el-table-column>
        <el-table-column align="center" label="1%" prop="prop3" width="100"> </el-table-column>
        <el-table-column align="center" label="2%" prop="prop4" width="100"> </el-table-column>
        <el-table-column align="center" label="5%" prop="prop5" width="100"> </el-table-column>
        <el-table-column align="center" label="10%" prop="prop6" width="100"> </el-table-column>
        <el-table-column align="center" label="20%" prop="prop7" width="100"> </el-table-column>
        <el-table-column align="center" label="50%" prop="prop8" width="100"> </el-table-column>
      </el-table>
    </el-col>
  </el-row>
</template>

<script>
import storageUtils from "@/utils/storageUtils";
import FileSaver from "file-saver";
import XLSX from "xlsx";

export default {
  data() {
    return {
      tableData: [],
      gRiskRes: {},
      title: [
        "设计洪水值(m³/s)",
        "基准期风险率",
        "CNRM风险率",
        "MIROC风险率",
        "CanESM风险率",
        "GFDL风险率"
      ]
    };
  },
  methods: {
    setTableData() {
      this.tableData = [];
      let obj = this.gRiskRes;
      let index = 1;
      let tmp_title = ["design", "obs", "cnrm", "miroc", "canesm", "gfdl"];
      if (JSON.stringify(obj) != "{}") {
        for (let i = 0; i < tmp_title.length; i++) {
          let key = tmp_title[i];
          console.log(key + "---" + obj[key]);
          let tmp = {};
          tmp.title = this.title[i];
          tmp.prop1 = obj[key][0].toFixed(2);
          tmp.prop2 = obj[key][1].toFixed(2);
          tmp.prop3 = obj[key][2].toFixed(2);
          tmp.prop4 = obj[key][3].toFixed(2);
          tmp.prop5 = obj[key][4].toFixed(2);
          tmp.prop6 = obj[key][5].toFixed(2);
          tmp.prop7 = obj[key][6].toFixed(2);
          tmp.prop8 = obj[key][7].toFixed(2);
          this.tableData.push(tmp);
        }
      } else {
        for (let i = 0; i < tmp_title.length; i++) {
          let key = tmp_title[i];
          let tmp = {};
          tmp.title = this.title[i];
          this.tableData.push(tmp);
        }
      }
    },
    inintChartData() {
      this.gRiskRes = storageUtils.readGRiskRes();
    },
    exportExcel() {
      /* generate workbook object from table */
      let wb = XLSX.utils.table_to_book(
        document.querySelector("#dfResultTable")
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
          "设计洪水结果.xlsx"
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
    bus.$on("gRiskRes", data => {
      this.gRiskRes = data;
    });
  },
  beforeDestroy() {
    bus.$off("gRiskRes");
  },
  watch: {
    gRiskRes() {
      this.setTableData();
    }
  }
};
</script>


<style>
.Xtable .el-table__header th,
.el-table__header tr {
  background-color: #f0f8ff;
  color: #333;
  /* text-align: center; */
  padding: 0;
  height: 25px;
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

.Xtable {
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
  border-width: "4px";
}

/* 斜线表头格式 */
.Xtable .el-table thead.is-group th {
  background: none;
}
.Xtable .el-table thead.is-group tr:first-of-type th:first-of-type {
  border-bottom: none;
}
.Xtable .el-table thead.is-group tr:first-of-type th:first-of-type:before {
  content: "";
  position: absolute;
  width: 1px;
  height: 75px; /*这里需要自己调整，根据td的宽度和高度*/
  top: 0;
  left: 0;
  background-color: #696969;
  opacity: 0.5;
  display: block;
  transform: rotate(-66deg); /*这里需要自己调整，根据线的位置*/
  transform-origin: top;
}
.Xtable .el-table thead.is-group tr:last-of-type th:first-of-type:before {
  content: "";
  position: absolute;
  width: 1px;
  height: 75px; /*这里需要自己调整，根据td的宽度和高度*/
  bottom: 0;
  right: 0;
  background-color: #696969;
  opacity: 0.5;
  display: block;
  transform: rotate(-66deg); /*这里需要自己调整，根据线的位置*/
  transform-origin: bottom;
  /* background: red; */
}
</style>
