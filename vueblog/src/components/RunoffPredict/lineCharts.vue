<template>
  <chart ref="dschart" :options="option" style="height:100%;width:100%"></chart>
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
  data: function() {
    return {
      option: {
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
          text: "未来月径流均值",
          x: "center"
        },
        xAxis: [
          {
            type: "category",
            name: "时间/月",
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
            // "CanESM", "CNRM", "GFDL", "MIROC"
            // { name: "OBS", icon: "line" },
            { name: "CanESM", icon: "line" },
            { name: "CNRM", icon: "line" },
            { name: "GFDL", icon: "line" },
            { name: "MIROC", icon: "line" }
          ]
        },
        series: [
          // {
          //   name: "OBS",
          //   data: [],
          //   type: "line"
          // },

          {
            name: "CanESM",
            data: [],
            type: "line"
          },
          {
            name: "CNRM",
            data: [],
            type: "line"
          },
          {
            name: "GFDL",
            data: [],
            type: "line"
          },
          {
            name: "MIROC",
            data: [],
            type: "line"
          }
        ]
      }
    };
  },
  components: {
    chart: ECharts
  },

  methods: {
    setChartData() {
      bus.$on("swatData", data => {
        for (let i = 0; i < data.length; i++) {
          this.option.series[i].data = data[i].avgMon;
          console.log("avgMon", this.option.series[i].data);
        }
      });
    },
    inintChartData() {}
  },
  mounted() {
    this.inintChartData();
  },
  created() {
    this.setChartData();
  },
  beforeDestroy() {
    bus.$off("swatData");
  }
};
</script>
