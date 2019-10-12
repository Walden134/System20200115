<template>
  <!-- <chart ref="dschart" :options="polarA" style="height:100%;width:100%"></chart> -->
  <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
    <el-tab-pane label="2030S" name="first">
      <chart ref="2030Sdschart" :options="polarA" style="width:1300px;height:340px"></chart>
    </el-tab-pane>
    <el-tab-pane label="2060S" name="second">
      <chart ref="2060Sdschart" :options="polarB" style="width:1300px;height:340px"></chart>
    </el-tab-pane>
    <el-tab-pane label="2090S" name="third">
      <chart ref="2090Sdschart" :options="polarC" style="width:1300px;height:340px"></chart>
    </el-tab-pane>
  </el-tabs>
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
import chartsData from "./runoffJsonData/chartsData.json";

export default {
  data: function() {
    return {
      activeName: "first",
      isAdmin: false,
      polarA: {
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
            restore: { show: true },
            dataZoom: {
              yAxisIndex: "none"
            },
            saveAsImage: { show: true }
          }
        },
        legend: {
          y: "bottom",
          data: [
            { name: "CNRM降水" },
            { name: "CanESM降水" },
            { name: "GFDL降水" },
            { name: "MIROC降水" },
            { name: "CNRM径流", icon: "line" },
            { name: "CanESM径流", icon: "line" },
            { name: "GFDL径流", icon: "line" },
            { name: "MIROC径流", icon: "line" }
          ]
        },
        grid: {
          left: "5%", // 与容器左侧的距离
          right: "5%", // 与容器右侧的距离
          top: "9%" // 与容器顶部的距离
          // bottom: '10%', // 与容器底部的距离
        },
        title: {
          text: "各气候模式2030S降雨-径流过程图",
          x: "center"
        },
        xAxis: {
          type: "category",
          name: "年",
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
            interval: 11,
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
            min: 0,
            max: 800,
            interval: 100,
            nameLocation: "center",
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
            min: 0,
            max: 12000,
            interval: 1500,
            nameLocation: "center",
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
            name: "CNRM降水",
            type: "bar",
            data: []
          },
          {
            name: "CanESM降水",
            type: "bar",
            data: []
          },
          {
            name: "GFDL降水",
            type: "bar",
            data: []
          },
          {
            name: "MIROC降水",
            type: "bar",
            data: []
          },
          {
            name: "CNRM径流",
            type: "line",
            yAxisIndex: 1,
            lineStyle: {
              width: 1
            },
            data: []
          },
          {
            name: "CanESM径流",
            lineStyle: {
              width: 1
            },
            type: "line",
            yAxisIndex: 1,
            data: []
          },
          {
            name: "GFDL径流",
            lineStyle: {
              width: 1
            },
            type: "line",
            yAxisIndex: 1,
            data: []
          },
          {
            name: "MIROC径流",
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
          }
        ]
      },

      polarB: {
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
            restore: { show: true },
            dataZoom: {
              yAxisIndex: "none"
            },
            saveAsImage: { show: true }
          }
        },
        legend: {
          y: "bottom",
          data: [
            { name: "CNRM降水" },
            { name: "CanESM降水" },
            { name: "GFDL降水" },
            { name: "MIROC降水" },
            { name: "CNRM径流", icon: "line" },
            { name: "CanESM径流", icon: "line" },
            { name: "GFDL径流", icon: "line" },
            { name: "MIROC径流", icon: "line" }
          ]
        },
        grid: {
          left: "5%", // 与容器左侧的距离
          right: "5%", // 与容器右侧的距离
          top: "9%" // 与容器顶部的距离
          // bottom: '10%', // 与容器底部的距离
        },
        title: {
          text: "各气候模式2060S降雨-径流过程图",
          x: "center"
        },
        xAxis: {
          type: "category",
          name: "年",
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
            interval: 11,
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
            min: 0,
            max: 800,
            interval: 100,
            nameLocation: "center",
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
            min: 0,
            max: 12000,
            interval: 1500,
            nameLocation: "center",
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
            name: "CNRM降水",
            type: "bar",
            data: []
          },
          {
            name: "CanESM降水",
            type: "bar",
            data: []
          },
          {
            name: "GFDL降水",
            type: "bar",
            data: []
          },
          {
            name: "MIROC降水",
            type: "bar",
            data: []
          },
          {
            name: "CNRM径流",
            type: "line",
            yAxisIndex: 1,
            lineStyle: {
              width: 1
            },
            data: []
          },
          {
            name: "CanESM径流",
            lineStyle: {
              width: 1
            },
            type: "line",
            yAxisIndex: 1,
            data: []
          },
          {
            name: "GFDL径流",
            lineStyle: {
              width: 1
            },
            type: "line",
            yAxisIndex: 1,
            data: []
          },
          {
            name: "MIROC径流",
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
          }
        ]
      },

      polarC: {
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
          // top:"1%",
          feature: {
            // dataView: { show: true, readOnly: false },
            // magicType: {show: true, type: ['line', 'bar']},
            restore: { show: true },
            dataZoom: {
              yAxisIndex: "none"
            },
            saveAsImage: { show: true }
          }
        },
        legend: {
          y: "bottom",
          data: [
            { name: "CNRM降水" },
            { name: "CanESM降水" },
            { name: "GFDL降水" },
            { name: "MIROC降水" },
            { name: "CNRM径流", icon: "line" },
            { name: "CanESM径流", icon: "line" },
            { name: "GFDL径流", icon: "line" },
            { name: "MIROC径流", icon: "line" }
          ]
        },
        grid: {
          left: "5%", // 与容器左侧的距离
          right: "5%", // 与容器右侧的距离
          top: "9%" // 与容器顶部的距离
          // bottom: '10%', // 与容器底部的距离
        },
        title: {
          text: "各气候模式2090S降雨-径流过程图",
          //   textStyle: {
          //     fontSize: 16
          //   },
          // subtext: '',
          x: "center"
        },
        xAxis: {
          type: "category",
          name: "年",
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
            interval: 11,
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
            min: 0,
            max: 800,
            interval: 100,
            nameLocation: "center",
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
            min: 0,
            max: 12000,
            interval: 1500,
            nameLocation: "center",
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
            name: "CNRM降水",
            type: "bar",
            data: []
          },
          {
            name: "CanESM降水",
            type: "bar",
            data: []
          },
          {
            name: "GFDL降水",
            type: "bar",
            data: []
          },
          {
            name: "MIROC降水",
            type: "bar",
            data: []
          },
          {
            name: "CNRM径流",
            type: "line",
            yAxisIndex: 1,
            lineStyle: {
              width: 1
            },
            data: []
          },
          {
            name: "CanESM径流",
            lineStyle: {
              width: 1
            },
            type: "line",
            yAxisIndex: 1,
            data: []
          },
          {
            name: "GFDL径流",
            lineStyle: {
              width: 1
            },
            type: "line",
            yAxisIndex: 1,
            data: []
          },
          {
            name: "MIROC径流",
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
          }
        ]
      }
    };
  },
  components: {
    chart: ECharts
  },
  mounted: function() {},

  created() {
    this.polarA.xAxis.data = chartsData.polarA_xAxis;
    this.polarA.series[0].data = chartsData.polarA_series0;
    this.polarA.series[1].data = chartsData.polarA_series1;
    this.polarA.series[3].data = chartsData.polarA_series3;
    this.polarA.series[4].data = chartsData.polarA_series4;
    this.polarA.series[5].data = chartsData.polarA_series5;
    this.polarA.series[6].data = chartsData.polarA_series6;
    this.polarA.series[7].data = chartsData.polarA_series7;
    this.polarB.xAxis.data = chartsData.polarB_xAxis;
    this.polarB.series[0].data = chartsData.polarB_series0;
    this.polarB.series[1].data = chartsData.polarB_series1;
    this.polarB.series[2].data = chartsData.polarB_series2;
    this.polarB.series[3].data = chartsData.polarB_series3;
    this.polarB.series[4].data = chartsData.polarB_series4;
    this.polarB.series[5].data = chartsData.polarB_series5;
    this.polarB.series[6].data = chartsData.polarB_series6;
    this.polarB.series[7].data = chartsData.polarB_series7;
    this.polarC.xAxis.data = chartsData.polarC_xAxis;
    this.polarC.series[0].data = chartsData.polarC_series0;
    this.polarC.series[1].data = chartsData.polarC_series1;
    this.polarC.series[2].data = chartsData.polarC_series2;
    this.polarC.series[3].data = chartsData.polarC_series3;
    this.polarC.series[4].data = chartsData.polarC_series4;
    this.polarC.series[5].data = chartsData.polarC_series5;
    this.polarC.series[6].data = chartsData.polarC_series6;
    this.polarC.series[7].data = chartsData.polarC_series7;
  },

  methods: {
    handleClick(tab, event) {}
  },
  beforeDestroy() {
    this.chart.clear();
  }
};
</script>

<style>
.el-tabs__nav {
  float: right;
}
</style>