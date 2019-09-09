
<template>
  <el-upload class="upload-demo" ref="upload" action="https://jsonplaceholder.typicode.com/posts/"
    :on-preview="handlePreview" :on-remove="handleRemove" :on-success="handleSuccess" :show-file-list="true"
    :file-list="fileList">
    <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
  </el-upload>
</template>

<script>
export default {
  props: ["fileList"],
  data() {
    return {};
  },
  methods: {
    open(file) {
      this.$alert(file.name + "上传成功\t\t", "文件上传", {
        confirmButtonText: "确定",
        callback: action => {}
      });
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleSuccess(response, file, fileList) {
      this.open(file);
    }
  }
};
</script>

<style>
</style>