import Vue from "vue";
import Router from "vue-router";
import Login from "@/components/Login";
import Home from "@/components/Home";
import TimeChangeDesign from "@/components/timechangedesign/TimeChangeDesign";
import benefitCalculate from "@/components/power/benefitCalculate";
import PowerRisk from "@/components/powerrisk/PowerRisk";
import FloodRisk from "@/components/floodrisk/FloodRisk";
import GeneralFlood from "@/components/designflood/GeneralFlood";
import modelApply from "@/components/RunoffPredict/modelApply";
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
          path: "/modelApply",
          name: "径流预测",
          component: modelApply,
        },
      ]
    },
    {
      path: "/home",
      component: Home,
      name: "设计洪水",
      children: [
        {
          path: "/GeneralFlood",
          name: "常规设计",
          component: GeneralFlood
        },
        {
          path: "/TimeChangeDesign",
          name: "时变设计",
          component: TimeChangeDesign
        }
      ]
    },
    {
      path: "/home",
      component: Home,
      name: "发电风险",
      children: [
        {
          path: "/benefitCalculate",
          name: "发电效益计算",
          component: benefitCalculate,
        },
        {
          path: "/PowerRisk",
          name: "发电风险评估",
          component: PowerRisk
        }
      ]
    },
    {
      path: "/home",
      component: Home,
      name: "防洪风险",
      children: [
        {
          path: "/FloodRisk",
          name: "防洪风险",
          component: FloodRisk
        }
      ]
    }
  ]
});
