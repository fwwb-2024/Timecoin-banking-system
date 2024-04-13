<template>
	<view class="main">
		<page-meta :root-font-size="size"></page-meta>
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="back-text">
				<text>任务详情</text>
			</view>		
		</view>
		
		
		<view class="body" v-if="loading">
			<!--修改任务页面-->
			<view v-show="changeTaskShow">
				<view class="detail-head">
					<view class="change-lable">
						<text class="change-lable-title">类别：</text>
						<text class="change-lable-content" @click="changeLable">{{mission.taskLable}}</text>
					</view>
					
					<view class="change-title">
						<text class="change-title-title">标题：</text>
						<input class="change-title-content" v-model="mission.taskName"></input>
					</view>
					
					<view class="change-coins">
						<text class="change-coins-title">悬赏：</text>
						<input class="change-coins-content" type="number" v-model="mission.taskTimeCoinBounty"></input>
						<text class="change-coins-unit">时间币/时</text>
					</view>
				</view>
				
				<view class="detail-body">
					<view class="change-brief">
						<text class="change-brief-title">简介(不超过50字)：</text>
						<textarea class="change-brief-content" maxlength="50" style="height: 140rpx;" v-model="mission.taskBrief"></textarea>
					</view>
					
					<view class="change-detail">
						<text class="change-detail-title">详情(不超过500字)：</text>
						<textarea class="change-detail-content" @input="inputDetail" maxlength="500" :style="{'height':detailHeight}" v-model="mission.taskDetail"></textarea>
					</view>
					
					<!-- 图片附件 -->
					<view class="change-image">
						<view v-for="(item,index) in mission.taskPhoto">
							<image id="deletepic" @click="deletePic(index)" src="/static/deletepic.png"></image>
							<view class="change-image-element">
								<image :src="item"></image>
							</view>
						</view>
						<view class="upPic-add" @click="upPic">
							<image src="/static/addpic.png"></image>
						</view>
					</view>
					
					<view class="change-time">
						<text class="change-time-title">开始时间：</text>
						<picker mode="date" :value="mission.taskStartTime" :start="startDate" :end="endDate" @change="bindStartDateChange">
							<view class="change-time-content">{{mission.taskStartTime}}</view>
						</picker>
					</view>
					<view class="change-time">
						<text class="change-time-title">截止时间：</text>
						<picker mode="date" :value="mission.taskEndTime" :start="startDate" :end="endDate" @change="bindEndDateChange">
							<view class="change-time-content">{{mission.taskEndTime}}</view>
						</picker>
					</view>
				</view>
			</view>
			
			<!--任务详情页面-->
			<!-- <view class="detail-head"> -->
			<view class="detail-head" v-show="taskDetail">
				<view class="detail-title">
					<text>{{mission.taskName}}</text>
				</view>
				
				<text class="detail-coins">{{mission.taskTimeCoinBounty}}时间币/时</text>
				<view class="detail-lable">
					<text class="detail-lable-title">类别：</text>
					<text class="detail-lable-content">{{mission.taskLable}}</text>
				</view>
			</view>
			
			<!-- <view class="detail-body"> -->
			<view class="detail-body"  v-show="taskDetail">
				<view class="detail-status" v-show="statusShow">
					<text class="detail-status-title">状态：</text>
					<text class="detail-status-content">{{mission.taskStatus}}</text>
				</view>
				
				<text class="detail-breif-title">任务简介：</text>
				<text class="detail-breif-content">{{mission.taskBrief}}</text>
				
				<text class="detail-detail-title">任务详情：</text>
				<text class="detail-detail-content">{{mission.taskDetail}}</text>
				<view class="detail-image" v-if="taskPhotoShow">
					<view class="detail-image-element" v-for="(item,index) in mission.taskPhoto">
						<image :src="item"></image>
					</view>
				</view>
				
				<view class="detail-time">
					<text class="detail-time-title">截止时间：</text>
					<text class="detail-time-content">{{mission.taskEndTime}}</text>
				</view>
				
				<!-- 显示审核失败原因 -->
				<text class="detail-fail-title" v-show="remarkShow">审核失败：</text>
				<view class="detail-fail-content" v-show="remarkShow">{{mission.taskStatusRemark}}</view>
			</view>
			
			<!-- 显示开始任务内容 -->
			<!-- <view class="detail" v-if="taskDoingShow>0">
				<view class="detail-start-title">
					<text>志愿者已经开始任务</text>
				</view>
				<view class="detail-start-text" v-if="taskDoingShow==2 || taskDoingShow==4">
					<text>{{mission.taskDoingText}}</text>
				</view>
				<view class="detail-start-image" v-if="taskDoingShow==3 || taskDoingShow==4">
					<image :src="mission.taskDoingPhoto"></image>
				</view>
			</view> -->
			
			<!-- 切换查看修改按钮 -->
			<!-- <button v-if="(!changeTaskShow) && (!taskDetail)" class="detail-button" @click="()=>{changeTaskShow = false;taskDetail = true}">查看详情</button> -->
			<!-- <button v-if="taskDetail" class="detail-button" @click="()=>{taskDetail=false}">隐藏</button> -->
			<view style="display: flex;flex-direction: row;justify-content: center;width: 80%;">
				<button style="flex-grow: 1;margin-right: 50rpx; background-color: white;color: black;" v-if="(!changeTaskShow) && changeTaskStatus" class="detail-button" @click="()=>{changeTaskShow = true;taskDetail=false}">修改</button>
				<button style="flex-grow: 1;background-color: #ff6666;" v-if="changeTaskStatus && (!changeTaskShow)" class="detail-button" @click="deleteTask">删除</button>
			</view>
						
			<!-- 修改任务按钮 -->
			<view v-if="changeTaskShow" style="display: flex;flex-direction: row;justify-content: center;width: 80%;">
				<button style="flex-grow: 1;margin-right: 50rpx;" @click="()=>{changeTaskShow=false;taskDetail=true}">取消</button>
				<button style="flex-grow: 1;background-color: skyblue;color: white;" @click="changeTask">确认</button>
			</view>
			
			<view v-if="confirmShow" style="display: flex;flex-direction: row;justify-content: center;width: 80%;">
				<button style="flex-grow: 1;margin-right: 50rpx;background-color: #ff6666;" class="detail-button" @click="refuseTask">拒绝</button>
				<button style="flex-grow: 1;" class="detail-button" @click="agreeTask">确认</button>
			</view>
			<button v-if="confirmShow" style="background-color: orange;width: 80%;" class="detail-button" @click="">有疑问？申请官方介入</button>
		</view>
	</view>
