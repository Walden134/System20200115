<template>
  <div id="app">
    <div></div>
    <router-view />
  </div>
</template>
<script>
export default {
  name: "app"
};
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#app > div {
  position: fixed;
  z-index: -1;
  top: 0%;
  left: 0%;
  width: 100vw;
  height: 100vh;
  background-image: url(assets/logo.png);
  /* background-size: 100vw 100vh; */
}
</style>
