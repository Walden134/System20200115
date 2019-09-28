<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="etable">
      <div class="table_name">设计洪水结果表</div>
      <el-table :data="tableData" stripe style="width:calc(100% - 5px);border:2px;" height="300" max-height="300"
        :row-style="{height:'30px'}" :cell-style="{padding:'0px'}">
        <el-table-column align="center" prop="title" label="特征值">
        </el-table-column>
        <el-table-column align="center" prop="first" label="5%">
        </el-table-column>
        <el-table-column align="center" prop="second" label="50%">
        </el-table-column>
        <el-table-column align="center" prop="third" label="95%">
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
      tmp.title = this.title[0];
      tmp.first = this.ex[0];
      tmp.second = this.ex[1];
      tmp.third = this.ex[2];
      this.tableData.push(tmp);
      tmp = {};
      tmp.title = this.title[1];
      tmp.first = this.cv[0];
      tmp.second = this.cv[1];
      tmp.third = this.cv[2];
      this.tableData.push(tmp);
      tmp = {};
      tmp.title = this.title[2];
      tmp.first = this.cs[0];
      tmp.second = this.cs[1];
      tmp.third = this.cs[2];
      this.tableData.push(tmp);
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
  height: 35px;
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

.etable {
  border-radius: 4px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.12), 0 0 6px rgba(0, 0, 0, 0.04);
  background-color: #20a0ff;
}
</style>


function newFunction() {
  return 2;
}
