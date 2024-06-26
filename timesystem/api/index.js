import http from '@/api/request.js'
export default {
	// 注册
	register (data){
	    return http.post('/user/register',data,false)
	},
	// 提交审核接口
	examine (userID){
		return http.get('/user/userCenter/userAccessUserNormal',{userID:userID},false)
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
	
	
	// 获取家庭成员余额
	getFamilyCoinsData(familyID){
		 return http.get('/familyusers/familyCenter/findUsersTimeCoin',{familyID:familyID},true)
	},
	// 新建任务
	postTask (data){
	    return http.post('/tasks/taskCenter/register',data,true)
	},
	// 接取任务
	accessTask (taskID,userID,userName){
		return http.get('/tasks/taskCenter/accessTask',{taskID:taskID,userID:userID,userName:userName},true)
	},
	// 修改任务
	changeTaskData (data){
	    return http.post('/tasks/taskCenter/changeTask',data,true)
	},
	// 完成任务
	completeTask (taskID,userName){
		return http.get('/tasks/taskCenter/userSuccessTask',{taskID:taskID,userName:userName},true)
	},
	// 取消任务
	canelTask (taskID,userName){
		return http.get('/tasks/taskCenter/userRefuseTask',{taskID:taskID,userName:userName},true)
	},
	// 删除任务
	deleteTask (taskID,taskEmployerID,taskTimeCoinBounty){
		return http.get('/tasks/taskCenter/deleteTasks',{taskID:taskID,taskEmployerID:taskEmployerID,taskTimeCoinBounty:taskTimeCoinBounty},true)
	},
	// 发布者同意完成任务
	agreeTask(taskID) {
		return http.get('/tasks/taskCenter/taskEmployerSuccessTask',{taskID:taskID},true)
	},
	// 发布者拒绝同意完成任务
	refuseTask(taskID,taskStatusRemark) {
		return http.get('/tasks/taskCenter/successTaskNot',{taskID:taskID,taskStatusRemark:taskStatusRemark},true)
	},
	
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
	getFamilyList (userID){
		return http.get('/familyusers/familyCenter/findFamilys',{userID:userID},true) 
	},
	// 查看家庭成员
	getFamilyMember (familyID){
		return http.get('/familyusers/familyCenter/findUsers',{familyID:familyID},true) 
	},
	// 增加家庭成员
	addFamilyMember (data){
		return http.post('/familyusers/familyCenter/createFamilyuser',data,true)
	},
	// 删除家庭成员
	deleteFamilyMember (familyID,userID){
		return http.get('/familyusers/familyCenter/deleteFamilyusers',{familyID:familyID,userID:userID},true)
	},
	// 更改家主
	changeFamilyMaster (data){
		return http.post('/family/familyCenter/changeHouseHolder',data,true)
	},
	// 解散家庭
	deleteFamily (familyID){
		return http.get('/family/familyCenter/deleteFamilys',{familyID:familyID},true)
	},
	// 新建家庭
	newFamily (data){
		return http.post('/family/familyCenter/createFamily',data,true)
	},
	
	// 查看账单
	getLedgers (userID,offSet){
		return http.get('/ledgers/userCenter/findLedgers',{userID:userID,offSet:offSet},true)
	},
	
	// 查看新闻列表
	 getnews (offSet){
	    return http.get('/new/findNewsList',{offSet:offSet},true)
	},
	// 查看新闻详情
	getnewsDetail (newID){
	    return http.get('/new/findNewsDetail',{newID:newID},true)
	},
	
	// 开始任务
	startMission (data){
	    return http.post('/tasks/taskCenter/userBeginTask',data,true)
	},
	// 搜索任务
	searchByTitle(taskName,offSet,chooses){
		return http.get('/tasks/taskCenter/tasksForTaskName',{taskName:taskName,offSet:offSet,chooses:chooses},true)
	},
	searchByUser(taskEmployer,offSet,chooses){
		return http.get('/tasks/taskCenter/tasksForTaskEmployer',{taskEmployer:taskEmployer,offSet:offSet,chooses:chooses},true)
	},
}