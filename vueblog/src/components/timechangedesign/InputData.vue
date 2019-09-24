<template>
  <div style="width:100%">
    <el-form :inline="true"
             class="demo-form-inline"
             label-width="160px"
             :label-position="labelPosition">
      <div class="time_mark">降水不确定性分析</div>
      <div class="pattern">
        <label class="time_label"
               for="">基准期降水</label>
        <uploadExcel @func="getBaseP"> </uploadExcel>
        <el-form-item label="未来降水"
                      style="margin-bottom: 10px; ">
          <el-select style=" width:80px"
                     v-model="time.pattern">
            <el-option label="RCP2.6"
                       value="26"></el-option>
            <el-option label="RCP4.5"
                       value="45"></el-option>
            <el-option label="RCP8.5"
                       value="85"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item style="margin-top: 10px;margin-bottom: 0px">
          <el-button type="primary"
                     @click.native.prevent="submitClick">开始计算</el-button>
          <el-button>保存</el-button>
        </el-form-item>
      </div>

      <div class="time_mark">时变设计洪水</div>
      <div class="pattern">
        <label class="time_label"
               for="">降水径流数据</label>
        <uploadExcel> </uploadExcel>
        <el-form-item label="结果显示">
          <el-select style="width:80px;"
                     v-model="time.pattern">
            <el-option label="洪峰"
                       value="洪峰"></el-option>
            <el-option label="24h洪量"
                       value="24h洪量"></el-option>
            <el-option label="3日洪量"
                       value="3日洪量"></el-option>
            <el-option label="7日洪量"
                       value="7日洪量"></el-option>
            <el-option label="15日洪量"
                       value="15日洪量"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item style="margin-top: 10px;margin-bottom: 0px">
          <el-button type="primary"
                     @click.native.prevent="submitClick">开始计算</el-button>
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
import storageUtils from "../../utils/storageUtils";
export default {
  name: "inputData",
  props: {},
  data() {
    return {
      labelPosition: "left",
      time: {
        ObjP: [],
        pattern: "26"
      },
      q: []
    };
  },
  components: {
    uploadExcel: UploadExcel
  },
  methods: {
    getBaseP(data) {
      let i = 0;
      this.time.ObjP[0] = [];
      this.time.ObjP[1] = [];
      this.time.ObjP[2] = [];
      this.time.ObjP[3] = [];
      this.time.ObjP[4] = [];
      data.map(val => {
        this.time.ObjP[0][i] = val["obj_P"];
        this.time.ObjP[1][i] = val["cnrm_P"];
        this.time.ObjP[2][i] = val["miroc_P"];
        this.time.ObjP[3][i] = val["canesm_P"];
        this.time.ObjP[4][i] = val["gfdl_P"];
        i++;
      });
      console.log(this.time.ObjP);
    },
    submitClick: function() {
      var _this = this;
      getRequest(
        "/flood/readBaseP" + "?time=" + JSON.stringify(_this.time)
      ).then(
        resp => {
          if (resp.status == 200) {
            //成功
            bus.$emit("q", resp.data.q);
            console.log(resp.data.q);
            _this.q = resp.data.q;
            _this.$alert("计算成功!", "成功!");
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
    bus.$emit("q", this.q);
  },
  watch: {
    q: {
      handler: storageUtils.saveQ,
      deep: true
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
