<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="mtable">
      <div class="table_name">{{title}}</div>
      <el-table :data="tableData" stripe style="width:calc(100% - 5px);height:300px;border:2px;"
        :row-style="{height:'20px'}" :cell-style="{padding:'0px'}">
        <el-table-column prop="number" :label="label1">
        </el-table-column>
        <el-table-column prop="measured_flow" :label="label2">
        </el-table-column>
        <el-table-column prop="empirical_frequency" :label="label3">
        </el-table-column>
        <el-table-column prop="empirical_frequency" :label="label4">
        </el-table-column>
      </el-table>
      <div style="background-color:#20a0ff;height:5px"></div>
    </el-col>
  </el-row>
</template>

<script>
export default {
  methods: {},
  data() {
    return {
      title: "防洪风险统计表",
      label1: "年份",
      label2: "超设计洪水风险率",
      label3: "超校核洪水风险率",
      label4: "超坝顶洪水风险率",

      tableData: [
        {
          number: 1,
          empirical_frequency: "4200",
          measured_flow: 1.2
        },
        {
          number: 2,
          empirical_frequency: "3700",
          measured_flow: 1.32
        },
        {
          number: 3,
          empirical_frequency: "3500",
          measured_flow: 1.6
        },
        {
          number: 4,
          empirical_frequency: "3350",
          measured_flow: 1.8
        },
        {
          number: 5,
          empirical_frequency: "3260",
          measured_flow: 2.1
        },
        {
          number: 6,
          empirical_frequency: "3180",
          measured_flow: 2.4
        },
        {
          number: 7,
          empirical_frequency: "3100",
          measured_flow: 2.7
        },
        {
          number: 8,
          empirical_frequency: "3050",
          measured_flow: 3.1
        },
        {
          number: 9,
          empirical_frequency: "2900",
          measured_flow: 3.5
        },
        {
          number: 10,
          empirical_frequency: "2860",
          measured_flow: 3.7
        },
        {
          number: 11,
          empirical_frequency: "2750",
          measured_flow: 3.8
        }
      ]
    };
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

.designfloodtable {
  width: 100%;
  height: 100%;
}

.firstcolumn {
  background: #d9e4ec;
}
</style>


function newFunction() {
  return 2;
}
