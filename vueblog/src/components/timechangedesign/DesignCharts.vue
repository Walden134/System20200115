<template>

  <chart ref="dschart"
         id="designChart"
         :options="chartdata"
         style="height:100%;width:100%"></chart>

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
import echarts from "echarts";
import storageUtils from "../../utils/storageUtils";

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
        legend: {
          top: 30,
          data: ["5%分位数", "50%分位数", "95%分位数"],
          orient: "vertical",
          right: 50,
          backgroundColor: "#FFFFFF"
        },
        grid: {
          top: 30,
          bottom: 40,
          left: "7%",
          right: "3%"
        },
        xAxis: {
          name: "频率(%)",
          nameLocation: "center",
          nameGap: 25,
          axisTick: {
            show: false
          },
          axisLabel: {
            show: false
          },
          splitLine: {
            show: false
          }
        },
        yAxis: [
          {
            name: "流量(m³/s)",
            nameLocation: "center",
            nameGap: 48,
            type: "value",
            splitLine: {
              show: true
              // color: "black"
            },
            axisTick: {
              show: false
            }
          }
        ],
        series: [
          {
            name: "5%分位数",
            type: "line",
            symbol: "triangle",
            symbolsize: 10,
            data: [],
            markLine: {
              symbol: "none",
              animation: false,
              label: {
                normal: { position: "bottom", formatter: "{b}" }
              },
              itemStyle: {
                normal: {
                  lineStyle: {
                    width: 1,
                    color: "black",
                    type: "solid"
                  }
                }
              },
              // emphasis: {
              //   label: {
              //     position: "end",
              //     formatter: "{b}"
              //   }
              // },
              data: [
                { name: 0.01, xAxis: 0 },
                { name: 0.05, xAxis: 0.428489754 },
                { name: 0.1, xAxis: 0.628784179 },
                { name: 0.2, xAxis: 0.840854746 },
                { name: 0.5, xAxis: 1.143187182 },
                { name: 1, xAxis: 1.392668611 },
                { name: 5, xAxis: 2.074162859 },
                { name: 10, xAxis: 2.43746492 },
                { name: 15, xAxis: 2.682583096 },
                { name: 20, xAxis: 2.877395252 },
                { name: 30, xAxis: 3.194615973 },
                { name: 40, xAxis: 3.465669382 },
                { name: 50, xAxis: 3.719016485 },
                { name: 60, xAxis: 3.972363589 },
                { name: 70, xAxis: 4.243416998 },
                { name: 80, xAxis: 4.560637719 },
                { name: 85, xAxis: 4.755449875 },
                { name: 90, xAxis: 5.000568051 },
                { name: 95, xAxis: 5.363870112 },
                { name: 99, xAxis: 6.045364359 },
                { name: 99.9, xAxis: 6.809248792 },
                { name: 99.95, xAxis: 7.009543217 },
                { name: 99.99, xAxis: 7.438032971 },
                { xAxis: 8.0 }
              ]
            }
          },
          {
            name: "50%分位数",
            type: "line",
            symbol: "triangle",
            symbolsize: 10,
            data: []
          },
          {
            name: "95%分位数",
            type: "line",
            symbol: "triangle",
            symbolsize: 10,
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
      bus.$on("designp", data => {
        // debugger;
        for (let j = 0; j < data[0].length; j++) {
          this.$refs.dschart.options.series[0].data[j] = [];
          this.$refs.dschart.options.series[1].data[j] = [];
          this.$refs.dschart.options.series[2].data[j] = [];

          this.$refs.dschart.options.series[0].data[j][0] = data[0][j];
          this.$refs.dschart.options.series[1].data[j][0] = data[0][j];
          this.$refs.dschart.options.series[2].data[j][0] = data[0][j];

          this.$refs.dschart.options.series[0].data[j][1] = data[1][j];
          this.$refs.dschart.options.series[1].data[j][1] = data[2][j];
          this.$refs.dschart.options.series[2].data[j][1] = data[3][j];
        }
        let Pchart = echarts.init(document.getElementById("designChart"));
        Pchart.setOption(this.chartdata, true);
      });
    },
    inintChartData() {
      let data2 = storageUtils.readDesignP();
      // debugger;
      if (data2.length > 0) {
        for (let j = 0; j < data2[0].length; j++) {
          this.$refs.dschart.options.series[0].data[j] = [];
          this.$refs.dschart.options.series[1].data[j] = [];
          this.$refs.dschart.options.series[2].data[j] = [];

          this.$refs.dschart.options.series[0].data[j][0] = data2[0][j];
          this.$refs.dschart.options.series[1].data[j][0] = data2[0][j];
          this.$refs.dschart.options.series[2].data[j][0] = data2[0][j];

          this.$refs.dschart.options.series[0].data[j][1] = data2[1][j];
          this.$refs.dschart.options.series[1].data[j][1] = data2[2][j];
          this.$refs.dschart.options.series[2].data[j][1] = data2[3][j];
        }
      }
      let Pchart = echarts.init(document.getElementById("designChart"));
      Pchart.setOption(this.chartdata, true);
    }
  },
  mounted() {
    this.inintChartData();
  },
  created() {
    this.setChartData();
  },
  beforeDestroy() {
    bus.$off("designp");
  }
};
</script>

<style>
</style>