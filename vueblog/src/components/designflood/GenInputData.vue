<template>
  <div style="width:100%">
    <el-form :inline="true" :model="formData" class="demo-form-inline" label-width="160px"
      :label-position="labelPosition">
      <div class="gen_mark">数据导入</div>
      <div class="input">
        <br>
        <label class="gen_label" for="">实测流量</label>
        <upload_excel :fileList="levelCapacityCurve" @func="getLevelCapacityCurve"> </upload_excel>
      </div>
      <div class="gen_mark">参数结果显示</div>
      <div class="params">
        <el-form-item label="样本均值Ex" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="formData.hydrostation.LevelNormal"></el-input>
        </el-form-item>
        <el-form-item label="变差系数Cv" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="formData.hydrostation.OutflowMax"></el-input>
        </el-form-item>
        <el-form-item label="偏态系数Cs" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="formData.hydrostation.OutflowMin"></el-input>
        </el-form-item>
        <el-form-item label="拟合度" style="margin-bottom: 1px; ">
          <el-input style=" width:80px" v-model="formData.hydrostation.LevelDead"></el-input>
        </el-form-item>
        <el-form-item label="洪水系列选择" style="margin-bottom: 1px; ">
          <el-select style=" width:80px" v-model="formData.calculateBean.region" placeholder="请选择计算洪水系列">
            <el-option label="洪峰" value="洪峰"></el-option>
            <el-option label="24h洪量" value="24h洪量"></el-option>
            <el-option label="1日洪量" value="1日洪量"></el-option>
            <el-option label="3日洪量" value="3日洪量"></el-option>
            <el-option label="7日洪量" value="7日洪量"></el-option>
            <el-option label="15日洪量" value="15日洪量"></el-option>
            <el-option label="水平年径流" value="水平年径流"></el-option>
            <el-option label="枯水期径流" value="枯水期径流"></el-option>
          </el-select>
        </el-form-item>
      </div>

      <el-form-item style="margin-top: 10px;margin-bottom: 0px">
        <el-button type="primary" @click.native.prevent="submitClick">开始计算</el-button>
        <el-button>保存</el-button>
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
        hydrostation: {
          LevelDead: "0.9876",
          LevelNormal: "3500",
          OutflowMax: "1.5",
          OutflowMin: "3.2",
          OutputCoefficient: "8.5"
        },
        calculateBean: { LevelBegin: "2094", LevelEnd: "2094" }
      }
    };
  },
  components: {
    upload_excel: UploadExcel
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
.gen_mark {
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
