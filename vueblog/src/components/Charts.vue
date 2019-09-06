<template>
  <chart ref="dschart" :options="chartdata"></chart>
</template>

<style>
</style>

<script>
import ECharts from "vue-echarts/components/ECharts.vue";
import "echarts/lib/chart/line";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/polar";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import "echarts/theme/dark";
import "echarts/lib/chart/bar";
import { getRequest } from "../utils/api";
export default {
  components: {
    chart: ECharts
  },
  mounted: function() {
    var _this = this;
    getRequest("/article/dataStatistics").then(
      resp => {
        if (resp.status == 200) {
          _this.$refs.dschart.options.xAxis.data = resp.data.categories;
          _this.$refs.dschart.options.series[0].data = resp.data.ds;
        } else {
          _this.$message({ type: "error", message: "数据加载失败!" });
        }
      },
      resp => {
        _this.$message({ type: "error", message: "数据加载失败!" });
      }
    );
  },
  methods: {},
  data: function() {
    return {
      chartdata: {
        title: {
          text: ""
        },
        toolbox: {
          show: true,
          feature: {
            magicType: {
              type: ["bar", "line"]
            },
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          data: []
        },
        yAxis: {},
        grid: {
          left: "5%", // 与容器左侧的距离
          right: "5%", // 与容器右侧的距离
          top: "10%", // 与容器顶部的距离
          bottom: "10%" // 与容器底部的距离
        },
        series: [
          {
            type: "bar",
            data: []
          }
        ],
        animationDuration: 3000
      }
    };
  }
};
</script>
