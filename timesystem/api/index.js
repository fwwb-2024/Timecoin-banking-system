import http from '@/api/request.js'
export default {
	// 注册
	register (data){
	    return http.post('/user/register',data,false)
	},
	// 账号密码登录
	accountLogin (data){
	    return http.post('/user/login/byUserName',data,false)
	},
	// 手机号密码登录
	phoneLogin (data){
	    return http.post('/user/login/byUserPhoneNumber',data,false)
	},
	// 获取用户数据
	getUserData (userName){
	    return http.get('/user/userCenter/findUserData',{userName:userName},true)
	},
	// 获取时间币余额
	getCoins (userName){
	    return http.get('/user/userCenter/findTimeCoin',{userName:userName},true)
	},
	// 查看时间币账单
	getCoinsHistory (){
	    return http.get('/user/userCenter/findLedgers',true)
	},
	// 修改用户数据
	changeUserData (data){
	    return http.post('/user/userCenter/changeUserData',data,true)
	},
	// 修改密码
	changeUserPassword (data){
	    return http.post('/user/userCenter/changeUserPassword',data,true)
	},
	// 注销用户
	// deleteUser (data){
	//     return http.delete('/user/userCenter/deleteUsers',data,true)
	// },
	
	// 新建任务
	postTask (data){
	    return http.post('/tasks/taskCenter/register',data,true)
	},
	// 修改任务
	changeTaskData (data){
	    return http.post('/tasks/taskCenter/changeTask',data,true)
	},
	// 删除任务
	// deleteTask (data){
	//     return http.delete('/tasks/taskCenter/deleteTasks',data,true)
	// }
	// 查看所有任务
	getTasklist (offSet,chooses){
	    return http.get('/tasks/taskCenter/tasks',{offSet:offSet,chooses:chooses},true)
	},
	// 搜索任务名,查看任务列表
	getTasklistByTaskName (taskEmployer,offSet,chooses){
	    return http.get('/tasks/taskCenter/tasksForTaskName',{taskEmployer:taskEmployer,offSet:offSet,chooses:chooses},true)
	},
	// 搜索发布者名,查看任务列表
	getTasklistByUserName (taskEmployer,offSet,chooses){
	    return http.get('/tasks/taskCenter/tasksForTaskEmployer',{taskEmployer:taskEmployer,offSet:offSet,chooses:chooses},true)
	},
	// 查看任务详情
	getTaskData (taskID){
	    return http.get('/tasks/taskCenter/findTask',{taskID:taskID},true)
	},
	// 志愿者查看当前任务
	getTaskNow_1 (userID,offSet){
	    return http.get('/taskhistory/taskCenter/findTaskhistoryUser',{userID:userID,offSet:offSet},true)
	},
	// 志愿者查看历史任务
	getTaskHistory_1 (userID,offSet){
	    return http.get('/taskhistory/taskCenter/findAllTaskhistoryUser',{userID:userID,offSet:offSet},true)
	},
	// 发布者查看当前任务
	getTaskNow_2 (taskEmployerID,offSet){
	    return http.get('/taskhistory/taskCenter/findTaskhistoryTaskEmployer',{taskEmployerID:taskEmployerID,offSet:offSet},true)
	},
	// 发布者查看历史任务
	getTaskHistory_2 (taskEmployerID,offSet){
	    return http.get('/taskhistory/taskCenter/findAllTaskhistoryTaskEmployer',{taskEmployerID:taskEmployerID,offSet:offSet},true)
	},
	
	// 查看所在家庭
	getFamilyList (userID) {
		return http.get('/familyusers/familyCenter/findFamilys',{userID:userID},true) 
	},
	// 查看家庭成员
	getFamilyMember (familyID) {
		return http.get('/familyusers/familyCenter/findUsers',{familyID:familyID},true) 
	}
}