<template>
	<view>
		<page-meta :root-font-size="size"></page-meta>
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!--搜索框-->
			<view class="search-area">
				<view class="search-bar">
					<image src="/static/search/search.png"></image>
					<view class="search-input">
						<input v-model="searchContent"></input>
					</view>
					<image src="/static/search/cancel.png" @click="back"></image>
				</view>
				<view class="search-button" >
					<text @click="search">搜索</text>
				</view>
			</view>
		</view>
		
		<!-- 指定搜索内容 -->
		<view class="content">
			<view class="content-title">
				<text>指定搜索内容</text>
			</view>
			
			<view class="content-body">
				<view class="content-button">
					<text v-if="searchChoose!=0" class="content-button" @click="chooseSearch(0)">全部</text>
					<view v-if="searchChoose==0" class="content-button-select" >
						<text @click="chooseSearch(0)">全部</text>
					</view>
				</view>
				<text>   |  </text>
				<view class="content-button">
					<text v-if="searchChoose!=1" class="content-button" @click="chooseSearch(1)">发布者</text>
					<view v-if="searchChoose==1" class="content-button-select" >
						<text @click="chooseSearch(1)">发布者</text>
					</view>
				</view>
				<text>  |  </text>
				<view class="content-button">
					<text v-if="searchChoose!=2" class="content-button" @click="chooseSearch(2)">标题</text>
					<view v-if="searchChoose==2" class="content-button-select" >
						<text @click="chooseSearch(2)">标题</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				searchContent:'',
				searchChoose:0,
				size:'',
			}
		},
		created() {
			this.size = uni.getStorageSync("size")
		},
		methods: {
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
			},
			chooseSearch(searchChoose) {
				this.searchChoose = searchChoose
			},
			search() {
				uni.navigateTo({
					url:'/pages/index/searchList?'+'searchChoose='+this.searchChoose+'&searchContent='+this.searchContent
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
		height: 80rpx;
		align-items: center;
	}
	.search-bar  {
		display: flex;
		width: 550rpx;
		height: 70rpx;
		align-items: center;
		background-color: #cccccc;
		border-radius: 60px;
		padding: 0 12px 0 12px ;
		box-sizing: border-box;
		margin-left: 30rpx;
	}
	.search-bar image {
		width: 50rpx;
		height: 50rpx;
		margin-top: 8rpx;
	}
	.search-input {
		display: flex;
		width: 400rpx;
		height: 50rpx;
		color: white;
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
		align-items: center;
		height: 100rpx;
	}
	.content-title text {
		display: flex;
		justify-content: center;
		color: rgba(0, 0, 0, 0.3);
		font-size: 25rpx;
	}
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
		margin-top: 200rpx;
	}
	.content-body {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-left: 40rpx;
		margin-right: 40rpx;
	}
	.content-button {
		display: flex;
		align-items: center;
		margin-left: 20rpx;
		margin-right: 20rpx;
	}
	.content-button text {
		color: rgba(0, 0, 0, 1.0);
		font-size: 28rpx;
	}
	.content-button-select {
		display: flex;
		align-items: center;
		margin-left: 20rpx;
		margin-right: 20rpx;
	}
	.content-button-select text{
		color: #80aaff;
	}
</style>