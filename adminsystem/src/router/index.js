import Vue from 'vue'
import VueRouter from 'vue-router'
import home from '@/views/home'
import taskAnalysis from "@/views/analysis/taskAnalysis";
import lableAnalysis from "@/views/analysis/lableAnalysis";
import webUserAnalysis from "@/views/analysis/webUserAnalysis";
import customerReview from "@/views/review/customerReview";
import publishTaskReview from "@/views/review/publishTaskReview";
import taskCompleteReview from "@/views/review/taskCompleteReview";
import viewAdmin from "@/views/register/viewAdmin";
import personal from "@/views/personal/personal";
import login from "@/views/login";
import editor from "@/views/editor/editor";
import manageEditor from "@/views/editor/manageEditor";
import timeCoinsAnalysis from "@/views/analysis/timeCoinsAnalysis";
import analysis from "@/views/analysis/analysis";

Vue.use(VueRouter)

function beforeEnter(to, from, next) {
  if(localStorage.token){
    next()
  }
  else{
    alert('请登录后访问')
    next({path:'/login'})
  }
}

const router=new VueRouter({
  //路由重定位到登录页面
  routes:  [
    {path:'/',redirect:'/home/analysis',},
    {path:'/home', component:home,
    children:[
      {path:'/home/analysis', component:analysis,beforeEnter:beforeEnter},
      {path:'/home/lableAnalysis', component:lableAnalysis,beforeEnter:beforeEnter},
      {path:'/home/taskAnalysis', component:taskAnalysis,beforeEnter:beforeEnter},
      {path:'/home/webUserAnalysis', component:webUserAnalysis,beforeEnter:beforeEnter},
      {path:'/home/timeCoinsAnalysis', component:timeCoinsAnalysis,beforeEnter:beforeEnter},
      {path:'/home/customerReview', component:customerReview,beforeEnter:beforeEnter},
      {path:'/home/publishTaskReview', component:publishTaskReview,beforeEnter:beforeEnter},
      {path:'/home/taskCompleteReview', component:taskCompleteReview,beforeEnter:beforeEnter},
      {path:'/home/viewAdmin', component:viewAdmin,beforeEnter:beforeEnter},
      {path:'/home/personal', component:personal,beforeEnter:beforeEnter},
      {path:'/home/manageEditor', component:manageEditor,beforeEnter:beforeEnter},
      {path:'/home/editor', component:editor,beforeEnter:beforeEnter},
    ]},//主页面
    {path:'/login',component:login} //登录页面
  ]
})

const originalPush = VueRouter.prototype.push
VueRouter.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => err)
}
export default router
