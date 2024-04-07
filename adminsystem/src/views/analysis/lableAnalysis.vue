<template>
  <div class="main">
    <div class="main-table" ref="main" style="width: 800px;height:500px;"></div>
  </div>
</template>

<script>
import {getLabelTask} from "@/api/api";

export default {
  name: "lableAnalysis",
  data(){
    return {
      chart: null,
      data:[]
    }
  },
  mounted() {
    this.loadData()
    this.initChart()
  },
  methods:{
    loadData(){
      getLabelTask().then((res)=>{
        for(let i=0;i<res.data.length;i++){
          let lable
          switch(res.data[i].taskLable){
            case 2:lable = '跑腿';break;
            case 3:lable = '代购';break;
            case 4:lable = '打理';break;
            case 5:lable = '陪伴';break;
            case 6:lable = '线上';break;
            case 1:lable = '其他';break;
          }
          this.data.push({label:lable,counts:res.data[i].counts})
        }
        this.setOptions();
      })
    },
    // 渲染图像
    initChart() {
      this.chart = this.$echarts.init(this.$refs.main);
      this.setOptions();
    },
    setOptions() {
      const option = {
        title: {
          text: '各类别任务统计',
          left: 'center',
          top: 'top',
          textStyle: {
            fontSize: 18,
            fontWeight: 'bold'
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: '{b}任务数量: {c} ({d}%)'
        },
        legend: {
          orient: 'horizontal',
          left: 'center',
          bottom: 10,
          data: this.data.map(item => item.label) // 图例的数据来源于数据数组中的 label 属性
        },
        series: [
          {
            name: '数据项',
            type: 'pie',
            radius: '50%', // 饼图半径，可设置为百分比或具体数值
            center: ['50%', '50%'], // 饼图的位置
            avoidLabelOverlap: false,
            label: {
              show: false,
              position: 'center'
            },
            emphasis: {
              label: {
                show: true,
                fontSize: '20',
                fontWeight: 'bold'
              }
            },
            // 设置标签和引导线
            label: {
              normal: {
                show: true,
                position: 'outside', // 标签显示在扇区外部
                formatter: '{b}:{d}%' // 自定义标签的显示内容
              }
            },
            labelLine: {
              normal: {
                show: true, // 显示引导线
                length: 10, // 引导线的长度
                length2: 20, // 第二段引导线的长度
                smooth: true // 引导线平滑
              }
            },
            data: this.data.map(item => ({ value: item.counts, name: item.label }))
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