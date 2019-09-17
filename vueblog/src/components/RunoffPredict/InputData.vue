<template>
  <div style="width:100%">
    <el-form :inline="true" :model="formData" class="demo-form-inline" :label-position="labelPosition" size="small">
      <div class="run_mark">图层导入</div>
      <div class="input">
        <div>
          <label class="run_label" for="" style="width:68px">DEM</label>
          <upload_excel :fileList="DEM" @func="getDEM"> </upload_excel>
        </div>
        <div>
          <label class="run_label" for="">土地利用</label>
          <upload_excel :fileList="landuse" @func="getLanduse"> </upload_excel>
        </div>
        <div>
          <label class="run_label" for="">土壤类型</label>
          <upload_excel :fileList="soil" @func="getSoil"> </upload_excel>
        </div>
        <div>
          <label class="run_label" for="">站点分布</label>
          <upload_excel :fileList="site" @func="getSite"> </upload_excel>
        </div>
      </div>

      <div class="run_mark">数据输入</div>
      <div class="input">
        <div>
          <label class="run_label" for="" style="width:68px">气温</label>
          <upload_excel :fileList="temperature" @func="getTemperature"> </upload_excel>
        </div>
        <div>
          <label class="run_label" for="" style="width:68px">降水</label>
          <upload_excel :fileList="precipitation" @func="getPrecipitation"> </upload_excel>
        </div>
        <div>
          <label class="run_label" for="" style="width:68px">径流</label>
          <upload_excel :fileList="runoff" @func="getRunoff"> </upload_excel>
        </div>
      </div>

      <div class="run_mark">方案设置</div>
      <div class="input">
        <label class="run_label" for="">参数方案</label>
        <upload_excel :fileList="paramsPlan" @func="getParamsPlan"> </upload_excel>
        <label class="run_label" for="">断面选择</label>
        <div style="margin-bottom: 1px; ">
          <el-select style=" width:95px" v-model="hydrostation.sectionSelect">
            <el-option label="雅江" value="雅江"></el-option>
            <el-option label="洼里" value="洼里"></el-option>
            <el-option label="小得石" value="小得石"></el-option>
            <el-option label="麦地龙" value="麦地龙"></el-option>
          </el-select>
        </div>
        <label class="run_label" for="">计算时段</label>
        <div style="margin-bottom: 1px; ">
          <el-select style=" width:95px" v-model="calculateBean.calculatePeriod">
            <el-option label="日" value="日"></el-option>
            <el-option label="月" value="月"></el-option>
          </el-select>
        </div>
        <label class="run_label" for="">情景选择</label>
        <div style="margin-bottom: 1px; ">
          <el-select style=" width:95px" v-model="calculateBean.sceneSelect">
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
import { postRequest1 } from "../../utils/api";
import { postRequest } from "../../utils/api";

export default {
  props: {},
  data() {
    return {
      hydrostation: {
        sectionSelect: ""
      },
      calculateBean: {
        calculatePeriod: "",
        sceneSelect: ""
      }
    };
  },
  components: {
    upload_excel: UploadExcel
  },
  methods: {
    submitClick: function() {
      var _this = this;
      getRequest(
        "/power/submit" +
          "?hydrostation=" +
          JSON.stringify(_this.hydrostation) +
          "&calculateBean=" +
          JSON.stringify(_this.calculateBean)
      ).then(
        resp => {
          if (resp.status == 200) {
            //成功
            _this.$store.commit("flag", true);
            bus.$emit("xAxis", resp.data.xAxis);
            bus.$emit("powerList", resp.data.powerList);
            bus.$emit("outputList", resp.data.outputList);
            _this.$alert("计算成功!", "成功!");
          } else {
            //失败
            _this.$alert("计算失败!", "失败!");
          }
        },
        resp => {
          _this.$alert("找不到服务器⊙﹏⊙∥!", "失败!");
        }
      );
    }
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
  background-color: blue;
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
