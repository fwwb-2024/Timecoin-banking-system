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
			<view class="accountdata-headpic line" @click="uploadHeadpic">
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
					<text>{{userName}}</text>
				</view>
			</view>
			<!-- 地址 -->
			<view class="accountdata-account line">
				<view class="accountdata-account-front">
					<text>邮箱</text>
				</view>
				<view class="accountdata-address-after">
					<text>{{userEmail}}</text>
				</view>
			</view>
			<!-- 手机号 -->
			<view class="accountdata-account line">
				<view class="accountdata-account-front">
					<text>手机号</text>
				</view>
				<view class="accountdata-phonenumber-after">
					<text>{{phoneNumber}}</text>
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
			<!-- 切换账号 -->
			<button style="width: 100%;" @click="navTo('/pages/login')">切换账号</button>
		</view>
	</view>
</template>

<script>
import http from '../../api/request';
	export default {
		data() {
			return {
				// 头像图片
				headerImage:'/static/headpic.jpg',
				userName:'',
				userEmail:'',
				phoneNumber:'',
			}
		},
		created:function(){
			this.$api.getUserData(uni.getStorageSync('userName')).then((res)=>{
				this.headerImage = res.data.userPhoto
				this.userName = res.data.userName
				this.userEmail = res.data.userEmail
				this.phoneNumber = res.data.userPhoneNumber
			})
		},
		methods: {
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
			},
			navTo(url){
				uni.navigateTo({
					url,
				})
			},
			uploadHeadpic(){
				// 选择图片并且上传
				uni.chooseImage({
					count: 1,
					sizeType: ['original'],
					sourceType: ['album','camera'], 
					success: (chooseImageRes)=>{
						const tempFilePaths = chooseImageRes.tempFilePaths[0];
						this.headerImage = tempFilePaths
						const file = new File([tempFilePaths], 'image.jpg');
						// 上传头像图片
						uni.uploadFile({
						  url: 'http://10.195.28.44:9090/user/userCenter/changeUserPhoto', 
						  filePath: tempFilePaths,
						  name: 'file', 
						  formData: {
						    userID: uni.getStorageSync('userID'),
						  },
						  header: {
							  'Authorization': uni.getStorageSync('token')
						  },
						  success: (uploadRes) => {
							  // token正确则更新token
							  if(uploadRes.statusCode == 200) {
							  	if(uploadRes.data.token){
							  		uni.setStorage({
							  			key: 'token',
							  			data: uploadRes.data.token,
							  		});
							  	}
							  }
							  // 否则重新登陆
							  else if(uploadRes.statusCode == 201) {
							  	uni.showToast({
							  		title: '登录已过期',
							  		icon:'error',
							  		duration: 1000
							  	});
							  	setTimeout(function() {
							  	    uni.reLaunch({
							  	    	url:'/pages/login'
							  	    })
							  	}, 1000);
							  }
							  else {
							  	uni.showToast({
							  		title: '未登录',
							  		icon:'error',
							  		duration: 1000
							  	});
							  	setTimeout(function() {
							  	    uni.reLaunch({
							  	    	url:'/pages/login'
							  	    })
							  	}, 1000);
							  }
						  },
						});
					}
				})
			},
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
		-webkit-line-clamp:1;	//设置行数1行
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