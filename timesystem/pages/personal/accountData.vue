<template>
	<view class="main">
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="header-background-text">
				<text>个人信息</text>
			</view>
		</view>
		
		<!-- 账号详情 -->
		<view class="accountdata">
			<!-- 头像 -->
			<view class="accountdata-headpic line">
				<view>
					<text>头像</text>
				</view>
				<view>
					<image :src="headerImage"></image>
				</view>
			</view>
			<!-- 账号 -->
			<view class="accountdata-account line">
				<view class="accountdata-account-front">
					<text>账号</text>
				</view>
				<view class="accountdata-account-after">
					<text>用户</text>
				</view>
			</view>
			<!-- 地址 -->
			<view class="accountdata-account line">
				<view class="accountdata-account-front">
					<text>地址</text>
				</view>
				<view class="accountdata-address-after">
					<text>河海大学佛城西路</text>
				</view>
			</view>
			<!-- 手机号 -->
			<view class="accountdata-account line">
				<view class="accountdata-account-front">
					<text>手机号</text>
				</view>
				<view class="accountdata-phonenumber-after">
					<text>13195630111</text>
				</view>
			</view>
			<!-- 实名认证 -->
			<view class="accountdata-account">
				<view class="accountdata-account-front">
					<text>实名认证</text>
				</view>
				<view class="accountdata-authentication-after">
					<text>已完成</text>
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
			}
		},
		methods: {
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
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
		flex-basis: 160rpx;
		font-size: 38rpx;
		color: white;
	}
	.accountdata {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 700rpx;
		margin-top: 30rpx;
		padding: 30rpx 0 0 0;
		background-color: white;
		border-radius: 10px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
	}
	.accountdata-headpic {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		align-items: center;
		width: 550rpx;
		height: 200rpx;
		padding: 0 0 0 10rpx;
	}
	.accountdata-headpic text {
		font-size: 38rpx;
		margin-right: 340rpx;
	}
	.accountdata-headpic image {
		width: 120rpx;
		height: 120rpx;
		border-radius: 50px;
	}
	.accountdata-account {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		align-items: center;
		width: 550rpx;
		height: 150rpx;
		padding: 0 0 0 10rpx;
	}
	.accountdata-account-front {
		font-size: 38rpx;
		margin-left: 0;
		width: 300rpx;
		margin-right: 100rpx;
	}
	.accountdata-account-after {
		display: flex;
		justify-content: flex-end;
		font-size: 35rpx;
		width: 250rpx;
		margin-right: 50rpx;
	}
	.accountdata-address-after {
		display: flex;
		justify-content: flex-end;
		font-size: 40rpx;
		width: 250rpx;
		margin-right: 50rpx;
		font-size: 35rpx;
		overflow:hidden;
		text-overflow:ellipsis;
		display:-webkit-box;
		-webkit-box-orient:vertical;
		-webkit-line-clamp:1;	//设置行数未2行
	}
	.accountdata-phonenumber-after {
		display: flex;
		justify-content: flex-end;
		font-size: 35rpx;
		width: 250rpx;
		margin-right: 50rpx;
		font-size: 35rpx;
		color: gray;
	}
	.accountdata-authentication-after {
		display: flex;
		justify-content: flex-end;
		font-size: 35rpx;
		width: 250rpx;
		margin-right: 50rpx;
		font-size: 35rpx;
		font-weight: bold;
		color: green;
	}
	.line {
		border-bottom: thin solid rgb(179, 179, 179);
	}
</style>