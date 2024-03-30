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
		</view>
		
		<!--接取任务按钮-->
		<button class="detail-button" v-show="!statusShow" @click="accessMission">接取任务</button>
		
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
				statusShow:true,
				
				// 显示图片
				taskPhotoShow:false,
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
				switch(res.data.taskStatus){
					case 1:this.mission.taskStatus = '未审核';break;
					case 2:this.statusShow = false;break;
					case 3:this.mission.taskStatus = '已接取';break;
					case 4:this.mission.taskStatus = '已处理';break;
					case 5:this.mission.taskStatus = '已完成';break;
					case 6:this.mission.taskStatus = '已完结';break;
					case 7:this.mission.taskStatus = '进行中';break;
				}
			})
		},
		methods: {
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
			},
			// 接取任务
			accessMission(){
				this.$api.accessTask(this.missionId,uni.getStorageSync('userID'),uni.getStorageSync('userName')).then((res)=>{
					if(res.data == '接取成功'){
						uni.showToast({
							title:'接取成功',
							duration:1000
						})
						setTimeout(function() {
							uni.reLaunch({url: '/pages/index/index'});
						},1000)
					}
					else{
						uni.showToast({
							title:'接取失败',
							icon:'error',
							duration:1000
						})
						setTimeout(function() {
							uni.reLaunch({url: '/pages/index/index'});
						},1000)
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
		margin-top: 160rpx;
		border-radius: 10px;
		width: 700rpx;
		background-color: white;
		padding-top: 50rpx;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
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
		margin: 50rpx 10rpx 50rpx 10rpx;
		padding: 30rpx 30rpx 30rpx 30rpx;
		width: 500rpx;
		background: rgba(255, 141, 26, 0.6);
		box-shadow: 0px 2px 4px  rgba(0, 0, 0, 0.25);
		color: rgba(255, 255, 255, 1);
		text-align: center;
		font-size: 38rpx;
		line-height: 38rpx;
	}
</style>