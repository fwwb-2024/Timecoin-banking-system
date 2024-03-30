<template>
	<view class="main">
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="header-background-text">
				<text>时间币管理</text>
			</view>
		</view>
		
		<!-- 时间币管理 -->
		<view class="timeCoin">
			<!-- 时间币管理 -->
			<view class="timeCoin-wallet">
				<view class="timeCoin-image">
					<image src="/static/personal/wallet.png"></image>
				</view>
				<view class="timeCoin-wallet-text">
					<text>时间币余额:{{userTimeCoin}}</text>
				</view>
			</view>
			<!-- 账单 -->
			<view class="timeCoin-bill">
				<view class="timeCoin-image" @click="navTo">
					<image src="/static/personal/bill.png"></image>
				</view>
				<view class="timeCoin-bill-text" @click="navTo">
					<text>账单</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 时间币余额
				userTimeCoin:null,
			}
		},
		created: function(){
			this.$api.getCoins(uni.getStorageSync('userName')).then((res)=>{
				this.userTimeCoin = res.data.userTimeCoin
			})
		},
		methods: {
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
			},
			// 跳转到账单
			navTo() {
				uni.navigateTo({
					url:'/pages/personal/coinsLedgers',
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
		margin-right: 155rpx;
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
	.timeCoin {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 700rpx;
		margin-top: 190rpx;
		padding: 50rpx 0 50rpx 0;
		border-radius: 10px;
		background-color: white;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
	}
	.timeCoin-image image{
		width: 100rpx;
		height: 100rpx;
	}
	.timeCoin-wallet {
		order: 0;
		
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 350rpx;
	}
	.timeCoin-wallet-text {
		margin-top: 30rpx;
		font-size: 35rpx;
		color: orange;
	}
	.timeCoin-bill {
		order: 1;
		
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 350rpx;
	}
	.timeCoin-bill-text {
		margin-top: 30rpx;
		font-size: 35rpx;
		margin-bottom: 10rpx;
	}
</style>