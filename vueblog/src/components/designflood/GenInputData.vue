<template>
  <div style="width:100%">
    <el-form :inline="true" class="demo-form-inline" label-width="130px" :label-position="labelPosition">
      <div class="gen_mark">数据导入</div>
      <div class="input">
        <el-form-item label="洪水系列选择" style="margin-bottom: 1px; ">
          <el-select style=" width:110px" v-model="dataFlag">
            <el-option label="洪峰" value="洪峰"></el-option>
            <el-option label="24h洪量" value="24h洪量"></el-option>
            <el-option label="3日洪量" value="3日洪量"></el-option>
            <el-option label="7日洪量" value="7日洪量"></el-option>
            <el-option label="15日洪量" value="15日洪量"></el-option>
          </el-select>
        </el-form-item>
        <label class="gen_label" for="">实测洪水&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <uploadExcel style="margin-top: 10px;" @func="getMesureData"> </uploadExcel>
      </div>
      <div class="gen_mark">参数设置</div>
      <div class="params">
        <el-form-item label="样本均值Ex" style="margin-bottom: 1px; ">
          <el-input style=" width:110px" v-model="params.ex"></el-input>
        </el-form-item>
        <el-form-item label="变差系数Cv" style="margin-bottom: 1px; ">
          <el-input style=" width:110px" v-model="params.cv"></el-input>
        </el-form-item>
        <el-form-item label="偏态系数Cs" style="margin-bottom: 1px; ">
          <el-input style=" width:110px" v-model="params.cs"></el-input>
        </el-form-item>
        <el-form-item label="拟合误差" style="margin-bottom: 1px; ">
          <el-input style=" width:110px" v-model="params.fitError"></el-input>
        </el-form-item>
      </div>
      <el-form-item style="margin-top: 10px;margin-bottom: 0px">
        <el-button type="warning" @click.native.prevent="paramEstClick">参数估计</el-button>
        <el-button type="primary" @click.native.prevent="drawLineClick">绘制曲线</el-button>
      </el-form-item>

    </el-form>
  </div>
</template>
<script>
import UploadExcel from "@/components/UploadExcel";
import { getRequest } from "../../utils/api";
import { putRequest } from "../../utils/api";
import { postRequest } from "../../utils/api";
export default {
  name: "inputData",
  props: {},
  data() {
    return {
      labelPosition: "left",
      params: {
        ex: "",
        cv: "",
        cs: "",
        fitError: "",
        mesureData: []
      },
      dataFlag: "洪峰"
    };
  },
  components: {
    uploadExcel: UploadExcel
  },
  methods: {
    drawLineClick() {
      var _this = this;
      getRequest(
        "/flood/drawLine" + "?params=" + JSON.stringify(_this.params)
      ).then(
        resp => {
          if (resp.status == 200) {
            //成功
            bus.$emit("xAxis", resp.data.xAxis);
            bus.$emit("powerList", resp.data.powerList);
            bus.$emit("outputList", resp.data.outputList);
            bus.$emit("outputRatexAxis", resp.data.outputRatexAxis);
            bus.$emit("outputRateList", resp.data.outputRateList);
            _this.category = resp.data.xAxis;
            _this.powers = resp.data.powerList;
            _this.outputs = resp.data.outputList;
            _this.outputratelist = resp.data.outputRateList;
            _this.outputratexaxis = resp.data.outputRatexAxis;
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
    },
    paramEstClick() {
      var _this = this;
      getRequest(
        "/flood/paramsEst" +
          "?mesureData=" +
          JSON.stringify(_this.params.mesureData)
      ).then(
        resp => {
          if (resp.status == 200) {
            //成功
            bus.$emit("xAxis", resp.data.xAxis);
            bus.$emit("powerList", resp.data.powerList);
            bus.$emit("outputList", resp.data.outputList);
            bus.$emit("outputRatexAxis", resp.data.outputRatexAxis);
            bus.$emit("outputRateList", resp.data.outputRateList);
            _this.category = resp.data.xAxis;
            _this.powers = resp.data.powerList;
            _this.outputs = resp.data.outputList;
            _this.outputratelist = resp.data.outputRateList;
            _this.outputratexaxis = resp.data.outputRatexAxis;
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
    },
    getMesureData(data) {
      let i = 0;
      data.map(val => {
        this.params.mesureData[i] = [];
        this.params.mesureData[i][0] = val["系列值"];
        this.params.mesureData[i][1] = val["经验频率"];
        i++;
      });
    }
  },
  watch: {
    dataFlag(newVal, oldVal) {
      console.log("newVal=", newVal, "oldVal=", oldVal);
    }
  }
};
</script>
<style>
.gen_mark {
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

.gen_label {
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
