<template>
  <div class="loading">
    <span>正在计算</span>
  </div>
</template>

<script>
export default {
  name: "Loading" // 定义的组件名称 使用时写法：loading
};
</script>

<style scoped>
.loading {
  position: fixed;
  left: 890px;
  top: 490px;
  background: url("~@/assets/loading-ball.svg") center center no-repeat;
  width: 120px;
  height: 120px;
  z-index: 1000;
}

.loading span {
  position: relative;
  top: 48px;
  color: red;
}
.fade-enter,
.fade-leave-active {
  opacity: 0;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
</style>