</template>

<script>
	import editorl from "@/components/editor/index.vue"
	export default {
		data() {
			const currentDate = this.getDate({
			    format: true
			})
			return {
				// 页面加载显示
				loading:false,
				
				missionId:null,
				mission:{
					taskID:null,
					taskLable:'',
					taskName:'',
					taskBrief:'',
					taskDetail:'',
					taskTimeCoinBounty:null,
					taskAddress:'',
					taskStartTime:currentDate,
					taskEndTime:currentDate,
					taskStatus:null,
					taskStatusRemark:null,
					taskPhoto:[],
					// 开始任务信息
					taskDoingText:'',
					taskDoingPhoto:'',
				},
				// 显示任务状态
				statusShow:true,
				// 显示审核不通过原因
				remarkShow:false,
				// 任务详情加载完毕
				loading:false,
				// 显示确认完成任务按钮
				confirmShow:false,
				// 显示开始任务信息
				taskDoingShow:0,
				
				// 显示图片
				taskPhotoShow:false,
				// 显示修改任务
				changeTaskShow:false,
				// 显示任务详情
				taskDetail:true,
				// 若未接取任务，则显示修改任务按钮
				changeTaskStatus:true,
				
				detailHeight:'380rpx',
				
				sizze:'',
			}
		},
		onLoad(options) {
			this.size = uni.getStorageSync("size")
			this.missionId = options.id
			this.$api.getTaskData(this.missionId).then((res)=>{
				switch(res.data.taskLable){
					case 1:this.mission.taskLable = '其他';break;
					case 2:this.mission.taskLable = '跑腿';break;
					case 3:this.mission.taskLable = '代购';break;
					case 4:this.mission.taskLable = '打理';break;
					case 5:this.mission.taskLable = '陪伴';break;
					case 6:this.mission.taskLable = '线上';break;
					default:break;
				}
				this.mission.taskID = res.data.taskID
				this.mission.taskName = res.data.taskName
				this.mission.taskBrief = res.data.taskBrief
				this.mission.taskDetail = res.data.taskDetail
				if(res.data.taskDetail.length > 140){
					this.detailHeight = 'auto'
				}
				this.loading = true
				this.mission.taskTimeCoinBounty = res.data.taskTimeCoinBounty
				this.mission.taskAddress = res.data.taskAddress
				this.mission.taskBeginTime = res.data.taskBeginTime
				this.mission.taskEndTime = res.data.taskEndTime
				if(res.data.taskStatusRemark !== null && res.data.taskStatusRemark !== ''){
					this.mission.taskStatusRemark = res.data.taskStatusRemark
					this.remarkShow = true
				}
				if(res.data.taskPhoto != null && res.data.taskPhoto != '' && res.data.taskPhoto[0] != '' && res.data.taskPhoto[0] != null){
					this.taskPhotoShow = true
					this.mission.taskPhoto = res.data.taskPhoto
				}
				
				switch(res.data.taskStatus){
					case 1:this.mission.taskStatus = '未审核(请等待管理员审核)';this.taskDetail=true;break;
					case 2:this.mission.taskStatus = '未完成(请等待志愿者接取)';this.taskDetail=true;break;
					case 3:this.mission.taskStatus = '已接取';this.changeTaskStatus=false;break;
					case 4:this.mission.taskStatus = '已处理';this.changeTaskStatus=false;this.confirmShow=true;break;
					case 5:this.mission.taskStatus = '已完成(请等待管理员审核)';this.changeTaskStatus=false;break;
					case 6:this.mission.taskStatus = '已完结';this.changeTaskStatus=false;break;
					case 7:this.mission.taskStatus = '进行中(请等待志愿者完成任务)';this.changeTaskStatus=false;this.taskDoingShow=1;break;
					default:break;
				}
				
				if(res.data.taskDoingText != '' && res.data.taskDoingText != null){
					this.mission.taskDoingText = res.data.taskDoingText
					this.taskDoingShow = 2
				}
				if(res.data.taskDoingPhoto != '' && res.data.taskDoingPhoto != null){
					this.mission.taskDoingPhoto = res.data.taskDoingPhoto
					this.taskDoingShow = 3
				}
				if(res.data.taskDoingText != '' && res.data.taskDoingPhoto != '' && res.data.taskDoingText != null && res.data.taskDoingPhoto != null){
					this.taskDoingShow = 4
				}
				if(res.data.taskDoingText == '' && res.data.taskDoingPhoto == ''){
					this.taskDoingShow = 0
				}
				
				this.loading = true
			})
		},
		methods: {
			// 输入详情
			inputDetail(value) {
				if(value.detail.cursor >= 140){
					this.detailHeight = 'auto'
				}
			},
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
			},
			// 任务详情修改内容
			getcontent(content) {
				this.mission.taskDetail = content
			},
			// 更改图片类别
			changeLable(){
				let temp = ['跑腿','代购','打理','陪伴','线上','其他']
				let that = this
				uni.showActionSheet({
					title:'请选择任务类别',
				    itemList:temp,
				    success: function (response) {
						if (response.tapIndex > -1) {
							that.mission.taskLable = temp[response.tapIndex]
				        }
				    },
				});
			},
			// 上传图片
			upPic(){
				let that = this
				// 选择图片并且上传
				uni.chooseImage({
					count: 1,
					sizeType: ['original'],
					sourceType: ['album','camera'], 
					success: (chooseImageRes)=>{
						// 如果图片过大
						if(chooseImageRes.tempFiles[0].size > 10485760){
							uni.showToast({
								title: '图片大于10M!',
								icon:'error',
								duration: 1000
							});
						}
						else {
							// 上传图片
							uni.uploadFile({
							  url: 'http://123.249.5.46:9090/tasks/taskCenter/uploadimage', 
							  filePath: chooseImageRes.tempFilePaths[0],
							  name: 'file', 
							  header: {
								  'Authorization': uni.getStorageSync('token')
							  },
							  success: (uploadRes) => {
								  that.mission.taskPhoto.push(uploadRes.data)
							  },
							});
						}
					}
				})
			},
			// 删除图片
			deletePic(index){
				this.mission.taskPhoto.splice(index,1)
			},
			// 修改任务内容
			changeTask() {
				let taskLable = 1
				switch(this.mission.taskLable){
					case '跑腿':taskLable = 2;break;
					case '带货':taskLable = 3;break;
					case '打理':taskLable = 4;break;
					case '陪伴':taskLable = 5;break;
					case '线上':taskLable = 6;break;
					case '其他':taskLable = 1;break;
				}
				let temp = {
					taskID: this.mission.taskID,
					taskLable: taskLable,
					taskName: this.mission.taskName,
					taskBrief: this.mission.taskBrief,
					taskDetail: this.mission.taskDetail,
					taskTimeCoinBounty: this.mission.taskTimeCoinBounty,
					taskAddress: this.mission.taskAddress,
					taskBeginTime: this.mission.taskBeginTime,
					taskEndTime: this.mission.taskEndTime,
					taskEmployer:uni.getStorageSync('userName'),
					taskEmployerID: uni.getStorageSync('userID'),
					taskPhoto: this.mission.taskPhoto,
				}
				this.$api.changeTaskData(temp).then((res)=>{
					if(res.data == '修改成功'){
						uni.showToast({
							title:'修改成功',
							duration:1000
						})
						setTimeout(function() {
							uni.reLaunch({url: '/pages/mymission/myMission'});
						},1000)
					}
					else{
						uni.showToast({
							title:'修改失败',
							icon:'error',
							duration:1000
						})
					}
				})
			},
			// 删除任务
			deleteTask(){
				let that = this
				uni.showModal({
						title: '提示',
						// 提示文字
						content: '确认删除该任务吗？',
						// 取消按钮的文字自定义
						cancelText: "取消",
						// 确认按钮的文字自定义
						confirmText: "删除",
						//删除字体的颜色
						confirmColor:'red',
						//取消字体的颜色
						cancelColor:'#000000',
						success: function(res) {
						if (res.confirm) {
							if(that.mission.taskStatus == '已接取' || that.mission.taskStatus == '已处理' || that.mission.taskStatus == '已完成'){
								uni.showToast({
									title:'不能删除已被接取的任务',
									icon:'error',
									duration:1000
								})
							}
							else{
								that.$api.deleteTask(that.mission.taskID,uni.getStorageSync('userID'),that.mission.taskTimeCoinBounty).then((res)=>{
									if(res.data == '删除成功'){
										uni.showToast({
											title:'已删除',
											icon:'success',
											duration:1000
										})
										setTimeout(function() {
											uni.reLaunch({url: '/pages/mymission/myMission'});
										},1000)
									}
									else{
										uni.showToast({
											title:'删除失败',
											icon:'error',
											duration:1000
										})
									}
								})
							}
						}
					},
				})
			},
			// 确认同意任务
			agreeTask(){
				this.$api.agreeTask(this.mission.taskID).then((res)=>{
					if(res.data == '完成成功'){
						uni.showToast({
							title:'已确认',
							icon:'success',
							duration:1000
						})
						setTimeout(function() {
							uni.reLaunch({url: '/pages/mymission/myMission'});
						},1000)
					}
					else {
						uni.showToast({
							title:'确认失败',
							icon:'error',
							duration:1000
						})
					}
				})
			},
			// 拒绝同意任务
			refuseTask(){
				// 记录根节点
				let that = this
				uni.showModal({
				    title: '输入不同意原因',
					editable: true,
				    success: function (response) {
						if(response.content) {
							let tempRamark = '发布者不同意完成，原因为：'+ response.content
							that.$api.refuseTask(that.mission.taskID,tempRamark).then((res)=>{
								if(res.data == '批改成功'){
									uni.showToast({
										title:'已拒绝',
										icon:'success',
										duration:1000
									})
									setTimeout(function() {
										uni.reLaunch({url: '/pages/mymission/myMission'});
									},1000)
								}
								else {
									uni.showToast({
										title:'拒绝失败',
										icon:'error',
										duration:1000
									})
								}
							})
						}
					},
				})
			},
			// 与时间计算相关方法
			bindStartDateChange (e) {
			    this.mission.taskStartTime = e.detail.value.replace('-', '.').replace('-', '.');
			},
			bindEndDateChange (e) {
			    this.mission.taskEndTime = e.detail.value.replace('-', '.').replace('-', '.');
			},
			// 时间限制
			getLimtDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
				
				if(type == 'start') {
				    year = year - 20;
				}			
				else if (type == 'end') {
				    year = year + 20;
				}
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
								
				if (type === 'start') {
				    year = year - 60;
				} else if (type === 'end') {
				    year = year + 2;
				}
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				return `${year}.${month}.${day}`;
			},
		},
		computed: {
		    startDate() {
		        return this.getLimtDate('start');
		    },
		    endDate() {
		        return this.getLimtDate('end');
		    }
		},
	}
