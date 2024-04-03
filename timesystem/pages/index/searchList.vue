<template>
	<view>
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="header-background-text">
				<text>搜索任务</text>
			</view>
		</view>
		
		
		<view class="body">
			<!--任务列表-->
			<view class="mission-list" v-for="(item,index) in missionDataList" :key="item.id">
				<view class="mission-list-element" >
					<view @click="navTo('/pages/missionDetail?id='+item.taskID)">
						<mission :missionData="item"></mission>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import mission from '@/components/mission.vue'
	export default {
		data() {
			return {
				searchContent:'',
				searchChoose:0,
				pages:0,
				missionDataList:[],
			}
		},
		onLoad(option) {
			this.searchChoose = option.searchChoose
			this.searchContent = option.searchContent
			this.reload()
		},
		// 页面上拉到顶事件
		onReachBottom(){
			this.pages+=10
			this.reload()
			if(this.missionDataList.length <= this.pages) {
				this.pages-=10
				uni.showToast({
					title:'已经没有了~',
					icon: "none",
					duration:1000
				})
			}
		},
		methods:{
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
			},
			// 页面跳转
			navTo(url){
				uni.navigateTo({
					url,
				})
			},
			// 加载数据
			reload(){
				switch(this.searchChoose){
					case '0':
					this.$api.searchByUser(this.searchContent,this.pages,9).then((res)=>{
						const length = res.data.length
						for(let i=0;i<length;i++) {
							this.missionDataList.push({
								taskLable: res.data[i].taskLable,
								taskID: res.data[i].taskID,
								title: res.data[i].taskName,
								introduction: res.data[i].taskBrief,
								timeCoins: res.data[i].taskTimeCoinBounty,
								publisher:{
									username: res.data[i].taskEmployer,
									headpicture: res.data[i].userPhoto,
								},
								endTime: res.data[i].taskEndTime,
								status: res.data[i].taskStatus,
								statusShow:false
							})
						}
					});
					this.$api.searchByTitle(this.searchContent,this.pages,9).then((res)=>{
						const length = res.data.length
						for(let i=0;i<length;i++) {
							this.missionDataList.push({
								taskLable: res.data[i].taskLable,
								taskID: res.data[i].taskID,
								title: res.data[i].taskName,
								introduction: res.data[i].taskBrief,
								timeCoins: res.data[i].taskTimeCoinBounty,
								publisher:{
									username: res.data[i].taskEmployer,
									headpicture: res.data[i].userPhoto,
								},
								endTime: res.data[i].taskEndTime,
								status: res.data[i].taskStatus,
								statusShow:false
							})
						}
					});
					break;
					case '1':
					this.$api.searchByUser(this.searchContent,this.pages,9).then((res)=>{
						const length = res.data.length
						for(let i=0;i<length;i++) {
							this.missionDataList.push({
								taskLable: res.data[i].taskLable,
								taskID: res.data[i].taskID,
								title: res.data[i].taskName,
								introduction: res.data[i].taskBrief,
								timeCoins: res.data[i].taskTimeCoinBounty,
								publisher:{
									username: res.data[i].taskEmployer,
									headpicture: res.data[i].userPhoto,
								},
								endTime: res.data[i].taskEndTime,
								status: res.data[i].taskStatus,
								statusShow:false
							})
						}
					});break;
					case '2':
					this.$api.searchByTitle(this.searchContent,this.pages,9).then((res)=>{
						const length = res.data.length
						for(let i=0;i<length;i++) {
							this.missionDataList.push({
								taskLable: res.data[i].taskLable,
								taskID: res.data[i].taskID,
								title: res.data[i].taskName,
								introduction: res.data[i].taskBrief,
								timeCoins: res.data[i].taskTimeCoinBounty,
								publisher:{
									username: res.data[i].taskEmployer,
									headpicture: res.data[i].userPhoto,
								},
								endTime: res.data[i].taskEndTime,
								status: res.data[i].taskStatus,
								statusShow:false
							})
						}
					});break;
				}
				
			}
		},
		components:{
			mission,
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
		margin-right: 175rpx;
	}
	.back-image image {
		width: 50rpx;
		height: 50rpx;
	}
	.header-background-text {
		order: 1;
		
		display: flex;
		justify-content: center;
		flex-basis: 200rpx;
		font-size: 38rpx;
		color: white;
	}
	.body {
		margin-top: 200rpx;
	}
	.header-select {
		display: flex;
		flex-direction: row;
		height: 120rpx;
		width: 700rpx;
		background-color: white;
		margin-bottom: 20rpx;
		align-items: center;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
	}
	.header-select-button {
		margin:20rpx 0 0 40rpx;
	}
	.header-select-button text {
		font-size: 32rpx;
	}
	.header-select-button view {
		border-bottom: orange solid 2px;
	}
	.mission-list {
		background-color: white;
		border-radius: 15px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
		width: 700rpx;
		margin-bottom: 15rpx;
	}
</style>
