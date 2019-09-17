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
  mounted: function() {
    var _this = this;
    getRequest("/article/dataStatistics").then(
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
  },
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
          right: "8%",
          feature: {
            dataView: {
              show: true,
              readOnly: false,
              optionToContent: function(opt) {
                var axisData = opt.xAxis[0].data; //坐标数据
                var series = opt.series; //折线图数据
                var tdHeads = "<td>时间</td>"; //表头第一列
                var tdBodys = ""; //表数据
                //组装表头
                var nameData = new Array(
                  "OBS",
                  "CNRM",
                  "CanESM",
                  "GFDL",
                  "MIROC"
                );
                for (var i = 0; i < nameData.length; i++) {
                  tdHeads +=
                    '<td style="padding: 0 10px">' + nameData[i] + "</td>";
                }

                var table =
                  '<table style="width:100%;text-align:center" border="1" cellspacing="0" cellpadding="1" ><tbody><tr>' +
                  tdHeads +
                  " </tr>";

                //组装表数据
                for (var i = 0, l = axisData.length; i < l; i++) {
                  for (var j = 0; j < series.length; j++) {
                    var temp = series[j].data[i];
                    if (temp != null && temp != undefined) {
                      tdBodys += "<td>" + temp.toFixed(0) + "</td>";
                    } else {
                      tdBodys += "<td></td>";
                    }
                  }
                  table +=
                    '<tr><td style="padding: 0 10px">' +
                    axisData[i] +
                    "</td>" +
                    tdBodys +
                    "</tr>";
                  tdBodys = "";
                }
                table += "</tbody></table>";
                return table;
              }
            },
            // magicType: { show: true, type: ["line", "bar"] },
            // restore: { show: true },
            saveAsImage: { show: true }
          }
        },
        grid: {
          left: "10%", // 与容器左侧的距离
          right: "10%", // 与容器右侧的距离
          top: "8%" // 与容器顶部的距离
          // bottom: '5%', // 与容器底部的距离
        },
        title: {
          text: "RCP2.6情景下各气候模式未来输出径流月平均过程对比",
          // textStyle: {
          //   fontSize: 16
          // },
          // subtext: '',
          x: "center"
        },
        xAxis: [
          {
            type: "category",
            name: "月",
            nameGap: 20,
            nameLocation: "center",
            axisTick: { show: false },
            data: [
              "1",
              "2",
              "3",
              "4",
              "5",
              "6",
              "7",
              "8",
              "9",
              "10",
              "11",
              "12"
            ]
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
            data: [
              538.93,
              459.43,
              435.3,
              503.89,
              769.58,
              1393.17,
              3284.54,
              3188.34,
              3537.91,
              2746.15,
              1297.17,
              775.17
            ],
            type: "line"
          },
          {
            name: "CNRM",
            data: [
              536.67,
              402.17,
              399.09,
              531.5,
              911.5,
              2123.24,
              3872.63,
              4463.34,
              4702.88,
              3474.8,
              2042.34,
              1106.83
            ],
            type: "line"
          },
          {
            name: "CanESM",
            data: [
              447.97,
              393.12,
              455.96,
              683.97,
              1183.18,
              2927.63,
              3422.02,
              3803.08,
              3946.59,
              2599.96,
              1486.64,
              760.36
            ],
            type: "line"
          },
          {
            name: "GFDL",
            data: [
              463.69,
              395.55,
              440.41,
              684.68,
              1211.92,
              2726.74,
              3843.86,
              4365.11,
              4254.13,
              2799.39,
              1580.16,
              804.29
            ],
            type: "line"
          },
          {
            name: "MIROC",
            data: [
              474.24,
              388.84,
              430.36,
              595.5,
              919.42,
              2149.77,
              3459.02,
              3741.55,
              3495.51,
              2621.2,
              1594.1,
              856.83
            ],
            type: "line"
          }
        ]
      }
    };
  }
};
</script>
