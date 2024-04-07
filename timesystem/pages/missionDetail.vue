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
		
		<view class="body">
			<!--任务信息-->
			<view class="detail-head">
				<view class="detail-title">
					<text>{{mission.taskName}}</text>
				</view>
				
				<text class="detail-coins">{{mission.taskTimeCoinBounty}}时间币/时</text>
				<view class="detail-lable">
					<text class="detail-lable-title">类别：</text>
					<text class="detail-lable-content">{{mission.taskLable}}</text>
				</view>
			</view>
			
			<view class="detail-body">
				<text class="detail-breif-title">任务简介：</text>
				<text class="detail-breif-content">{{mission.taskBrief}}</text>
				
				<text class="detail-detail-title">任务详情：</text>
				<text class="detail-detail-content">{{mission.taskDetail}}</text>
				<view class="detail-image" v-if="taskPhotoShow">
					<view class="detail-image-element" v-for="(item,index) in mission.taskPhoto">
						<image :src="item"></image>
					</view>
				</view>
			</view>
			
			<!--接取任务按钮-->
			<view class="detail-end">
				<button class="detail-button" @click="accessMission">接取</button>
			</view>
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
				
				// 显示图片
				taskPhotoShow:false,
				
				size:'',
			}
		},
		onLoad(options) {
			this.size = uni.getStorageSync("size")
			this.missionId = options.id
			this.$api.getTaskData(this.missionId).then((res)=>{
				this.mission.taskID = res.data.taskID
				switch(res.data.taskLable){
					case 1:this.mission.taskLable = '其他';break;
					case 2:this.mission.taskLable = '跑腿';break;
					case 3:this.mission.taskLable = '代购';break;
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
				if(res.data.taskPhoto != null && res.data.taskPhoto != '' && res.data.taskPhoto[0] != '' && res.data.taskPhoto[0] != null){
					this.taskPhotoShow = true
					this.mission.taskPhoto = res.data.taskPhoto
				}
				switch(res.data.taskStatus){
					case 1:this.mission.taskStatus = '未审核';break;
					case 2:this.mission.taskStatus = '未完成';break;
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
							title:'已接取',
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
		padding: 190rpx 0 50rpx 0;
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
		display: flex;
		flex-direction: column;
		align-items: flex-start;
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
	
	/* 接取任务*/
	.detail-end {
		position: fixed;
		background-color: white;
		bottom: 0;
		width: 750rpx;
		height: 100rpx;
		padding-top: 10rpx;
	}
	/* 按钮 */
	.detail-button {
		width: 600rpx;
		height: 80rpx;
		background-color: #80aaff;
		box-shadow: 0px 2px 4px  rgba(0, 0, 0, 0.25);
		color: rgba(255, 255, 255, 1);
		text-align: center;
		font-size: 38rpx;
		line-height: 80rpx;
	}
</style>