<template>

  <chart ref="dschart" :options="chartdata">
  </chart>

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
  data: function() {
    return {
      chartdata: {
        legend: {
          data: [
            { name: "不确定性区间", icon: "rect" },
            { name: "CNRM", icon: "line" },
            { name: "MIROC", icon: "line" },
            { name: "CanESM", icon: "line" },
            { name: "GFDL", icon: "line" },
            { name: "BMA", icon: "line" }
          ],
          top: 40
        },
        grid: {
          left: 70,
          right: 10,
          bottom: 60
        },
        title: {
          left: "center",
          text: "年降水量的不确定性区间"
        },
        toolbox: {
          top: "10%",
          right: "3%",
          feature: {
            dataZoom: {
              yAxisIndex: "none"
            },
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          name: "年份",
          nameLocation: "center",
          nameGap: 40,
          type: "category",
          boundaryGap: false,

          axisLabel: {
            interval: 10
          },
          axisTick: { inside: true },
          data: [
            "2021",
            "2022",
            "2023",
            "2024",
            "2025",
            "2026",
            "2027",
            "2028",
            "2029",
            "2030",
            "2031",
            "2032",
            "2033",
            "2034",
            "2035",
            "2036",
            "2037",
            "2038",
            "2039",
            "2040",
            "2041",
            "2042",
            "2043",
            "2044",
            "2045",
            "2046",
            "2047",
            "2048",
            "2049",
            "2050",
            "2051",
            "2052",
            "2053",
            "2054",
            "2055",
            "2056",
            "2057",
            "2058",
            "2059",
            "2060",
            "2061",
            "2062",
            "2063",
            "2064",
            "2065",
            "2066",
            "2067",
            "2068",
            "2069",
            "2070",
            "2071",
            "2072",
            "2073",
            "2074",
            "2075",
            "2076",
            "2077",
            "2078",
            "2079",
            "2080",
            "2081",
            "2082",
            "2083",
            "2084",
            "2085",
            "2086",
            "2087",
            "2088",
            "2089",
            "2090",
            "2091",
            "2092",
            "2093",
            "2094",
            "2095",
            "2096",
            "2097",
            "2098",
            "2099",
            "2100"
          ]
        },
        yAxis: {
          name: "年降水量(mm)",
          nameLocation: "center",
          nameGap: 35,
          type: "value",
          axisTick: { inside: true },
          boundaryGap: [0, "10%"],
          splitLine: {
            show: false
          }
        },
        dataZoom: [
          {
            type: "inside",
            start: 0,
            end: 100
          },
          {
            start: 0,
            end: 10,
            height: "10",
            width: "600",
            x: "center",
            handleIcon:
              "M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z",
            handleSize: "80%",
            handleStyle: {
              color: "#fff",
              shadowBlur: 3,
              shadowColor: "rgba(0, 0, 0, 0.6)",
              shadowOffsetX: 2,
              shadowOffsetY: 2
            }
          }
        ],
        series: [
          {
            name: "5%",
            stack: " 区间",
            type: "line",
            itemStyle: {
              normal: {
                color: "#FFFFFF"
              }
            },
            areaStyle: {
              normal: {
                color: "rgba(255,255,255,1)"
              }
            },
            data: []
          },
          {
            name: "不确定性区间",
            stack: " 区间",
            type: "line",
            itemStyle: {
              normal: {
                color: "#A9A9A9"
              }
            },
            areaStyle: {
              normal: {
                color: "#A9A9A9"
              }
            },
            data: []
          },
          {
            name: "CNRM",
            type: "line",
            data: []
          },
          {
            name: "MIROC",
            type: "line",
            data: []
          },
          {
            name: "CanESM",
            type: "line",
            data: []
          },
          {
            name: "GFDL",
            type: "line",
            data: []
          },
          {
            name: "BMA",
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
      bus.$on("q", data => {
        this.$refs.dschart.options.series[0].data = data[0];
        this.$refs.dschart.options.series[1].data = data[1];
        this.$refs.dschart.options.series[2].data = data[2];
        this.$refs.dschart.options.series[3].data = data[3];
        this.$refs.dschart.options.series[4].data = data[4];
        this.$refs.dschart.options.series[5].data = data[5];
        this.$refs.dschart.options.series[6].data = data[6];
      });
    },
    inintChartData() {
      let data2 = storageUtils.readQ();
      if (data2.length > 0) {
        this.$refs.dschart.options.series[0].data = data2[0];
        this.$refs.dschart.options.series[1].data = data2[1];
        this.$refs.dschart.options.series[2].data = data2[2];
        this.$refs.dschart.options.series[3].data = data2[3];
        this.$refs.dschart.options.series[4].data = data2[4];
        this.$refs.dschart.options.series[5].data = data2[5];
        this.$refs.dschart.options.series[6].data = data2[6];
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
    bus.$off("q");
  }
};
</script>

<style>
</style>