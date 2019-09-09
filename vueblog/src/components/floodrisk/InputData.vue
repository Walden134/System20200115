<template>
  <div class="form" style="width:100%">
    <el-form :inline="true" :model="formData" class="demo-form-inline" label-width="140px"
      :label-position="labelPosition">
      <!-- <el-form-item label="最优模型参数"
                    style="margin-bottom: 20px; ">
      </el-form-item> -->
      <p style="fontsize:'15px'">数据导入</p>
      <el-form-item label="典型洪水过程线" style="margin-bottom: 10px; ">
        <upload_excel :fileList="fileList1" style=" width:80px"></upload_excel>
      </el-form-item>
      <el-form-item label="水位库容关系" style="margin-bottom: 10px; ">
        <upload_excel :fileList="fileList1" style=" width:80px"></upload_excel>
      </el-form-item>
      <el-form-item label="水位泄流能力" style="margin-bottom: 10px; ">
        <upload_excel :fileList="fileList1" style=" width:80px"></upload_excel>
      </el-form-item>
      <hr>

      <p style="fontsize:'15px'">参数设置</p>
      <el-form-item label="起调水位" style="margin-bottom: 8px; ">
        <el-input style=" width:80px" v-model="formData.hydrostation.Level1"></el-input>
      </el-form-item>
      <el-form-item label="设计洪水位" style="margin-bottom: 8px; ">
        <el-input style=" width:80px" v-model="formData.hydrostation.Level2"></el-input>
      </el-form-item>
      <el-form-item label="校核洪水位" style="margin-bottom: 8px; ">
        <el-input style=" width:80px" v-model="formData.hydrostation.Level3"></el-input>
      </el-form-item>
      <el-form-item label="坝顶高程" style="margin-bottom: 8px; ">
        <el-input style=" width:80px" v-model="formData.hydrostation.Level4"></el-input>
      </el-form-item>
      <el-form-item label="计算时段" style="margin-bottom: 8px; ">
        <el-input style=" width:80px" v-model="formData.hydrostation.Level5"></el-input>
      </el-form-item>
      <el-form-item label="情景选择" style="margin-bottom: 10px; ">
        <el-select style=" width:80px" v-model="formData.calculateBean.region" placeholder="请选择计算情景">
          <el-option label="RCP2.6" value="RCP2.6"></el-option>
          <el-option label="RCP4.5" value="RCP4.5"></el-option>
          <el-option label="RCP8.5" value="RCP8.5"></el-option>
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
import UploadExcel from "@/components/floodrisk/UploadExcel";

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
          Level1: "1250",
          Level2: "2093.2",
          Level3: "2094.5",
          Level4: "2096.8",
          Level5: "2h"
        },
        calculateBean: {
          LevelBegin: "2094",
          LevelEnd: "2094"
          // region: "RCP"
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
