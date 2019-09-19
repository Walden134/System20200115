<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="mtable">
      <div class="table_name">发电量和保证出力</div>
      <el-table :data="tableData" :cell-class-name="Ftable" fit height="300" max-height="300"
        style="width:calc(100% - 5px);" :row-style="{height:'40px'}" :cell-style="{padding:'0px'}">
        <el-table-column prop="circumstances" label="情景" style="width:140px">
        </el-table-column>
        <el-table-column label="95%保证出力">
          <el-table-column prop="annual_output" label="值(MW)"></el-table-column>
          <el-table-column prop="annual_amp" label="增幅(%)"></el-table-column>
          <!-- <el-table-column prop="assurance_rate" label="保证率(%)"> </el-table-column> -->
          <!-- <el-table-column prop="risk_rate" label="风险率(%)"> </el-table-column> -->
        </el-table-column>
        <el-table-column label="全年发电量">
          <el-table-column prop="annual_power" label="值(亿kW•h)"></el-table-column>
          <el-table-column prop="annual_amp" label="增幅(%)"></el-table-column>
          <!-- <el-table-column label="发电风险率(%)">
            <el-table-column prop="dry_risk" label="枯水年"></el-table-column>
            <el-table-column prop="avg_risk" label="多年平均"></el-table-column>
          </el-table-column> -->

        </el-table-column>
        <el-table-column label="丰水期发电量">
          <el-table-column prop="wet_power" label="值(亿kW•h)"></el-table-column>
          <el-table-column prop="wet_amp" label="增幅(%)"></el-table-column>
          <!-- <el-table-column label="发电风险率(%)">
            <el-table-column prop="dry_risk" label="枯水年(%)"></el-table-column>
            <el-table-column prop="avg_risk" label="多年平均(%)"></el-table-column>
          </el-table-column> -->
        </el-table-column>
        <el-table-column label="平水期发电量">
          <el-table-column prop="normal_power" label="值(亿kW•h)"></el-table-column>
          <el-table-column prop="normal_amp" label="增幅(%)"></el-table-column>
          <!-- <el-table-column label="发电风险率(%)">
            <el-table-column prop="dry_risk" label="枯水年"></el-table-column>
            <el-table-column prop="avg_risk" label="多年平均"></el-table-column>
          </el-table-column> -->
        </el-table-column>
        <el-table-column label="枯水期发电量">
          <el-table-column prop="dry_power" label="值(亿kW•h)"></el-table-column>
          <el-table-column prop="dry_amp" label="增幅(%)"></el-table-column>
          <!-- <el-table-column label="发电风险率(%)">
            <el-table-column prop="dry_risk" label="枯水年"></el-table-column>
            <el-table-column prop="avg_risk" label="多年平均"></el-table-column>
          </el-table-column> -->
        </el-table-column>
      </el-table>
      <div class="table_name" style="height: 5px;"></div>
    </el-col>
  </el-row>
</template>

<script>
import storageUtils from "../../utils/storageUtils";
export default {
  props: ["title"],
  data() {
    return {
      tableData: [
        {
          circumstances: "设计值",
          annual_output: "176",
          annual_amp: "-",
          assurance_rate: "-",
          annual_power: "59.62",
          annual_amp: "-",
          wet_power: "42.85",
          wet_amp: "-",
          normal_power: "7.52",
          normal_amp: "-",
          dry_power: "9.25",
          dry_amp: "-",
          dry_risk: "-",
          avg_risk: "-"
        }
      ],

      category: [],
      powerList: [],
      avgDesiginPower: 59.62,
      outputList: [],
      outputDesign: 176,
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
        annual_amp: "-",
        assurance_rate: "-",
        annual_power: this.avgDesiginPower,
        annual_amp: "-",
        wet_power: "42.85",
        wet_amp: "-",
        normal_power: "7.52",
        normal_amp: "-",
        dry_power: "9.25",
        dry_amp: "-"
      };
      this.tableData = [];
      this.tableData.push(old);
      for (let i = 0; i < this.powerList.length; i++) {
        let tmp = {};
        tmp.circumstances = this.category[i];
        tmp.annual_power = this.powerList[i][0];
        tmp.annual_amp =
          ((this.powerList[i][0] - old.annual_power) / old.annual_power) * 100;
        tmp.annual_amp = tmp.annual_amp.toFixed(2);
        tmp.wet_power = this.powerList[i][1];
        tmp.wet_amp =
          ((this.powerList[i][1] - old.wet_power) / old.wet_power) * 100;
        tmp.wet_amp = tmp.wet_amp.toFixed(2);
        tmp.normal_power = this.powerList[i][2];
        tmp.normal_amp =
          ((this.powerList[i][2] - old.normal_power) / old.normal_power) * 100;
        tmp.normal_amp = tmp.normal_amp.toFixed(2);
        tmp.dry_power = this.powerList[i][3];
        tmp.dry_amp =
          ((this.powerList[i][3] - old.dry_power) / old.dry_power) * 100;
        tmp.dry_amp = tmp.dry_amp.toFixed(2);
        tmp.annual_output = this.outputList[i];
        tmp.annual_amp =
          ((this.outputList[i] - old.annual_output) / old.annual_output) * 100;
        tmp.annual_amp = tmp.annual_amp.toFixed(2);
        this.tableData.push(tmp);
      }
    },
    inintChartData() {
      this.category = storageUtils.readCategory();
      this.powerList = storageUtils.readPowers();
      this.outputList = storageUtils.readOutputs();
    }
  },
  mounted() {
    this.inintChartData();
  },
  created() {
    bus.$on("xAxis", data => {
      this.category = data;
    });
    bus.$on("powerList", data => {
      this.powerList = data;
    });
    bus.$on("avgDesiginPower", data => {
      this.avgDesiginPower = data;
    });
    bus.$on("outputList", data => {
      this.outputList = data;
    });
    bus.$on("outputDesign", data => {
      this.outputDesign = data;
    });
  },
  beforeDestroy() {
    bus.$off("xAxis");
    bus.$off("outputList");
    bus.$off("outputDesign");
    bus.$off("powerList");
    bus.$off("avgDesiginPower");
  },
  computed: {
    flag() {
      return this.$store.state.flag;
    }
  },
  watch: {
    powerList() {
      this.setTableData();
    },
    category() {
      this.setTableData();
    },
    avgDesiginPower() {
      this.setTableData();
    },
    outputList() {
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

