import Vue from "vue";
import Router from "vue-router";
import Login from "@/components/Login";
import Home from "@/components/Home";
import ArticleList from "@/components/mxyy";
import CateMana from "@/components/CateMana";
import DataCharts from "@/components/DataCharts";
import PostArticle from "@/components/PostArticle";
import UserMana from "@/components/UserMana";
import BlogDetail from "@/components/BlogDetail";

import mxyy from "@/components/mxyy";
import cgsj from "@/components/cgsj";
import sbsj from "@/components/sbsj";
import fdxyjs from "@/components/fdxyjs";
import fdfxpg from "@/components/fdfxpg";

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
          path: "/mxyy",
          name: "模型应用",
          component: mxyy,
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
          path: "/cgsj",
          name: "常规设计",
          component: cgsj,
          meta: {
            keepAlive: true
          }
        },
        {
          path: "/sbsj",
          name: "时变设计",
          component: sbsj,
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
          path: "/fdxyjs",
          name: "发电效益计算",
          component: fdxyjs,
          meta: {
            keepAlive: true
          }
        },
        {
          path: "/fdfxpg",
          name: "发电风险评估",
          component: fdfxpg,
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
