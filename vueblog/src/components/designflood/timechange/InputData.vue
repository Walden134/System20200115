<template>
  <div style="width:100%">
    <el-form :inline="true" class="demo-form-inline" label-width="140px" :label-position="labelPosition">
      <div class="time_mark">降水不确定性分析</div>
      <div class="pattern">
        <label class="time_label" for="">基准期降水&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <uploadExcel @func="getBaseP"> </uploadExcel>
        <el-form-item label="未来年降水" style="margin-top:10px;margin-bottom: 0px">
          <el-select style=" width:100px" v-model="time.pattern">
            <el-option label="RCP2.6" value="26"></el-option>
            <el-option label="RCP4.5" value="45"></el-option>
            <el-option label="RCP8.5" value="85"></el-option>
          </el-select>
        </el-form-item>
      </div>
      <el-form-item style="margin-top:10px;margin-bottom: 0px">
        <el-button type="primary" @click.native.prevent="submitClick1">开始计算</el-button>
        <el-button type="warning" @click.native.prevent="resetData1">重置</el-button>

      </el-form-item>
      <div class="time_mark">时变设计洪水</div>
      <div class="pattern">
        <label class="time_label" for="">降水径流数据&nbsp;&nbsp;&nbsp;&nbsp;</label>
        <uploadExcel @func="getBaseFlood"> </uploadExcel>
        <el-form-item label="洪水系列选择" style="margin-top:10px;margin-bottom: 0px">
          <el-select style="width:100px;" v-model="time.flag">
            <el-option label="洪峰" value="1"></el-option>
            <el-option label="24h洪量" value="2"></el-option>
            <el-option label="3日洪量" value="3"></el-option>
            <el-option label="7日洪量" value="4"></el-option>
            <el-option label="15日洪量" value="5"></el-option>
          </el-select>
        </el-form-item>
      </div>
      <el-form-item style="margin-top: 10px;margin-bottom: 0px">
        <el-button type="primary" @click.native.prevent="submitClick2">开始计算</el-button>
        <el-button type="warning" @click.native.prevent="resetData2">重置</el-button>

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
  name: "inputData",
  props: {},
  data() {
    return {
      labelPosition: "left",
      time: {
        ObjP: [],
        BaseFlood: [],
        pattern: "26",
        flag: "1"
      },
      timeFlag: "1",
      q: [],
      designp: [],
      ex: [],
      cv: [],
      cs: []
    };
  },
  components: {
    uploadExcel: UploadExcel
  },
  methods: {
    resetData1() {
      this.q = [];
      bus.$emit("q", []);
    },
    resetData2() {
      this.designp = [];
      this.ex = [];
      this.cv = [];
      this.cs = [];
      bus.$emit("designp", []);
      bus.$emit("ex", []);
      bus.$emit("cv", []);
      bus.$emit("cs", []);
    },
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
    getBaseFlood(data) {
      let i = 0;
      this.time.BaseFlood[0] = [];
      this.time.BaseFlood[1] = [];
      this.time.BaseFlood[2] = [];
      this.time.BaseFlood[3] = [];
      this.time.BaseFlood[4] = [];
      this.time.BaseFlood[5] = [];
      data.map(val => {
        this.time.BaseFlood[0][i] = val["q"];
        this.time.BaseFlood[1][i] = val["w1"];
        this.time.BaseFlood[2][i] = val["w3"];
        this.time.BaseFlood[3][i] = val["w7"];
        this.time.BaseFlood[4][i] = val["w15"];
        this.time.BaseFlood[5][i] = val["p"];
        i++;
      });
      console.log(this.time.BaseFlood);
    },
    submitClick1: function() {
      var _this = this;
      if (_this.time.ObjP.length == 0) {
        _this.$alert("请先导入数据!", "失败!");
        return;
      }
      bus.$emit("q", []);
      getRequest(
        "/flood/readBaseP" + "?time=" + JSON.stringify(_this.time)
      ).then(
        resp => {
          if (resp.status == 200) {
            bus.$emit("q", resp.data.q);
            _this.q = resp.data.q;
          } else {
            _this.$alert("计算失败!", "失败!");
          }
        },
        resp => {
          _this.$alert("请重新登陆⊙﹏⊙∥!", "失败!");
        }
      );
    },
    submitClick2: function() {
      var _this = this;
      if (_this.time.BaseFlood.length == 0) {
        _this.$alert("请先导入数据!", "失败!");
        return;
      }
      bus.$emit("designp", []);
      bus.$emit("ex", []);
      bus.$emit("cv", []);
      bus.$emit("cs", []);
      getRequest(
        "/flood/readBaseFlood" + "?time=" + JSON.stringify(_this.time)
      ).then(
        resp => {
          if (resp.status == 200) {
            bus.$emit("designp", resp.data.designp);
            bus.$emit("ex", resp.data.ex);
            bus.$emit("cv", resp.data.cv);
            bus.$emit("cs", resp.data.cs);
            _this.designp = resp.data.designp;
            _this.ex = resp.data.ex;
            _this.cv = resp.data.cv;
            _this.cs = resp.data.cs;
          } else {
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
    bus.$emit("designp", this.designp);
    bus.$emit("ex", this.ex);
    bus.$emit("cv", this.cv);
    bus.$emit("cs", this.cs);
  },
  watch: {
    q: {
      handler: storageUtils.saveQ,
      deep: true
    },
    designp: {
      handler: storageUtils.saveDesignP,
      deep: true
    },
    ex: {
      handler: storageUtils.saveEx,
      deep: true
    },
    cv: {
      handler: storageUtils.saveCv,
      deep: true
    },
    cs: {
      handler: storageUtils.saveCs,
      deep: true
    },
    time: {
      deep: true,
      handler: function() {
        bus.$emit("timeFlag", this.time.flag);
      }
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
  background-color: #3366ff;
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
