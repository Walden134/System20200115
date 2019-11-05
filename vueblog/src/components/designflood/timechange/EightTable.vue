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
        style="width:calc(100% - 5px);border:2px;background-color:#f0f8ff" :row-style="{height:'30px'}"
        :cell-style="{padding:'0px'}">
        <el-table-column label="特征值" align="right" width="10">
          <el-table-column prop="title" align="left" label="年降水" width="111"> </el-table-column>
        </el-table-column>
        <!-- <el-table-column align="center" label="均值" prop="5%" width="80"> </el-table-column>
        <el-table-column align="center" label="cv" prop="50%" width="80"> </el-table-column>
        <el-table-column align="center" label="cs" prop="95%" width="80"> </el-table-column> -->
        <el-table-column align="center" label="0.01%" prop="prop1" width="80"> </el-table-column>
        <el-table-column align="center" label="0.05%" prop="prop2" width="80"> </el-table-column>
        <el-table-column align="center" label="0.1%" prop="prop3" width="80"> </el-table-column>
        <el-table-column align="center" label="0.2%" prop="prop4" width="80"> </el-table-column>
        <el-table-column align="center" label="0.5%" prop="prop5" width="80"> </el-table-column>
        <el-table-column align="center" label="1%" prop="prop6" width="80"> </el-table-column>
        <el-table-column align="center" label="5%" prop="prop7" width="80"> </el-table-column>
        <el-table-column align="center" label="10%" prop="prop8" width="80"> </el-table-column>
        <el-table-column align="center" label="15%" prop="prop9" width="80"> </el-table-column>
        <el-table-column align="center" label="20%" prop="prop10" width="80"> </el-table-column>
        <el-table-column align="center" label="30%" prop="prop11" width="80"> </el-table-column>
        <el-table-column align="center" label="40%" prop="prop12" width="80"> </el-table-column>
        <el-table-column align="center" label="50%" prop="prop13" width="80"> </el-table-column>
        <el-table-column align="center" label="60%" prop="prop14" width="80"> </el-table-column>
        <el-table-column align="center" label="70%" prop="prop15" width="80"> </el-table-column>
        <el-table-column align="center" label="80%" prop="prop16" width="80"> </el-table-column>
        <el-table-column align="center" label="90%" prop="prop17" width="80"> </el-table-column>
        <el-table-column align="center" label="95%" prop="prop18" width="80"> </el-table-column>
        <el-table-column align="center" label="99%" prop="prop19" width="80"> </el-table-column>
        <el-table-column align="center" label="99.9%" prop="prop20" width="80"> </el-table-column>
        <el-table-column align="center" label="99.95%" prop="prop21" width="80"> </el-table-column>
        <el-table-column align="center" label="99.99%" prop="prop22" width="80"> </el-table-column>
      </el-table>
      <!-- <div style="background-color:#f0f8ff;height:5px"></div> -->
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
      title: ["5%", "50%", "95%"]
    };
  },
  methods: {
    setTableData() {
      this.tableData = [];
      for (let j = 1; j < 4; j++) {
        let tmp = {};
        tmp.title = this.title[j - 1];
        if (this.designp.length > 0) {
          tmp.prop1 = this.designp[j][0];
          tmp.prop2 = this.designp[j][1];
          tmp.prop3 = this.designp[j][2];
          tmp.prop4 = this.designp[j][3];
          tmp.prop5 = this.designp[j][4];
          tmp.prop6 = this.designp[j][5];
          tmp.prop7 = this.designp[j][6];
          tmp.prop8 = this.designp[j][7];
          tmp.prop9 = this.designp[j][8];
          tmp.prop10 = this.designp[j][9];
          tmp.prop11 = this.designp[j][10];
          tmp.prop12 = this.designp[j][11];
          tmp.prop13 = this.designp[j][12];
          tmp.prop14 = this.designp[j][13];
          tmp.prop15 = this.designp[j][14];
          tmp.prop16 = this.designp[j][15];
          tmp.prop17 = this.designp[j][16];
          tmp.prop18 = this.designp[j][17];
          tmp.prop19 = this.designp[j][18];
          tmp.prop20 = this.designp[j][19];
          tmp.prop21 = this.designp[j][20];
          tmp.prop22 = this.designp[j][21];
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
