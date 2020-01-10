<template>
  <chart ref="dschart" :options="option" style="width:100%;height:100%"></chart>
</template>

<script>
import ECharts from "vue-echarts/components/ECharts.vue";
import "echarts/lib/chart/line";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/polar";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import "echarts/theme/dark";
import "echarts/lib/chart/bar";

export default {
  data: function() {
    return {
      option: {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          y: "bottom",
          data: [
            {
              name: "不确定性区间",
              icon: "rect"
            },
            {
              name: "BMA模型",
              icon: "line"
            }
          ],
          top: 40
        },
        title: {
          left: "center",
          text: "日径流不确定性区间"
        },
        toolbox: {
          top: "2%",
          right: "15px",
          feature: {
            dataZoom: {
              yAxisIndex: "none"
            },
            restore: {},

            saveAsImage: {}
          }
        },
        grid: {
          left: "5%", // 与容器左侧的距离
          right: "15px", // 与容器右侧的距离
          top: "7%" // 与容器顶部的距离
          // bottom: '10%', // 与容器底部的距离
        },
        xAxis: {
          name: "年",
          nameGap: 40,
          nameLocation: "center",
          type: "category",
          boundaryGap: false,
          axisLabel: {
            interval: 2045
          },
          axisTick: { show: false },
          data: []
        },
        yAxis: {
          name: "流量(m³/s)",
          nameGap: 40,
          nameLocation: "center",
          type: "value",
          axisTick: { show: false },
          boundaryGap: [0, "10%"],
          splitLine: {
            show: false
          }
        },

        series: [
          {
            name: "不确定性区间",
            type: "line",
            smooth: true,
            symbol: "none",
            sampling: "average",
            itemStyle: {
              normal: {
                color: "#A9A9A9"
              }
            },
            areaStyle: {
              normal: {
                color: "#A9A9A9"
              }
            },
            data: []
          },

          {
            name: "5%",
            type: "line",
            smooth: true,
            symbol: "none",
            sampling: "average",
            itemStyle: {
              normal: {
                color: "#FFFFFF"
              }
            },
            areaStyle: {
              normal: {
                color: "rgba(255,255,255,1)"
              }
            },
            data: []
          },

          {
            name: "BMA模型",
            type: "line",
            data: []
          }
        ]
      }
    };
  },
  components: {
    chart: ECharts
  },
  mounted: function() {},
  methods: {},
  created() {},
  beforeDestroy() {}
};
</script>

<style>
</style>