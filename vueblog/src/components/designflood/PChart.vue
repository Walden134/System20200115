<template>
  <chart ref="dschart" :options="P3" style="height:100%;width:100%"> </chart>

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
          }
        },
        yAxis: [
          {
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
          }
        ],
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
              { value: [0, 12000], name: "0.01" },
              { value: [0.428489753963786, 12000], name: "0.02" },
              { value: [0.628784179287867, 12000], name: "0.12" },
              { value: [0.840854746360197, 12000], name: "0.2" },
              { value: [1.14318718190678, 12000], name: "0.5" },
              { value: [1.39266861141484, 12000], name: "1" },
              { value: [2.07416285850421, 12000], name: "5" },
              { value: [2.43746491991108, 12000], name: "10" },
              { value: [2.68258309596189, 12000], name: "15" },
              { value: [2.87739525188277, 12000], name: "20" },
              { value: [3.19461597274764, 12000], name: "30" },
              { value: [3.46566938231988, 12000], name: "40" },
              { value: [3.71901648545568, 12000], name: "50" },
              { value: [3.97236358859148, 12000], name: "60" },
              { value: [4.24341699816372, 12000], name: "70" },
              { value: [4.56063771902859, 12000], name: "80" },
              { value: [4.75544987494947, 12000], name: "85" },
              { value: [5.00056805100028, 12000], name: "90" },
              { value: [5.36387011240715, 12000], name: "95" },
              { value: [6.04536435949652, 12000], name: "99" },
              { value: [6.80924879162353, 12000], name: "99.9" },
              { value: [7.00954321694761, 12000], name: "99.95" },
              { value: [7.43803297091111, 12000], name: "99.99" },
              { value: [8, 12000], name: "" }
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
            // data:   [[1.3815987854718905, 2520],
            //   [1.65308502319529, 2200],
            //   [2.0842515803989348, 1400],
            //   [2.331753969015686, 1210],
            //   [2.5151084291133636, 960],
            //   [2.665028691172713, 920],
            //   [2.7943739784840145, 890],
            //   [2.909842860575382, 880],
            //   [3.015411285207077, 790],
            //   [3.1136598535012308, 784],
            //   [3.206379746214251, 670],
            //   [3.2948835097066254, 650],
            //   [3.3801790876036284, 638],
            //   [3.4630744753034763, 590],
            //   [3.544244756944189, 520],
            //   [3.624277803432958, 510],
            //   [3.7037076341863187, 480],
            //   [3.7830408452817723, 470],
            //   [3.86277971260816, 462],
            //   [3.9434447840554903, 440],
            //   [4.025599656727455, 386],
            //   [4.109881147636483, 368],
            //   [4.197039412774888, 340],
            //   [4.28799532854101, 322],
            //   [4.383927970288792, 300],
            //   [4.486416497512472, 288],
            //   [4.597686295116908, 262],
            //   [4.721071665509594, 240],
            //   [4.861980445781249, 220],
            //   [5.030216591585532, 200],
            //   [5.246959137040463, 186],
            //   [5.576352321894907, 160]
            // ]
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
        }
      });
    },
    inintChartData() {
      let data1 = storageUtils.readExpFrequency();
      console.log(data1);
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