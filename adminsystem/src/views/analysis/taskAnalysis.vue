<template>
  <div class="main">
    <div ref="main" style="width: 800px;height:500px;"></div>
    <el-dropdown class="main-button" @command="handleClick" v-if="show">
      <el-button type="primary">
        近{{timeRange}}数据<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown" >
        <el-dropdown-item command="一周">一周</el-dropdown-item>
        <el-dropdown-item command="一月">一月</el-dropdown-item>
        <el-dropdown-item command="一季度">一季度</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
  </div>
</template>

<script>
import {getCompleteTask, getCreateTask} from "@/api/api";

export default {
  name: "taskAnalysis",
  data(){
    return {
      timeRange: '一周',
      chart: null,
      xAxisData: [],
      y1Data:[],
      y2Data: [],
      is:false
    }
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
  methods:{
    // 加载数据
    loadData(){
      let temp
      switch (this.timeRange){
        case '一周':temp = 1;break;
        case '一月':temp = 2;break;
        case '一季度':temp = 3;break;
      }
      getCreateTask(temp).then((res)=>{
        if (res.data) {
          this.xAxisData = []
          this.y1Data = []
          for(let i=0;i<res.data.length;i++){
            this.xAxisData.push(res.data[i].taskBeginTime)
            this.y1Data.push(res.data[i].counts+1)
          }
          getCompleteTask(temp).then((res)=>{
            this.y2Data = []
            if (res.data) {
              for(let i=0;i<res.data.length;i++){
                if(res.data[i].counts == 0){
                  this.y2Data.push(1)
                }
                else{
                  this.y2Data.push(res.data[i].counts)
                }
              }
              this.setOptions();
            }
          })
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
          text: '近期发布及完成任务统计',
          left: 'center',
          top: 'top',
          textStyle: {
            fontSize: 18,
            fontWeight: 'bold'
          }
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['发布任务数量', '完成任务数量'],
          orient: 'horizontal', // 设置图例水平排列
          bottom: 10 // 设置图例距离底部的距离
        },
        xAxis: {
          type: 'category',
          data: this.xAxisData
        },
        yAxis: {
          type: 'value',
          name: '任务数量',
          axisLabel: {
            formatter: function (value) {
              return Math.round(value); // 直接返回整数值
            }
          },
        },
        series: [
          {
            name: '发布任务数量',
            type: 'bar',
            data: this.y1Data,
            // 设置第一个系列的样式
            itemStyle: {
              color: '#5ab1ef' // 柱状图颜色
            }
          },
          {
            name: '完成任务数量',
            type: 'bar',
            data: this.y2Data,
            // 设置第二个系列的样式
            itemStyle: {
              color: '#ffb980' // 柱状图颜色
            }
          }
        ]
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