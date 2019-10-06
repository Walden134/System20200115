import Vue from "vue";
import Router from "vue-router";
import Login from "@/components/Login";
import Home from "@/components/Home";
import TimeChangeDesign from "@/components/timechangedesign/TimeChangeDesign";
import PowerBenefit from "@/components/power/PowerBenefit";
import FloodRisk from "@/components/floodrisk/FloodRisk";
import GeneralFlood from "@/components/designflood/GeneralFlood";
import Runoff from "@/components/RunoffPredict/Runoff";
import store from '../store/index'
Vue.use(Router);
const routes = [
  {
    path: "/",
    name: "登录",
    hidden: true,
    component: Login
  },
  {
    path: "/home",
    name: "首页",
    component: Home,
    hidden: true
  },
  {
    path: "/home",
    component: Home,
    // name: "径流预测",
    children: [
      {
        path: "/runoff",
        name: "径流预测",
        meta: {
          requireAuth: true,//添加该字段，表示进入这个路由是需要登录的。
          keepAlive: true
        },
        component: Runoff,
      },
    ]
  },
  {
    path: "/home",
    component: Home,
    name: "设计洪水",
    children: [
      {
        path: "/generalFlood",
        name: "常规设计", meta: {
          requireAuth: true,//添加该字段，表示进入这个路由是需要登录的。
          keepAlive: true
        },
        component: GeneralFlood
      },
      {
        path: "/timeChangeDesign",
        name: "时变设计", meta: {
          requireAuth: true,//添加该字段，表示进入这个路由是需要登录的。
          keepAlive: true
        },
        component: TimeChangeDesign
      }
    ]
  },
  {
    path: "/home",
    component: Home,
    // name: "发电效益",
    children: [
      {
        path: "/powerBenefit",
        name: "发电效益", meta: {
          requireAuth: true,//添加该字段，表示进入这个路由是需要登录的。
          keepAlive: true
        },
        component: PowerBenefit,
      },
    ]
  },
  {
    path: "/home",
    component: Home,
    // name: "防洪风险",
    children: [
      {
        path: "/floodRisk",
        name: "防洪风险", meta: {
          requireAuth: true,//添加该字段，表示进入这个路由是需要登录的。
          keepAlive: true
        },
        component: FloodRisk
      }
    ]
  }
]

// 页面刷新时，重新赋值token
if (window.localStorage.getItem('token')) {
  store.commit(LOGIN, window.localStorage.getItem('token'))
}

const router = new Router({
  routes
});
export default router;
