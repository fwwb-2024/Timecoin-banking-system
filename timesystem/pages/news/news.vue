<template>
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
	</view>
</template>

<script>
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
</style>