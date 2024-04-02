<template>
	<view class="main">
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
		
		<view class="body">
			<!--任务信息-->
			<view class="detail">
				<text class="detail-title">类别：</text>
				<text class="detail-content">{{mission.taskLable}}</text>
			</view>
			<view class="detail">
				<text class="detail-title">标题：</text>
				<text class="detail-content">{{mission.taskName}}</text>
			</view>
			<view class="detail">
				<text class="detail-title">简介：</text>
				<text class="detail-content">{{mission.taskBrief}}</text>
			</view>
			<view class="detail">
				<text class="detail-title">详情：</text>
				<text class="detail-content">{{mission.taskDetail}}</text>
			</view>
			<view class="detail-image" v-if="taskPhotoShow">
				<text class="detail-image-title">图片：</text>
				<view class="detail-image-element" v-for="(item,index) in mission.taskPhoto">
					<image :src="item"></image>
				</view>
			</view>
			<view class="detail">
				<text class="detail-title">悬赏：</text>
				<text class="detail-content">{{mission.taskTimeCoinBounty}}</text>
			</view>
			<view class="detail" v-show="statusShow">
				<text class="detail-title">状态：</text>
				<text class="detail-content">{{mission.taskStatus}}</text>
			</view>
			
			<!-- 显示审核失败原因 -->
			<view  class="detail" v-show="remarkShow">
				<text class="detail-time">审核失败：</text>
				<view class="detail-content">{{mission.taskStatusRemark}}</view>
			</view>
			
			<!-- 任务打卡按钮 -->
			<button v-if="startMissionShow==1" class="detail-button" @click="startMission">开始任务</button>
			<!--完成任务按钮-->
			<button v-if="startMissionShow==2" class="detail-button" @click="completeMission">完成任务</button>
			<!--取消任务按钮-->
			<button class="detail-button" @click="canelMission">取消任务</button>
			
			<!-- 开始任务弹出层 -->
			<uni-popup ref="popup" type="bottom" border-radius="15px 15px 0 0" @maskClick="popClose">
				<view class="start">
					<text class="start-title">请输入打卡信息</text>
					<view class="start-text">
						<text>请输入文字信息：</text>
						<input v-model="taskDoingText"></input>
					</view>
					<view class="start-image">
						<text>请上传打卡图片：</text>
						<image @click="upPic" :src="taskDoingPhoto"></image>
					</view>
					
					<view style="display: flex;flex-direction: row;justify-content: center;">
						<button @click="cancel" style="margin:20rpx 100rpx 0 0;background-color: wheat;">取消开始</button>
						<button @click="start" style="margin:20rpx 0 0 0;background-color: orange;">确认开始</button>
					</view>
				</view>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				missionId:null,
				mission:{
					taskID:null,
					taskLable:'',
					taskName:'',
					taskBrief:'',
					taskDetail:'',
					taskTimeCoinBounty:null,
					taskAddress:'',
					taskEndTime:'',
					taskStatus:null,
					taskPhoto:[],
				},
				
				// 开始任务信息
				taskDoingText:'',
				taskDoingPhoto:'',
				
				remarkShow:false,
				statusShow:true,
				// 显示图片
				taskPhotoShow:false,
				// 显示开始任务
				startMissionShow:0,
			}
		},
		onLoad(options) {
			this.missionId = options.id
			this.$api.getTaskData(this.missionId).then((res)=>{
				this.mission.taskID = res.data.taskID
				switch(res.data.taskLable){
					case 1:this.mission.taskLable = '其他';break;
					case 2:this.mission.taskLable = '跑腿';break;
					case 3:this.mission.taskLable = '带货';break;
					case 4:this.mission.taskLable = '打理';break;
					case 5:this.mission.taskLable = '陪伴';break;
					case 6:this.mission.taskLable = '线上';break;
					default:break;
				}
				this.mission.taskName = res.data.taskName
				this.mission.taskBrief = res.data.taskBrief
				this.mission.taskDetail = res.data.taskDetail
				this.mission.taskTimeCoinBounty = res.data.taskTimeCoinBounty
				this.mission.taskAddress = res.data.taskAddress
				this.mission.taskEndTime = res.data.taskEndTime
				if(res.data.taskPhoto[0] != ''){
					this.taskPhotoShow = true
					this.mission.taskPhoto = res.data.taskPhoto
				}
				if(res.data.taskStatusRemark !== null && res.data.taskStatusRemark !== ''){
					this.mission.taskStatusRemark = res.data.taskStatusRemark
					this.remarkShow = true
				}
				switch(res.data.taskStatus){
					case 1:this.mission.taskStatus = '未审核';break;
					case 2:this.mission.taskStatus = '未完成';break;
					case 3:this.mission.taskStatus = '已接取';this.startMissionShow=1;break;
					case 4:this.mission.taskStatus = '已处理';break;
					case 5:this.mission.taskStatus = '已完成';break;
					case 6:this.mission.taskStatus = '已完结';break;
					case 7:this.mission.taskStatus = '进行中';this.startMissionShow=2;break;
				}
			})
		},
		methods: {
			//返回上一级页面
			back() {
				uni.reLaunch({
					url:'/pages/mymission/myMission'
				})
			},
			// 开始任务
			startMission(){
				this.$refs.popup.open('buttom')
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
						if(chooseImageRes.tempFiles[0].size > 1048576){
							uni.showToast({
								title: '图片大于1M!',
								icon:'error',
								duration: 1000
							});
						}
						else {
							// 上传图片
							uni.uploadFile({
							  url: 'http://10.195.28.44:9090/tasks/taskCenter/uploadimage', 
							  filePath: chooseImageRes.tempFilePaths[0],
							  name: 'file', 
							  header: {
								  'Authorization': uni.getStorageSync('token')
							  },
							  success: (uploadRes) => {
								  that.taskDoingPhoto = uploadRes.data
							  },
							});
						}
					}
				})
			},
			// 关闭弹出层
			popClose(){
				this.taskDoingText = ''
				this.taskDoingPhoto = ''
			},
			// 取消确认
			cancel(){
				this.taskDoingText = ''
				this.taskDoingPhoto = ''
				this.$refs.popup.close()
			},
			// 确认开始任务
			start(){
				let that = this
				let temp = {
					taskID:this.missionId,
					taskDoingText: this.taskDoingText,
					taskDoingPhoto: this.taskDoingPhoto,
				}
				this.$api.startMission(temp).then((res)=>{
					if(res.data == '进行成功'){
						uni.showToast({
							title: '开始成功',
							duration: 1000
						});
						setTimeout(function() {
							that.back()
						},1000)
					}
					else {
						uni.showToast({
							title: '网络错误',
							duration: 1000
						});
					}
				})
			},
			// 完成任务
			completeMission(){
				this.$api.completeTask(this.missionId,uni.getStorageSync('userID')).then((res)=>{
					if(res.data == '完成成功'){
						uni.showToast({
							title:'完成任务成功',
							duration:1000
						})
						setTimeout(function() {
							uni.reLaunch({url: '/pages/mymission/myMission'});
						},1000)
					}
					else{
						uni.showToast({
							title:'完成任务失败',
							icon:'error',
							duration:1000
						})
					}
				})
			},
			// 取消任务
			canelMission(){
				this.$api.canelTask(this.missionId,uni.getStorageSync('userID')).then((res)=>{
					if(res.data == '取消成功'){
					uni.showToast({
							title:'取消成功',
							duration:1000
						})
						setTimeout(function() {
							uni.reLaunch({url: '/pages/mymission/myMission'});
						},1000)
					}
					else{
						uni.showToast({
							title:'取消失败',
							icon:'error',
							duration:1000
						})
					}
				})
			}
		}
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
		margin-right: 195rpx;
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
		margin-top: 200rpx;
		border-radius: 10px;
		width: 700rpx;
		background-color: white;
		padding-top: 50rpx;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
		margin-bottom: 50rpx;
	}
	.detail {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		width: 650rpx;
		flex-wrap: wrap;
	}
	.detail-title {
		width: 120rpx;
		font-size: 38rpx;
		color: gray;
		margin-bottom: 50rpx;
	}
	.detail-content {
		font-size: 35rpx;
	}
	.detail-image {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		flex-wrap: wrap;
		width: 650rpx;
		margin: 30rpx 0 30rpx 0;
	}
	.detail-time {
		width: 200rpx;
		font-size: 38rpx;
		color: gray;
		margin-bottom: 50rpx;
	}
	.detail-image-title {
		width: 120rpx;
		font-size: 38rpx;
		color: gray;
		margin-bottom: 50rpx;
	}
	.detail-image-element {
		margin: 0 10rpx 10rpx 20rpx;
	}
	.detail-image-element image {
		width: 600rpx;
	}
	.detail-button {
		margin: 10rpx 10rpx 50rpx 10rpx;
		padding: 30rpx 30rpx 30rpx 30rpx;
		width: 500rpx;
		background-color: #80aaff;
		box-shadow: 0px 2px 4px  rgba(0, 0, 0, 0.25);
		color: rgba(255, 255, 255, 1);
		text-align: center;
		font-size: 38rpx;
		line-height: 38rpx;
	}
	.start {
		height: 900rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		background-color: white;
		padding: 20rpx 0 0 0;
	}
	.start-title {
		font-size: 37rpx;
	}
	.start-text {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		align-items: center;
		height: 100rpx;
	}
	.start-text text {
		font-size: 35rpx;
	}
	.start-text input {
		border-radius: 15px;
		border: 1px solid #e6e6e6;
		height: 70rpx;
	}
	.start-image {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		flex-wrap: wrap;
	}
	.start-image text {
		font-size: 35rpx;
		margin-left: 60rpx;
	}
	.start-image image {
		width: 600rpx;
		height: 500rpx;
		margin-left: 75rpx;
		border: 1px solid #e6e6e6;
	}
</style>