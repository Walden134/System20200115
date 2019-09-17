<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="etable">
      <div class="table_name">{{title.title}}</div>
      <el-table :data="tableData" stripe style="width:calc(100% - 5px);border:2px;" height:270px
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
export default {
  props: ["title"],
  methods: {},
  data() {
    return {
      tableData: [
        {
          title: "均值",
          first: "4200",
          second: "4300",
          third: "4400"
        },
        {
          title: "Cv",
          first: "4200",
          second: "4300",
          third: "4400"
        },
        {
          title: "Cs",
          first: "4200",
          second: "4300",
          third: "4400"
        },
        {
          title: "0.1%",
          first: "4200",
          second: "4300",
          third: "4400"
        },
        {
          title: "0.2%",
          first: "4200",
          second: "4300",
          third: "4400"
        },
        {
          title: "0.2%",
          first: "4200",
          second: "4300",
          third: "4400"
        },
        {
          title: "0.5%",
          first: "4200",
          second: "4300",
          third: "4400"
        },
        {
          title: "0.5%",
          first: "4200",
          second: "4300",
          third: "4400"
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
