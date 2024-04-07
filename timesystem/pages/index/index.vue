<template>
	<view class="body">
        <page-meta :root-font-size="size"></page-meta>
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
				<text @click="changeSort(9)">最新</text>
				<view v-if="select==9"></view>
			</view>
			<view class="header-select-button">
				<text @click="changeSort(11)">悬赏最高</text>
				<view v-if="select==11"></view>
			</view>
		</view>
		
		<!--任务列表-->
		<view class="mission-list" v-for="(item,index) in missionDataList" :key="item.id">
			<view class="mission-list-element" >
				<view @click="navTo('/pages/missionDetail?id='+item.taskID)">
					<mission :missionData="item"></mission>
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
				// 头像图片
				headerImage:'/static/headpic.jpg',
				// 用户名
				username:'登录/注册',
				// 渲染的任务列表数据
				missionDataList:[],
				// 加载的任务页数
				pages:0,
				// 显示选中
				select:9,
				
				// 显示字体大小
				size:""
			}
		},
		// 创建页面时的事件
		created:function(){
			this.size = uni.getStorageSync("size")
			this.$api.getUserData(uni.getStorageSync('userName')).then((res)=>{
				this.headerImage = res.data.userPhoto
				this.username = res.data.userName
			})
			this.reload()
		},
		// 页面下拉事件
		onPullDownRefresh:function(){
			uni.reLaunch({url:'/pages/index/index'})
		},
		// 页面上拉到顶事件
		onReachBottom(){
			this.pages+=10
			this.reload()
		},
		methods: {
			// 页面跳转
			navTo(url){
				uni.navigateTo({
					url,
				})
			},
			// 加载数据
			reload(){
				this.$api.getTasklist(this.pages,this.select).then((res)=>{
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
					if(this.missionDataList.length <= this.pages) {
						this.pages-=10
						uni.showToast({
							title:'已经没有了~',
							icon: "none",
							duration:1000
						})
					}
				})
			},
			// 切换排序方式
			changeSort(select){
				this.select = select
				this.missionDataList=[]
				this.pages=0
				this.reload()
			}
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
		flex-basis: 250rpx;
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
		flex-direction: row;
		height: 120rpx;
		width: 700rpx;
		background-color: white;
		margin-top: 240rpx;
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
