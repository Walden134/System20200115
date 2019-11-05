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
          text: "水位频率曲线",
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
          data: [
            "1",
            "2",
            "4",
            "5",
            "6",
            "7",
            "9",
            "10",
            "11",
            "12",
            "14",
            "15",
            "16",
            "17",
            "19",
            "20",
            "21",
            "22",
            "23",
            "25",
            "26",
            "27",
            "28",
            "30",
            "31",
            "32",
            "33",
            "35",
            "36",
            "37",
            "38",
            "40",
            "41",
            "42",
            "43",
            "44",
            "46",
            "47",
            "48",
            "49",
            "50",
            "51",
            "52",
            "53",
            "54",
            "56",
            "57",
            "58",
            "59",
            "60",
            "62",
            "63",
            "64",
            "65",
            "67",
            "68",
            "69",
            "70",
            "72",
            "73",
            "74",
            "75",
            "77",
            "78",
            "79",
            "80",
            "81",
            "83",
            "84",
            "85",
            "86",
            "88",
            "89",
            "90",
            "91",
            "93",
            "94",
            "95",
            "96",
            "98",
            "99",
            "100"
          ],
          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          },
          axisLabel: {
            interval: function(index, value) {
              if (value == 1 || value % 10 == 0) {
                return true;
              }
              return false;
            },
            rotate: 0,
            fontSize: 12
          }
        },
        yAxis: {
          type: "value",
          name: "水位(m)",
          nameLocation: "center",
          nameGap: 40,
          min: 2090,
          max: 2110,
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
            { name: "50%", icon: "line" },
            { name: "75%", icon: "line" },
            { name: "90%", icon: "line" },
            { name: "95%", icon: "line" }
          ]
        },
        grid: {
          left: "60px",
          right: "10px",
          top: "40px"
        },
        series: [
          {
            name: "50%",
            type: "line",
            data: []
          },
          {
            name: "75%",
            type: "line",
            data: []
          },
          {
            name: "90%",
            type: "line",
            data: []
          },
          {
            name: "95%",
            type: "line",
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
      bus.$on("riskRes", data => {
        if (data.length > 0) {
          data[3].reverse();
          data[4].reverse();
          data[5].reverse();
          data[6].reverse();
        }
        this.chartdata.series[0].data = data[3];
        this.chartdata.series[1].data = data[4];
        this.chartdata.series[2].data = data[5];
        this.chartdata.series[3].data = data[6];
      });
    },
    inintChartData() {
      let data2 = storageUtils.readRiskRes();
      if (data2.length > 0) {
        this.chartdata.series[0].data = data2[3];
        this.chartdata.series[1].data = data2[4];
        this.chartdata.series[2].data = data2[5];
        this.chartdata.series[3].data = data2[6];
      }
    }
  },
  mounted() {
    this.inintChartData();
  },
  beforeDestroy() {
    bus.$off("riskRes");
  },
  created() {
    this.setChartData();
  }
};
</script>