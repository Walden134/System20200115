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

import { getRequest } from "../../utils/api";
import storageUtils from "../../utils/storageUtils";
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
            data: []
          },
          {
            name: "经验频率",
            type: "scatter",
            symbol: "triangle",
            symbolsize: 10,
            data: []
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
          data: ["历史洪水", "经验频率", "理论频率"],
          orient: "vertical",
          right: "1%",
          backgroundColor: "#FFFFFF"
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
        this.$refs.dschart.options.series[3].data = [];
        for (let j = 0; j < data[0].length; j++) {
          if (j >= this.a) {
            this.$refs.dschart.options.series[3].data[j - this.a] = [];
            this.$refs.dschart.options.series[3].data[j - this.a][0] =
              this.normsinv(data[0][j]) + 3.719016485;
            this.$refs.dschart.options.series[3].data[j - this.a][1] =
              data[1][j];
          } else {
            this.$refs.dschart.options.series[2].data[j] = [];
            this.$refs.dschart.options.series[2].data[j][0] =
              this.normsinv(data[0][j]) + 3.719016485;
            this.$refs.dschart.options.series[2].data[j][1] = data[1][j];
          }
        }
        let max = Math.max.apply(null, data[1]);
        let barData = this.$refs.dschart.options.series[1].data;
        for (let j = 0; j < barData.length; j++) {
          barData[j].value[1] = max;
        }
        let Pchart = echarts.init(document.getElementById("Pchart"));
        Pchart.setOption(this.chartdata, true);
      });
      bus.$on("theoryFrequency", data => {
        this.$refs.dschart.options.series[0].data = [];
        for (let j = 0; j < data[0].length; j++) {
          this.$refs.dschart.options.series[0].data[j] = [];
          this.$refs.dschart.options.series[0].data[j][0] =
            this.normsinv(data[0][j]) + 3.719016485;
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
            this.$refs.dschart.options.series[3].data[j - a] = [];
            this.$refs.dschart.options.series[3].data[j - a][0] =
              this.normsinv(data1[0][j]) + 3.719016485;
            this.$refs.dschart.options.series[3].data[j - a][1] = data1[1][j];
          } else {
            this.$refs.dschart.options.series[2].data[j] = [];
            this.$refs.dschart.options.series[2].data[j][0] =
              this.normsinv(data1[0][j]) + 3.719016485;
            this.$refs.dschart.options.series[2].data[j][1] = data1[1][j];
          }
        }
        let max = Math.max.apply(null, data1[1]);
        let barData = this.$refs.dschart.options.series[1].data;
        for (let j = 0; j < barData.length; j++) {
          barData[j].value[1] = max;
        }
      }
      let data2 = storageUtils.readTheoryFrequency();
      if (data2.length > 0) {
        this.$refs.dschart.options.series[0].data = [];
        for (let j = 0; j < data2[0].length; j++) {
          this.$refs.dschart.options.series[0].data[j] = [];
          this.$refs.dschart.options.series[0].data[j][0] =
            this.normsinv(data2[0][j]) + 3.719016485;
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
