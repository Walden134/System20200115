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
import { getRequest1 } from "../../utils/api";
export default {
  props: ["title", "request", "situations", "patterns"],
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
          type: "category",
          data: [],
          axisLabel: {
            interval: 0,
            rotate: 0,
            fontSize: 12
          }
        },
        yAxis: { type: "value" },
        grid: {
          left: "5%", // 与容器左侧的距离
          right: "5%", // 与容器右侧的距离
          top: "10%", // 与容器顶部的距离
          bottom: "10%" // 与容器底部的距离
        },
        series: [
          {
            type: "bar",
            data: []
          }
        ],
        animationDuration: 30
      }
    };
  },
  computed: {},
  components: {
    chart: ECharts
  },
  methods: {
    getRequestData() {
      let _this = this;
      let url =
        _this.request +
        "?situations=" +
        JSON.stringify(_this.situations) +
        "&patterns=" +
        JSON.stringify(_this.patterns);

      getRequest(url).then(
        resp => {
          if (resp.status == 200) {
            console.log(resp.data.xAxis.length);
            if (resp.data.xAxis.length > 5) {
              _this.chartdata.xAxis.axisLabel.rotate = 30;
            } else {
              _this.chartdata.xAxis.axisLabel.rotate = 0;
            }
            _this.$refs.dschart.options.xAxis.data = resp.data.xAxis;

            _this.$refs.dschart.options.series[0].data = resp.data.yAxis;
          } else {
            _this.$message({
              type: "error",
              message: "数据加载失败!"
            });
          }
        },
        resp => {
          _this.$message({
            type: "error",
            message: "数据加载失败!"
          });
        }
      );
    }
  },
  mounted() {
    this.getRequestData();
  },
  watch: {
    situations() {
      this.getRequestData();
    },
    patterns() {
      this.getRequestData();
    }
  }
};
</script>
