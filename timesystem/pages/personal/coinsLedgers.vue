<template>
	<view class="main">
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="header-background-text">
				<text>时间币账单</text>
			</view>
		</view>
		
		<view class="ledger">
			<view class="ledger-element" v-for="(item,index) in ledgers" v-if="index!=ledgers.length-1">
				<view class="ledger-element-name">
					<text>{{item.taskName}}</text>
				</view>
				<view class="ledger-element-ledgerTimeCoin">
					<text>{{item.ledgerTimeCoin}}</text>
				</view>
				<view class="ledger-element-brief">
					<text>{{item.taskBrief}}</text>
				</view>
			</view>
			<view class="ledger-element-end" v-if="endShow">
				<view class="ledger-element-name">
					<text>{{this.ledgers[this.ledgers.length-1].taskName}}</text>
				</view>
				<view class="ledger-element-ledgerTimeCoin">
					<text>{{this.ledgers[this.ledgers.length-1].ledgerTimeCoin}}</text>
				</view>
				<view class="ledger-element-brief">
					<text>{{this.ledgers[this.ledgers.length-1].taskBrief}}</text>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data(){
			return {
				ledgers:[],
				// 显示最后一个元素
				endShow:false,
				pages:0,
			}
		},
		created() {
			this.reload()
		},
		methods:{
			reload(){
				this.$api.getLedgers(uni.getStorageSync('userID'),this.pages).then((res)=>{
					for(let i=0;i<res.data.length;i++){
						this.ledgers.push({taskName:res.data[i].taskName,taskBrief:res.data[i].taskBrief,ledgerTimeCoin:res.data[i].ledgerTimeCoin})
					}
					this.endShow=true
				})
			},
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
			},
			// 页面下拉事件
			onPullDownRefresh(){
				uni.redirectTo({url:'/pages/personal/coinsLedgers'})
			},
			// 页面上拉到顶事件
			onReachBottom(){
				this.pages+=10
				this.reload()
				if(this.ledgers.length <= this.pages) {
					this.pages-=10
					uni.showToast({
						title:'已经没有了~',
						icon: "none",
						duration:1000
					})
				}
			},
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
		margin-right: 120rpx;
	}
	.back-image image {
		width: 50rpx;
		height: 50rpx;
	}
	.header-background-text {
		order: 1;
		
		display: flex;
		justify-content: center;
		flex-basis: 280rpx;
		font-size: 38rpx;
		color: white;
	}
	.ledger {
		width: 620rpx;
		border-radius: 10px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
		margin-top: 210rpx;
		background-color: white;
		padding: 40rpx 40rpx 10rpx 40rpx;
		margin-bottom: 30rpx;
	}
	.ledger-element {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		padding-bottom: 20rpx;
		border-bottom: #d9d9d9 solid 1px;
		margin-bottom: 20rpx;
	}
	.ledger-element-end {
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		padding-bottom: 20rpx;
		margin-bottom: 20rpx;
	}
	.ledger-element-name {
		width: 30%;
		font-size: 38rpx;
	}
	.ledger-element-ledgerTimeCoin {
		width: 70%;
		display: flex;
		justify-content: flex-end;
		font-size: 35rpx;
		color: black;
	}
	.ledger-element-brief {
		width: 80%;
		margin-top: 20rpx;
		font-size: 32rpx;
		color: gray;
		overflow:hidden;
		text-overflow:ellipsis;
		display:-webkit-box;
		-webkit-box-orient:vertical;
		-webkit-line-clamp:1;	//设置行数1行
	}
</style>