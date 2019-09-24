<template>
  <chart class="PChart" ref="dschart" :options="P3" style="height:100%;width:100%"> </chart>
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
  methods: {},
  data: function() {
    return {
      P3: {
        title: {
          text: "设计洪水频率曲线",
          left: "center"
        },

        grid: {
          top: 25,
          bottom: 45,
          right: 5,
          left: 60
        },
        legend: {
          top: 25, //与grid中top一致
          data: ["历史洪水", "经验频率", "理论频率"],
          orient: "vertical",
          right: "1%",
          backgroundColor: "#FFFFFF"
        },
        xAxis: {
          type: "value",
          name: "频率(%)",
          nameLocation: "middle",
          nameGap: 30, //坐标轴名字与坐标轴距离
          axisTick: {
            show: false
          },
          axisLabel: {
            show: false
          },
          splitLine: {
            show: false
          },
          data: []
        },
        yAxis: {
          name: "流量",
          nameLocation: "middle",
          nameGap: 50, //坐标轴名字与坐标轴距离
          type: "value",
          splitLine: {
            show: true
          },
          axisTick: {
            show: false
          }
        },
        series: [
          {
            name: "理论频率",
            type: "line",
            smooth: true,
            data: [
              [0.01, 11481.83],
              [0.315810296178081, 10496.33],
              [0.428489753963786, 10160.68],
              [0.628784179287867, 9584.61],
              [0.840854746360197, 9003.17],
              [1.00593145845897, 8570.71],
              [1.14318718190678, 8224.44],
              [1.39266861141484, 7625.71],
              [1.66526757482386, 7016.18],
              [1.88509735919476, 6558.12],
              [2.07416285850421, 6187.7],
              [2.43746491991108, 5535.79],
              [2.87739525188277, 4848.71],
              [3.0445267352596, 4616.07],
              [3.19461597274764, 4420.08],
              [3.46566938231988, 4096.25],
              [3.71901648545568, 3827.42],
              [3.97236358859148, 3589.82],
              [4.24341699816372, 3368.37],
              [4.39350623565176, 3259.55],
              [4.56063771902859, 3149.38],
              [4.75544987494947, 3034.85],
              [5.00056805100028, 2910.63],
              [5.36387011240715, 2763.32],
              [5.59981009360693, 2688.44],
              [6.04536435949652, 2584.26],
              [6.80924879162353, 2488.55],
              [7.43803297091111, 2456.79],
              [7.98390727937704, 2445.86]
            ]
          },
          {
            name: "刻度",
            type: "bar",
            barWidth: 1,
            itemStyle: {
              normal: {
                label: {
                  position: "bottom",
                  show: true,
                  formatter: "{b}"
                }
              }
            },
            data: [
              { value: [0, 1800], name: "0.01" },
              { value: [0.428489753963786, 1800], name: "0.02" },
              { value: [0.628784179287867, 1800], name: "0.12" },
              { value: [0.840854746360197, 1800], name: "0.2" },
              { value: [1.14318718190678, 1800], name: "0.5" },
              { value: [1.39266861141484, 1800], name: "1" },
              { value: [2.07416285850421, 1800], name: "5" },
              { value: [2.43746491991108, 1800], name: "10" },
              { value: [2.68258309596189, 1800], name: "15" },
              { value: [2.87739525188277, 1800], name: "20" },
              { value: [3.19461597274764, 1800], name: "30" },
              { value: [3.46566938231988, 1800], name: "40" },
              { value: [3.71901648545568, 1800], name: "50" },
              { value: [3.97236358859148, 1800], name: "60" },
              { value: [4.24341699816372, 1800], name: "70" },
              { value: [4.56063771902859, 1800], name: "80" },
              { value: [4.75544987494947, 1800], name: "85" },
              { value: [5.00056805100028, 1800], name: "90" },
              { value: [5.36387011240715, 1800], name: "95" },
              { value: [6.04536435949652, 1800], name: "99" },
              { value: [6.80924879162353, 1800], name: "99.9" },
              { value: [7.00954321694761, 1800], name: "99.95" },
              { value: [7.43803297091111, 1800], name: "99.99" },
              { value: [8, 1800], name: "" }
            ]
          },
          {
            name: "历史洪水",
            type: "scatter",
            symbolsize: 2,
            data: [
              [1.5589720622714, 7380],
              [1.84940983833678, 7200],
              [2.03566822145207, 7200],
              [2.17703018660123, 6470]
            ]
          },
          {
            name: "经验频率",
            type: "scatter",
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
      bus.$on("expFrequency", data => {
        for (let j = 0; j < data[0].length; j++) {
          this.$refs.dschart.options.series[3].data[j] = [];
          this.$refs.dschart.options.series[3].data[j][0] = data[0][j];
          this.$refs.dschart.options.series[3].data[j][1] = data[1][j];
          console.log(this.$refs["dschart"].$options.updated);
        }
      });
    },
    inintChartData() {
      let data1 = storageUtils.readExpFrequency();
      if (data1.length > 0) {
        for (let j = 0; j < data1[0].length; j++) {
          this.$refs.dschart.options.series[3].data[j] = [];
          this.$refs.dschart.options.series[3].data[j][0] = data1[0][j];
          this.$refs.dschart.options.series[3].data[j][1] = data1[1][j];
        }
      }
    }
  },
  mounted() {
    this.inintChartData();
  },
  beforeDestroy() {
    bus.$off("expFrequency");
  },

  created() {
    this.setChartData();
  },
  watch: {}
};
</script>

<style>
</style>