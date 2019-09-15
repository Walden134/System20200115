<template>
  <el-row class="designfloodtable">
    <el-col :span="24" class="mtable">
      <div class="table_name">{{title.title}}</div>
      <el-table :data="tableData" :cell-class-name="Ftable" height=400 fit style="width:99%;"
        :row-style="{height:'20px'}" :cell-style="{padding:'0px'}">
        <el-table-column prop="circumstances" label="情景" width="80px">
        </el-table-column>
        <el-table-column label="全年">
          <el-table-column prop="annual_power" label="发电量" width="70px"></el-table-column>
          <el-table-column prop="annual_amp" label="增幅" width="70px"></el-table-column>
        </el-table-column>
        <el-table-column label="丰水期">
          <el-table-column prop="wet_power" label="发电量" width="70px"></el-table-column>
          <el-table-column prop="wet_amp" label="增幅" width="70px"></el-table-column>
        </el-table-column>
        <el-table-column label="平水期">
          <el-table-column prop="normal_power" label="发电量" width="70px"></el-table-column>
          <el-table-column prop="normal_amp" label="增幅" width="70px"></el-table-column>
        </el-table-column>
        <el-table-column label="枯水期">
          <el-table-column prop="dry_power" label="发电量" width="70px"></el-table-column>
          <el-table-column prop="dry_amp" label="增幅" width="70px"></el-table-column>
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
          circumstances: "设计值",
          annual_power: "130",
          annual_amp: "-",
          wet_power: "130",
          wet_amp: "-",
          normal_power: "130",
          normal_amp: "-",
          dry_power: "130",
          dry_amp: "-"
        }
      ]
    };
  },
  mounted() {
    bus.$on("powerList", data => (this.powerList = data));
    bus.$on("xAxis", data => (this.xAxis = data));
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

