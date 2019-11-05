<template>
  <chart ref="dschart" id="Pchart" :options="chartdata" style="height:100%;width:100%"></chart>
</template>

<style>
</style>

<script>
import ECharts from "vue-echarts/components/ECharts";
import "echarts/lib/chart/line";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/polar";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import "echarts/theme/dark";
import "echarts/lib/chart/bar";
import echarts from "echarts";

import { getRequest } from "@/utils/api";
import storageUtils from "@/utils/storageUtils";
export default {
  data: function() {
    return {
      a: "",
      dataFlag: 0,
      chartdata: {
        title: {
          text: "设计洪水频率曲线",
          left: "center"
        },
        toolbox: {
          feature: {
            dataZoom: {
              yAxisIndex: "none"
            },
            restore: {},
            saveAsImage: {}
          }
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
          name: "流量(m³/s)",
          nameLocation: "middle",
          nameGap: 40, //坐标轴名字与坐标轴距离
          type: "value",

          splitLine: {
            show: true,
            lineStyle: {
              color: "black"
            }
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
            data: [],
            markLine: {
              symbol: "none",
              animation: false,
              label: {
                normal: {
                  position: "bottom",
                  formatter: "{b}",
                  color: "black"
                }
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
              data: [
                { name: 0.01, xAxis: 0 },
                { name: 0.05, xAxis: 0.428489754 },
                { name: 0, xAxis: 0.628784179 },
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
            name: "历史洪水",
            type: "scatter",
            symbolsize: 2,
            data: []
          },
          {
            name: "经验频率",
            type: "scatter",
            symbol: "triangle",
            symbolsize: 10,
            data: []
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
              { value: [0, 0], name: "" },
              { value: [0.428489753963786, 0], name: "" },
              { value: [0.628784179287867, 0], name: "" },
              { value: [0.840854746360197, 0], name: "" },
              { value: [1.14318718190678, 0], name: "" },
              { value: [1.39266861141484, 0], name: "" },
              { value: [2.07416285850421, 0], name: "" },
              { value: [2.43746491991108, 0], name: "" },
              { value: [2.68258309596189, 0], name: "" },
              { value: [2.87739525188277, 0], name: "" },
              { value: [3.19461597274764, 0], name: "" },
              { value: [3.46566938231988, 0], name: "" },
              { value: [3.71901648545568, 0], name: "" },
              { value: [3.97236358859148, 0], name: "" },
              { value: [4.24341699816372, 0], name: "" },
              { value: [4.56063771902859, 0], name: "" },
              { value: [4.75544987494947, 0], name: "" },
              { value: [5.00056805100028, 0], name: "" },
              { value: [5.36387011240715, 0], name: "" },
              { value: [6.04536435949652, 0], name: "" },
              { value: [6.80924879162353, 0], name: "" },
              { value: [7.00954321694761, 0], name: "" },
              { value: [7.43803297091111, 0], name: "" },
              { value: [8, 0], name: "" }
            ]
          }
        ],
        grid: {
          top: 25,
          bottom: 45,
          right: 10,
          left: 60
        },
        legend: {
          top: 25,
          data: [
            {
              name: "历史洪水"
            },
            {
              name: "经验频率"
            },
            {
              name: "理论频率",
              icon: "line"
            }
          ],
          orient: "vertical",
          right: "1%",
          backgroundColor: "#FFFFFF",
          zlevel: 1
        }
      }
    };
  },
  computed: {},
  components: {
    chart: ECharts
  },
  methods: {
    normsinv(p) {
      let LOW = 0.02425;
      let HIGH = 0.97575;
      let a = [
        -3.969683028665376e1,
        2.209460984245205e2,
        -2.759285104469687e2,
        1.38357751867269e2,
        -3.066479806614716e1,
        2.506628277459239
      ];
      let b = [
        -5.447609879822406e1,
        1.615858368580409e2,
        -1.556989798598866e2,
        6.680131188771972e1,
        -1.328068155288572e1
      ];
      let c = [
        -7.784894002430293e-3,
        -3.223964580411365e-1,
        -2.400758277161838,
        -2.549732539343734,
        4.374664141464968,
        2.938163982698783
      ];
      let d = [
        7.784695709041462e-3,
        3.224671290700398e-1,
        2.445134137142996,
        3.754408661907416
      ];
      let q, r;
      if (p < LOW) {
        q = Math.sqrt(-2 * Math.log(p));
        return (
          (((((c[0] * q + c[1]) * q + c[2]) * q + c[3]) * q + c[4]) * q +
            c[5]) /
          ((((d[0] * q + d[1]) * q + d[2]) * q + d[3]) * q + 1)
        );
      } else if (p > HIGH) {
        q = Math.sqrt(-2 * Math.log(1 - p));
        return (
          -(
            ((((c[0] * q + c[1]) * q + c[2]) * q + c[3]) * q + c[4]) * q +
            c[5]
          ) /
          ((((d[0] * q + d[1]) * q + d[2]) * q + d[3]) * q + 1)
        );
      } else {
        q = p - 0.5;
        r = q * q;
        return (
          ((((((a[0] * r + a[1]) * r + a[2]) * r + a[3]) * r + a[4]) * r +
            a[5]) *
            q) /
          (((((b[0] * r + b[1]) * r + b[2]) * r + b[3]) * r + b[4]) * r + 1)
        );
      }
    },
    setChartData() {
      bus.$on("a", data => {
        this.a = data;
      });
      bus.$on("dataFlag", data => {
        this.dataFlag = data;
      });
      bus.$on("expFrequency", data => {
        this.$refs.dschart.options.series[2].data = [];
        this.$refs.dschart.options.series[1].data = [];
        if (data.length > 0)
          for (let j = 0; j < data[0].length; j++) {
            if (j >= this.a) {
              this.$refs.dschart.options.series[2].data[j - this.a] = [];
              this.$refs.dschart.options.series[2].data[j - this.a][0] =
                this.normsinv(data[0][j]) + 3.719016485;
              this.$refs.dschart.options.series[2].data[j - this.a][1] =
                data[1][j];
            } else {
              this.$refs.dschart.options.series[1].data[j] = [];
              this.$refs.dschart.options.series[1].data[j][0] =
                this.normsinv(data[0][j]) + 3.719016485;
              this.$refs.dschart.options.series[1].data[j][1] = data[1][j];
            }
          }
        let Pchart = echarts.init(document.getElementById("Pchart"));
        Pchart.setOption(this.chartdata, true);
      });
      bus.$on("theoryFrequency", data => {
        this.$refs.dschart.options.series[0].data = [];
        if (data.length > 0)
          for (let j = 0; j < data[0].length; j++) {
            this.$refs.dschart.options.series[0].data[j] = [];
            this.$refs.dschart.options.series[0].data[j][0] =
              this.normsinv(data[0][j] / 100) + 3.719016485;
            this.$refs.dschart.options.series[0].data[j][1] = data[1][j];
          }
        let Pchart = echarts.init(document.getElementById("Pchart"));
        Pchart.setOption(this.chartdata, true);
      });
    },
    inintChartData() {
      let data1 = storageUtils.readExpFrequency();
      let a = storageUtils.readParams().a;
      if (data1.length > 0) {
        for (let j = 0; j < data1[0].length; j++) {
          if (j >= a) {
            this.$refs.dschart.options.series[2].data[j - a] = [];
            this.$refs.dschart.options.series[2].data[j - a][0] =
              this.normsinv(data1[0][j]) + 3.719016485;
            this.$refs.dschart.options.series[2].data[j - a][1] = data1[1][j];
          } else {
            this.$refs.dschart.options.series[1].data[j] = [];
            this.$refs.dschart.options.series[1].data[j][0] =
              this.normsinv(data1[0][j]) + 3.719016485;
            this.$refs.dschart.options.series[1].data[j][1] = data1[1][j];
          }
        }
      }
      let data2 = storageUtils.readTheoryFrequency();
      if (data2.length > 0) {
        this.$refs.dschart.options.series[0].data = [];
        for (let j = 0; j < data2[0].length; j++) {
          this.$refs.dschart.options.series[0].data[j] = [];
          this.$refs.dschart.options.series[0].data[j][0] =
            this.normsinv(data2[0][j] / 100) + 3.719016485;
          this.$refs.dschart.options.series[0].data[j][1] = data2[1][j];
        }
      }
      let Pchart = echarts.init(document.getElementById("Pchart"));
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
    bus.$off("theoryFrequency");
    bus.$off("expFrequency");
    bus.$off("a");
    this.chart.clear();
  },
  watch: {
    dataFlag(newVal, oldVal) {
      if (newVal > 0) {
        this.$refs.dschart.options.yAxis.name = "洪量(亿m³)";
      } else {
        this.$refs.dschart.options.yAxis.name = "流量(m³/s)";
      }
    }
  }
};
</script>
