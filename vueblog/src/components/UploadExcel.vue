
<template>
  <el-upload class="upload-demo upload-excel" ref="upload" list-type='text' :show-file-list='true'
    :before-upload="beforeUpload" action='https://jsonplaceholder.typicode.com/posts/' :limit="1"
    :on-exceed="handleExceed" :on-success="handleSuccess" accept=".xlsx">
    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
  </el-upload>
</template>

<script>
export default {
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
    handleSuccess(response, file, fileList) {}
  }
};
</script>

<style>
.upload-excel .el-upload-list__item-name {
  display: none;
}
.upload-excel .el-upload-list__item .el-icon-close-tip {
  display: none;
}
.el-upload-list__item .el-icon-close {
  display: none;
}
.upload-excel .el-upload-list {
  position: relative;
  top: -35px;
  margin: 0;
  padding: 0;
  list-style: none;
}
</style>