<template>
	<view>
		<view class="headerBackgroundColor header-background">
			<!-- 头像图片 -->
			<view class="header-image" @click="toLogin">
				<image :src="headerImage"></image>
			</view>
			<!-- 用户名 -->
			<view class="head-text" @click="toLogin">
				<text>{{username}}</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				headerImage:'/static/headpic.jpg',
				username:'登录/注册'
			}
		},
		methods: {
			//跳转到登录页面
			toLogin() {
				uni.navigateTo({
					url:'/pages/login'
				})
			}
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
		font-weight: bold;
	}
</style>