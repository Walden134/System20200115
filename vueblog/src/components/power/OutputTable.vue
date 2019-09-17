<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="mtable">
      <div class="table_name">{{title.title}}</div>
      <el-table :data="tableData" :cell-class-name="Ftable" fit style="width:calc(100% - 5px);" height="300"
        max-height="300" stripe :row-style="{height:'35px'}" :cell-style="{padding:'0px'}">
        <el-table-column prop="circumstances" label="情景"> </el-table-column>
        <el-table-column label="95%保证出力">
          <el-table-column prop="annual_output" label="值(MW)"></el-table-column>
          <el-table-column prop="annual_amp" label="增幅(%)"></el-table-column>
          <el-table-column prop="assurance_rate" label="保证率(%)"> </el-table-column>
          <el-table-column prop="risk_rate" label="风险率(%)"> </el-table-column>
        </el-table-column>
      </el-table>
      <div class="table_name" style="height: 10px;"></div>
    </el-col>
  </el-row>
</template>
<script>
export default {
  props: ["title"],

  data() {
    return {
      tableData: [
        {
          circumstances: "设计值",
          annual_output: "176",
          annual_amp: "-"
        }
      ],
      outputList: [],
      outputDesign: 176,
      category: [],

      situations: [],
      patterns: []
    };
  },
  methods: {
    Ftable({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        return "firstcolumn";
      } else {
        return "";
      }
    },
    setTableData() {
      let old = {
        circumstances: "设计值",
        annual_output: this.outputDesign,
        annual_amp: "-"
      };
      this.tableData = [];
      this.tableData.push(old);
      for (let i = 0; i < this.outputList.length; i++) {
        let tmp = {};
        tmp.circumstances = this.category[i];
        tmp.annual_output = this.outputList[i];
        tmp.annual_amp =
          ((this.outputList[i] - old.annual_output) / old.annual_output) * 100;
        tmp.annual_amp = tmp.annual_amp.toFixed(2);
        this.tableData.push(tmp);
      }
    }
  },
  mounted() {},
  created() {
    bus.$on("outputList", data => {
      console.log("outputList", data);
      this.outputList = data;
    });
    bus.$on("xAxis", data => {
      console.log("xAxis", data);
      this.category = data;
    });
    bus.$on("outputDesign", data => {
      console.log("outputDesign", data);
      this.outputDesign = data;
    });
  },
  beforeDestroy() {
    bus.$off("outputList");
    bus.$off("xAxis");
    bus.$off("outputDesign");
  },
  computed: {
    flag() {
      return this.$store.state.flag;
    }
  },
  watch: {
    outputList() {
      this.setTableData();
    },
    category() {
      this.setTableData();
    },
    outputDesign() {
      this.setTableData();
    }
    // flag() {
    //   this.setTableData();
    //   this.$store.commit("flag", false);
    // }
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
  /* background-color: #d9e4ec; */
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

