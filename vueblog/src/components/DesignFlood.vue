<template>
  <el-row class="designfloodtable">
    <el-col :span="12" class="mtable">
      <div class="table_name">实测点距信息</div>
      <el-table :data="tableData1" :cell-class-name="Ftable" border="2px" height="250" style="width: 99%"
        :row-style="{height:'20px'}" :cell-style="{padding:'0px'}">
        <el-table-column prop="number" label="序号" width="85px">
        </el-table-column>
        <el-table-column prop="empirical_frequency" :label="label" width="215px">
        </el-table-column>
        <el-table-column prop="measured_flow" label="实测流量m³/s" width="215px">
        </el-table-column>
      </el-table>
    </el-col>
    <el-col :span="12" class="mtable">
      <div class="table_name">设计洪水计算值</div>
      <el-table :data="tableData2" :cell-class-name="Ftable" height="250" border="2px" style="width:99%"
        :row-style="{height:'20px'}" :cell-style="{padding:'0px'}">
        <el-table-column prop="number" label="序号" width="85px">
        </el-table-column>
        <el-table-column prop="theoretical_frequency" label="理论频率%" width="215px">
        </el-table-column>
        <el-table-column prop="theoretical_flow" label="理论流量m³/s" width="215px">
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>

</template>

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
  background-color: #d9e4ec;
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
  width: 1050px;
  height: 380px;
}

.firstcolumn {
  background: #d9e4ec;
}
</style>

<script>
// import '//unpkg.com/element-ui@2.12.0/lib/theme-chalk/index.css'

export default {
  methods: {
    Ftable({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        return "firstcolumn";
      } else {
        return "";
      }
    }
  },
  data() {
    return {
      label: "经验频率%",
      tableData1: [
        {
          number: 1,
          empirical_frequency: "70",
          measured_flow: 500
        },
        {
          number: 2,
          empirical_frequency: "70",
          measured_flow: 500
        },
        {
          number: 3,
          empirical_frequency: "70",
          measured_flow: 500
        },
        {
          number: 1,
          empirical_frequency: "70",
          measured_flow: 500
        },
        {
          number: 1,
          empirical_frequency: "70",
          measured_flow: 500
        },
        {
          number: 1,
          empirical_frequency: "70",
          measured_flow: 500
        },
        {
          number: 1,
          empirical_frequency: "70",
          measured_flow: 500
        },
        {
          number: 1,
          empirical_frequency: "70",
          measured_flow: 500
        },
        {
          number: 1,
          empirical_frequency: "70",
          measured_flow: 500
        },
        {
          number: 1,
          empirical_frequency: "70",
          measured_flow: 500
        },
        {
          number: 1,
          empirical_frequency: "70",
          measured_flow: 500
        },
        {
          number: 1,
          empirical_frequency: "70",
          measured_flow: 500
        }
      ],

      tableData2: [
        {
          number: 1,
          theoretical_frequency: "80",
          theoretical_flow: 600
        },
        {
          number: 1,
          theoretical_frequency: "80",
          theoretical_flow: 600
        },
        {
          number: 1,
          theoretical_frequency: "80",
          theoretical_flow: 600
        },
        {
          number: 1,
          theoretical_frequency: "80",
          theoretical_flow: 600
        },
        {
          number: 1,
          theoretical_frequency: "80",
          theoretical_flow: 600
        },
        {
          number: 1,
          theoretical_frequency: "80",
          theoretical_flow: 600
        },
        {
          number: 1,
          theoretical_frequency: "80",
          theoretical_flow: 600
        },
        {
          number: 1,
          theoretical_frequency: "80",
          theoretical_flow: 600
        },
        {
          number: 1,
          theoretical_frequency: "80",
          theoretical_flow: 600
        },
        {
          number: 1,
          theoretical_frequency: "80",
          theoretical_flow: 600
        },
        {
          number: 1,
          theoretical_frequency: "80",
          theoretical_flow: 600
        }
      ]
    };
  }
};
</script>


