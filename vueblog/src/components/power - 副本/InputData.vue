<template>
  <div style="width:100%">
    <el-form :inline="true" :model="formData" class="demo-form-inline" label-width="160px"
      :label-position="labelPosition" size="small">
      <div class="mark">数据导入</div>
      <div class="input">
        <div>
          <label class="label" for="">水位库容关系</label>
          <upload_excel :fileList="levelCapacityCurve" @func="getLevelCapacityCurve">
          </upload_excel>
        </div>
        <div>
          <label class="label" for="">尾水流量关系</label>
          <upload_excel :fileList="leveldownOutflowCurve" @func="getLeveldownOutflowCurve">
          </upload_excel>
        </div>
        <div>
          <label class="label" for="">水头损失曲线</label>
          <upload_excel :fileList="headlossOutflowCurve" @func="getHeadlossOutflowCurve">
          </upload_excel>
        </div>
        <div>
          <label class="label" for="">出力限制曲线</label>
          <upload_excel :fileList="expectOutputHeadCurve" @func="getExpectOutputHeadCurve">
          </upload_excel>
        </div>
      </div>
      <div class="mark">参数设置</div>
      <div class="params">
        <el-form-item label="死水位(m)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.levelDead"></el-input>
        </el-form-item>
        <el-form-item label="正常蓄水位(m)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.levelNormal"></el-input>
        </el-form-item>
        <el-form-item label="出力系数" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.outputCoefficient"></el-input>
        </el-form-item>
        <el-form-item label="最大下泄流量(m³/s)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.outflowMax"></el-input>
        </el-form-item>
        <el-form-item label="最小下泄流量(m³/s)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.outflowMin"></el-input>
        </el-form-item>
        <el-form-item label="起调水位(m)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="calculateBean.levelBegin"></el-input>
        </el-form-item>
        <el-form-item label="结束水位(m)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="calculateBean.levelEnd"></el-input>
        </el-form-item>
        <el-form-item label="多年平均发电量设计值" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.avgDesiginPower"></el-input>
        </el-form-item>
        <el-form-item label="保证出力设计值" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.outputDesign"></el-input>
        </el-form-item>
        <el-form-item label="计算时段" style="margin-bottom: 1px; ">
          <el-select style=" width:80px" v-model="calculateBean.deltaT">
            <el-option label="日" value="日"></el-option>
            <el-option label="时" value="时"></el-option>
          </el-select>
        </el-form-item>
      </div>
      <div class="mark">模式选择</div>
      <div class="pattern">
        <!-- <label class="el-form-item__label" style="width: 100%; height:32px">情景选择</label>
      <el-checkbox-group v-model="checkedSituations" @change="handleCheckedSituationsChange" :min="1" size="mini">
        <el-checkbox v-for="situation in situations" :label="situation" :key="situation"></el-checkbox>
      </el-checkbox-group> -->
        <!-- <label class="el-form-item__label" style="width: 100%; height:32px">模式选择</label> -->
        <el-checkbox-group v-model="checkedPatterns" @change="handleCheckedPatternsChange" :min="1" size="medium">
          <el-checkbox v-for="pattern in patterns" :label="pattern" :key="pattern" border></el-checkbox>
        </el-checkbox-group>
      </div>

      <el-form-item style="margin-top: 10px;margin-bottom: 0px">
        <el-button type="primary" @click.native.prevent="submitClick">开始计算</el-button>
        <el-button>保存</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>
<script>
import UploadExcel from "@/components/power/UploadExcel";
import { getRequest } from "../../utils/api";
import { putRequest } from "../../utils/api";
import { postRequest1 } from "../../utils/api";
import { postRequest } from "../../utils/api";
const patternOptions = ["Base", "RCP2.6", "RCP4.5", "RCP8.5"];
const situationOptions = ["GFDL", "CNRM", "CanESM", "MIROC", "BMA"];

