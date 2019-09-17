import Vue from "vue";
import Router from "vue-router";
import Login from "@/components/Login";
import Home from "@/components/Home";
import TimeChangeDesign from "@/components/timechangedesign/TimeChangeDesign";
import PowerBenefit from "@/components/power/PowerBenefit";
import FloodRisk from "@/components/floodrisk/FloodRisk";
import GeneralFlood from "@/components/designflood/GeneralFlood";
import Runoff from "@/components/RunoffPredict/Runoff";
Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      name: "登录",
      hidden: true,
      component: Login
    },
    {
      path: "/home",
      name: "径流预测",
      component: Home,
      hidden: true
    },
    {
      path: "/home",
      component: Home,
      name: "径流预测",
      children: [
        {
          path: "/runoff",
          name: "径流预测",
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
          name: "常规设计",
          component: GeneralFlood
        },
        {
          path: "/timeChangeDesign",
          name: "时变设计",
          component: TimeChangeDesign
        }
      ]
    },
    {
      path: "/home",
      component: Home,
      name: "发电效益",
      children: [
        {
          path: "/powerBenefit",
          name: "发电效益",
          component: PowerBenefit,
        },
      ]
    },
    {
      path: "/home",
      component: Home,
      name: "防洪风险",
      children: [
        {
          path: "/floodRisk",
          name: "防洪风险",
          component: FloodRisk
        }
      ]
    }
  ]
});
