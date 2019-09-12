<template>
  <chart ref="dschart" :options="chartdata"></chart>
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
import { getRequest } from "../../utils/api";
export default {
  props: ["title", "chartdatas", "request"],
  data: function() {
    return {
      chartdata: {
        title: {
          text: this.title.title,
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
        yAxis: {},
        series: [
          {
            data: [
              0.03,
              0,
              0.006,
              0.032,
              0.006,
              0.028,
              0.014,
              0.052,
              0.006,
              0.022,
              0.014,
              0.028,
              0.03,
              0.012,
              0.002,
              0.002,
              0.06,
              0.038,
              0.018,
              0.052,
              0.028,
              0.058,
              0.03,
              0.008,
              0.008,
              0.044,
              0.114,
              0.012,
              0.05,
              0.03,
              0.092,
              0,
              0.024,
              0.014,
              0.022,
              0.04,
              0.042,
              0.02,
              0.074,
              0.008,
              0.016,
              0.012,
              0.03,
              0.026,
              0.032,
              0.036,
              0.018,
              0.016,
              0.026,
              0.01,
              0.04,
              0,
              0.05,
              0.108,
              0.048,
              0.038,
              0.02,
              0.026,
              0.108,
              0.086,
              0.034,
              0.022,
              0.06,
              0.052,
              0.014,
              0.064,
              0.006,
              0.01,
              0.002,
              0.006,
              0.026,
              0.01,
              0.072,
              0.042,
              0.048,
              0.026,
              0.036,
              0.012,
              0.062,
              0.004
            ],
            type: "bar"
          }
        ],
        grid: {
          left: "5%", // 与容器左侧的距离
          right: "5%", // 与容器右侧的距离
          top: "10%", // 与容器顶部的距离
          bottom: "10%" // 与容器底部的距离
        },
        animationDuration: 3000
      }
    };
  },
  components: {
    chart: ECharts
  },
  mounted() {
    console.log("组件开始加载时该方法被执行");
    this.getRequestData();
  },
  methods: {
    getRequestData() {
      var _this = this;
      getRequest(this.request).then(
        resp => {
          if (resp.status == 200) {
            // _this.$refs.dschart.options.xAxis.data = resp.data.categories;
            // _this.$refs.dschart.options.series[0].data = resp.data.ds;
          } else {
            _this.$message({ type: "error", message: "数据加载失败!" });
          }
        },
        resp => {
          _this.$message({ type: "error", message: "数据加载失败!" });
        }
      );
    }
  }
};
</script>
