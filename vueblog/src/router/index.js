import Vue from "vue";
import Router from "vue-router";
import Login from "@/components/Login";
import Home from "@/components/Home";
import ArticleList from "@/components/ArticleList";
import CateMana from "@/components/CateMana";
import DataCharts from "@/components/DataCharts";
import PostArticle from "@/components/PostArticle";
import UserMana from "@/components/UserMana";
import BlogDetail from "@/components/BlogDetail";

import modelApply from "@/components/modelApply";
import conventionalDesign from "@/components/conventionalDesign";
import timevaryingDesign from "@/components/timevaryingDesign";
import benefitCalculate from "@/components/benefitCalculate";
import riskAnalysis from "@/components/riskAnalysis";

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
      // iconCls: "fa fa-file-text-o",
      children: [
        {
          path: "/modelApply",
          name: "模型应用",
          component: modelApply,
          meta: {
            keepAlive: true
          }
        },
        // {
        //   path: "/articleList",
        //   name: "文章列表",
        //   component: ArticleList,
        //   meta: {
        //     keepAlive: true
        //   }
        // },
        // {
        //   path: "/postArticle",
        //   name: "发表文章",
        //   component: PostArticle,
        //   meta: {
        //     keepAlive: false
        //   }
        // },
        {
          path: "/blogDetail",
          name: "博客详情",
          component: BlogDetail,
          hidden: true,
          meta: {
            keepAlive: false
          }
        },
        {
          path: "/editBlog",
          name: "编辑博客",
          component: PostArticle,
          hidden: true,
          meta: {
            keepAlive: false
          }
        }
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
          meta: {
            keepAlive: true
          }
        },
        {
          path: "/timevaryingDesign",
          name: "时变设计",
          component: timevaryingDesign,
          meta: {
            keepAlive: true
          }
        },
        {
          path: "/blogDetail",
          name: "博客详情",
          component: BlogDetail,
          hidden: true,
          meta: {
            keepAlive: false
          }
        },
        {
          path: "/editBlog",
          name: "编辑博客",
          component: PostArticle,
          hidden: true,
          meta: {
            keepAlive: false
          }
        }
        // {
        //   path: "/user",
        //   iconCls: "fa fa-user-o",
        //   name: "设计洪水",
        //   component: UserMana
        // }
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
          meta: {
            keepAlive: true
          }
        },
        {
          path: "/riskAnalysis",
          name: "发电风险评估",
          component: riskAnalysis,
          meta: {
            keepAlive: true
          }
        },
        {
          path: "/blogDetail",
          name: "博客详情",
          component: BlogDetail,
          hidden: true,
          meta: {
            keepAlive: false
          }
        },
        {
          path: "/editBlog",
          name: "编辑博客",
          component: PostArticle,
          hidden: true,
          meta: {
            keepAlive: false
          }
        }
        // {
        //   path: "/cateMana",
        //   iconCls: "fa fa-reorder",
        //   name: "栏目管理",
        //   component: CateMana
        // }
      ]
    },
    {
      path: "/home",
      component: Home,
      name: "防洪风险",
      // iconCls: "fa fa-bar-chart",
      children: [
        {
          path: "/articleList",
          name: "数据导入",
          component: ArticleList,
          meta: {
            keepAlive: true
          }
        },
        {
          path: "/articleList",
          name: "设计洪水过程性推求",
          component: ArticleList,
          meta: {
            keepAlive: true
          }
        },
        {
          path: "/articleList",
          name: "调洪演算",
          component: ArticleList,
          meta: {
            keepAlive: true
          }
        },
        {
          path: "/articleList",
          name: "风险评估",
          component: DataCharts,
          meta: {
            keepAlive: true
          }
        },
        {
          path: "/blogDetail",
          name: "博客详情",
          component: BlogDetail,
          hidden: true,
          meta: {
            keepAlive: false
          }
        },
        {
          path: "/editBlog",
          name: "编辑博客",
          component: PostArticle,
          hidden: true,
          meta: {
            keepAlive: false
          }
        }
        // {
        //   path: "/charts",
        //   iconCls: "fa fa-bar-chart",
        //   name: "数据统计",
        //   component: DataCharts
        // }
      ]
    }
  ]
});
