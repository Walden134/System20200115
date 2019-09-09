<template>
  <el-row class="designfloodtable">
    <el-col :span="24"
            class="mtable">
      <div class="table_name">{{title.title}}</div>
      <el-table :data="tableData"
                :cell-class-name="Ftable"
                style="width:100%;height:270px;border:2px;"
                :row-style="{height:'90px'}"
                :cell-style="{padding:'0px'}">
        <el-table-column prop="number"
                         :label="title.label1">
        </el-table-column>
        <el-table-column prop="mean"
                         :label="title.label2">
        </el-table-column>
        <el-table-column prop="cv"
                         :label="title.label3">
        </el-table-column>
        <el-table-column prop="cs"
                         :label="title.label4">
        </el-table-column>
        <el-table-column prop="p1"
                         :label="title.label5">
        </el-table-column>
        <el-table-column prop="p2"
                         :label="title.label6">
        </el-table-column>
        <el-table-column prop="p3"
                         :label="title.label7">
        </el-table-column>
        <el-table-column prop="p4"
                         :label="title.label8">
        </el-table-column>
        <el-table-column prop="p5"
                         :label="title.label9">
        </el-table-column>
        <el-table-column prop="p6"
                         :label="title.label10">
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
          mean: "4200",
          cv: 1.2,
          cs: 2.5,
          p1: 5455,
          p2: 5200,
          p3: 5050,
          p4: 4800,
          p5: 4600,
          p6: 4400
        },
        {
          number: 2,
          mean: "4200",
          cv: 1.2,
          cs: 2.5,
          p1: 5455,
          p2: 5200,
          p3: 5050,
          p4: 4800,
          p5: 4600,
          p6: 4400
        },
        {
          number: 3,
          mean: "4200",
          cv: 1.2,
          cs: 2.5,
          p1: 5455,
          p2: 5200,
          p3: 5050,
          p4: 4800,
          p5: 4600,
          p6: 4400
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
