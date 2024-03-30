<template>
  <div>
    <el-card class="card">
      <div style="margin: 20px;">
        <span>个人信息</span>
      </div>
      <el-form label-position="left" label-width="80px" :model="adminData">
        <el-form-item label="账号">
          <el-input v-model="adminData.adminName"></el-input>
        </el-form-item>
        <el-form-item label="密码">
          <el-input type="password" v-model="adminData.adminPassword"></el-input>
        </el-form-item>
        <el-form-item label="权限">
          <el-input :placeholder="adminData.adminPreviliege"></el-input>
        </el-form-item>
      </el-form>
      <div style="display: flex;justify-content: center;margin-top: 10px">
        <el-button style="flex-grow: 1" @click="cancel">取消修改</el-button>
        <el-button style="flex-grow: 1" type="primary" @click="changeAdminData">确认修改</el-button>
        <el-button style="flex-grow: 1" @click="()=>{this.$router.push('/login')}">切换账号</el-button>
      </div>
    </el-card>
  </div>
</template>

<script>
import {changeData, changePassword, getAdminData} from "@/api/api";

export default {
  name: "personal",
  data(){
    return {
      adminData:{
        adminName:'',
        adminPassword:'',
        adminPreviliege:''
      },
    }
  },
  created() {
    this.loadData()
  },
  methods:{
    loadData(){
      getAdminData(localStorage.adminName).then((res)=>{
        this.adminData.adminName = res.data.adminName
        switch (res.data.adminPreviliege){
          case "1": this.adminData.adminPreviliege = '管理员';break;
          case "2": this.adminData.adminPreviliege = '超级管理员';break;
          default:break;
        }
      })
    },
    cancel(){
      location.reload()
    },
    // 更改用户数据
    changeAdminData(){
      // 如果密码修改了
      if(this.adminData.adminPassword != ''){
        let temp = {
          adminID:localStorage.adminID,
          adminPassword:this.adminData.adminPassword
        }
        changePassword(temp).then((res)=>{
          if(res.data != "修改成功"){
            alert("修改失败")
          }
        })
      }
      // 修改名字
      if(this.adminData.adminName == ''){
        alert('用户名不能为空')
      }
      else {
        let temp = {
          adminName:this.adminData.adminName,
          adminID:localStorage.adminID
        }
        changeData(temp).then((res)=>{
          if(res.data == '用户已存在'){
            alert('用户已存在')
            location.reload()
          }
          else if(res.data){
            alert('修改成功')
            localStorage.adminName = temp.adminName
            location.reload()
          }
          else {
            alert('修改失败')
            location.reload()
          }
        })
      }
    }
  }
}
</script>

<style scoped>
.card {
  position: relative;
  left: 25vw;
  top: 50px;
  width: 30vw;
  height: 60vh;
  display: flex;
  flex-direction: row;
  justify-content: center;
  user-select: none;
}
</style>