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
  data: function() {
    return {
      chartdata: {
        title: {
          text: "保证出力",
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
          type: "category",
          data: [],
          // name: "不同情景",
          // nameLocation: "center",
          // nameGap: 25,
          axisLabel: {
            interval: 0,
            rotate: 0,
            fontSize: 10
          },
          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          }
        },
        yAxis: {
          type: "value",
          name: "95%的保证出力(MW)",
          nameLocation: "center",
          nameGap: 35,
          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          }
        },
        grid: {
          left: "13%", // 与容器左侧的距离
          right: "5%", // 与容器右侧的距离
          top: "10%" // 与容器顶部的距离
          // bottom: "10%" // 与容器底部的距离
        },
        series: [
          {
            type: "bar",
            // barWidth: 20,
            name: "全年",
            data: []
          }
        ],
        animationDuration: 30
      },
      situations: [],
      patterns: []
    };
  },
  components: {
    chart: ECharts
  },
  methods: {
    setChartData() {
      bus.$on("outputList", data => {
        for (let j = 0; j < data.length; j++) {
          this.$refs.dschart.options.series[0].data[j] = data[j][0];
        }
      });
      bus.$on("xAxis", data => {
        this.$refs.dschart.options.xAxis.data = data;
      });
    },
    inintChartData() {
      let data1 = storageUtils.readOutputs();
      if (data1) {
        for (let j = 0; j < data1.length; j++) {
          this.$refs.dschart.options.series[0].data[j] = data1[j][0];
        }
      }
      let data2 = storageUtils.readCategory();
      if (data2) {
        this.$refs.dschart.options.xAxis.data = data2;
      }
    }
  },
  mounted() {
    this.inintChartData();
  },
  beforeDestroy() {
    bus.$off("outputList");
    bus.$off("xAxis");
  },

  created() {
    this.setChartData();
  },
  watch: {}
};
</script>
