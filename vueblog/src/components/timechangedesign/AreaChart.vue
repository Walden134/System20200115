<template>

  <chart ref="dschart"
         :options="chartdata">
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
            {
              name: "不确定性区间",
              icon: "rect"
            },
            { name: "CNRM" },
            { name: "MIROC" },
            { name: "CanESM" },
            { name: "GFDL" },
            { name: "BMA" }
          ],
          top: 40
        },
        grid: {
          left: "5%",
          right: "3%",
          bottom: "15%"
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
            // data: [
            //   "545.245821720463",
            //   "502.072149795254",
            //   "466.036010686855",
            //   "599.928405823267",
            //   "412.482424770727",
            //   "507.877204916314",
            //   "577.819176494475",
            //   "552.11675706928",
            //   "485.682718030732",
            //   "568.761157103028",
            //   "596.243906292081",
            //   "565.391615662978",
            //   "599.014371123446",
            //   "549.675609255193",
            //   "508.595245641429",
            //   "508.40396982465",
            //   "625.850765042761",
            //   "573.183395071587",
            //   "591.514378634432",
            //   "478.794805677273",
            //   "505.564800802446",
            //   "636.998375405474",
            //   "483.047987259162",
            //   "565.981310757366",
            //   "462.376884062399",
            //   "525.911185099117",
            //   "533.3956007175",
            //   "604.463558115068",
            //   "623.1282721056",
            //   "595.848550542872",
            //   "683.645609603148",
            //   "462.084113663702",
            //   "597.364277426522",
            //   "570.147204939785",
            //   "518.976444042925",
            //   "557.447491692076",
            //   "636.362721521976",
            //   "397.870039222444",
            //   "650.790432094741",
            //   "514.652448584206",
            //   "594.174874067759",
            //   "541.695018340784",
            //   "604.142272851305",
            //   "560.131477666351",
            //   "466.225380530606",
            //   "421.845502783583",
            //   "556.909508904673",
            //   "561.575497116814",
            //   "597.28496487607",
            //   "520.420565541688",
            //   "512.953402718319",
            //   "481.141206308192",
            //   "508.396801494597",
            //   "674.160557951154",
            //   "643.473424365061",
            //   "610.403001554754",
            //   "565.986996324935",
            //   "565.178840376738",
            //   "676.195265815369",
            //   "661.948751234097",
            //   "611.595394880959",
            //   "570.580048144233",
            //   "618.838352006632",
            //   "617.520253479328",
            //   "563.006330329813",
            //   "654.337922597425",
            //   "518.067239513651",
            //   "505.007073134032",
            //   "562.591636498385",
            //   "466.076046677997",
            //   "533.634294817477",
            //   "502.387974158419",
            //   "656.75497395816",
            //   "635.738508987657",
            //   "573.763404096361",
            //   "555.120705488581",
            //   "625.136595580412",
            //   "528.112254742727",
            //   "609.804428233608",
            //   "493.647958139362"
            // ]
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