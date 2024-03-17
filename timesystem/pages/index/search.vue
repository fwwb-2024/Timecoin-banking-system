<template>
	<view>
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!--搜索框-->
			<view class="search-area">
				<view class="search-bar">
					<image src="/static/search/search.png" @click=""></image>
					<input class="search-input" @input="handleSearch">
					<image src="/static/search/cancel.png" @click=""></image>
				</view>
				<view class="search-button" >
					<text @click="back">取消</text>
				</view>
			</view>
		</view>
		
		<!-- 指定搜索内容 -->
		<view class="content-title">
			<text>指定搜索内容</text>
		</view>
		<view class="content">
			<view class="content-button">
				<text class="content-button">全部</text>
			</view>
			<text>   |  </text>
			<view class="content-button">
				<text class="content-button">发布者</text>
			</view>
			<text>  |  </text>
			<view class="content-button">
				<text class="content-button">标题</text>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
			}
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
	.search-area {
		display: flex;
		margin: 0 auto;
		height: 80rpx;
	}
	.search-bar  {
		display: flex;
		width: 550rpx;
		height: 60rpx;
		background-color: rgba(227, 116, 73, 1);
		border-radius: 60rpx; 
		padding: 8px 12px; 
		box-sizing: border-box; 
		margin-left: 30rpx;
	}
	.search-bar image {
		width: 50rpx;
		height: 50rpx;
		margin-top: -10rpx;
	}
	.search-input {
		display: flex;
		width: 400rpx;
	}
	.search-button {
		display: flex;
		align-items: center;
		margin-left: 30rpx;
		width: 200rpx;
		height: 60rpx;	
	}
	.search-button text {
		display: flex;
		align-items: center;
		color: rgba(255, 255, 255, 1);
	}
	.content-title {
		display: flex;
		justify-content: center;
		margin: 30rpx;
	}
	.content-title text {
		display: flex;
		justify-content: center;
		color: rgba(0, 0, 0, 0.3);
		font-size: 25rpx;
		margin: 50rpx;
	}
	.content {
		display: flex;
		align-items: center;
		justify-content: center;
	}
	.content-button {
		display: flex;
		align-items: center;
		margin-left: 40rpx;
		margin-right: 40rpx;
	}
	.content-button text {
		color: rgba(0, 0, 0, 1.0);
		font-size: 25rpx;
	}
</style>