<template>
	<view class="main">
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="header-background-text">
				<text>资讯详情</text>
			</view>
		</view>
		
		<view class="detail">
			<view v-html="content"></view>
		</view>
	</view>
</template>

<script>
	export default {
		data(){
			return {
				content:'',
			}
		},
		onLoad(options){
			this.$api.getnewsDetail(options.id).then((res)=>{
				this.content = res.data.newsDetail
			})
		},
		methods:{
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
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
	.detail {
		width: 600rpx;
		border-radius: 10px;
		background-color: white;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
		margin-top: 220rpx;
		padding: 50rpx 50rpx 50rpx 50rpx;
	}
</style>