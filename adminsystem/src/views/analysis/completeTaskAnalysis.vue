<template>
  <div>
    完成任务分析页面
    <div ref="main" style="width: 600px;height:400px;"></div>
  </div>
</template>

<script>
export default {
  name: "completeTaskAnalysis",
  mounted() {
    this.drawBarChart();
  },
  methods:{
    drawBarChart() {
      let myChart = this.$echarts.init(this.$refs.main);

      let option = {
        title: {
          text: '柱状图示例'
        },
        tooltip: {},
        legend: {
          data:['销量']
        },
        xAxis: {
          data: ["衬衫","羊毛衫","雪纺衫","裤子","高跟鞋","袜子"]
        },
        yAxis: {},
        series: [{
          name: '销量',
          type: 'bar',
          data: [5, 20, 36, 10, 10, 20]
        }]
      };
      myChart.setOption(option);
    }
  }
}
</script>

<style scoped>

</style>
