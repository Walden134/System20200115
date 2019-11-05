<template>
  <chart ref="dschart" :options="chartdata" style="height:100%;width:100%"></chart>
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
import { getRequest } from "../../utils/api";
import storageUtils from "../../utils/storageUtils";
export default {
  props: ["title"],
  data: function() {
    return {
      chartdata: {
        title: {
          text: "逐年发电量",
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
          name: "",
          nameGap: 25,
          type: "category",
          data: [],
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
          name: "发电量(亿kW•h)",
          nameLocation: "center",
          nameGap: 40,
          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          }
        },
        legend: {
          y: "bottom",
          data: ["CanESM", "GFDL", "BMA", "CNRM", "MIROC"]
        },
        grid: {
          left: "60px",
          right: "10px",
          top: "40px"
        },
        series: [
          {
            type: "line",
            name: "CanESM",
            data: []
          },
          {
            type: "line",
            name: "GFDL",
            data: []
          },
          {
            type: "line",
            name: "BMA",
            data: []
          },

          {
            type: "line",
            name: "CNRM",
            data: []
          },

          {
            type: "line",
            name: "MIROC",
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
      bus.$on("powerONEList", data => {
        for (let j = 0; j < data.length; j++) {
          this.chartdata.series[j].data = data[j];
        }
        if (data.length == 0 || data == null)
          for (let j = 0; j < this.chartdata.series.length; j++)
            this.chartdata.series[j].data = [];
      });
      bus.$on("xAxisONE", data => {
        this.chartdata.xAxis.data = data;
      });
    },
    inintChartData() {
      let PowerONE = storageUtils.readPowersONE();
      if (PowerONE) {
        for (let j = 0; j < PowerONE.length; j++) {
          this.chartdata.series[j].data = PowerONE[j];
        }
      }
      let XAxisONE = storageUtils.readXAxisONE();
      if (XAxisONE) {
        this.chartdata.xAxis.data = XAxisONE;
      }
    }
  },
  mounted() {
    this.inintChartData();
  },
  created() {
    this.setChartData();
  },
  beforeDestroy() {
    bus.$off("xAxisONE");
    bus.$off("powerONEList");
    this.chart.clear();
  },
  watch: {}
};
</script>

<style>
</style>
