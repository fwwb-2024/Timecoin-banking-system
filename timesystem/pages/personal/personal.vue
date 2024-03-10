<template>
	<view class="body">
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 头像图片 -->
			<view class="header-image" @click="navTo('/pages/login')">
				<image :src="headerImage"></image>
			</view>
			<!-- 用户名 -->
			<view class="head-text" @click="navTo('/pages/login')">
				<text>{{username}}</text>
			</view>
		</view>
		
		<!-- 个人中心功能 -->
		<view class="peronal-function">
			<!-- 账号信息 -->
			<view class="function-part line" @click="navTo('/pages/personal/accountData')">
				<view>
					<image src="../../static/uni.png"></image>
				</view>
				<view>
					<text>账号信息</text>
				</view>
			</view>
			
			
			<!-- 时间币管理 -->
			<view class="function-part line" @click="navTo('/pages/personal/timeCoinData')">
				<view>
					<image src="../../static/uni.png"></image>
				</view>
				<view>
					<text>时间币管理</text>
				</view>
			</view>
			
			<!-- 家庭管理 -->
			<view class="function-part line" @click="navTo('/pages/personal/familyList')">
				<view>
					<image src="../../static/uni.png"></image>
				</view>
				<view>
					<text>家庭管理</text>
				</view>
			</view>
			
			<!-- 切换身份 -->
			<view class="function-part">
				<view>
					<image src="../../static/uni.png"></image>
				</view>
				<view>
					<text>切换身份</text>
				</view>
				<view id="now-persoanl-status">
					<text>当前身份: {{nowPersonlStatus}}</text>
				</view>
			</view>
		</view>
		
		<!-- 关于开发者信息 -->
		<view class="about">
			<view class="about-us" @click="navTo('/pages/personal/aboutUs')">
				<view>
					<image src="../../static/uni.png"></image>
				</view>
				<view>
					<text>关于我们</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 头像图片 
				headerImage:'/static/headpic.jpg',
				// 用户名
				username:'登录/注册',
				// 当前身份
				nowPersonlStatus:'志愿者',
			}
		},
		methods: {
			// 页面跳转
			navTo(url){
				uni.navigateTo({
					url,
				})
			},
		},
	}
</script>

<style>
	.header-background {
		display: flex;
		align-items: center;
		width: 750rpx;
		height: 120rpx;
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
	.peronal-function {
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		align-items: center;
		width: 700rpx;
		background-color: white;
		margin-top: 30rpx;
		border-radius: 20px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
		padding: 30rpx 0 30rpx 0;
	}
	.function-part {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		align-items: center;
		width: 550rpx;
		margin: 20rpx 0 20rpx 0;
		padding-bottom: 20rpx;
	}
	.function-part view {
		margin: 10rpx 10rpx 10rpx 0;
	}
	.function-part image {
		width: 60rpx;
		height: 60rpx;
	}
	.function-part text {
		margin-left: 20rpx;
		font-size: 35rpx;
	}
	.line {
		border-bottom: thin solid rgb(179, 179, 179);
	}
	#now-persoanl-status {
		margin-left: 100rpx;
	}
	#now-persoanl-status text {
		font-size: 25rpx;
		font-weight: bold;
		color: gray;
	}
	.about {
		display: flex;
		flex-direction: column;
		justify-content: flex-start;
		align-items: center;
		width: 700rpx;
		background-color: white;
		margin-top: 30rpx;
		border-radius: 20px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
		padding: 20rpx 0 20rpx 0;
	}
	.about-us {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		align-items: center;
		width: 550rpx;
		margin: 0 10rpx 0 10rpx;
	}
	.about image {
		width: 60rpx;
		height: 60rpx;
	}
	.about text {
		margin-left: 20rpx;
		font-size: 35rpx;
	}
</style>