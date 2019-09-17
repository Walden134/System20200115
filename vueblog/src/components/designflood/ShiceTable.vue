<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="mtable">
      <div class="table_name">{{title.title}}</div>
      <el-table :data="tableData" stripe style="width:calc(100% - 5px);height:300px;border:2px;" :row-style="{height:'20px'}" :cell-style="{padding:'0px'}">
        <el-table-column prop="number" :label="title.label1">
        </el-table-column>
        <el-table-column prop="frequency1" :label="title.label2">
        </el-table-column>
        <el-table-column prop="flow1" :label="title.label3">
        </el-table-column>
      </el-table>
      <div style="background-color:#20a0ff;height:5px"></div>
    </el-col>
  </el-row>
</template>

<script>
export default {
  props: ["title"],
  methods: {},
  data() {
    return {
      tableData: [
        {
          number: 1,
          frequency1: "1.2",
          flow1: 4200
        },
        {
          number: 2,
          frequency1: "1.32",
          flow1: 3700
        },
        {
          number: 3,
          frequency1: "1.6",
          flow1: 3500
        },
        {
          number: 4,
          frequency1: "1.8",
          flow1: 3350
        },
        {
          number: 5,
          frequency1: "2.1",
          flow1: 3260
        },
        {
          number: 6,
          frequency1: "2.4",
          flow1: 3180
        },
        {
          number: 7,
          frequency1: "2.7",
          flow1: 3100
        },
        {
          number: 8,
          frequency1: "3.1",
          flow1: 3050
        },
        {
          number: 9,
          frequency1: "3.5",
          flow1: 2900
        },
        {
          number: 10,
          frequency1: "3.7",
          flow1: 2860
        },
        {
          number: 11,
          frequency1: "42",
          flow1: 2700
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
</style>


function newFunction() {
  return 2;
}
