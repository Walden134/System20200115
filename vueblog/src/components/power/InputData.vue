<template>
  <div class="form" style="width:100%">
    <el-form :inline="true" :model="formData" class="demo-form-inline" label-width="140px"
      :label-position="labelPosition" size="small">
      <el-form-item label="死水位(m)" style="margin-bottom: 1px; ">
        <el-input style=" width:80px" v-model="hydrostation.levelDead"></el-input>
      </el-form-item>
      <el-form-item label="正常蓄水位(m)" style="margin-bottom: 1px; ">
        <el-input style=" width:80px" v-model="hydrostation.levelNormal"></el-input>
      </el-form-item>
      <el-form-item label="最大下泄流量(m³/s)" style="margin-bottom: 1px; ">
        <el-input style=" width:80px" v-model="hydrostation.outflowMax" placeholder="请选择计算时段"></el-input>
      </el-form-item>
      <el-form-item label="最小下泄流量(m³/s)" style="margin-bottom: 1px; ">
        <el-input style=" width:80px" v-model="hydrostation.outflowMin"></el-input>
      </el-form-item>
      <el-form-item label="出力系数" style="margin-bottom: 1px; ">
        <el-input style=" width:80px" v-model="hydrostation.outputCoefficient"></el-input>
      </el-form-item>
      <el-form-item label="起调水位(m)" style="margin-bottom: 1px; ">
        <el-input style=" width:80px" v-model="calculateBean.levelBegin"></el-input>
      </el-form-item>
      <el-form-item label="结束水位(m)" style="margin-bottom: 1px; ">
        <el-input style=" width:80px" v-model="calculateBean.levelEnd"></el-input>
      </el-form-item>
      <el-form-item label="计算时段" style="margin-bottom: 1px; ">
        <el-select style=" width:80px" v-model="calculateBean.region">
          <el-option label="日" value="日"></el-option>
          <el-option label="时" value="时"></el-option>
        </el-select>
      </el-form-item>
      <div style="height:68px">
        <el-form-item label="水位库容曲线" style="margin-bottom: 1px; ">
          <upload_excel :fileList="levelCapacityCurve" style=" 80px" @func="getLevelCapacityCurve"></upload_excel>
        </el-form-item>
      </div>
      <div style="height:68px">
        <el-form-item label="下泄尾水曲线" style="margin-bottom: 1px; ">
          <upload_excel :fileList="leveldownOutflowCurve" style=" width:80px" @func="getLeveldownOutflowCurve">
          </upload_excel>
        </el-form-item>
      </div>
      <div style="height:68px">
        <el-form-item label="水头损失曲线" style="margin-bottom: 1px; ">
          <upload_excel :fileList="headlossOutflowCurve" style=" width:80px" @func="getHeadlossOutflowCurve">
          </upload_excel>
        </el-form-item>
      </div>
      <label class="el-form-item__label" style="width: 100%; height:32px">情景选择</label>
      <el-checkbox-group v-model="checkedSituations" @change="handleCheckedSituationsChange" :min="1" size="mini">
        <el-checkbox v-for="situation in situations" :label="situation" :key="situation"></el-checkbox>
      </el-checkbox-group>
      <label class="el-form-item__label" style="width: 100%; height:32px">模式选择</label>
      <el-checkbox-group v-model="checkedPatterns" @change="handleCheckedPatternsChange" :min="1" size="mini">
        <el-checkbox v-for="pattern in patterns" :label="pattern" :key="pattern"></el-checkbox>
      </el-checkbox-group>
      <el-form-item label="数据源选择" style="margin-bottom: 1px; ">
        <el-select style=" width:80px" v-model="calculateBean.region">
          <el-option label="日" value="日"></el-option>
          <el-option label="时" value="时"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item style="margin-top: 10px;margin-bottom: 0px">
        <el-button type="primary" @click.native.prevent="submitClick">计算</el-button>
        <el-button>取消</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import UploadExcel from "@/components/power/UploadExcel";
