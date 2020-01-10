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
import storageUtils from "@/utils/storageUtils";
export default {
  data: function() {
    return {
      chartdata: {
        title: {
          text: " ",
          // tect: "",
          text: "洪水风险率曲线",
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
          name: "频率(%)",
          nameGap: 25,
          nameLocation: "center",
          type: "category",
          data: ["0.2", "0.5", "1", "2", "5", "10", "20", "50"],
          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          }
        },
        yAxis: {
          type: "value",
          name: "风险率",
          nameLocation: "center",
          nameGap: 40,
          min: 0,
          max: 1,
          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          }
        },
        legend: {
          y: "bottom",
          data: [
            { name: "基准期", icon: "bar" },
            { name: "CNRM", icon: "bar" },
            { name: "MIROC", icon: "bar" },
            { name: "CanESM", icon: "bar" },
            { name: "GFDL", icon: "bar" }
          ]
        },
        grid: {
          left: "60px",
          right: "10px",
          top: "40px"
        },
        series: [
          {
            name: "基准期",
            type: "bar",
            data: []
          },
          {
            name: "CNRM",
            type: "bar",
            data: []
          },
          {
            name: "MIROC",
            type: "bar",
            data: []
          },
          {
            name: "CanESM",
            type: "bar",
            data: []
          },
          {
            name: "GFDL",
            type: "bar",
            data: []
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
      bus.$on("gRiskRes", data => {
        this.chartdata.series[0].data = data.obs;
        this.chartdata.series[1].data = data.cnrm;
        this.chartdata.series[2].data = data.miroc;
        this.chartdata.series[3].data = data.canesm;
        this.chartdata.series[4].data = data.gfdl;
      });
    },
    inintChartData() {
      let data2 = storageUtils.readRiskRes();
      if (data2.length > 0) {
        this.chartdata.series[0].data = data2.obs;
        this.chartdata.series[1].data = data2.cnrm;
        this.chartdata.series[2].data = data2.miroc;
        this.chartdata.series[3].data = data2.canesm;
        this.chartdata.series[4].data = data2.gfdl;
      }
    }
  },
  mounted() {
    this.inintChartData();
  },
  beforeDestroy() {
    bus.$off("gRiskRes");
  },
  created() {
    this.setChartData();
  }
};
</script>