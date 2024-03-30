import http from "@/api/request"

// 注册管理员
export const registerAdmin =(data)=>{
    return http.post('/admin/register',data)
}
// 管理员登录
export const login =(data)=>{
    return http.post('/admin/login/byAdminName',data)
}
// 修改密码
export const changePassword =(data)=>{
    return http.post('/admin/adminCenter/changeAdminPassword',data)
}
// 查看个人信息
export const getAdminData =(adminName)=>{
    return http.get('/admin/adminCenter/findAdminData',{params:{adminName:adminName}})
}
// 查看所有低级管理员数据
export const getAllAdminData =(adminName)=>{
    return http.get('/admin/adminCenter/findAllAdminData',{params:{adminName:adminName}})
}
// 修改个人信息
export const changeData =(data)=>{
    return http.post('/admin/adminCenter/changeAdminData',data)
}
// 注销管理员
export const deleteAdmin =(adminID)=>{
    return http.get('/admin/adminCenter/deleteAdmins',{params:{adminID:adminID}})
}

// 管理员查看未审核任务
export const getTask =(offSet,chooses)=>{
    return http.get('/tasks/taskCenter/tasksAdmin',{params:{offSet:offSet,chooses:chooses}})
}
// 查看任务详情
export const getTaskDetail =(taskID)=>{
    return http.get('/tasks/taskCenter/findTask',{params:{taskID:taskID}})
}
// 审核通过任务
export const passRemark =(taskID)=>{
    return http.get('/tasks/taskCenter/checkTask',{params:{taskID:taskID}})
}
// 审核不通过任务
export const noPassRemark =(taskID,taskStatusRemark)=>{
    return http.get('/tasks/taskCenter/checkTaskNot',{params:{taskID:taskID,taskStatusRemark:taskStatusRemark}})
}
// 管理员审核已完成任务
export const completeRemark =(taskID)=>{
    return http.get('/tasks/taskCenter/adminSuccessTask',{params:{taskID:taskID}})
}
// 管理员审核不通过已完成任务
export const noCompleteRemark =(taskID,taskStatusRemark)=>{
    return http.get('/tasks/taskCenter/successTaskNot',{params:{taskID:taskID,taskStatusRemark:taskStatusRemark}})
}

// 查看任务发布总量
export const getCreateTask =(chooses)=>{
    return http.get('/admin/taskCenter/findCreatedTask',{params:{chooses:chooses}})
}
// 查看任务完成总量
export const getCompleteTask =(chooses)=>{
    return http.get('/admin/taskCenter/findSuccessedTask',{params:{chooses:chooses}})
}

// 发布资讯
export const postnews =(data)=>{
    return http.post('/new/register',data)
}
//上传图片
export const upPic =(data)=>{
    return http.post('/tasks/taskCenter/uploadimage',data)
}
// 查看新闻列表
export const getnews =(offSet)=>{
    return http.get('/new/findNewsList',{params:{offSet:offSet}})
}
// 查看新闻详情
export const getnewsDetail =(newID)=>{
    return http.get('/new/findNewsDetail',{params:{newID:newID}})
}
// 修改新闻
export const changeNewsDetail =(data)=>{
    return http.post('/new/changeNews',data)
}
// 删除新闻
export const deleteNews =(newsID)=>{
    return http.get('/new/deleteNews',{params:{newsID:newsID}})
}