import { getRequest } from "../../utils/api";
import { putRequest } from "../../utils/api";
import { postRequest1 } from "../../utils/api";
const patternOptions = ["Base", "RCP2.6", "RCP4.5", "RCP8.5"];
const situationOptions = ["GFDL", "CNRM", "CanESM", "MIROC", "BMA"];

export default {
  name: "inputData",
  props: {},
  data() {
    return {
      levelCapacityCurve: [{ name: "", url: "" }],
      leveldownOutflowCurve: [{ name: "", url: "" }],
      headlossOutflowCurve: [{ name: "", url: "" }],
      checkedPatterns: ["Base"],
      patterns: patternOptions,
      checkedSituations: ["GFDL", "CNRM", "CanESM", "MIROC", "BMA"],
      situations: situationOptions,
      labelPosition: "left",
      formData: {},
      hydrostation: {
        // id: "1",
        // name: "杨房沟",
        levelDead: "2088",
        levelNormal: "2094",
        outflowMax: "15200",
        outflowMin: "0",
        installPower: 90,
        levelCapacityCurve: { curveData: [] },
        leveldownOutflowCurve: { curveData: [] },
        headlossOutflowCurve: { curveData: [] },
        ExpectOutputHeadCurve: { curveData: [] },
        outputCoefficient: "8.5"
      },
      calculateBean: {
        situations: ["GFDL", "CNRM", "CanESM", "MIROC", "BMA"],
        patterns: ["Base"],
        levelBegin: "2094",
        levelEnd: "2094",
        region: "时"
      }
    };
  },
  components: {
    upload_excel: UploadExcel
  },
  methods: {
    getLevelCapacityCurve(data) {
      let i = 0;
      this.hydrostation.levelCapacityCurve.curveData[0] = [];
      this.hydrostation.levelCapacityCurve.curveData[1] = [];
      data.map(val => {
        this.hydrostation.levelCapacityCurve.curveData[0][i] = val["水位"];
        this.hydrostation.levelCapacityCurve.curveData[1][i] = val["库容"];
        i++;
      });
    },
    getHeadlossOutflowCurve(data) {
      let i = 0;
      this.hydrostation.headlossOutflowCurve.curveData[0] = [];
      this.hydrostation.headlossOutflowCurve.curveData[1] = [];
      data.map(val => {
        this.hydrostation.headlossOutflowCurve.curveData[0][i] = val["距离"];
        this.hydrostation.headlossOutflowCurve.curveData[1][i] =
          val["水头损失"];
        i++;
      });
    },
    getLeveldownOutflowCurve(data) {
      let i = 0;
      this.hydrostation.leveldownOutflowCurve.curveData[0] = [];
      this.hydrostation.leveldownOutflowCurve.curveData[1] = [];
      data.map(val => {
        this.hydrostation.leveldownOutflowCurve.curveData[0][i] = val["水位"];
        this.hydrostation.leveldownOutflowCurve.curveData[1][i] =
          val["下泄流量"];
        i++;
      });
    },
    handleCheckedPatternsChange(value) {
      this.calculateBean.patterns = value;
      this.$emit("patterns", value);
    },
    handleCheckedSituationsChange(value) {
      this.calculateBean.situations = value;
      this.$emit("situations", value);
    },
    submitClick: function() {
      var _this = this;
      postRequest1("/power/submit", {
        hydrostation: _this.hydrostation,
        calculateBean: _this.calculateBean
      }).then(
        resp => {
          if (resp.status == 200) {
            //成功
            var json = resp.data;
            if (json.status == "success") {
              _this.$alert("计算成功!", "成功!");
              // _this.$router.replace({ path: "/home" });
            } else {
              _this.$alert("计算失败!", "失败!");
            }
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
  },
  mounted() {},
  watch: {}
};
</script>
<style>
.el-checkbox + .el-checkbox {
  margin: 0px;
}
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
