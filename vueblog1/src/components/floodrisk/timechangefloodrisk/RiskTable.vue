<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="mtable">
      <div class="table_name">设计水位统计表
        <div class="toexcel">
          <el-button @click="exportExcel" type="primary" plain class="button"
            style="width:70px;position:absolute;top:0;right:10px">导出</el-button>
        </div>
      </div>
      <el-table :data="tableData" id="riskTable" border cell-class-name="dyg"
        style="width:calc(100% - 5px);border:1px solid #EBEEF5;background-color:#f0f8ff" :row-style="{height:'20px'}"
        :cell-style="{padding:'0px'}" height="280" max-height="280">
        <el-table-column prop="number" label="水位"> </el-table-column>

        <el-table-column prop="rcp1" :label="rcp1">
          <el-table-column label="2030S"></el-table-column>
          <el-table-column label="2060S"></el-table-column>
          <el-table-column label="2090S"></el-table-column>
        </el-table-column>
        <el-table-column prop="rcp2" :label="rcp2">
          <el-table-column label="2030S"></el-table-column>
          <el-table-column label="2060S"></el-table-column>
          <el-table-column label="2090S"></el-table-column>
        </el-table-column>
        <el-table-column prop="rcp3" :label="rcp3">
          <el-table-column label="2030S"></el-table-column>
          <el-table-column label="2060S"></el-table-column>
          <el-table-column label="2090S"></el-table-column>
        </el-table-column>
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
      number: ["设计洪水", "校核洪水"],
      rcp1: "RCP2.6",
      rcp2: "RCP4.5",
      rcp3: "RCP8.5",
      theoryFrequency: [],
      tableData: [],
      dataFlag: 0,
      title: ["超设计洪水", "超校核洪水", "超坝顶高程"]
    };
  },
  methods: {
    setTableData() {
      this.tableData = [];
      if (this.theoryFrequency.length > 0) {
        let start = 1;
        for (let j = 0; j < this.theoryFrequency[0].length; j++) {
          let tmp = {};
          tmp.number = start + j;
          tmp.frequency = this.theoryFrequency[0][j];
          tmp.flow = this.theoryFrequency[1][j];
          this.tableData.push(tmp);
        }
      }
    },
    inintChartData() {
      this.theoryFrequency = storageUtils.readTheoryFrequency();
    },
    exportExcel() {
      /* generate workbook object from table */
      let wb = XLSX.utils.table_to_book(document.querySelector("#lilunTable"));
      /* get binary string as output */
      let wbout = XLSX.write(wb, {
        bookType: "xlsx",
        bookSST: true,
        type: "array"
      });
      try {
        FileSaver.saveAs(
          new Blob([wbout], { type: "application/octet-stream" }),
          "设计洪水计算值.xlsx"
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
    bus.$on("theoryFrequency", data => {
      this.theoryFrequency = data;
    });
    bus.$on("dataFlag", data => {
      this.dataFlag = data;
    });
  },
  beforeDestroy() {
    bus.$off("theoryFrequency");
  },
  computed: {},
  watch: {
    theoryFrequency() {
      this.setTableData();
    },
    dataFlag(newVal, oldVal) {
      if (newVal > 0) {
        this.flow = "理论洪量(亿m³)";
      } else {
        this.flow = "理论流量(m³/s)";
      }
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
.mtable {
  padding-left: 5px;
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  background-color: #f0f8ff;
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
  border-width: "4px";
}
</style>