</script>

<style>
	.main {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	.header-background {
		display: flex;
		align-items: center;
		width: 750rpx;
	}
	.back-image {
		order:0;
		
		display: flex;
		justify-content: center;
		flex-basis: 120rpx;
		margin-right: 170rpx;
	}
	.back-image image {
		width: 50rpx;
		height: 50rpx;
	}
	.back-text {
		order: 1;		
		justify-content: center;
	}
	.back-text text {
		flex-basis: 120rpx;
		font-size: 38rpx;
		color: white;
	}
	
	.body {
		padding: 190rpx 0 50rpx 0;
		width: 750rpx;
	}
	
	/* 修改类别 */
	.change-lable {
		display: flex;
		flex-direction: row;
		margin-bottom: 10rpx;
	}
	.change-lable-title {
		font-size: 38rpx;
		font-weight: 600;
	}
	.change-lable-content {
		font-size: 35rpx;
	}
	
	/* 修改标题*/
	.change-title {
		display: flex;
		flex-direction: row;
		margin-bottom: 10rpx;
	}
	.change-title-title {
		font-size: 38rpx;
		font-weight: 600;
	}
	.change-title-content {
		font-size: 35rpx;
		width: 530rpx;
	}
	
	/* 修改简介 */
	.change-brief {
		margin-bottom: 10rpx;
	}
	.change-brief-title {
		font-size: 38rpx;
		font-weight: 600;
		margin-bottom: 10rpx;
	}
	.change-brief-content {
		font-size: 35rpx;
		width: 630rpx;
		padding: 5rpx 5rpx 5rpx 5rpx;
	}
	/* 修改详情 */
	.change-detail {
		margin-bottom: 10rpx;
	}
	.change-detail-title {
		font-size: 38rpx;
		font-weight: 600;
		margin-bottom: 10rpx;
	}
	.change-detail-content {
		font-size: 35rpx;
		width: 630rpx;
		padding: 5rpx 5rpx 5rpx 5rpx;
	}
	/* 修改悬赏 */
	.change-coins {
		display: flex;
		flex-direction: row;
		margin-bottom: 10rpx;
	}
	.change-coins-title {
		font-size: 38rpx;
		font-weight: 600;
	}
	.change-coins-content {
		font-size: 35rpx;
		width: 70rpx;
	}
	.change-coins-unit {
		font-size: 35rpx;
	}
	/* 修改时间 */
	.change-time {
		display: flex;
		flex-direction: row;
		margin-bottom: 10rpx;
	}
	.change-time-title {
		font-size: 38rpx;
		font-weight: 600;
	}
	.change-time-content {
		font-size: 35rpx;
	}
	/* 修改图片 */
	.change-image {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		flex-wrap: wrap;
		width: 650rpx;
		margin: 0 0 30rpx 0;
		border-radius: 15px;
		padding: 0 0 10rpx 20rpx;
	}
	.change-image-title {
		width: 120rpx;
		font-size: 38rpx;
		color: gray;
		margin-bottom: 50rpx;
	}
	.change-image-element {
		margin: 0 10rpx 10rpx 20rpx;
	}
	.change-image-element image {
		width: 600rpx;
	}
	.detail-button {
		margin: 20rpx 0 0 0 ;
		width: 80%;
		height: 80rpx;
		background-color: #80aaff;
		box-shadow: 0px 2px 4px  rgba(0, 0, 0, 0.25);
		color: rgba(255, 255, 255, 1);
		text-align: center;
		font-size: 38rpx;
		line-height: 80rpx;
	}
	.upPic {
		width: 100%;
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: flex-start;
		margin-top: 50rpx;
		padding-left: 40rpx;
	}
	#deletepic {
		width: 30rpx;
		height: 30rpx;
		position: relative;
		left: 600rpx;
		top: 20rpx;
		z-index: 1;
	}
	.upPic-add {
		width: 200rpx;
		height: 200rpx;
		margin: 40rpx 0 0 20rpx;
	}
	.upPic-add image{
		width: 200rpx;
		height: 200rpx;
	}
	
	/* 开始任务信息显示 */
	.detail-start-title{
		font-size: 38rpx;
		width: 100%;
		color: orange;
	}
	.detail-start-text{
		margin-top: 30rpx;
		font-size: 35rpx;
		width: 100%;
	}
	.detail-start-image {
		margin-top: 30rpx;
		width: 100%;
	}
	.detail-start-image image {
		width: 100%;
		background-color: blue
	}
	
	/* 任务首部 */
	.detail-head {
		width: 680rpx;
		border-radius: 15px;
		background-color: white;
		padding: 50rpx 0 30rpx 20rpx;
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		margin-bottom: 50rpx;
	}
	/* 任务标题 */
	.detail-title {
		width: 100%;
		font-size: 38rpx;
		font-weight: bold;
		margin-bottom: 10rpx;
	}
	/* 时间币悬赏 */
	.detail-coins {
		width: 650rpx;
		font-size: 36rpx;
		color: orange;
		padding-bottom: 40rpx;
		border-bottom: #e6e6e6 solid 1px;
	}
	
	/* 类别详情 */
	.detail-lable {
		margin-top: 20rpx;
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
	}
	.detail-lable-title {
		font-size: 30rpx;
		color: gray;
	}
	.detail-lable-content {
		font-size: 30rpx;
	}
	
	/* 任务主体 */
	.detail-body {
		width: 660rpx;
		border-radius: 15px;
		background-color: white;
		padding: 20rpx 20rpx 30rpx 20rpx;
		margin-bottom: 50rpx;
		display: flex;
		flex-direction: column;
		align-items: flex-start;
	}
	
	/* 任务状态 */
	.detail-status {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-bottom: 20rpx;
	}
	.detail-status-title {
		font-size: 35rpx;
		font-weight: 600;
	}
	.detail-status-content {
		margin-top: 10rpx;
		font-size: 33rpx;
		margin-bottom: 5rpx;
	}
	
	/* 任务简介 */
	.detail-breif-title {
		font-size: 35rpx;
		font-weight: 600;
	}
	.detail-breif-content {
		margin-top: 10rpx;
		font-size: 33rpx;
		margin-bottom: 20rpx;
	}
	/* 任务详情 */
	.detail-detail-title {
		font-size: 35rpx;
		font-weight: 600;
	}
	.detail-detail-content {
		margin-top: 10rpx;
		font-size: 33rpx;
	}
	/* 任务图片 */
	.detail-image {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		flex-wrap: wrap;
		width: 650rpx;
		margin: 30rpx 0 30rpx 0;
		padding: 20rpx 0 10rpx 20rpx;
	}
	.detail-image-element {
		margin: 0 10rpx 10rpx 20rpx;
	}
	.detail-image-element image {
		width: 600rpx;
	}
	
	/* 截止时间 */
	.detail-time {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-bottom: 10rpx;
	}
	.detail-time-title {
		font-size: 35rpx;
		font-weight: 600;
	}
	.detail-time-content {
		margin-top: 10rpx;
		font-size: 33rpx;
		margin-bottom: 5rpx;
	}
	
	/* 审核失败原因 */
	.detail-fail-title {
		font-size: 35rpx;
		font-weight: 600;
	}
	.detail-fail-content {
		margin-top: 10rpx;
		font-size: 33rpx;
	}
</style>