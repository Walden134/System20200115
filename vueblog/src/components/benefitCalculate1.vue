<template>
  <el-container class="article_list">
    <el-main class="main">
      <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
        <el-tab-pane label="数据导入" name="all">
          <upload_excel> </upload_excel>
        </el-tab-pane>
        <el-tab-pane label="发电量计算" name="post">
          <data_chart> </data_chart>
        </el-tab-pane>
        <el-tab-pane label="保证出力计算" name="draft">
          <blog_table state="0" :showEdit="true" :showDelete="true" :activeName="activeName"></blog_table>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import DataCharts from "@/components/DataCharts";
import UploadExcel from "@/components/UploadExcel";
import { postRequest } from "../utils/api";
import { putRequest } from "../utils/api";
import { deleteRequest } from "../utils/api";
import { getRequest } from "../utils/api";
export default {
  mounted: function() {
    var _this = this;
    getRequest("/isAdmin").then(resp => {
      if (resp.status == 200) {
        _this.isAdmin = resp.data;
      }
    });
  },
  data() {
    return {
      activeName: "post",
      isAdmin: false
    };
  },
  methods: {
    handleClick(tab, event) {
      //        console.log(tab, event);
    }
  },
  components: {
    data_chart: DataCharts,
    upload_excel: UploadExcel
  }
};
</script>
<style>
.article_list > .header {
  background-color: #ececec;
  margin-top: 10px;
  padding-left: 5px;
  display: flex;
  justify-content: flex-start;
}

.article_list > .main {
  /*justify-content: flex-start;*/
  display: flex;
  flex-direction: column;
  padding-left: 0px;
  background-color: #fff;
  padding-top: 0px;
  margin-top: 8px;
}
</style>
