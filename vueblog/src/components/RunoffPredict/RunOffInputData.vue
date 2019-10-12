<template>
  <div style="width:100%">
    <el-form :inline="true" class="demo-form-inline" :label-position="labelPosition" size="small">
      <div class="run_mark">图层导入</div>
      <div class="input">
        <div>
          <label class="run_label" for="" style="width:68px">DEM</label>
          <uploadExcel @func="getDEM"> </uploadExcel>
        </div>
        <div>
          <label class="run_label" for="">土地利用</label>
          <uploadExcel @func="getLanduse"> </uploadExcel>
        </div>
        <div>
          <label class="run_label" for="">土壤类型</label>
          <uploadExcel @func="getSoil"> </uploadExcel>
        </div>
        <div>
          <label class="run_label" for="">站点分布</label>
          <uploadExcel @func="getSite"> </uploadExcel>
        </div>
        <input id="fileFolder" name="fileFolder" type="file" webkitdirectory>
      </div>

      <div class="run_mark">数据输入</div>
      <div class="input">
        <div>
          <label class="run_label" for="" style="width:68px">未来气温</label>
          <uploadExcel @func="getTemperature"> </uploadExcel>
        </div>
        <div>
          <label class="run_label" for="" style="width:68px">未来降水</label>
          <uploadExcel @func="getPrecipitation"> </uploadExcel>
        </div>
        <div>
          <label class="run_label" for="" style="width:68px">实测径流</label>
          <uploadExcel @func="getRunoff"> </uploadExcel>
        </div>
      </div>

      <div class="run_mark">方案设置</div>
      <div class="input">
        <label class="run_label" for="">参数方案</label>
        <uploadExcel @func="getParamsPlan"> </uploadExcel>
        <label class="run_label" for="">断面选择</label>
        <div style="margin-bottom: 1px; ">
          <el-select style=" width:95px" v-model="section">
            <el-option label="雅江" value="雅江"></el-option>
            <el-option label="洼里" value="洼里"></el-option>
            <el-option label="小得石" value="小得石"></el-option>
            <el-option label="麦地龙" value="麦地龙"></el-option>
          </el-select>
        </div>
        <label class="run_label" for="">计算时段</label>
        <div style="margin-bottom: 1px; ">
          <el-select style=" width:95px" v-model="period">
            <el-option label="日" value="日"></el-option>
            <el-option label="月" value="月"></el-option>
          </el-select>
        </div>
        <label class="run_label" for="">情景选择</label>
        <div style="margin-bottom: 1px; ">
          <el-select style=" width:95px" v-model="pattern">
            <el-option label="RCP2.6" value="RCP2.6"></el-option>
            <el-option label="RCP4.5" value="RCP4.5"></el-option>
            <el-option label="RCP8.5" value="RCP8.5"></el-option>
          </el-select>
        </div>
      </div>
      <el-form-item style="margin-top: 10px;margin-bottom: 0px">
        <el-button type="primary" @click.native.prevent="submitClick">开始计算</el-button>
        <el-button>保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import UploadExcel from "@/components/UploadExcel";
import { getRequest } from "../../utils/api";
import { putRequest } from "../../utils/api";
import { postRequest } from "../../utils/api";
import storageUtils from "../../utils/storageUtils";
export default {
  props: {},
  data() {
    return { labelPosition: "left", section: "", period: "", pattern: "" };
  },
  components: {
    uploadExcel: UploadExcel
  },
  methods: {
    getDEM(data) {},
    getLanduse(data) {},
    getSoil(data) {},
    getSite(data) {},
    getTemperature(data) {},
    getPrecipitation(data) {},
    getRunoff(data) {},
    getParamsPlan(data) {},
    submitClick: function() {}
  }
};
</script>
<style>
.run_mark {
  font: 19px "Microsoft YaHei";
  position: relative;
  top: 10px;
  right: -13px;
  width: 100px;
  z-index: 10;
  border-radius: 5px;
  background-color: #3366ff;
  color: white;
}
.run_label {
  text-align: right;
  vertical-align: middle;
  float: left;
  font-size: 14px;
  color: #606266;
  line-height: 40px;
  padding: 0 12px 0 0;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
</style>
