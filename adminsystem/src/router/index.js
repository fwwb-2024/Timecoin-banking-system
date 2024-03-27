import Vue from 'vue'
import VueRouter from 'vue-router'
import home from '@/views/home'
import completeTaskAnalysis from "@/views/analysis/completeTaskAnalysis";
import publishTaskAnalysis from "@/views/analysis/publishTaskAnalysis";
import webUserAnalysis from "@/views/analysis/webUserAnalysis";
import customerReview from "@/views/review/customerReview";
import publishTaskReview from "@/views/review/publishTaskReview";
import taskCompleteReview from "@/views/review/taskCompleteReview";
import viewAdmin from "@/views/register/viewAdmin";
import personal from "@/views/personal/personal";
import login from "@/views/login";
import editor from "@/views/editor/editor";

Vue.use(VueRouter)

const router=new VueRouter({
  //路由重定位到登录页面
  routes:  [
    {path:'/',redirect:'/login'},
    {path:'/home', component:home,
    children:[
      {path:'/home/publishTaskAnalysis', component:publishTaskAnalysis,},
      {path:'/home/completeTaskAnalysis', component:completeTaskAnalysis,},
      {path:'/home/webUserAnalysis', component:webUserAnalysis,},
      {path:'/home/customerReview', component:customerReview,},
      {path:'/home/publishTaskReview', component:publishTaskReview,},
      {path:'/home/taskCompleteReview', component:taskCompleteReview,},
      {path:'/home/viewAdmin', component:viewAdmin,},
      {path:'/home/personal', component:personal,},
      {path:'/home/editor', component:editor,},
    ]},//主页面
    {path:'/login',component:login} //登录页面
  ]
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default router
