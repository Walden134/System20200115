<template>
  <chart ref="dschart" :options="option" style="height:100%;width:100%"></chart>
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
import echarts from "echarts";
import $ from "jquery";
import storageUtils from "../../utils/storageUtils";
import { getRequest } from "../../utils/api";

export default {
  data: function() {
    return {
      option: {
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
          right: "5%",
          feature: {
            dataZoom: {
              yAxisIndex: "none"
            },
            restore: { show: true },

            saveAsImage: { show: true }
          }
        },
        legend: {
          y: "bottom",
          data: [
            { name: "CanESM降水" },
            { name: "CNRM降水" },
            { name: "GFDL降水" },
            { name: "MIROC降水" },
            { name: "CanESM径流", icon: "line" },
            { name: "CNRM径流", icon: "line" },
            { name: "GFDL径流", icon: "line" },
            { name: "MIROC径流", icon: "line" }
          ]
        },
        grid: {
          left: "5%",
          right: "5%",
          top: "9%"
        },
        title: {
          text: "降水-径流过程",
          x: "center"
        },
        xAxis: {
          type: "category",
          name: "时间",
          nameGap: 25,
          nameLocation: "center",
          data: [],
          axisPointer: {
            type: "shadow"
          },
          axisLine: {
            lineStyle: {
              width: 2,
              color: "grey"
            }
          },
          axisLabel: {
            interval: function(index, value) {
              var d = new Date(value);
              var day = d.getDate();
              var month = d.getMonth();
              if (month == "0" && day == "1") {
                return true;
              }
              return false;
            },
            textStyle: {
              color: "black"
            }
          },
          axisTick: { show: false },
          position: "bottom"
        },
        yAxis: [
          {
            type: "value",
            name: "降水量(mm)",
            nameGap: 40,
            nameLocation: "center",
            boundaryGap: ["0%", "150%"],
            axisLine: {
              lineStyle: {
                width: 2,
                color: "grey"
              }
            },
            axisLabel: {
              formatter: "{value} ",
              textStyle: {
                color: "black"
              }
            },
            axisTick: { show: false },
            splitLine: { show: false },
            inverse: true
          },
          {
            type: "value",
            name: "流量(m³/s)",
            nameGap: 45,
            nameLocation: "center",
            boundaryGap: ["0%", "150%"],

            axisLine: {
              lineStyle: {
                width: 2,
                color: "grey"
              }
            },
            axisLabel: {
              formatter: "{value} ",
              textStyle: {
                color: "black"
              }
            },
            axisTick: { show: false },
            splitLine: { show: false }
          }
        ],
        series: [
          {
            showSymbol: false,
            sampling: "average",
            showAllSymbol: false,
            name: "CanESM降水",
            type: "bar",
            data: []
          },
          {
            showSymbol: false,
            sampling: "average",
            showAllSymbol: false,
            name: "CNRM降水",
            type: "bar",
            data: []
          },
          {
            showSymbol: false,
            sampling: "average",
            showAllSymbol: false,
            name: "GFDL降水",
            type: "bar",
            data: []
          },
          {
            showSymbol: false,
            sampling: "average",
            showAllSymbol: false,
            name: "MIROC降水",
            type: "bar",
            data: []
          },
          {
            name: "CanESM径流",
            type: "line",
            yAxisIndex: 1,
            markLine: {
              itemStyle: {
                normal: {
                  lineStyle: {
                    width: 1,
                    color: "black",
                    type: "solid"
                  },
                  label: { show: false }
                }
              },
              data: [{ yAxis: 0 }],
              symbol: ["none", "none"]
            },
            data: []
          },
          {
            name: "CNRM径流",
            type: "line",
            yAxisIndex: 1,
            data: []
          },
          {
            name: "GFDL径流",
            type: "line",
            yAxisIndex: 1,
            data: []
          },
          {
            name: "MIROC径流",
            type: "line",
            yAxisIndex: 1,
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
      bus.$on("swatData", data => {
        // CanESM CNRM GFDL MIROC   ssq ccq
        for (let i = 0; i < data.length; i++) {
          this.option.series[i].data = data[i].ssp;
          this.option.series[i + 4].data = data[i].ccq;
          console.log("ssp", this.option.series[i].data);
        }
        this.option.xAxis.data = data[0].xAxis;
        console.log(data);
      });
    },
    inintChartData() {
      let data1 = storageUtils.readPowers();
    },
    handleClick(tab, event) {
      switch (tab.index) {
        case "0": //30s
          console.log("0", tab.index);
          break;
        case "1": //60s
          console.log("1", tab.index);
          break;
        case "2": //90s
          console.log("2", tab.index);
          break;
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
    bus.$off("swatData");
  }
};
</script>

<style>
.el-tabs__nav {
  float: right;
}
</style>