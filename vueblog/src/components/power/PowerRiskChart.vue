<template>
  <chart ref="dschart" :options="chartdata" style="height:100%;width:100%"></chart>
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
  props: ["title"],
  data: function() {
    return {
      chartdata: {
        title: {
          text: "出力保证率曲线图",
          left: "center"
        },

        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            crossStyle: {
              color: "#999"
            }
          }
        },
        toolbox: {
          show: true,
          feature: {
            dataView: {},
            magicType: {
              type: ["bar", "line"]
            },
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          name: "保证率(%)",
          nameGap: 25,
          nameLocation: "center",
          data: [],
          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          },
          axisLabel: {
            interval: 0,
            rotate: 0,
            fontSize: 12
          }
        },
        yAxis: {
          type: "value",
          name: "出力(MW)",
          nameLocation: "center",
          nameGap: 35,
          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          }
        },
        legend: {
          // y: "8%",
          y: "bottom",
          data: ["GFDL", "CNRM", "CanESM", "MIROC", "BMA"]
        },
        grid: {
          left: "13%", // 与容器左侧的距离
          right: "5%", // 与容器右侧的距离
          top: "10%" // 与容器顶部的距离
          // bottom: "10%" // 与容器底部的距离
        },
        series: [
          {
            name: "GFDL",
            type: "line",
            data: []
          },
          {
            name: "CNRM",
            type: "line",
            data: []
          },
          {
            name: "CanESM",
            type: "line",
            data: []
          },
          {
            name: "MIROC",
            type: "line",
            data: []
          },
          {
            name: "BMA",
            type: "line",
            data: []
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
    // var _this = this;
    // getRequest(this.request).then(
    //   resp => {
    //     if (resp.status == 200) {
    //       // _this.$refs.dschart.options.xAxis.data = resp.data.categories;
    //       // _this.$refs.dschart.options.series[0].data = resp.data.ds;
    //     } else {
    //       _this.$message({ type: "error", message: "数据加载失败!" });
    //     }
    //   },
    //   resp => {
    //     _this.$message({ type: "error", message: "数据加载失败!" });
    //   }
    // );
  },
  methods: {}
};
</script>
