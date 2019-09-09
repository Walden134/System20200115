<template>
  <chart ref="dschart"
         :options="chartdata"></chart>
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
import { getRequest } from "../../utils/api";
export default {
  props: ["title", "chartdatas", "request"],
  data: function() {
    return {
      chartdata: {
        title: {
          text: this.title.title,
          left: "center"
        },

        toolbox: {
          show: true,
          feature: {
            magicType: {
              type: ["line", "bar"]
            },
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          data: [
            "0",
            "10",
            "20",
            "30",
            "40",
            "50",
            "60",
            "70",
            "80",
            "90",
            "100"
          ]
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
            type: "line",
            data: [1500, 1500, 1500, 1300, 1200, 1000, 800, 620, 480, 300, 150]
          },
          {
            type: "line",
            data: [1500, 1500, 1500, 1200, 1100, 900, 700, 520, 380, 200, 120]
          },
          {
            type: "line",
            data: [1500, 1500, 1500, 1100, 1000, 800, 600, 420, 230, 180, 110]
          },
          {
            type: "line",
            data: [1500, 1500, 1500, 1000, 900, 700, 500, 320, 200, 150, 100]
          },
          {
            type: "line",
            data: [1500, 1500, 1500, 920, 850, 610, 420, 220, 180, 130, 80]
          }
        ],
        animationDuration: 30
      }
    };
  },
  components: {
    chart: ECharts
  },
  mounted: function() {
    var _this = this;
    getRequest(this.request).then(
      resp => {
        if (resp.status == 200) {
          // _this.$refs.dschart.options.xAxis.data = resp.data.categories;
          // _this.$refs.dschart.options.series[0].data = resp.data.ds;
        } else {
          _this.$message({ type: "error", message: "数据加载失败!" });
        }
      },
      resp => {
        _this.$message({ type: "error", message: "数据加载失败!" });
      }
    );
  },
  methods: {}
};
</script>
