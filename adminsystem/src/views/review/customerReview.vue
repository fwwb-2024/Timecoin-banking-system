<template>
  <div>
    <div class="table">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column type="index" label="用户序号" width="250"></el-table-column>
        <el-table-column prop="userName" label="账号" width="300"></el-table-column>
        <el-table-column prop="userPhoneNumber" label="手机号" width="250"></el-table-column>
        <el-table-column width="200">
          <template v-slot:default="scope">
            <el-button type="primary" @click="passAccount(scope.row.userID)">审核通过</el-button>
          </template>
        </el-table-column>
        <el-table-column width="200">
          <template v-slot:default="scope">
            <el-button slot="reference" type="danger" @click="noPassAccount(scope.row.userID)">审核不通过</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-popover style="position: fixed;right: 600px;top: 300px;" placement="top" width="300" v-model="visible">
        <p>输入审核失败原因</p>
        <el-input v-model="userStatusRemark"></el-input>
        <div style="display: flex;justify-content: center;margin-top: 10px">
          <el-button style="flex-grow: 1" @click="()=>{this.visible = false;this.userStatusRemark = '';this.nowuserID = null}">取消</el-button>
          <el-button style="flex-grow: 1" type="primary" @click="noPass">确认</el-button>
        </div>
      </el-popover>
    </div>
  </div>
</template>

<script>
import {getNoAuditedAccount, noPassAccountRemark, passAccountRemark} from "@/api/api";

export default {
  name: "customerReviews",
  data(){
    return {
      tableData:[],
      userStatusRemark:'',
      nowuserID:'',
      pages:0,
      // 默认不显示审核不通过弹窗
      visible:false,
    }
  },
  created() {
    this.reload()
  },
  methods:{
    reload(){
      getNoAuditedAccount(this.pages).then((res)=>{
        console.log(res)
        for(let i=0;i<res.data.length;i++){
          this.tableData.push({userID:res.data[i].userID,userName:res.data[i].userName,userPhoneNumber:res.data[i].userPhoneNumber})
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
    // 审核通过
    passAccount(nowuserID){
      passAccountRemark(nowuserID).then((res)=>{
        if(res.data == "审核成功"){
          this.$message({
            message: '审核通过',
            type: 'success'
          });
        }
        this.tableData=[]
        this.pages=0
        this.reload()
      })
    },
    // 审核不通过
    noPassAccount(nowuserID){
      this.nowuserID = nowuserID
      this.visible = true
    },
    noPass(){
      noPassAccountRemark(this.nowuserID,this.userStatusRemark).then((res)=>{
        if(res.data == '审核成功'){
          this.$message({
            message: '已拒绝',
            type: 'success'
          });
          this.tableData=[]
          this.pages=0
          this.reload()
        }
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