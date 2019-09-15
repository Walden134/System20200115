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
          text: this.title.title,
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
          axisLabel: {
            interval: 0,
            rotate: 0,
            fontSize: 12
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
          formatter: "{value} kg",

          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          }
        },
        // legend: {
        //   y: "8%",
        //   data: ["全年", "丰水期", "平水期", "枯水期"]
        // },
        grid: {
          left: "10%", // 与容器左侧的距离
          right: "5%", // 与容器右侧的距离
          top: "10%", // 与容器顶部的距离
          bottom: "10%" // 与容器底部的距离
        },
        series: [
          {
            type: "bar",
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
  computed: {},
  components: {
    chart: ECharts
  },
  methods: {
    setChartData() {
      // bus.$on("situations", data => (this.situations = data));
      // bus.$on("patterns", data => (this.patterns = data));
      bus.$on("outputList", data => {
        for (let j = 0; j < data.length; j++) {
          this.$refs.dschart.options.series[0].data[j] = data[j][0];
        }
      });
      bus.$on("xAxis", data => {
        this.$refs.dschart.options.xAxis.data = data;
      });
    }
  },
  beforeDestroy() {
    bus.$off("outputList");
    bus.$off("xAxis");
  },
  mounted() {},
  created() {
    this.setChartData();
  },
  watch: {}
};
</script>
