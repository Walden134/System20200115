<template>
  <div style="width:100%">
    <el-form :inline="true" :model="formData" class="demo-form-inline" label-width="160px"
      :label-position="labelPosition">
      <div class="time_mark">降水不确定性分析</div>
      <div class="pattern">
        <label class="time_label" for="">基准期降水</label>
        <uploadExcel :fileList="levelCapacityCurve" @func="getLevelCapacityCurve"> </uploadExcel>
        <label class="time_label" for="">未来降水</label>
        <el-radio-group v-model="checkedPatterns" @change="handleCheckedPatternsChange" :min="1" size="mini">
          <el-radio v-for="pattern in patterns" :label="pattern" :key="pattern" border></el-radio>
        </el-radio-group>
        <el-form-item style="margin-top: 10px;margin-bottom: 0px">
          <el-button type="primary" @click.native.prevent="submitClick">开始计算</el-button>
          <el-button>保存</el-button>
        </el-form-item>
      </div>

      <div class="time_mark">时变设计洪水</div>
      <div class="pattern">
        <label class="time_label" for="">降水径流数据</label>
        <uploadExcel :fileList="levelCapacityCurve" @func="getLevelCapacityCurve"> </uploadExcel>
        <el-form-item label="结果显示">
          <el-select style="width:80px;" v-model="formData.calculateBean.region">
            <el-option label="洪峰" value="洪峰"></el-option>
            <el-option label="24h洪量" value="24h洪量"></el-option>
            <el-option label="3日洪量" value="3日洪量"></el-option>
            <el-option label="7日洪量" value="7日洪量"></el-option>
            <el-option label="15日洪量" value="15日洪量"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item style="margin-top: 10px;margin-bottom: 0px">
          <el-button type="primary" @click.native.prevent="submitClick">开始计算</el-button>
          <el-button>保存</el-button>
        </el-form-item>
      </div>

    </el-form>
  </div>

</template>
<script>
import UploadExcel from "@/components/UploadExcel";

import { getRequest } from "../../utils/api";
import { putRequest } from "../../utils/api";
import { postRequest } from "../../utils/api";
const patternOptions = ["RCP2.6", "RCP4.5", "RCP8.5"];
export default {
  name: "inputData",
  props: {},
  data() {
    return {
      checkedPatterns: "RCP2.6",
      patterns: patternOptions,
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
      formData: {
        hydrostation: {},
        calculateBean: {
          region1: "RCP "
        }
      }
    };
  },
  components: {
    uploadExcel: UploadExcel
  },
  methods: {
    submitClick: function() {
      var _this = this;
      alert("开始计算，请稍等。。。");
      postRequest("/power/submit", {
        hydrostation: _this.formData.hydrostation,
        calculateBean: _this.formData.calculateBean
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
  }
};
</script>
<style>
.time_mark {
  font: 19px "Microsoft YaHei";
  position: relative;
  top: 10px;
  right: -13px;
  width: 170px;
  z-index: 10;
  border-radius: 5px;
  background-color: blue;
  color: white;
}
.time_label {
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
