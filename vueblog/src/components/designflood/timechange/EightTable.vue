<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="Xtable">
      <div class="table_name">设计洪水结果表
        <div class="toexcel">
          <el-button @click="exportExcel" type="primary" plain class="button"
            style="width:70px;position:absolute;top:0;right:10px">导出</el-button>
        </div>
      </div>
      <el-table :data="tableData" id="dfResultTable" cell-class-name="dyg"
        style="width:calc(100% - 5px);border:2px;background-color:#f0f8ff" height="285" :row-style="{height:'30px'}"
        :cell-style="{padding:'0px'}">
        <el-table-column label="年降水" align="right" width="10">
          <el-table-column prop="title" align="left" label="特征值" width="111">
          </el-table-column>
        </el-table-column>
        <el-table-column align="center" prop="first" label="5%" width="80">
        </el-table-column>
        <el-table-column align="center" prop="second" label="50%" width="80">
        </el-table-column>
        <el-table-column align="center" prop="third" label="95%" width="80">
        </el-table-column>
      </el-table>
      <div style="background-color:#f0f8ff;height:5px"></div>
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
      designp: [],
      ex: [],
      cs: [],
      cv: [],
      title: [
        "均值",
        "cv",
        "cs",
        "0.01%",
        "0.05%",
        "0.1%",
        "0.2%",
        "0.5%",
        "1%",
        "5%",
        "10%",
        "15%",
        "20%",
        "30%",
        "40%",
        "50%",
        "60%",
        "70%",
        "80%",
        "85%",
        "90%",
        "95%",
        "99%",
        "99.9%",
        "99.95%",
        "99.99%"
      ]
    };
  },
  methods: {
    setTableData() {
      this.tableData = [];
      let tmp = {};
      // tmp.title = this.title[0];
      // tmp.first = this.ex[0];
      // tmp.second = this.ex[1];
      // tmp.third = this.ex[2];
      // this.tableData.push(tmp);
      // tmp = {};
      // tmp.title = this.title[1];
      // tmp.first = this.cv[0];
      // tmp.second = this.cv[1];
      // tmp.third = this.cv[2];
      // this.tableData.push(tmp);
      // tmp = {};
      // tmp.title = this.title[2];
      // tmp.first = this.cs[0];
      // tmp.second = this.cs[1];
      // tmp.third = this.cs[2];
      // this.tableData.push(tmp);
      for (let j = 0; j < this.title.length - 3; j++) {
        tmp = {};
        tmp.title = this.title[j + 3];
        if (this.designp.length > 0) {
          tmp.first = this.designp[1][j];
          tmp.second = this.designp[2][j];
          tmp.third = this.designp[3][j];
        }
        this.tableData.push(tmp);
      }
    },
    inintChartData() {
      this.designp = storageUtils.readDesignP();
      this.ex = storageUtils.readEx();
      this.cv = storageUtils.readCv();
      this.cs = storageUtils.readCs();
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
    bus.$on("designp", data => {
      this.designp = data;
    });
    bus.$on("cv", data => {
      this.cv = data;
    });
    bus.$on("cs", data => {
      this.cs = data;
    });
    bus.$on("ex", data => {
      this.ex = data;
    });
  },
  beforeDestroy() {
    bus.$off("designp");
    bus.$off("ex");
    bus.$off("cv");
    bus.$off("cs");
  },
  computed: {},
  watch: {
    designp() {
      this.setTableData();
    },
    ex() {
      this.setTableData();
    },
    cv() {
      this.setTableData();
    },
    cs() {
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


function newFunction() {
  return 2;
}
