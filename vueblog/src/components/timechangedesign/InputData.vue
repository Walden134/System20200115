<template>
  <div class="form" style="width:100%">
    <el-form :inline="true" :model="formData" class="demo-form-inline" label-width="200px"
      :label-position="labelPosition">
      <!-- <el-form-item label="最优模型参数"
                    style="margin-bottom: 20px; ">
      </el-form-item> -->
      <p style="fontsize:'15px'">降水不确定性分析</p>
      <el-form-item label="基准期降水" style="margin-bottom: 5px; ">
        <upload_excel :fileList="fileList1" style=" width:150px"></upload_excel>
      </el-form-item>
      <el-form-item label="未来降水" style="margin-bottom: 5px; ">
        <el-select style=" width:180px" v-model="formData.calculateBean.region" placeholder="请选择未来降水系列">
          <el-option label="RCP2.6" value="RCP2.6"></el-option>
          <el-option label="RCP4.5" value="RCP4.5"></el-option>
          <el-option label="RCP8.5" value="RCP8.5"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item style="margin-top: 10px;margin-bottom: 10px">
        <el-button type="primary" @click.native.prevent="submitClick">开始计算</el-button>
        <el-button>保存</el-button>
      </el-form-item>

      <hr>
      <p style="fontsize:'15px'">时变设计洪水</p>
      <el-form-item label="降水径流数据" style="margin-bottom: 10px; ">
        <upload_excel :fileList="fileList1" style=" width:80px"></upload_excel>
      </el-form-item>
      <el-form-item label="结果显示" style="margin-bottom: 5px; ">
        <el-select style=" width:180px" v-model="formData.calculateBean.region" placeholder="请选择计算洪水系列">
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
      <el-form-item style="margin-top: 10px;margin-bottom: 10px">
        <el-button type="primary" @click.native.prevent="submitClick">开始计算</el-button>
        <el-button>保存</el-button>
      </el-form-item>

    </el-form>
  </div>

</template>
<script>
import UploadExcel from "@/components/timechangedesign/UploadExcel";

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
        hydrostation: {},
        calculateBean: {
          region1: "RCP "
        }
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
