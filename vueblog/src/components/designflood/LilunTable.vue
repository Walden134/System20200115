<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="mtable">
      <div class="table_name">设计洪水计算值</div>
      <el-table :data="tableData" stripe fit height=" 300" max-height="300"
        style="width:calc(100% - 5px);height:300px;border:2px;" :row-style="{height:'20px'}"
        :cell-style="{padding:'0px'}">
        <el-table-column prop="number" :label="number"> </el-table-column>
        <el-table-column prop="frequency" :label="frequency"> </el-table-column>
        <el-table-column prop="flow" :label="flow"> </el-table-column>
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
      number: "序号",
      frequency: "理论频率（%）",
      flow: "理论流量(m³/s)",
      theoryFrequency: [],
      tableData: [],
      dataFlag: 0
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
</style>

