<template>
  <div style="width:100%">
    <el-form :inline="true" class="demo-form-inline" label-width="140px" :label-position="labelPosition">
      <!-- <el-form-item label="最优模型参数"
                    style="margin-bottom: 20px; ">
      </el-form-item> -->
      <div class="mark">数据导入</div>
      <div class="input">
        <label class="floodrisk_label" for="">典型洪水过程线</label>
        <uploadExcel @func="getTypicalFloods"> </uploadExcel>
        <label class="floodrisk_label" for="">水位库容关系&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <uploadExcel @func="getLevelCapacityCurve"> </uploadExcel>
        <label class="floodrisk_label" for="">水位泄流能力&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <uploadExcel @func="getLeveldownOutflowCurve"> </uploadExcel>
      </div>

      <div class="mark">参数设置</div>
      <div class="params">
        <!-- <el-form-item label="起调水位" style="margin-bottom: 1px; ">
          <el-input style=" width:100px" v-model="formData.hydrostation.Level1"></el-input>
        </el-form-item> -->
        <el-form-item label="设计洪水位(m)" style="margin-bottom: 1px; ">
          <el-input style=" width:100px" v-model="floodRisk.levelDesign"></el-input>
        </el-form-item>
        <el-form-item label="校核洪水位(m)" style="margin-bottom: 1px; ">
          <el-input style=" width:100px" v-model="floodRisk.levelCheck"></el-input>
        </el-form-item>
        <el-form-item label="坝顶高程(m)" style="margin-bottom: 1px; ">
          <el-input style=" width:100px" v-model="floodRisk.levelDam"></el-input>
        </el-form-item>
        <!-- <el-form-item label="计算时段" style="margin-bottom: 1px; ">
          <el-input style=" width:100px" v-model="formData.hydrostation.Level5"></el-input>
        </el-form-item> -->
        <el-form-item label="情景选择" style="margin-bottom: 10px; ">
          <el-select style=" width:100px" v-model="floodRisk.pattern">
            <el-option label="RCP2.6" value="26"></el-option>
            <el-option label="RCP4.5" value="45"></el-option>
            <el-option label="RCP8.5" value="85"></el-option>
          </el-select>
        </el-form-item>
      </div>
      <el-form-item style="margin-top: 10px;margin-bottom: 10px">
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
export default {
  name: "inputData",
  props: {},
  data() {
    return {
      labelPosition: "left",

      floodRisk: {
        typicalFloods: [],
        levelCapacityCurve: { curveData: [] },
        leveldownOutflowCurve: { curveData: [] },
        levelDesign: "2096.27",
        levelCheck: "2099.91",
        levelDam: "2102",
        pattern: "26"
      },
      riskRes: []
    };
  },
  components: {
    uploadExcel: UploadExcel
  },
  methods: {
    resetData() {
      this.riskRes = [];
      bus.$emit("riskRes", []);
    },
    getTypicalFloods(data) {
      let i = 0;
      this.floodRisk.typicalFloods[0] = [];
      this.floodRisk.typicalFloods[1] = [];
      data.map(val => {
        this.floodRisk.typicalFloods[0][i] = val["典型洪水过程"];
        this.floodRisk.typicalFloods[1][i] = val["典型洪水"];
        i++;
      });
    },

    getLeveldownOutflowCurve(data) {
      let i = 0;
      data.map(val => {
        this.floodRisk.leveldownOutflowCurve.curveData[i] = [];
        this.floodRisk.leveldownOutflowCurve.curveData[i] = [];
        this.floodRisk.leveldownOutflowCurve.curveData[i][0] = val["水位"];
        this.floodRisk.leveldownOutflowCurve.curveData[i][1] = val["下泄流量"];
        i++;
      });
    },
    getLevelCapacityCurve(data) {
      let i = 0;
      data.map(val => {
        this.floodRisk.levelCapacityCurve.curveData[i] = [];
        this.floodRisk.levelCapacityCurve.curveData[i] = [];
        this.floodRisk.levelCapacityCurve.curveData[i][0] = val["水位"];
        this.floodRisk.levelCapacityCurve.curveData[i][1] = val["库容"];
        i++;
      });
    },
    submitClick() {
      var _this = this;
      if (
        _this.floodRisk.typicalFloods.length == 0 ||
        _this.floodRisk.levelCapacityCurve.curveData.length == 0 ||
        _this.floodRisk.leveldownOutflowCurve.curveData.length == 0 ||
        _this.floodRisk.levelDesign === "" ||
        _this.floodRisk.levelDam === "" ||
        _this.floodRisk.levelCheck === "" ||
        _this.floodRisk.pattern === ""
      ) {
        _this.$alert("请先导入数据或设置参数值!", "失败!");
        return;
      }
      bus.$emit("riskRes", []);
      getRequest(
        "/flood/calcRisk" + "?floodRisk=" + JSON.stringify(_this.floodRisk)
      ).then(
        resp => {
          if (resp.status == 200) {
            //成功
            bus.$emit("riskRes", resp.data.riskRes);
            _this.riskRes = resp.data.riskRes;
          } else {
            //失败
            _this.$alert("计算失败!", "失败!");
          }
        },
        resp => {
          _this.$alert("请重新登陆⊙﹏⊙∥!", "失败!");
        }
      );
    }
  },
  created() {
    bus.$emit("riskRes", this.riskRes);
  },
  watch: {
    riskRes: {
      handler: storageUtils.saveRiskRes,
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
