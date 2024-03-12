import Vue from 'vue'
import VueRouter from 'vue-router'
import home from '@/views/home'
import completeTaskAnalysis from "@/views/analysis/completeTaskAnalysis";
import publishTaskAnalysis from "@/views/analysis/publishTaskAnalysis";
import webUserAnalysis from "@/views/analysis/webUserAnalysis";
import customerReview from "@/views/review/customerReview";
import publishTaskReview from "@/views/review/publishTaskReview";
import taskS0ettlementReview from "@/views/review/taskS0ettlementReview";

Vue.use(VueRouter)

const router=new VueRouter({
  //路由重定位到登录页面
  routes:  [
    {path:'/',redirect:'home/completeTaskAnalysis'},
    {path:'/home', component:home,
    children:[
      {path:'/home/publishTaskAnalysis', component:publishTaskAnalysis,},
      {path:'/home/completeTaskAnalysis', component:completeTaskAnalysis,},
      {path:'/home/webUserAnalysis', component:webUserAnalysis,},
      {path:'/home/customerReview', component:customerReview,},
      {path:'/home/publishTaskReview', component:publishTaskReview,},
      {path:'/home/taskS0ettlementReview', component:taskS0ettlementReview,}
    ]},//主页面
  ]
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default router
