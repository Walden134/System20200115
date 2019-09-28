<template>
  <el-tabs v-model="activeName" @tab-click="handleClick" type="card">
    <el-tab-pane label="2030S" name="first">
      <chart ref="2030Sdschart" :options="AreaA" style="width:1350px;height:340px"></chart>
    </el-tab-pane>
    <el-tab-pane label="2060S" name="second">
      <chart ref="2060Sdschart" :options="AreaB" style="width:1350px;height:340px"></chart>
    </el-tab-pane>
    <el-tab-pane label="2090S" name="third">
      <chart ref="2090Sdschart" :options="AreaC" style="width:1350px;height:340px"></chart>
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

import runoffData from "../../assets/runoffData.json";

import { getRequest } from "../../utils/api";

export default {
  components: {
    chart: ECharts
  },
  mounted: function() {
    this.AreaA.xAxis.data = runoffData.xAxis_30;
    this.AreaB.xAxis.data = runoffData.xAxis_60;
    this.AreaC.xAxis.data = runoffData.xAxis_90;
    // console.log(runoffData.xAxis_30);
    this.AreaA.series[0].data = runoffData.series1_30;
    this.AreaA.series[1].data = runoffData.series2_30;
    this.AreaA.series[2].data = runoffData.series3_30;
    // console.log("runoffData.series3_30", runoffData.series3_30);
    this.AreaB.series[0].data = runoffData.series1_60;
    this.AreaB.series[1].data = runoffData.series2_60;
    this.AreaB.series[2].data = runoffData.series3_60;
    this.AreaC.series[0].data = runoffData.series1_90;
    this.AreaC.series[1].data = runoffData.series2_90;
    this.AreaC.series[2].data = runoffData.series3_90;
  },
  methods: {
    handleClick(tab, event) {
      //console.log(tab, event);
    }
  },

  created() {},

  data: function() {
    return {
      activeName: "first",
      isAdmin: false,
      AreaA: {
        tooltip: {
          trigger: "axis"
          // position: function(pt) {
          //   return [pt[0], "10%"];
          // }
        },
        legend: {
          y: "bottom",
          data: [
            {
              name: "不确定性区间",
              icon: "rect"
            },
            // { name: "实测径流" },
            { name: "BMA模型" }
          ],
          top: 40
        },
        title: {
          left: "center",
          text: "2030S日径流不确定性区间"
        },
        toolbox: {
          top: "2%",
          right: "15px",
          feature: {
            // dataZoom: {
            //   yAxisIndex: "none"
            // },
            restore: {},
            saveAsImage: {}
          }
        },
        grid: {
          left: "5%", // 与容器左侧的距离
          right: "15px", // 与容器右侧的距离
          top: "7%" // 与容器顶部的距离
          // bottom: '10%', // 与容器底部的距离
        },
        xAxis: {
          name: "年",
          nameGap: 40,
          nameLocation: "center",
          type: "category",
          boundaryGap: false,
          axisLabel: {
            interval: 2045
          },
          axisTick: { show: false },
          data: []
        },
        yAxis: {
          name: "流量(m³/s)",
          nameGap: 40,
          nameLocation: "center",
          min: 0,
          max: 5000,
          type: "value",
          axisTick: { show: false },
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
            handleIcon:
              "M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z",
            handleSize: "100%",
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
            name: "不确定性区间",
            type: "line",
            smooth: true,
            symbol: "none",
            sampling: "average",
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
            name: "5%",
            type: "line",
            smooth: true,
            symbol: "none",
            sampling: "average",
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
            name: "BMA模型",
            type: "line",
            data: []
          }
        ]
      },
      AreaB: {
        tooltip: {
          trigger: "axis"
          // position: function(pt) {
          //   return [pt[0], "10%"];
          // }
        },
        legend: {
          y: "bottom",
          data: [
            {
              name: "不确定性区间",
              icon: "rect"
            },
            // { name: "实测径流" },
            { name: "BMA模型" }
          ],
          top: 40
        },
        title: {
          left: "center",
          text: "2060S日径流不确定性区间"
        },
        toolbox: {
          top: "2%",
          right: "15px",
          feature: {
            // dataZoom: {
            //   yAxisIndex: "none"
            // },
            restore: {},
            saveAsImage: {}
          }
        },
        grid: {
          left: "5%", // 与容器左侧的距离
          right: "15px", // 与容器右侧的距离
          top: "7%" // 与容器顶部的距离
          // bottom: '10%', // 与容器底部的距离
        },

        xAxis: {
          name: "年",
          nameGap: 40,
          nameLocation: "center",
          type: "category",
          boundaryGap: false,
          axisLabel: {
            interval: 2045
          },
          axisTick: { show: false },
          data: []
        },
        yAxis: {
          name: "流量(m³/s)",
          nameGap: 40,
          nameLocation: "center",
          min: 0,
          max: 5000,
          type: "value",
          axisTick: { show: false },
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
            handleIcon:
              "M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z",
            handleSize: "100%",
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
            name: "不确定性区间",
            type: "line",
            smooth: true,
            symbol: "none",
            sampling: "average",
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
            name: "5%",
            type: "line",
            smooth: true,
            symbol: "none",
            sampling: "average",
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
            name: "BMA模型",
            type: "line",
            data: []
          }
        ]
      },
      AreaC: {
        tooltip: {
          trigger: "axis"
          // position: function(pt) {
          //   return [pt[0], "10%"];
          // }
        },
        legend: {
          y: "bottom",
          data: [
            {
              name: "不确定性区间",
              icon: "rect"
            },
            // { name: "实测径流" },
            { name: "BMA模型" }
          ],
          top: 40
        },
        title: {
          left: "center",
          text: "2090S日径流不确定性区间"
        },
        toolbox: {
          top: "2%",
          right: "15px",
          feature: {
            // dataZoom: {
            //   yAxisIndex: "none"
            // },
            restore: {},
            saveAsImage: {}
          }
        },
        grid: {
          left: "5%", // 与容器左侧的距离
          right: "15px", // 与容器右侧的距离
          top: "7%" // 与容器顶部的距离
          // bottom: '10%', // 与容器底部的距离
        },

        xAxis: {
          name: "年",
          nameGap: 40,
          nameLocation: "center",
          type: "category",
          boundaryGap: false,
          axisLabel: {
            interval: 2045
          },
          axisTick: { show: false },
          data: []
        },
        yAxis: {
          name: "流量(m³/s)",
          nameGap: 40,
          nameLocation: "center",
          min: 0,
          max: 5000,
          type: "value",
          axisTick: { show: false },
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
            handleIcon:
              "M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z",
            handleSize: "100%",
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
            name: "不确定性区间",
            type: "line",
            smooth: true,
            symbol: "none",
            sampling: "average",
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
            name: "5%",
            type: "line",
            smooth: true,
            symbol: "none",
            sampling: "average",
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
            name: "BMA模型",
            type: "line",
            data: []
          }
        ]
      }
    };
  }
};
</script>

<style>
</style>