<template>
  <div style="width:100%">
    <transition name="fade">
      <loading v-if="isLoading"></loading>
    </transition>
    <el-form :inline="true" class="demo-form-inline" label-width="160px" :label-position="labelPosition">
      <div class="mark">数据导入</div>
      <div class="input">
        <div>
          <label class="power_label" for="">水位库容关系</label>
          <uploadExcel @func="getLevelCapacityCurve"> </uploadExcel>
        </div>
        <div>
          <label class="power_label" for="">尾水流量关系</label>
          <uploadExcel @func="getLeveldownOutflowCurve"> </uploadExcel>
        </div>
        <div>
          <label class="power_label" for="">水头损失曲线</label>
          <uploadExcel @func="getHeadlossOutflowCurve"> </uploadExcel>
        </div>
        <div>
          <label class="power_label" for="">出力限制曲线</label>
          <uploadExcel @func="getExpectOutputHeadCurve"> </uploadExcel>
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
        <el-form-item label="最大引用流量(m³/s)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.outflowMax"></el-input>
        </el-form-item>
        <el-form-item label="消落深度(m)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.drawdownDepth"></el-input>
        </el-form-item>
        <!-- <el-form-item label="最大下泄流量(m³/s)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.outflowMax"></el-input>
        </el-form-item>
        <el-form-item label="最小下泄流量(m³/s)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.outflowMin"></el-input>
        </el-form-item> -->
        <!-- <el-form-item label="起调水位(m)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="calculateBean.levelBegin"></el-input>
        </el-form-item>
        <el-form-item label="结束水位(m)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="calculateBean.levelEnd"></el-input>
        </el-form-item> -->
        <el-form-item label="发电量设计值(亿kW•h)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.avgDesiginPower"></el-input>
        </el-form-item>
        <el-form-item label="保证出力设计值(MW)" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="hydrostation.outputDesign"></el-input>
        </el-form-item>
        <!-- <el-form-item label="计算时段" style="margin-bottom: 1px; ">
          <el-select style=" width:80px" v-model="calculateBean.deltaT">
            <el-option label="日" value="日"></el-option>
            <el-option label="时" value="时"></el-option>
          </el-select>
        </el-form-item> -->
      </div>
      <div class="mark">情景选择</div>
      <div class="pattern">
        <!-- <label class="el-form-item__label" style="width: 100%; height:32px">情景选择</label>
      <el-checkbox-group v-model="checkedSituations" @change="handleCheckedSituationsChange" :min="1" size="mini">
        <el-checkbox v-for="situation in situations" :label="situation" :key="situation"></el-checkbox>
      </el-checkbox-group> -->
        <!-- <label class="el-form-item__label" style="width: 100%; height:32px">模式选择</label> -->
        <!-- <el-checkbox-group v-model="checkedPatterns" @change="handleCheckedPatternsChange" :min="1" size="medium">
          <el-checkbox v-for="pattern in patterns" :label="pattern" :key="pattern" border></el-checkbox>
        </el-checkbox-group> -->
        <el-radio-group v-model="checkedPatterns" @change="handleCheckedPatternsChange" :min="1" size="medium">
          <el-radio v-for="pattern in patterns" :label="pattern" :key="pattern" border></el-radio>
        </el-radio-group>
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
import Loading from "@/components/loading";
import { getRequest } from "../../utils/api";
import { postRequest } from "../../utils/api";
import storageUtils from "../../utils/storageUtils";
// import { showLoading, hideLoading } from "../../utils/loading";

