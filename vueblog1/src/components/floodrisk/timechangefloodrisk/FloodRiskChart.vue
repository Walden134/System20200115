<template>
  <chart ref="dschart" :options="chartdata" style="height:100%;width:100%" id="chart"></chart>
</template>

<style>
</style>

<script>
import ECharts from "vue-echarts/components/ECharts.vue";
import "echarts/lib/chart/line";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import "echarts/lib/chart/bar";
import echarts from "echarts";
import { getRequest } from "@/utils/api";
import storageUtils from "@/utils/storageUtils";

export default {
  props: ["title_text", "data"],
  data: function() {
    return {
      chartdata: {
        title: {
          text: this.title_text,
          left: "center"
        },
        toolbox: {
          show: true,
          feature: {
            magicType: {
              type: ["bar", "line"]
            },
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          name: "时间(年)",
          nameLocation: "center",
          // nameGap: 40,
          type: "category",
          // boundaryGap: false,
          // axisTick: { inside: true },

          data: ["2030S", "2060S", "2090S"]
          /* data: [
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
          ] */
        },
        yAxis: {
          name: "风险率（%）",
          nameLocation: "center",
          nameGap: 40,
          axisTick: {
            inside: true
          },
          splitLine: {
            show: false
          }
        },
        legend: {
          y: "bottom",
          data: [
            { name: "RCP2.6", icon: "bar" },
            { name: "RCP4.5", icon: "bar" },
            { name: "RCP8.5", icon: "bar" }
          ]
        },
        grid: {
          left: "60px",
          right: "20px",
          top: "40px"
        },
        series: [
          {
            name: "RCP2.6",
            data: [],
            type: "bar"
          },
          {
            name: "RCP4.5",
            data: [],
            type: "bar"
          },
          {
            name: "RCP8.5",
            data: [],
            type: "bar"
          }
        ]
      }
    };
  },
  components: {
    chart: ECharts
  },
  watch: {
    data() {
      this.chartdata.series[0].data = this.data;
      console.log(this.chartdata.series[0].data);
    }
  }
};
</script>
