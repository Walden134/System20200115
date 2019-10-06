<template>
  <chart ref="dschart" :options="polar" style="height:100%;width:100%"></chart>
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
  components: {
    chart: ECharts
  },
  mounted: function() {},
  methods: {},
  data: function() {
    return {
      polar: {
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
          right: "15px",
          feature: {
            // dataView: {
            //   show: true,
            //   readOnly: false
            // optionToContent: function(opt) {
            //   var axisData = opt.xAxis[0].data; //坐标数据
            //   var series = opt.series; //折线图数据
            //   var tdHeads = "<td>时间</td>"; //表头第一列
            //   var tdBodys = ""; //表数据
            //   //组装表头
            //   var nameData = new Array(
            //     "OBS",
            //     "CNRM",
            //     "CanESM",
            //     "GFDL",
            //     "MIROC"
            //   );
            //   for (var i = 0; i < nameData.length; i++) {
            //     tdHeads +=
            //       '<td style="padding: 0 10px">' + nameData[i] + "</td>";
            //   }

            //   var table =
            //     '<table style="width:100%;text-align:center" border="1" cellspacing="0" cellpadding="1" ><tbody><tr>' +
            //     tdHeads +
            //     " </tr>";

            //   //组装表数据
            //   for (var i = 0, l = axisData.length; i < l; i++) {
            //     for (var j = 0; j < series.length; j++) {
            //       var temp = series[j].data[i];
            //       if (temp != null && temp != undefined) {
            //         tdBodys += "<td>" + temp.toFixed(0) + "</td>";
            //       } else {
            //         tdBodys += "<td></td>";
            //       }
            //     }
            //     table +=
            //       '<tr><td style="padding: 0 10px">' +
            //       axisData[i] +
            //       "</td>" +
            //       tdBodys +
            //       "</tr>";
            //     tdBodys = "";
            //   }
            //   table += "</tbody></table>";
            //   return table;
            // }
            // },
            // magicType: { show: true, type: ["line", "bar"] },
            // restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        grid: {
          left: "10%", // 与容器左侧的距离
          right: "15px", // 与容器右侧的距离
          top: "8%" // 与容器顶部的距离
          // bottom: '5%', // 与容器底部的距离
        },
        title: {
          text: "各气候模式未来年径流均值",
          //   textStyle: {
          //     fontSize: 16
          //   },
          // subtext: '',
          x: "center"
        },
        xAxis: [
          {
            type: "category",
            name: "时间",
            nameGap: 22,
            nameLocation: "center",
            axisTick: { show: false },
            data: ["2030S", "2060S", "2090S"]
          }
        ],
        yAxis: [
          {
            type: "value",
            name: "流量(m³/s)",
            nameGap: 40,
            nameLocation: "center",
            axisTick: { show: false },
            splitLine: { show: false }
          }
        ],
        legend: {
          y: "bottom",
          data: ["OBS", "CNRM", "CanESM", "GFDL", "MIROC"]
        },
        series: [
          {
            name: "OBS",
            data: [1577, 1577, 1577],
            type: "bar"
          },
          {
            name: "CNRM",
            data: [2056, 2081, 2084],
            type: "bar"
          },
          {
            name: "CanESM",
            data: [1661, 1872, 2009],
            type: "bar"
          },
          {
            name: "GFDL",
            data: [2047, 1947, 1893],
            type: "bar"
          },
          {
            name: "MIROC",
            data: [1687, 1678, 1820],
            type: "bar"
          }
        ]
      }
    };
  }
};
</script>
