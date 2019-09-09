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
          data: ["1", "2", "3", "4", "5", "6", "7"]
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
            data: [120, 132, 101, 134, 90, 230, 210]
          },
          {
            type: "line",
            data: [220, 182, 191, 234, 290, 330, 310]
          },
          {
            type: "line",
            data: [150, 232, 201, 154, 190, 330, 410]
          },
          {
            type: "line",
            data: [320, 332, 301, 334, 390, 330, 320]
          }
        ],
        animationDuration: 800
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
