<template>
  <div style="width:100%">
    <el-form :inline="true" class="demo-form-inline" :label-position="labelPosition" size="small">
      <div class="run_mark">图层导入</div>
      <div class="input">
        <div>
          <label class="run_label" for="" style="width:68px">DEM</label>
          <!-- <uploadExcel @func="getDEM"> </uploadExcel> -->
          <el-upload class="upload-demo upload-excel" ref="upload" list-type='text' :show-file-list='true'
            action='/upload/dem' :limit="1" :on-exceed="handleExceed" accept=".txt">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          </el-upload>
        </div>
        <div>
          <label class="run_label" for="">土地利用</label>
          <!-- <uploadExcel @func="getLanduse"> </uploadExcel> -->
          <el-upload class="upload-demo upload-excel" ref="upload" list-type='text' :show-file-list='true'
            action='/upload/landuse' :limit="1" :on-exceed="handleExceed" accept=".txt">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          </el-upload>
        </div>
        <div>
          <label class="run_label" for="">土壤类型</label>
          <!-- <uploadExcel @func="getSoil"> </uploadExcel> -->
          <el-upload class="upload-demo upload-excel" ref="upload" list-type='text' :show-file-list='true'
            action='/upload/soil' :limit="1" :on-exceed="handleExceed" accept=".txt">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          </el-upload>
        </div>
        <div>
          <label class="run_label" for="">站点分布</label>
          <!-- <uploadExcel @func="getSite"> </uploadExcel> -->
          <el-upload class="upload-demo upload-excel" ref="upload" list-type='text' :show-file-list='true'
            action='/upload/site' :limit="1" :on-exceed="handleExceed" accept=".txt">
            <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          </el-upload>
        </div>
      </div>

      <!-- <input type="file" id="filepicker" name="fileList" webkitdirectory multiple />
      <button type="button" id="subButton" @click="commit" @change="change">Submit</button> -->

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
        <el-upload class="upload-demo upload-excel" ref="upload" list-type='text' :show-file-list='true'
          action='/upload/paramescheme' :limit="1" :on-exceed="handleExceed" accept=".txt">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        </el-upload>

        <label class="run_label" for="">开始日期</label>
        <div style="margin-bottom: 1px; ">
          <div class="block">
            <el-date-picker v-model="dtbeg" value-format="yyyy-MM-dd" style=" width:135px" type="date"
              placeholder="选择日期">
            </el-date-picker>
          </div>
        </div>
        <label class="run_label" for="">结束日期</label>
        <div style="margin-bottom: 1px; ">
          <div class="block">
            <el-date-picker v-model="dtend" value-format="yyyy-MM-dd" style=" width:135px" type="date"
              placeholder="选择日期">
            </el-date-picker>
          </div>
        </div>
        <label class="run_label" for="">断面选择</label>
        <div style="margin-bottom: 1px; ">
          <el-select style=" width:135px" v-model="section">
            <el-option label="雅江" value="雅江"></el-option>
            <el-option label="洼里" value="洼里"></el-option>
            <el-option label="小得石" value="小得石"></el-option>
            <el-option label="麦地龙" value="麦地龙"></el-option>
          </el-select>
        </div>
        <label class="run_label" for="">计算时段</label>
        <div style="margin-bottom: 1px; ">
          <el-select style=" width:135px" v-model="period">
            <el-option label="日" value="日"></el-option>
            <el-option label="月" value="月"></el-option>
          </el-select>
        </div>
        <label class="run_label" for="">情景选择</label>
        <div style="margin-bottom: 1px; ">
          <el-select style=" width:135px" v-model="rcp">
            <el-option label="RCP2.6" value="26"></el-option>
            <el-option label="RCP4.5" value="45"></el-option>
            <el-option label="RCP8.5" value="85"></el-option>
          </el-select>
        </div>
        <!-- CanESM CNRM GFDL MIROC -->
        <!-- <label class="run_label" for="">模式选择</label>
        <div style="margin-bottom: 1px; ">
          <el-checkbox-group v-model="gcms" :min="1" size="medium">
            <el-checkbox label="CanESM"></el-checkbox>
            <el-checkbox label="CNRM"></el-checkbox>
            <el-checkbox label="GFDL"></el-checkbox>
            <el-checkbox label="MIROC"></el-checkbox>
          </el-checkbox-group>
        </div>
        <div style="margin-bottom: 1px; ">
          <el-select style=" width:135px" v-model="gcm">
            <el-option label="CanESM" value="CanESM"></el-option>
            <el-option label="CNRM" value="CNRM"></el-option>
            <el-option label="GFDL" value="GFDL"></el-option>
            <el-option label="MIROC" value="MIROC"></el-option>
          </el-select>
        </div> -->
      </div>

      <el-form-item style="margin-top: 10px;margin-bottom: 0px">
        <el-button type="primary" @click.native.prevent="submitClick">开始计算</el-button>
        <el-button type="warning" @click.native.prevent="resetData">重置</el-button>
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
import $ from "jquery";

