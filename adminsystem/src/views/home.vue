<template>
  <div class="main">
<!--    顶部栏-->
    <div class="title">
      <div class="title-img">
        <img src="../../public/timesystem.jpg"></img>
      </div>
      <div class="title-txt">
        <span>时间银行管理系统</span>
      </div>
      <div  class="title-welcome">
        <span>{{daytime}}好! {{adminName}}</span>
      </div>
    </div>
<!--    菜单栏-->
    <div class="body">
      <div class="menu">
        <el-row>
          <el-col :span="12">
            <el-menu class="el-menu-vertical-demo" background-color="#545c64" text-color="white" active-text-color="#ffd04b" router="router">
              <el-menu-item index="/home/analysis">
                <i class="el-icon-menu"></i>
                <span slot="title" style="font-weight: bold;color: white;font-size: 16px">首页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
              </el-menu-item>
              <el-submenu index="2">
                <div class="el-menu-title" slot="title">
                  <i class="el-icon-menu"></i>
                  <span>数据分析</span>
                </div>
                <el-menu-item index="/home/webUserAnalysis">网站用户数据</el-menu-item>
                <el-menu-item index="/home/taskAnalysis">发布完成数据</el-menu-item>
                <el-menu-item index="/home/lableAnalysis">任务类别数据</el-menu-item>
                <el-menu-item index="/home/timeCoinsAnalysis">交易流水数据</el-menu-item>
              </el-submenu>
              <el-submenu index="3">
                <div class="el-menu-title" slot="title">
                  <i class="el-icon-menu"></i>
                  <span>审核模块</span>
                </div>
                <el-menu-item index="/home/customerReview">用户资质审核</el-menu-item>
                <el-menu-item index="/home/publishTaskReview">任务发布审核</el-menu-item>
                <el-menu-item index="/home/taskCompleteReview">任务结算审核</el-menu-item>
              </el-submenu>
              <el-submenu index="4" v-if="isSuperAdmin">
                <div class="el-menu-title" slot="title">
                  <i class="el-icon-menu"></i>
                  <span>资讯模块</span>
                </div>
                <el-menu-item index="/home/manageEditor">管理资讯</el-menu-item>
                <el-menu-item index="/home/editor">新增资讯</el-menu-item>
              </el-submenu>
              <el-submenu index="5" v-if="isSuperAdmin">
                <div class="el-menu-title" slot="title">
                  <i class="el-icon-menu"></i>
                  <span>任免管理</span>
                </div>
                <el-menu-item index="/home/viewAdmin">查看管理员</el-menu-item>
              </el-submenu>
              <el-submenu index="6">
                <div class="el-menu-title" slot="title">
                  <i class="el-icon-menu"></i>
                  <span>个人中心</span>
                </div>
                <el-menu-item index="/home/personal">个人中心</el-menu-item>
              </el-submenu>
            </el-menu>
          </el-col>
        </el-row>
      </div>
      <!--    子页面-->
      <div class="view">
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import {getAdminData} from "@/api/api";

export default {
  name: "home",
  data() {
    return {
      adminName:localStorage.adminName,
      daytime:'',
      isSuperAdmin:false,
    }
  },
  created() {
    this.getTime()
    this.getadminPreviliege()
  },
  methods:{
    // 获取当前问候时间
    getTime(){
      let hours = new Date().getHours();
      if (hours < 12) {
        this.daytime = '上午';
      } else if (hours < 18) {
        this.daytime = '下午';
      } else {
        this.daytime = '晚上';
      }
    },
    getadminPreviliege(){
      getAdminData(localStorage.adminName).then((res)=>{
        switch (res.data.adminPreviliege){
          case "1": this.isSuperAdmin = false;break;
          case "2": this.isSuperAdmin = true;break;
          default:break;
        }
      })
    }
  }
}
</script>

<style scoped>
.main {
  display: flex;
  width: 100%;
  height: 95vh;
  flex-direction: column;
  justify-content: flex-start;
  flex-wrap: wrap;
}
.title {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  width: 100%;
  height: 10vh;
  border-bottom: 5px solid #42b983;
  background-color: #545c64;
  user-select:none;
}
.title-img img{
  margin-left: 1vw;
  width: 2vw;
  height: 2vw;
}
.title-txt {
  font-size: 18px;
  margin-left: 1vw;
  color: white;
}
.title-welcome {
  position: absolute;
  right: 20px;
  font-size: 15px;
  margin-left: 1vw;
  color: white;
}
.body {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  width: 100%;
  height: 83vh;
}
.menu {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  width: 15vw;
  user-select:none;
  background-color: #545c64;
}
.el-menu-vertical-demo {
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 15vw!important;
}
.el-menu-title {
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  width: 12vw;
}
.el-menu-title span {
  font-size: 17px;
  font-weight: bold;
  color: white;
}
.el-icon-menu {
  margin-right: 2vw!important;
}
.view {
  width: 100vw;
  height: 595px;
  overflow-y: auto;
}
</style>