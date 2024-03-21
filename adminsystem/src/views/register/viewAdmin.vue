<template>
  <div>
    <div class="table">
      <el-table :data="tableData" style="width: 100%">
        <el-table-column type="index" label="管理员序号" width="400"></el-table-column>
        <el-table-column prop="adminName" label="管理员姓名" width="500"></el-table-column>
        <el-table-column width="150">
          <template v-slot:default="scope">
            <el-button type="primary" @click="clearPassword(scope.row)">重置密码</el-button>
          </template>
        </el-table-column>
        <el-table-column width="150">
          <template v-slot:default="scope">
            <el-button type="danger" @click="deleteAdmin(scope.row)">注销账号</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

<!--    新增管理员-->
    <el-popover placement="top" width="300" v-model="visible">
      <p>输入管理员账号</p>
      <div style="margin: 0">
        <el-input v-model="newAdmin.adminName"></el-input>
      </div>
      <p>输入管理员密码</p>
      <div style="text-align: right; margin: 0">
        <el-input v-model="newAdmin.adminPassword"></el-input>
      </div>
      <div style="display: flex;justify-content: center;margin-top: 10px">
        <el-button style="flex-grow: 1" @click="()=>{this.visible = false;this.newAdmin.adminName='';this.newAdmin.adminPassword='';}">取消</el-button>
        <el-button style="flex-grow: 1" type="primary" @click="addAdmin">确认</el-button>
      </div>
      <el-button slot="reference" type="success" style="position: absolute;right: 60px;top: 90px">新增管理员</el-button>
    </el-popover>
  </div>
</template>

<script>
import {changePassword, deleteAdmin, getAllAdminData, registerAdmin} from "@/api/api";

export default {
  name: "viewAdmin",
  data(){
    return {
      tableData:[],
      newAdmin:{
        adminName:'',
        adminPassword:'',
      },
      // 默认不显示新建管理员弹窗
      visible:false,
    }
  },
  created() {
    getAllAdminData(localStorage.adminName).then((res)=>{
      for(let i=0;i<res.data.length;i++){
        this.tableData.push({adminID:res.data[i].adminID,adminName:res.data[i].adminName})
      }
    })
  },
  methods:{
    clearPassword(row){
      let temp = {
        adminID:row.adminID,
        adminPassword:row.adminName
      }
      changePassword(temp).then((res)=>{
        if(res.data == "修改成功"){
          alert("重置成功")
        }
      })
    },
    deleteAdmin(row){
      deleteAdmin(row.adminID).then((res)=>{
        if(res.data == '注销成功'){
          alert('注销成功')
          location.reload()
        }
        else{
          alert('注销失败')
        }
      })
    },
    addAdmin(){
      if(this.newAdmin.adminName == '' || this.newAdmin.adminPassword == ''){
        alert('新增管理员账号密码不能为空')
      }
      else {
        registerAdmin(this.newAdmin).then((res)=>{
          if(res.data == "注册成功"){
            alert("注册成功")
            location.reload()
          }
          else if(res.data == "管理员已存在"){
            alert("管理员已存在")
          }
          else{
            alert("注册失败")
          }
        })
      }
      this.visible = false
      this.newAdmin.adminName='';this.newAdmin.adminPassword=''
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
</style>