export default {
  data() {
    return {
      labelPosition: "left",
      section: "小得石",
      period: "日",
      rcp: "26",
      gcm: "GFDL",
      dtbeg: "2021-1-1",
      dtend: "2022-12-31",
      gcms: ["CanESM", "CNRM", "GFDL", "MIROC"]
    };
  },
  components: {
    uploadExcel: UploadExcel
  },
  methods: {
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，请删除后继续上传`);
    },
    getDEM(data) {},
    getLanduse(data) {},
    getSoil(data) {},
    getSite(data) {},
    getTemperature(data) {},
    getPrecipitation(data) {},
    getRunoff(data) {},
    getParamsPlan(data) {},
    submitClick: function() {
      var _this = this;
      getRequest("/runoff/runSwat", {
        section: _this.section,
        period: _this.period,
        rcp: _this.rcp,
        gcm: _this.gcm,
        // gcms: _this.gcms,
        dtbeg: _this.dtbeg,
        dtend: _this.dtend
      }).then(
        resp => {
          if (resp.status == 200) {
            bus.$emit("swatData", resp.data);
          } else {
            _this.$alert("计算失败!", "失败!");
          }
        },
        resp => {
          _this.$alert("找不到服务器⊙﹏⊙∥!", "失败!");
        }
      );
    },
    commit: function() {
      let files;
      document.getElementById("filepicker").addEventListener(
        "change",
        function(event) {
          let output = document.getElementById("listing");
          files = event.target.files;
          for (let i = 0; i < files.length; i++) {
            let item = document.createElement("li");
            item.innerHTML = files[i].webkitRelativePath;
            output.appendChild(item);
          }
        },
        false
      );
      $("#subButton").click(function() {
        var fd = new FormData();
        for (var i = 0; i < files.length; i++) {
          fd.append("file", files[i]);
        }
        $.ajax({
          url: "/upload/uploadFolder",
          method: "POST",
          data: fd,
          contentType: false,
          processData: false,
          cache: false,
          success: function(data) {
            console.log(data);
          }
        });
      });
    },
    change: function(e) {
      //判断是否选中文件
      var file = $("#fileFolder").val();
      if (file != "") {
        $("#msg").text("");
      }
      var files = e.target.files; // FileList
      //文件数量
      actual_filesCount = files.length;
      if (actual_filesCount > filesCount) {
        $("#msg").text("文件过多，单次最多可上传" + filesCount + "个文件");
        return;
      }
      for (var i = 0, f; (f = files[i]); ++i) {
        actual_filesSize += f.size;
        if (actual_filesSize > filesSize) {
          $("#msg").text(
            "单次文件夹上传不能超过" + filesSize / 1024 / 1024 + "M"
          );
          return;
        }
      }
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
