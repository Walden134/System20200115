<template>
  <chart ref="dschart" :options="chartdata" style="height:100%;width:100%"></chart>
</template>
<script>
import ECharts from "vue-echarts/components/ECharts.vue";
import "echarts/lib/chart/line";
import "echarts/lib/component/tooltip";
import "echarts/lib/component/legend";
import "echarts/lib/component/title";
import "echarts/lib/chart/bar";
import { getRequest } from "../../utils/api";
import storageUtils from "../../utils/storageUtils";
export default {
  data: function() {
    return {
      chartdata: {
        title: {
          text: "多年平均发电量",
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
          type: "category",
          name: "不同情景",
          nameLocation: "center",
          nameGap: 25,
          data: [],
          axisLabel: {
            fontSize: 10
          },
          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          }
        },
        yAxis: {
          type: "value",
          name: "发电量(亿kW•h)",
          nameLocation: "center",
          nameGap: 40,
          splitLine: {
            show: false
          },
          axisTick: {
            inside: true
          }
        },
        legend: {
          y: "bottom",
          data: ["全年", "丰水期", "平水期", "枯水期"]
        },
        grid: {
          left: "60px",
          right: "20px",
          top: "40px"
        },
        series: [
          {
            type: "bar",
            name: "全年",
            data: []
          },
          {
            type: "bar",
            name: "丰水期",
            data: []
          },

          {
            type: "bar",
            name: "平水期",
            data: []
          },
          {
            type: "bar",
            name: "枯水期",
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
      bus.$on("powerList", data => {
        for (let j = 0; j < data.length; j++) {
          for (let i = 0; i < 4; i++) {
            this.$refs.dschart.options.series[i].data[j] = data[j][i];
          }
        }
        if (data.length == 0 || data == null)
          for (let j = 0; j < this.chartdata.series.length; j++)
            this.$refs.dschart.options.series[j].data = [];
      });
      bus.$on("xAxis", data => {
        this.$refs.dschart.options.xAxis.data = data;
      });
    },
    inintChartData() {
      let data1 = storageUtils.readPowers();
      if (data1) {
        for (let j = 0; j < data1.length; j++) {
          for (let i = 0; i < 4; i++) {
            this.$refs.dschart.options.series[i].data[j] = data1[j][i];
          }
        }
      }
      let data2 = storageUtils.readCategory();
      if (data2) {
        this.$refs.dschart.options.xAxis.data = data2;
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
    bus.$off("powerList");
    bus.$off("xAxis");
  }
};
</script>

<style>
</style>
