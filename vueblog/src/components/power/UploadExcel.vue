
<template>
  <el-upload class="upload-demo" ref="upload" list-type='text' :show-file-list='true' :before-upload="beforeUpload"
    action='https://jsonplaceholder.typicode.com/posts/' :limit="1" :on-exceed="handleExceed"
    :on-success="handleSuccess" :file-list="fileList" accept=".xlsx">
    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
    <!-- <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button> -->
    <!-- <div slot="tip" class="el-upload__tip">只能上传Excel文件</div> -->
  </el-upload>
</template>

<script>
export default {
  props: ["fileList"],
  data() {
    return {
      outdata: []
    };
  },
  methods: {
    beforeUpload(file) {
      const isText = file.type === "application/vnd.ms-excel";
      const isTextComputer =
        file.type ===
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
      let rABS = false; //是否将文件读取为二进制字符串
      let reader = new FileReader();
      let _this = this;
      FileReader.prototype.readAsBinaryString = function(file) {
        let binary = "";
        let rABS = false; //是否将文件读取为二进制字符串
        let wb; //读取完成的数据
        let reader = new FileReader();
        reader.onload = function(e) {
          let bytes = new Uint8Array(reader.result);
          let length = bytes.byteLength;
          for (let i = 0; i < length; i++) {
            binary += String.fromCharCode(bytes[i]);
          }
          let XLSX = require("xlsx");
          if (rABS) {
            wb = XLSX.read(btoa(fixdata(binary)), {
              type: "base64"
            });
          } else {
            wb = XLSX.read(binary, {
              type: "binary"
            });
          }
          // debugger;
          _this.outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);
          _this.$emit("func", _this.outdata);
          // debugger;
          // let arr = [];
          // _this.outdata.map(data => {
          //   let obj = {};
          //   obj["径流量"] = data["径流量"];
          //   obj["净流量"] = data["净流量"];
          //   obj["库水位"] = data["库水位"];
          //   obj["尾水位"] = data["尾水位"];
          //   console.log(obj);
          //   arr.push(obj);
          // });
        };
        reader.readAsArrayBuffer(file);
      };
      if (rABS) {
        reader.readAsArrayBuffer(file);
      } else {
        reader.readAsBinaryString(file);
      }
      return isText | isTextComputer;
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 1 个文件，请删除后继续上传`);
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleSuccess(response, file, fileList) {
      this.$alert(file.name + "上传成功\t\t", "文件上传", {
        confirmButtonText: "确定",
        callback: action => {}
      });
    }
  }
};
</script>

<style>
</style>