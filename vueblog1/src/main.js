import Vue from 'vue'
import App from './App'
import router from './router'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
// import './styles/element-variables.scss'
import 'font-awesome/css/font-awesome.min.css'
import './utils/filter_utils.js'
import store from './store'
import VueProgressBar from 'vue-progressbar'
import echarts from "echarts";


const options = {
  color: '#bffaf3',
  failedColor: '#874b4b',
  thickness: '5px',
  transition: {
    speed: '0.2s',
    opacity: '0.6s',
    termination: 300
  },
  autoRevert: true,
  location: 'left',
  inverse: false
}
Vue.use(VueProgressBar, options)
Vue.use(ElementUI)
Vue.use(echarts)
Vue.config.productionTip = false
window.bus = new Vue();
export default new Vue({
  ...App,
  el: '#app',
  router,
  template: '<App/>',
  components: { App },
  store, // 使用上vuex
}).$mount('#app')
