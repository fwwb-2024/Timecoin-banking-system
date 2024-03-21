<template>
  <div class="main">
    <div ref="main" style="width: 800px;height:500px;"></div>
    <el-dropdown class="main-button" @command="handleClick">
      <el-button type="primary">
        近{{timeRange}}数据<i class="el-icon-arrow-down el-icon--right" ></i>
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
import {getCompleteTask} from "@/api/api";

export default {
  name: "completeTaskAnalysis",
  data(){
    return {
      timeRange: '一周',
      chart: null,
      xAxisData: [],
      seriesData: []
    }
  },
  mounted() {
    this.loadData()
    this.initChart()
  },
  methods:{
    // 加载数据
    loadData(){
      let temp
      switch (this.timeRange){
        case '一周':temp = 1;break;
        case '一月':temp = 2;break;
        case '一季度':temp = 3;break;
      }
      getCompleteTask(temp).then((res)=>{
        if (res.data) {
          this.xAxisData = []
          this.seriesData = []
          for(let i=0;i<res.data.length;i++){
            this.xAxisData.push(res.data[i].taskBeginTime)
            this.seriesData.push(res.data[i].counts)
          }
          this.setOptions();
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
      const option = {
        title: {
          text: '近期完成任务统计',
          left: 'center',
          top: 'top',
          textStyle: {
            fontSize: 18,
            fontWeight: 'bold'
          }
        },
        tooltip: {},
        xAxis: {
          data: this.xAxisData
        },
        yAxis: {
          type:'value',
          axisLabel: {
            formatter: function (value) {
              return Math.round(value); // 直接返回整数值
            }
          },
          minInterval: 1 // 设置最小间隔为1
        },
        series: [{
          name:'完成量',
          type:'bar',
          data: this.seriesData
        }]
      };
      this.chart.setOption(option);
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