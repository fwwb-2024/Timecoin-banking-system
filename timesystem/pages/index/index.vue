<template>
	<view class="body">
		
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 头像图片 -->
			<view class="header-image" @click="navTo('/pages/personal/accountData')">
				<image :src="headerImage"></image>
			</view>
			<!-- 用户名 -->
			<view class="head-text" @click="navTo('/pages/accountData')">
				<text>{{username}}</text>
			</view>
			<!-- 搜索框 -->
			<view class="header-searchbar-image"  @click="navTo('/pages/index/search')">
				<image src="/static/index/searchbarbuttom.png"></image>
			</view>
			<!-- 搜索标志 -->
			<view class="header-search-image"  @click="navTo('/pages/index/search')">
				<image src="/static/index/searchbuttom.png"></image>
			</view>
		</view>
		
		<!-- 选择栏 -->
		<view class="header-select">
			<view class="header-select-button">
				<text @click="">最热</text>
			</view>
			<view class="header-select-button">
				<text @click="">最新</text>
			</view>
			<view class="header-select-button">
				<text @click="">悬赏最高</text>
			</view>
			
		</view>
		
		<!--任务列表-->
		<view class="mission-list" v-for="(item,index) in missionDataList" :key="item.id">
			<view class="mission-list-element" @click="navTo('/pages/missionDetail?'+item.taskID)">
				<mission :missionData="item"></mission>
			</view>
		</view>
	</view>
</template>

<script>
	import mission from '@/components/mission.vue'
	export default {
		data() {
			return {
				// 头像图片
				headerImage:'/static/headpic.jpg',
				// 用户名
				username:'登录/注册',
				// 渲染的任务列表数据
				missionDataList:[],
			}
		},
		created:function(){
			this.$api.getUserData(uni.getStorageSync('userName')).then((res)=>{
				this.headerImage = res.data.userPhoto
				this.username = res.data.userName
			})
			
			this.$api.getTasklist(0,1).then((res)=>{
				const length = res.data.length
				let temp = {
					taskID:null,
					title:'',
					introduction:'',
					timeCoins:null,
					publisher:{
						username:'',
						headpicture:'',
					},
					endTime:'',
					statusShow:true,
					status:'',
				}
				for(let i=0;i<length;i++) {
					// temp.taskID = res.data[i].taskID
					temp.taskID = res.data[i].taskID
					temp.title = res.data[i].taskName
					temp.introduction = res.data[i].taskBrief
					temp.timeCoins = res.data[i].taskTimeCoinBounty
					temp.publisher.username = res.data[i].taskEmployer
					// publisher.headpicture = temp.
					temp.endTime = res.data[i].taskEndTime
					temp.status = res.data[i].taskStatus
					this.missionDataList.push(temp)
				}
			})
		},
		methods: {
			// 页面跳转
			navTo(url){
				uni.navigateTo({
					url,
				})
			},
		},
		components: {
			mission,
		},
	}
</script>

<style>
	.header-background {
		display: flex;
		align-items: center;
		width: 750rpx;
	}
	.header-image {
		order:0;
		
		display: flex;
		justify-content: center;
		flex-basis: 120rpx;
	}
	.header-image image {
		width: 80rpx;
		height: 80rpx;
		border-radius: 50px;
		border: 1px solid rgb(184, 184, 184)
	}
	.head-text {
		order:1;
		
		display: flex;
		flex-basis: 200rpx;
		height: 60rpx;
	}
	.head-text text {
		align-self: flex-end;
		font-size: 35rpx;
		color: white;
	}
	.header-searchbar-image {
		order:2;
		
		display: flex;
		justify-content: right;
		align-items: center;
		flex-basis: 120rpx;
		margin-left: 50rpx;
		margin-right: -50rpx;
	}
	.header-searchbar-image image {
		width: 300rpx;
		height: 60rpx;
		border-radius: 50px;
	}
	.header-search-image {
		order:3;
		
		display: flex;
		justify-content: right;
		flex-basis: 120rpx;
		
	}
	.header-search-image image {
		width: 60rpx;
		height: 60rpx;
		border-radius: 50px;
		
	}
	.header-select {
		display: flex;
		height: 120rpx;
		width: 750rpx;
		background-color: white;
		margin-bottom: 20rpx;
		
	}
	.header-select-button {
		margin-top: 60rpx;
		margin-left: 40rpx;
	}
	.header-select-button text {
		font-size: 28rpx;
	}
	.mission-list {
		background-color: white;
	}
	.mission-list-element {
		box-shadow: 1px 1px 1px rgb(200, 200, 200);
	}
</style>
