<template>
  <div class="form" style="width:100%">
    <el-form :inline="true" :model="form" class="demo-form-inline" label-width="140px" :label-position="labelPosition">
      <el-form-item label="死水位(m)" style="margin-bottom: 5px; ">
        <el-input style=" width:80px" v-model="form.LevelDead"></el-input>
      </el-form-item>
      <el-form-item label="正常蓄水位(m)" style="margin-bottom: 5px; ">
        <el-input style=" width:80px" v-model="form.LevelNormal"></el-input>
      </el-form-item>
      <el-form-item label="最大下泄流量(m³/s)" style="margin-bottom: 5px; ">
        <el-input style=" width:80px" v-model="form.OutflowMax"></el-input>
      </el-form-item>
      <el-form-item label="最小下泄流量(m³/s)" style="margin-bottom: 5px; ">
        <el-input style=" width:80px" v-model="form.OutflowMin"></el-input>
      </el-form-item>
      <el-form-item label="出力系数" style="margin-bottom: 5px; ">
        <el-input style=" width:80px" v-model="form.OutputCoefficient"></el-input>
      </el-form-item>
      <el-form-item label="起调水位(m)" style="margin-bottom: 5px; ">
        <el-input style=" width:80px" v-model="form.LevelBegin"></el-input>
      </el-form-item>
      <el-form-item label="结束水位(m)" style="margin-bottom: 5px; ">
        <el-input style=" width:80px" v-model="form.LevelEnd"></el-input>
      </el-form-item>
      <el-form-item label="计算时段" style="margin-bottom: 5px; ">
        <el-select style=" width:80px" v-model="form.region" placeholder="请选择计算时段">
          <el-option label="日" value="24"></el-option>
          <el-option label="时" value="1"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="水位库容曲线" style="margin-bottom: 5px; ">
        <upload_excel :fileList="fileList1" style=" width:80px"></upload_excel>
      </el-form-item>
      <el-form-item label="下泄尾水曲线" style="margin-bottom: 5px; ">
        <upload_excel :fileList="fileList1" style=" width:80px"></upload_excel>
      </el-form-item>
      <el-form-item label="水头损失曲线" style="margin-bottom: 5px; ">
        <upload_excel :fileList="fileList2" style=" width:80px">
        </upload_excel>
      </el-form-item>
      <el-form-item style="margin-top: 10px;margin-bottom: 0px">
        <el-button type="primary" @click="onSubmit">计算</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import UploadExcel from "@/components/UploadExcel";

import { getRequest } from "../utils/api";
import { putRequest } from "../utils/api";
export default {
  name: "inputData",
  props: {},
  data() {
    return {
      fileList1: [
        {
          name: "food.jpeg",
          url:
            "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
        }
      ],
      fileList2: [
        {
          name: "food2.jpeg",
          url:
            "https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100"
        }
      ],
      labelPosition: "left",
      form: {
        LevelDead: 2088,
        LevelNormal: 2094,
        OutflowMax: 15200,
        OutflowMin: 0,
        OutputCoefficient: 8.5,
        LevelBegin: 2094,
        LevelEnd: 2094,
        region: "时"
      }
    };
  },
  components: {
    upload_excel: UploadExcel
  },
  methods: {
    onSubmit() {
      console.log("submit!");
    }
  }
};
</script>
<style>
.form {
  border: 2px solid rgba(74, 136, 220, 0.996078431372549);
  padding: 8px;
}
.label {
  text-align: right;
  vertical-align: middle;
  float: left;
  margin-left: -128px;
  font-size: 14px;
  color: #606266;
  line-height: 40px;
  padding: 0 12px 0 0;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
.upload {
  /* float: left; */
  line-height: 40px;
  position: relative;
  font-size: 14px;
}
</style>