export default {
  name: "inputData",
  props: {},
  data() {
    return {
      levelCapacityCurve: [{ name: "水位库容关系.xlsx", url: "" }],
      leveldownOutflowCurve: [{ name: "尾水流量关系.xlsx", url: "" }],
      headlossOutflowCurve: [{ name: "水头损失曲线.xlsx", url: "" }],
      expectOutputHeadCurve: [{ name: "出力限制曲线.xlsx", url: "" }],
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
        expectOutputHeadCurve: { curveData: [] },
        outputCoefficient: "8.5",
        outputDesign: "1500",
        avgDesiginPower: "130"
      },
      calculateBean: {
        situations: ["GFDL", "CNRM", "CanESM", "MIROC", "BMA"],
        patterns: ["Base"],
        levelBegin: "2094",
        levelEnd: "2094",
        deltaT: 1
      }
    };
  },
  components: {
    upload_excel: UploadExcel
  },
  methods: {
    getLevelCapacityCurve(data) {
      let i = 0;
      data.map(val => {
        this.hydrostation.levelCapacityCurve.curveData[i] = [];
        this.hydrostation.levelCapacityCurve.curveData[i] = [];
        this.hydrostation.levelCapacityCurve.curveData[i][0] = val["水位"];
        this.hydrostation.levelCapacityCurve.curveData[i][1] = val["库容"];
        i++;
      });
    },
    getHeadlossOutflowCurve(data) {
      let i = 0;

      data.map(val => {
        this.hydrostation.headlossOutflowCurve.curveData[i] = [];
        this.hydrostation.headlossOutflowCurve.curveData[i] = [];
        this.hydrostation.headlossOutflowCurve.curveData[i][0] = val["距离"];
        this.hydrostation.headlossOutflowCurve.curveData[i][1] =
          val["水头损失"];
        i++;
      });
    },
    getLeveldownOutflowCurve(data) {
      let i = 0;
      data.map(val => {
        this.hydrostation.leveldownOutflowCurve.curveData[i] = [];
        this.hydrostation.leveldownOutflowCurve.curveData[i] = [];
        this.hydrostation.leveldownOutflowCurve.curveData[i][0] = val["水位"];
        this.hydrostation.leveldownOutflowCurve.curveData[i][1] =
          val["下泄流量"];
        i++;
      });
    },
    getExpectOutputHeadCurve(data) {
      let i = 0;
      data.map(val => {
        this.hydrostation.expectOutputHeadCurve.curveData[i] = [];
        this.hydrostation.expectOutputHeadCurve.curveData[i] = [];
        this.hydrostation.expectOutputHeadCurve.curveData[i][0] = val["水位"];
        this.hydrostation.expectOutputHeadCurve.curveData[i][1] =
          val["下泄流量"];
        i++;
      });
    },
    handleCheckedPatternsChange(value) {
      this.calculateBean.patterns = value;
      // this.$emit("patterns", value);
      debugger;
      console.log("patterns", value);
      bus.$emit("patterns", value);
    },
    handleCheckedSituationsChange(value) {
      this.calculateBean.situations = value;
      // this.$emit("situations", value);
      console.log("situations", value);
      bus.$emit("situations", value);
    },
    submitClick: function() {
      var _this = this;
      // postRequest("/power/submit", {
      //   hydrostation: JSON.stringify(_this.hydrostation),
      //   calculateBean: JSON.stringify(_this.calculateBean)
      // })
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
            var json = resp.data;
            // if (json.status == "success") {
            _this.$store.commit("flag", true);
            bus.$emit("xAxis", resp.data.xAxis);
            bus.$emit("powerList", resp.data.powerList);
            bus.$emit("outputList", resp.data.outputList);
            _this.$alert("计算成功!", "成功!");
            // } else {
            //   _this.$alert("计算失败!", resp.data.status);
            // }
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
  mounted() {
    bus.$emit("patterns", this.checkedPatterns);
    bus.$emit("situations", this.checkedSituations);
  },
  watch: {}
};
</script>
<style>
.el-checkbox + .el-checkbox {
  margin: 0px;
}
.el-checkbox.is-bordered + .el-checkbox.is-bordered {
  margin-right: 10px;
}
.el-checkbox {
  width: 100px;
  margin: 10px;
}
.mark {
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
.input > div {
  height: 68px;
}
.input,
.params,
.pattern {
  border: 2px solid rgba(74, 136, 220, 0.996078431372549);
  padding: 8px;
}
.label {
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
