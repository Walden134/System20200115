<template>
  <chart ref="dschart" :options="polar" style="height:100%;width:100%"></chart>
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
  components: {
    chart: ECharts
  },
  mounted: function() {},
  methods: {},
  data: function() {
    return {
      polar: {
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
          right: "15px",
          feature: {
            // dataView: { show: true, readOnly: false },
            // magicType: { show: true, type: ["line", "bar"] },
            // restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        grid: {
          left: "10%", // 与容器左侧的距离
          right: "15px", // 与容器右侧的距离
          top: "8%" // 与容器顶部的距离
          // bottom: '5%', // 与容器底部的距离
        },
        title: {
          text: "各气候模式未来月径流均值",
          // textStyle: {
          //   fontSize: 16
          // },
          // subtext: '',
          x: "center"
        },
        xAxis: [
          {
            type: "category",
            name: "月",
            nameGap: 20,
            nameLocation: "center",
            axisTick: { show: false },
            data: [
              "1",
              "2",
              "3",
              "4",
              "5",
              "6",
              "7",
              "8",
              "9",
              "10",
              "11",
              "12"
            ]
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "流量(m³/s)",
            nameGap: 40,
            nameLocation: "center",
            axisTick: { show: false },
            splitLine: { show: false }
          }
        ],
        legend: {
          y: "bottom",
          data: [
            { name: "OBS", icon: "line" },
            { name: "CNRM", icon: "line" },
            { name: "CanESM", icon: "line" },
            { name: "GFDL", icon: "line" },
            { name: "MIROC", icon: "line" }
          ]
        },
        series: [
          {
            name: "OBS",
            data: [
              538.93,
              459.43,
              435.3,
              503.89,
              769.58,
              1393.17,
              3284.54,
              3188.34,
              3537.91,
              2746.15,
              1297.17,
              775.17
            ],
            type: "line"
          },
          {
            name: "CNRM",
            data: [
              536.67,
              402.17,
              399.09,
              531.5,
              911.5,
              2123.24,
              3872.63,
              4463.34,
              4702.88,
              3474.8,
              2042.34,
              1106.83
            ],
            type: "line"
          },
          {
            name: "CanESM",
            data: [
              447.97,
              393.12,
              455.96,
              683.97,
              1183.18,
              2927.63,
              3422.02,
              3803.08,
              3946.59,
              2599.96,
              1486.64,
              760.36
            ],
            type: "line"
          },
          {
            name: "GFDL",
            data: [
              463.69,
              395.55,
              440.41,
              684.68,
              1211.92,
              2726.74,
              3843.86,
              4365.11,
              4254.13,
              2799.39,
              1580.16,
              804.29
            ],
            type: "line"
          },
          {
            name: "MIROC",
            data: [
              474.24,
              388.84,
              430.36,
              595.5,
              919.42,
              2149.77,
              3459.02,
              3741.55,
              3495.51,
              2621.2,
              1594.1,
              856.83
            ],
            type: "line"
          }
        ]
      }
    };
  }
};
</script>
