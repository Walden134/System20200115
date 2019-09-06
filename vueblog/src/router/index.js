import Vue from "vue";
import Router from "vue-router";
import Login from "@/components/Login";
import Home from "@/components/Home";
import DataCharts from "@/components/DataCharts";
import conventionalDesign from "@/components/conventionalDesign";
import timevaryingDesign from "@/components/timevaryingDesign";
import benefitCalculate from "@/components/power/benefitCalculate";
import riskAnalysis from "@/components/power/riskAnalysis";

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
      name: "",
      component: Home,
      hidden: true
    },
    {
      path: "/home",
      component: Home,
      name: "径流预测",
      children: [
        {
          path: "/benefitCalculate",
          name: "模型应用",
          component: benefitCalculate,
        },
      ]
    },
    {
      path: "/home",
      component: Home,
      name: "设计洪水",
      children: [
        {
          path: "/conventionalDesign",
          name: "常规设计",
          component: conventionalDesign,
        },
        {
          path: "/timevaryingDesign",
          name: "时变设计",
          component: timevaryingDesign,
        },
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
          path: "/riskAnalysis",
          name: "发电风险评估",
          component: riskAnalysis,
        },
      ]
    },
    {
      path: "/home",
      component: Home,
      name: "防洪风险",
      children: [
        {
          path: "/articleList",
          name: "数据导入",
          component: DataCharts,
        },
        // {
        //   path: "/articleList",
        //   name: "设计洪水过程性推求",
        //   component: DataCharts,
        // },
        // {
        //   path: "/articleList",
        //   name: "调洪演算",
        //   component: DataCharts,
        // },
        // {
        //   path: "/articleList",
        //   name: "风险评估",
        //   component: DataCharts,
        // },
      ]
    }
  ]
});
