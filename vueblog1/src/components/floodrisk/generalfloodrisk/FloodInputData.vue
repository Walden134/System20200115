<template>
  <div style="width:100%">
    <el-form :inline="true"
             class="demo-form-inline"
             label-width="140px"
             :label-position="labelPosition">
      <div class="mark">参数设置</div>
      <div class="params">
        <el-form-item label="样本均值Ex"
                      style="margin-bottom: 1px; ">
          <el-input style=" width:100px"
                    v-model="ex"></el-input>
        </el-form-item>
        <el-form-item label="变差系数Cv"
                      style="margin-bottom: 1px; ">
          <el-input style=" width:100px"
                    v-model="cv"></el-input>
        </el-form-item>
        <el-form-item label="偏态系数Cs"
                      style="margin-bottom: 1px; ">
          <el-input style=" width:100px"
                    v-model="cs"></el-input>
        </el-form-item>

        <el-form-item label="情景选择"
                      style="margin-bottom: 10px; ">
          <el-select style=" width:100px"
                     v-model="rcp">
            <el-option label="RCP2.6"
                       value="26"></el-option>
            <el-option label="RCP4.5"
                       value="45"></el-option>
            <el-option label="RCP8.5"
                       value="85"></el-option>
          </el-select>
        </el-form-item>
      </div>
      <el-form-item style="margin-top: 10px;margin-bottom: 10px">
        <el-button type="primary"
                   @click.native.prevent="submitClick">开始计算</el-button>
        <el-button type="warning"
                   @click.native.prevent="resetData">重置</el-button>
      </el-form-item>

    </el-form>
  </div>

</template>

<script>
import UploadExcel from "@/components/UploadExcel";

import { getRequest } from "@/utils/api";
import { postRequest } from "@/utils/api";
import storageUtils from "@/utils/storageUtils";
export default {
  data() {
    return {
      labelPosition: "left",
      ex: "4160",
      cv: "0.29",
      cs: "1.16",
      rcp: "26",
      gRiskRes: {}
    };
  },
  components: {
    uploadExcel: UploadExcel
  },
  methods: {
    submitClick() {
      var _this = this;
      bus.$emit("gRiskRes", {});
      getRequest(
        "/flood/calcGRisk" +
          "?ex=" +
          _this.ex +
          "&cv=" +
          _this.cv +
          "&cs=" +
          _this.cs +
          "&rcp=" +
          _this.rcp
      ).then(
        resp => {
          if (resp.status == 200) {
            //成功
            bus.$emit("gRiskRes", resp.data);
            _this.gRiskRes = resp.data;
            console.log("gRiskRes", _this.gRiskRes.canesm);
          } else {
            //失败
            _this.$alert("计算失败!", "失败!");
          }
        },
        resp => {
          _this.$alert("请重新登陆⊙﹏⊙∥!", "失败!");
        }
      );
    },
    resetData() {
      // this.gRiskRes = {};
      // this.ex = "";
      // this.cv = "";
      // this.cs = "";
      // this.rcp = "";
      bus.$emit("gRiskRes", {});
    }
  },
  created() {
    bus.$emit("gRiskRes", this.gRiskRes);
  },
  watch: {
    gRiskRes: {
      handler: storageUtils.saveGRiskRes,
      deep: true
    }
  }
};
</script>

<style>
.mark {
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

.floodrisk_label {
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