export default {
  name: "inputData",
  data() {
    return {
      isLoading: false,
      checkedPatterns: "基准期",
      patterns: ["基准期", "RCP2.6", "RCP4.5", "RCP8.5"],
      // checkedSituations: ["GFDL", "CNRM", "CanESM", "MIROC", "BMA"],
      // situations: ["GFDL", "CNRM", "CanESM", "MIROC", "BMA"],
      labelPosition: "left",
      hydrostation: {
        drawdownDepth: "1",
        levelDead: "2088",
        levelNormal: "2094",
        outflowMax: "1698.8",
        outflowMin: "0",
        installPower: 1500,
        levelCapacityCurve: { curveData: [] },
        leveldownOutflowCurve: { curveData: [] },
        headlossOutflowCurve: { curveData: [] },
        expectOutputHeadCurve: { curveData: [] },
        outputCoefficient: "8.5",
        outputDesign: "176",
        avgDesiginPower: "59.62"
      },
      calculateBean: {
        situations: ["GFDL", "CNRM", "CanESM", "MIROC", "BMA"],
        patterns: ["基准期"],
        pattern: "基准期",
        levelBegin: "2094",
        levelEnd: "2094",
        deltaT: 1
      },
      category: [],
      xAxisONE: [],
      outputs: [],
      powers: [],
      powersONE: [],
      outputratelist: [],
      outputratexaxis: []
    };
  },
  components: {
    uploadExcel: UploadExcel,
    loading: Loading
  },
  methods: {
    resetData() {
      this.category = [];
      this.xAxisONE = [];
      this.outputs = [];
      this.powers = [];
      this.powersONE = [];
      this.outputratelist = [];
      this.outputratexaxis = [];
      bus.$emit("xAxis", []);
      bus.$emit("xAxisONE", []);
      bus.$emit("powerList", []);
      bus.$emit("powerONEList", []);
      bus.$emit("outputList", []);
      bus.$emit("outputRatexAxis", []);
      bus.$emit("outputRateList", []);
    },
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
        this.hydrostation.expectOutputHeadCurve.curveData[i][0] = val["水头"];
        this.hydrostation.expectOutputHeadCurve.curveData[i][1] =
          val["最大出力"];
        i++;
      });
    },
    handleCheckedPatternsChange(value) {
      this.calculateBean.pattern = value;
      bus.$emit("pattern", value);
    },
    handleCheckedSituationsChange(value) {
      this.calculateBean.situations = value;
      // this.$emit("situations", value);
      bus.$emit("situations", value);
    },
    submitClick: function() {
      var _this = this;
      if (
        _this.hydrostation.levelCapacityCurve.curveData.length == 0 ||
        _this.hydrostation.leveldownOutflowCurve.curveData.length == 0 ||
        _this.hydrostation.headlossOutflowCurve.curveData.length == 0 ||
        _this.hydrostation.expectOutputHeadCurve.curveData.length == 0
      ) {
        _this.$alert("请先导入数据!", "失败!");
        return;
      }
      bus.$emit("xAxis", []);
      bus.$emit("xAxisONE", []);
      bus.$emit("powerList", []);
      bus.$emit("powerONEList", []);

      bus.$emit("outputList", []);
      bus.$emit("outputRatexAxis", []);
      bus.$emit("outputRateList", []);
      // this.isLoading = true;
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
            console.log(resp.data);
            bus.$emit("xAxis", resp.data.xAxis);
            bus.$emit("xAxisONE", resp.data.xAxisONE);
            bus.$emit("powerList", resp.data.powerList);
            bus.$emit("powerONEList", resp.data.powerONEList);
            bus.$emit("outputList", resp.data.outputList);
            bus.$emit("outputRatexAxis", resp.data.outputRatexAxis);
            bus.$emit("outputRateList", resp.data.outputRateList);

            _this.category = resp.data.xAxis;
            _this.xAxisONE = resp.data.xAxisONE;
            _this.powers = resp.data.powerList;
            _this.powersONE = resp.data.powerONEList;
            _this.outputs = resp.data.outputList;
            _this.outputratelist = resp.data.outputRateList;
            _this.outputratexaxis = resp.data.outputRatexAxis;
            // this.isLoading = false;
            // _this.$alert("计算成功!", "成功!");
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
  created() {
    bus.$emit("patterns", this.checkedPatterns);
    bus.$emit("situations", this.checkedSituations);
    bus.$emit("avgDesiginPower", this.hydrostation.avgDesiginPower);
    bus.$emit("outputDesign", this.hydrostation.outputDesign);
  },
  watch: {
    hydrostation: {
      handler: function() {
        bus.$emit("avgDesiginPower", this.hydrostation.avgDesiginPower);
        bus.$emit("outputDesign", this.hydrostation.outputDesign);
      },
      deep: true
    },
    powers: {
      deep: true,
      handler: storageUtils.savePowers
    },
    powersONE: {
      deep: true,
      handler: storageUtils.savePowersONE
    },
    outputs: {
      deep: true,
      handler: storageUtils.saveOutputs
    },
    category: {
      deep: true,
      handler: storageUtils.saveCategory
    },
    xAxisONE: {
      deep: true,
      handler: storageUtils.saveXAxisONE
    },
    outputratelist: {
      deep: true,
      handler: storageUtils.saveOutputRateList
    },
    outputratexaxis: {
      deep: true,
      handler: storageUtils.saveOutputRatexAxis
    }
  }
};
</script>
<style>
.el-radio + .el-radio {
  margin: 0px;
}
.el-radio.is-bordered + .el-radio.is-bordered {
  margin-right: 10px;
}
.el-radio {
  width: 100px;
  margin: 10px 10px 2px 10px;
}
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

.power_label {
  text-align: right;
  vertical-align: middle;
  float: left;
  font-size: 14px;
  color: #606266;
  line-height: 40px;
  padding: 0;
  /* padding: 0 12px 0 0; */
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
</style>
