<template>
  <div>
    <div class="table">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column type="index" label="任务序号" width="250"></el-table-column>
        <el-table-column prop="taskName" label="任务名" width="300"></el-table-column>
        <el-table-column prop="taskEmployer" label="发布者" width="250"></el-table-column>
        <el-table-column prop="taskTimeCoinBounty" label="悬赏" width="200"></el-table-column>
        <el-table-column width="150">
          <template v-slot:default="scope">
            <el-button type="primary" @click="remarkTask(scope.row.taskID)">审核任务</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog title="审核任务" width="400px" :visible.sync="dialogTableVisible">
      <div style="margin: 0 50px 0 50px">
        <p class="font-title">任务名称:</p>
        <div class="font-content">{{this.task.taskName}}</div>
        <p class="font-title">任务简介:</p>
        <div class="font-content">{{this.task.taskBrief}}</div>
        <p class="font-title">任务详情:</p>
        <div class="font-content" v-html="this.task.taskDetail"></div>
        <p class="font-title">发布者:</p>
        <div class="font-content">{{this.task.taskEmployer}}</div>
        <p class="font-title">时间币悬赏:</p>
        <div class="font-content">{{this.task.taskTimeCoinBounty}}</div>
      </div>
      <div style="display: flex;justify-content: center;margin-top: 10px">
        <span style="flex-grow: 1">
          <span class="font-title">开始时间:</span>
          <span class="font-content">{{this.task.taskBeginTime}}</span>
        </span>
        <span style="flex-grow: 1">
          <span class="font-title">截止时间:</span>
          <span class="font-content">{{this.task.taskEndTime}}</span>
        </span>
      </div>

      <el-input type="textarea" :rows="2" placeholder="审核不通过原因" v-model="task.taskStatusRemark"></el-input>
      <div style="display: flex;justify-content: center;margin-top: 10px">
        <el-button style="flex-grow: 1" @click="()=>{this.dialogTableVisible = false;}">取消审核</el-button>
        <el-button style="flex-grow: 1" type="primary" @click="remarkPass">审核通过</el-button>
        <el-button style="flex-grow: 1" type="warning" @click="noRemarkPass">审核不通过</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getTask, getTaskDetail, noPassRemark, passRemark} from "@/api/api";

export default {
  name: "publishTaskReview",
  data(){
    return {
      tableData:[],
      // 显示任务详情
      dialogTableVisible:false,
      // 任务数据
      task:{
        taskID:null,
        taskName:'',
        taskBrief:'',
        taskDetail:'',
        taskEmployer:'',
        taskBeginTime:'',
        taskEndTime:'',
        taskTimeCoinBounty:null,
        taskStatusRemark:''
      },
      pages:0,
    }
  },
  created() {
    this.reload()
  },
  methods:{
    reload(){
      getTask(this.pages,1).then((res)=>{
        for(let i=0;i<res.data.length;i++){
          if(res.data[i].taskStatusRemark != null && res.data[i].taskStatusRemark != ''){
            continue;
          }
          this.tableData.push({
            taskID:res.data[i].taskID,
            taskName:res.data[i].taskName,
            taskBrief:res.data[i].taskBrief,
            taskEmployer:res.data[i].taskEmployer,
            taskTimeCoinBounty:res.data[i].taskTimeCoinBounty,
          })
        }
        // 加载全部数据
        if((this.pages+10) == this.tableData.length){
          this.pages += 10
          this.reload()
        }
        else {
          this.pages -= 10
        }
      })
    },
    // 展示任务详情
    remarkTask(taskID){
      getTaskDetail(taskID).then((res)=>{
        this.task.taskID = res.data.taskID
        this.task.taskName = res.data.taskName
        this.task.taskBrief = res.data.taskBrief
        this.task.taskDetail = res.data.taskDetail
        this.task.taskEmployer = res.data.taskEmployer
        this.task.taskBeginTime = res.data.taskBeginTime
        this.task.taskEndTime = res.data.taskEndTime
        this.task.taskTimeCoinBounty = res.data.taskTimeCoinBounty
        this.task.taskStatusRemark = res.data.taskStatusRemark
      })
      this.dialogTableVisible = true
    },
    // 审核通过
    remarkPass(){
      passRemark(this.task.taskID).then((res)=>{
        if(res.data == '审核成功'){
          alert('审核通过成功')
        }
        else if(res.data == '任务已被其他管理员审核通过'){
          alert('任务已被其他管理员审核通过')
        }
        else{
          alert('网络错误')
        }
        location.reload()
      })
    },
    // 审核不通过
    noRemarkPass(){
      let tempRamark = '管理员审核不通过，原因为：'+ this.task.taskStatusRemark
      noPassRemark(this.task.taskID,tempRamark).then((res)=>{
        console.log(res)
        if(res.data == '批改完成'){
          alert('审核不通过成功')
        }
        else {
          alert('网络错误')
        }
        location.reload()
      })
    }
  }
}
</script>

<style scoped>
.table {
  display: flex;
  margin: 10px 0 0 20px;
  user-select:none;
}
.font-title {
  font-size: 18px;
  color: #545c64;
}
.font-content {
  font-size: 15px;
}
</style>