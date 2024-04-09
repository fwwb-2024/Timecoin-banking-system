<template>
    <div class="main">
      <div class="head">
        <el-card class="head-user">
          <img src="../../../public/userIndex.png">
          <span class="head-title">用户数量</span>
          <div class="head-content">{{usercount}}</div>
        </el-card>
        <el-card class="head-coins">
          <img src="../../../public/coinIndex.png">
          <span class="head-title">交易金额</span>
          <div class="head-content">{{coincount}}</div>
        </el-card>
        <el-card class="head-task">
          <img src="../../../public/taskIndex.png">
          <span class="head-title">任务数量</span>
          <div class="head-content">{{taskcount}}</div>
        </el-card>
      </div>

      <el-card class="webUserAnalysis">
        <webUserAnalysis style="background-color: white" :show="false"></webUserAnalysis>
      </el-card>
      <el-card class="taskAnalysis">
        <taskAnalysis style="background-color: white" :show='false'></taskAnalysis>
      </el-card>
      <el-card class="lableAnalysis">
        <lableAnalysis style="background-color: white"></lableAnalysis>
      </el-card>
      <el-card class="timeCoinsAnalysis">
        <timeCoinsAnalysis style="background-color: white" :show='false'></timeCoinsAnalysis>
      </el-card>
    </div>
</template>

<script>
import lableAnalysis from "@/views/analysis/lableAnalysis";
import taskAnalysis from "@/views/analysis/taskAnalysis";
import timeCoinsAnalysis from "@/views/analysis/timeCoinsAnalysis";
import webUserAnalysis from "@/views/analysis/webUserAnalysis";
import {getCoinNum, getTaskNum, getUserNum} from "@/api/api";

export default {
  name: "analysis",
  data(){
    return {
      show:'show',

      usercount:0,
      coincount:0,
      taskcount:0,
    }
  },
  created() {
    getUserNum().then((res1)=>{
      this.usercount = res1.data
    })
    getCoinNum().then((res2)=>{
      this.coincount = res2.data
    })
    getTaskNum().then((res3)=>{
      this.taskcount = res3.data
    })
  },
  components:{
    lableAnalysis,
    taskAnalysis,
    timeCoinsAnalysis,
    webUserAnalysis,
  }
}
</script>

<style scoped>
.main {
  display: flex;
  flex-direction: row;
  justify-content: center;
  height: 100%;
  flex-wrap: wrap;
}
.head {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  margin-top: 30px;
  user-select: none;
}
.head-user {
  height: 100px;
  width: 300px;
  margin-right: 100px;
}
.head-coins {
  height: 100px;
  width: 300px;
  margin-right: 100px;
}
.head-task {
  height: 100px;
  width: 300px;
}
.head img {
  width: 50px;
  height: 50px;
  margin-right: 80px;
}
.head-title {
  position: relative;
  top: -35px;
  right: -10px;
  font-weight: bold;
  color: #8c939d;
}
.head-content {
  position: relative;
  bottom: 20px;
  left: 150px;
  width: 88px;
  font-weight: bold;
  text-align: right;
}

.webUserAnalysis {
  width: 500px;
  height: 520px;
  margin-top: 10px;
  margin-right: 100px;
  transform: scaleY(0.9);
}
.taskAnalysis {
  width: 500px;
  height: 520px;
  margin-top: 10px;
  transform: scaleY(0.9);
}
.lableAnalysis {
  width: 500px;
  height: 530px;
  margin-top: 10px;
  margin-right: 100px;
  transform: scaleY(0.95);
}
.timeCoinsAnalysis {
  width: 500px;
  height: 530px;
  margin-top: 10px;
  transform: scaleY(0.95);
}
</style>