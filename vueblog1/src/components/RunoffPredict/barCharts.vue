<template>
  <chart ref="dschart" :options="option" style="height:100%;width:100%"></chart>
</template>

<style>
</style>
<script>
import ECharts from "vue-echarts/components/ECharts.vue";
import "echarts/lib/chart/line";
import "echarts/lib/component/tooltip";
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
          left: "60px",
          right: "10px",
          top: "40px"
        },
        title: {
          text: "未来年径流均值",
          x: "center"
        },
        xAxis: {
          type: "category",
          name: "时间/年",
          nameGap: 22,
          nameLocation: "center",
          axisTick: { show: false },
          // data: ["2030S", "2060S", "2090S"]
          data: []
        },
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
          data: ["CanESM", "CNRM", "GFDL", "MIROC"]
          // data: ["OBS", "CNRM", "CanESM", "GFDL", "MIROC"]
        },
        series: [
          // {
          //   name: "OBS",
          //   data: [],
          //   type: "bar"
          // },
          {
            name: "CanESM",
            data: [],
            type: "bar"
          },
          {
            name: "CNRM",
            data: [],
            type: "bar"
          },

          {
            name: "GFDL",
            data: [],
            type: "bar"
          },
          {
            name: "MIROC",
            data: [],
            type: "bar"
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
          this.option.series[i].data = data[i].avgYear;
          console.log("avgYear", this.option.series[i].data);
        }
        this.option.xAxis.data = data[0].xYear;
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
