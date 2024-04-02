<template>
	<view class="body">
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 头像图片 -->
			<view class="header-image" @click="navTo('/pages/personal/accountData')">
				<image :src="headerImage"></image>
			</view>
			<!-- 用户名 -->
			<view class="head-text" @click="navTo('/pages/personal/accountData')">
				<text>{{username}}</text>
			</view>
		</view>
		
		<!-- 个人中心功能 -->
		<view class="peronal-function">
			<!-- 账号信息 -->
			<view class="function-part line" @click="navTo('/pages/personal/accountData')">
				<view>
					<image src="/static/personal/accountData.png"></image>
				</view>
				<view>
					<text>账号信息</text>
				</view>
			</view>
			
			
			<!-- 时间币管理 -->
			<view class="function-part line" @click="navTo('/pages/personal/timeCoinData')">
				<view>
					<image src="/static/personal/timeCoinData.png"></image>
				</view>
				<view>
					<text>时间币管理</text>
				</view>
			</view>
			
			<!-- 家庭管理 -->
			<view class="function-part line" @click="navTo('/pages/personal/familyList')">
				<view>
					<image src="/static/personal/familyList.png"></image>
				</view>
				<view>
					<text>家庭管理</text>
				</view>
			</view>
			
			<!-- 任务历史 -->
			<view class="function-part line" @click="navTo('/pages/mymission/historyTask')">
				<view>
					<image src="/static/history.png"></image>
				</view>
				<view>
					<text>任务历史</text>
				</view>
			</view>
			
			<!-- 切换身份 -->
			<view class="function-part" @click="changePosition">
				<view>
					<image src="/static/personal/changePosition.png"></image>
				</view>
				<view>
					<text>切换身份</text>
				</view>
				<view id="now-persoanl-status">
					<text style="color: green;" v-if="nowPersonlStatus=='志愿者'">当前身份: 志愿者</text>
					<text style="color: orange;" v-if="nowPersonlStatus=='发布者'">当前身份: 发布者</text>
				</view>
			</view>
		</view>
		
		<!-- 关于开发者信息 -->
		<view class="about">
			<view class="about-us" @click="navTo('/pages/personal/aboutUs')">
				<view>
					<image src="/static/personal/aboutUs.png"></image>
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
		created() {
			this.$api.getUserData(uni.getStorageSync('userName')).then((res)=>{
				this.headerImage = res.data.userPhoto
				this.username = res.data.userName
			})
			
			const value = uni.getStorageSync('position');
			if (value) {
				this.nowPersonlStatus = value;
			}
		},
		methods: {
			// 页面跳转
			navTo(url){
				uni.navigateTo({
					url,
				})
			},
			// 改变身份
			changePosition(){
				let temp = '志愿者';
				if(this.nowPersonlStatus == '志愿者'){
					temp = '发布者'
				}
				uni.setStorage({
					key: 'position',
					data: temp,
					success: function () {
						this.nowPersonlStatus = temp;
						uni.reLaunch({
						    url: '/pages/personal/personal'
						})
					}
				});
			}
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
		margin-top: 250rpx;
		border-radius: 10px;
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
		border-radius: 10px;
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