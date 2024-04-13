<template>
  <div class="main">
    <div ref="main" style="width: 800px;height:500px;"></div>
    <el-dropdown class="main-button" @command="handleClick" v-if="show">
      <el-button type="primary">
        近{{timeRange}}数据<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item command="一周">一周</el-dropdown-item>
        <el-dropdown-item command="一月">一月</el-dropdown-item>
        <el-dropdown-item command="一季度">一季度</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import {getCoinsPaper} from "@/api/api";

export default {
  name: "timeCoinsAnalysis",
  data() {
    return {
      timeRange: '一周',
      chart: null,
      xAxisData: [],
      y1Data: [],
      y2Data: [],
    };
  },
  props: {
    show: {
      type: Boolean,
      default: true // 默认值
    }
  },
  mounted() {
    this.loadData()
    this.initChart()
  },
  methods: {
    // 加载数据
    loadData(){
      let temp
      switch (this.timeRange){
        case '一周':temp = 1;break;
        case '一月':temp = 2;break;
        case '一季度':temp = 3;break;
      }
      getCoinsPaper(temp).then((res)=>{
        if (res.data) {
          this.xAxisData = []
          this.y1Data = []
          this.y2Data = []
          for (let i = 0; i < res.data.length; i++) {
            this.xAxisData.push(res.data[i].taskBeginTime)
            this.y1Data.push(res.data[i].counts+1)
          }
          // 计算时间币增长率
          this.y2Data.push(0)
          for (let i = 1; i < this.y1Data.length; i++) {
            this.y2Data.push(((this.y1Data[i] - this.y1Data[i - 1]) / this.y1Data[i - 1]) * 100)
          }
          this.setOptions()
        }
      })
    },
    // 更改查看日期
    handleClick(value){
      this.timeRange = value
      this.loadData()
    },
    // 渲染图像
    initChart() {
      this.chart = this.$echarts.init(this.$refs.main);
      this.setOptions();
    },
    setOptions() {
      this.chart.setOption({
        title: {
          text: '时间币流水及增长率',
          left: 'center',
          top: 'top',
          textStyle: {
            fontSize: 18,
            fontWeight: 'bold'
          }
        },
        // 鼠标悬停提示框
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          formatter: function (params) {
            let tooltip = params[0].name + '<br/>';
            params.forEach(function (item) {
              tooltip += item.seriesName + ' : ' + item.value + '<br/>'; // 每个系列的数据
            });
            return tooltip;
          }
        },
        xAxis: {
          type: 'category',
          data: this.xAxisData
        },
        yAxis: [
          {
            type: 'value',
            name: '时间币流水',
            min: 0,
            axisLabel: {
              formatter: function (value) {
                return Math.round(value); // 直接返回整数值
              }
            },
            minInterval: 1 // 设置最小间隔为1
          },
          {
            type: 'value',
            name: '增长率',
            min: 0,
            axisLabel: {
              formatter: '{value}%'
            }
          }
        ],
        series: [
          {
            name: '时间币流水',
            type: 'bar',
            data: this.y1Data,
            itemStyle: {
              color: '#FF9800' // 柱状图颜色
            }
          },
          {
            name: '增长率',
            type: 'line',
            yAxisIndex: 1,
            data: this.y2Data,
            itemStyle: {
              color: '#7cb5ec' // 柱状图颜色
            }
          }
        ]
      })
    }
  }
}
</script>

<style scoped>
.main {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.main-button {
  position: absolute;
  top: 100px;
  right: 100px;
}
</style>