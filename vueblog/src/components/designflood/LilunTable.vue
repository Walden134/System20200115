<template>
  <el-row class="designfloodtable">
    <el-col :span="24"
            class="mtable">
      <div class="table_name">{{title.title}}</div>
      <el-table :data="tableData"
                :cell-class-name="Ftable"
                style="width:99%;height:300px;border:2px;"
                :row-style="{height:'20px'}"
                :cell-style="{padding:'0px'}">
        <el-table-column prop="number"
                         :label="title.label1">
        </el-table-column>
        <el-table-column prop="frequency2"
                         :label="title.label2">
        </el-table-column>
        <el-table-column prop="flow2"
                         :label="title.label3">
        </el-table-column>
      </el-table>
    </el-col>
  </el-row>
</template>

<script>
export default {
  props: ["title"],
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
      tableData: [
        {
          number: 1,
          frequency2: "0.01",
          flow2: 4200
        },
        {
          number: 2,
          frequency2: "0.02",
          flow2: 3700
        },
        {
          number: 3,
          frequency2: "0.05",
          flow2: 3500
        },
        {
          number: 4,
          frequency2: "0.1",
          flow2: 3350
        },
        {
          number: 5,
          frequency2: "0.2",
          flow2: 3260
        },
        {
          number: 6,
          frequency2: "0.5",
          flow2: 3180
        },
        {
          number: 7,
          frequency2: "1",
          flow2: 3100
        },
        {
          number: 8,
          frequency2: "2",
          flow2: 3050
        },
        {
          number: 9,
          frequency2: "3.3",
          flow2: 2900
        },
        {
          number: 10,
          frequency2: "5",
          flow2: 2860
        },
        {
          number: 11,
          frequency2: "10",
          flow2: 2766
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
