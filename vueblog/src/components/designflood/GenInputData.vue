<template>
  <div style="width:100%">
    <el-form :inline="true" class="demo-form-inline" label-width="130px" :label-position="labelPosition">
      <div class="gen_mark">初始化参数</div>
      <div class="input">
        <el-form-item label="洪水系列选择" style="margin-bottom: 1px; ">
          <el-select style=" width:110px" v-model="dataFlag">
            <el-option label="洪峰" value="0"></el-option>
            <el-option label="24h洪量" value="1"></el-option>
            <el-option label="3日洪量" value="3"></el-option>
            <el-option label="7日洪量" value="7"></el-option>
            <el-option label="15日洪量" value="15"></el-option>
          </el-select>
        </el-form-item>
        <label class="gen_label" for="">实测洪水&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <uploadExcel style="margin-top: 10px;" @func="getMesureData"> </uploadExcel>
        <el-form-item label="历史调查期年数" style="margin-bottom: 1px; ">
          <el-input style=" width:110px" v-model="params.n"></el-input>
        </el-form-item>
        <el-form-item label="特大洪水项数" style="margin-bottom: 1px; ">
          <el-input style=" width:110px" v-model="params.a"></el-input>
        </el-form-item>
        <el-form-item label="实测系列的年数" style="margin-bottom: 1px; ">
          <el-input style=" width:110px" v-model="params.l"></el-input>
        </el-form-item>
      </div>
      <el-form-item style="margin-top: 10px;margin-bottom: 0px">
        <el-button type="warning" @click.native.prevent="expFrequencyClick">经验频率计算</el-button>
      </el-form-item>
      <div class="gen_mark">参数优化</div>
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
        <!-- <el-form-item label="拟合误差" style="margin-bottom: 1px; ">
          <el-input style=" width:110px" v-model="params.fitError"></el-input>
        </el-form-item> -->
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
import storageUtils from "../../utils/storageUtils";
export default {
  name: "inputData",
  props: {},
  data() {
    return {
      labelPosition: "left",
      params: {
        n: "",
        a: "",
        l: "",
        ex: "",
        cv: "",
        cs: "",
        fitError: "",
        mesureData: []
      },
      dataFlag: "0",
      expFrequency: [],
      theoryFrequency: []
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
            bus.$emit("theoryFrequency", resp.data.theoryFrequency);
            _this.theoryFrequency = resp.data.theoryFrequency;
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
    expFrequencyClick() {
      var _this = this;
      getRequest(
        "/flood/expFrequency" + "?params=" + JSON.stringify(_this.params)
      ).then(
        resp => {
          if (resp.status == 200) {
            //成功
            bus.$emit("a", this.params.a);
            bus.$emit("expFrequency", resp.data.expFrequency);
            _this.expFrequency = resp.data.expFrequency;
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
        "/flood/paramEst" + "?params=" + JSON.stringify(_this.params)
      ).then(
        resp => {
          if (resp.status == 200) {
            //成功
            _this.params.ex = resp.data.ex;
            _this.params.cv = resp.data.cv;
            _this.params.cs = resp.data.cs;
            _this.params.fitError = resp.data.fitError;
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
        this.params.mesureData[i] = val["实测洪水"];
        i++;
      });
    }
  },
  created() {
    bus.$emit("theoryFrequency", this.theoryFrequency);
    bus.$emit("expFrequency", this.expFrequency);
    bus.$emit("a", this.a);
  },
  watch: {
    expFrequency: {
      deep: true,
      handler: storageUtils.saveExpFrequency
    },
    theoryFrequency: {
      deep: true,
      handler: storageUtils.saveTheoryFrequency
    },
    params: {
      deep: true,
      handler: storageUtils.saveParams
    },
    dataFlag() {
      bus.$emit("dataFlag", this.dataFlag);
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
