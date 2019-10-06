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
import storageUtils from "../../utils/storageUtils";
export default {
  props: ["title"],
  data: function() {
    return {
      chartdata: {
        title: {
          text: "出力保证率曲线",
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
          type: "category",
          data: [],
          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          },
          axisLabel: {
            interval: 9,
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
          data: [
            { name: "GFDL", icon: "line" },
            { name: "CNRM", icon: "line" },
            { name: "CanESM", icon: "line" },
            { name: "MIROC", icon: "line" }
          ]
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
  methods: {
    setChartData() {
      bus.$on("outputRateList", data => {
        for (let j = 0; j < data.length; j++) {
          this.$refs.dschart.options.series[j].data = data[j];
        }
      });
      bus.$on("outputRatexAxis", data => {
        this.$refs.dschart.options.xAxis.data = data;
      });
    },
    inintChartData() {
      let data1 = storageUtils.readOutputRateList();
      if (data1.length > 0) {
        for (let j = 0; j < data1.length; j++) {
          this.$refs.dschart.options.series[j].data = data1[j];
        }
      }
      let data2 = storageUtils.readOutputRatexAxis();
      if (data2.length > 0) {
        this.$refs.dschart.options.xAxis.data = data2;
      }
    }
  },
  mounted() {
    this.inintChartData();
  },
  beforeDestroy() {
    bus.$off("outputRatexAxis");
    bus.$off("outputRateList");
  },

  created() {
    this.setChartData();
  },
  watch: {
    // situations() {
    //   this.getRequestData();
    // },
    // patterns() {
    //   this.getRequestData();
    // }
  }
};
</script>
