<template>
	<view>
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
		
		<!--任务信息-->
		<view class="detail-text">
			<text>任务标题：{{mission.taskName}}</text>
		</view>
		<view class="detail-text">
			<text>任务简介：{{mission.taskBrief}}</text>
		</view>
		<view class="detail-text">
			<text>任务详情：</text>
			<view v-html="mission.taskDetail"></view>
		</view>
		<view class="detail-text">
			<text>任务悬赏：{{mission.taskTimeCoinBounty}}</text>
		</view>
		<view class="detail-text">
			<text>任务状态：{{mission.taskStatus}}</text>
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
					taskName:'',
					taskBrief:'',
					taskDetail:'',
					taskTimeCoinBounty:null,
					taskAddress:'',
					taskEndTime:'',
					taskStatus:null,
				},
			}
		},
		onLoad(options) {
			this.missionId = options.id
			this.$api.getTaskData(this.missionId).then((res)=>{
				this.mission.taskID = res.data.taskID
				this.mission.taskName = res.data.taskName
				this.mission.taskBrief = res.data.taskBrief
				this.mission.taskDetail = res.data.taskDetail
				this.mission.taskTimeCoinBounty = res.data.taskTimeCoinBounty
				this.mission.taskAddress = res.data.taskAddress
				this.mission.taskEndTime = res.data.taskEndTime
				switch(res.data.taskStatus){
					case 1:this.mission.taskStatus = '未审核';break;
					case 2:this.mission.taskStatus = '未完成';break;
					case 3:this.mission.taskStatus = '已接取';break;
					case 4:this.mission.taskStatus = '已处理';break;
					case 5:this.mission.taskStatus = '已完成';break;
					case 6:this.mission.taskStatus = '已完结';break;
					case 7:this.mission.taskStatus = '已取消';break;
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
		}
	}
</script>

<style>
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
	.detail-text {
		display: flex;
		width: 670rpx;
		margin-top: 10rpx;
		margin-left: 10rpx;
		margin-right: 10rpx;
		padding: 30rpx 30rpx 30rpx 30rpx;
		background-color: rgba(204, 204, 204, 0.3);
		border-radius: 10px;
	}
	.detail-text text {
		font-size: 38rpx;
		color: #000000
	}
	.detail-button {
		margin-top: 50rpx;
		margin-left: 10rpx;
		margin-right: 10rpx;
		padding: 30rpx 30rpx 30rpx 30rpx;
		background: rgba(255, 141, 26, 0.6);
		box-shadow: 0px 2px 4px  rgba(0, 0, 0, 0.25);
		color: rgba(255, 255, 255, 1);
		text-align: center;
		font-size: 38rpx;
		line-height: 38rpx;
	}

</style>