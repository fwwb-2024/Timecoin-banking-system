<template>
  <div class="main">
    <el-card style="width: 30vw;height: 70vh;display:flex;flex-direction: row;justify-content: center;align-items: center;padding: 0 30px 0 0">
      <div style="margin: 0 0 50px 30px;font-size: 30px;font-weight: bold;color: dodgerblue;">时间银行管理系统</div>
      <el-form label-width="80px" :model="adminData">
        <el-form-item label="账号"> <!--账号输入栏-->
          <el-input placeholder="请输入账号" v-model="adminData.adminName"></el-input>
        </el-form-item>
        <el-form-item label="密码" @keyup.enter.native="loginAdmin"> <!--密码输入栏-->
          <el-input type="password" placeholder="请输入密码" autocomplete="off" v-model="adminData.adminPassword"></el-input>
        </el-form-item>
      </el-form>
      <div style="position: relative;top: 10px;">
        <el-button type="primary" @click="loginAdmin" style="width: 60%">登录</el-button>  <!--登录按钮-->
      </div>
    </el-card>
  </div>
</template>

<script>
import {login} from "@/api/api";
import router from "@/router";

export default {
  name: "login",
  data(){
    return {
      adminData:{
        adminName:'',
        adminPassword:null,
      }
    }
  },
  methods:{
    loginAdmin(){
      login(this.adminData).then((res)=>{
        if(res.data.token){
          localStorage.adminID = res.data.adminID
          localStorage.adminName = res.data.adminName
          localStorage.token = res.data.token
          router.push('/home/completeTaskAnalysis')
        }
        else if(res.data == '该管理员不存在') {
          alert('该管理员不存在')
        }
        else if(res.data == '密码错误') {
          alert('密码错误')
        }
        else{
          alert('登录失败')
        }
      })
    }
  }
}
</script>

<style scoped>
.main {
  display: flex;
  height: 80vh;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  user-select: none;
}
</style>