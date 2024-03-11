<template>
	<view>
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="header-background-text">
				<text>登录</text>
			</view>
		</view>
		<!-- 登录页面 -->
		<view class="login" v-if="loginShow">
			<!-- 账号登录 -->
			<view v-show="wayShow">
				<view class="login-account">
					<view>
						<text>账号：</text>
					</view>
					<view>
						<input class="uni-input" focus type="text" focus placeholder="请输入账号" />
					</view>
				</view>
				
				<view class="login-password">
					<view>
						<text>密码：</text>
					</view>
					<view>
						<input class="uni-input" type="text" placeholder="请输入密码" />
					</view>
				</view>
			</view>
			
			<!-- 短信登录 -->
			<view v-if="!wayShow">
				<view class="login-account">
					<view>
						<text>手机号：</text>
					</view>
					<view>
						<input class="uni-input" focus type="number" placeholder="请输入手机号" />
					</view>
				</view>

				<view class="idCode">
					<view>
						<text>验证码：</text>
					</view>
					<view>
						<input class="uni-input" placeholder="请输入验证码" />
					</view>
					<view>
						<button>发送</button>
					</view>
				</view>
			</view>
			<!-- 登录按钮 -->
			<view class="button-login">
				<button>登录</button>
			</view>
			
			<!-- 注册按钮 -->
			<view class="button-register" @click="changePage">
				<button>注册</button>
			</view>
			
			<!-- 切换登录方式 -->
			<view class="login-change" @click="changeLoginWay">
				<view>
					<text>其他登录方式</text>
				</view>
				<view>
					<image src="../static/uni.png"></image>
				</view>
			</view>
		</view>
		
		<!-- 注册页面 -->
		<view  class="login" v-if="!loginShow">
			<!-- 短信登录 -->
			<view>
				<view class="login-account">
					<view>
						<text>账号：</text>
					</view>
					<view>
						<input class="uni-input" focus type="text" focus placeholder="请输入账号" />
					</view>
				</view>
				<view class="login-account">
					<view>
						<text>手机号：</text>
					</view>
					<view>
						<input class="uni-input" type="number" placeholder="请输入手机号" />
					</view>
				</view>
				
				<!-- 输入密码 -->
				<view class="register-password">
					<view>
						<text>密码：</text>
					</view>
					<view>
						<input class="uni-input" password placeholder="请输入密码" />
					</view>
				</view>
				<!-- 确认密码 -->
				<view class="register-password">
					<view id="register-password-confirm">
						<text>确认密码：</text>
					</view>
					<view>
						<input class="uni-input" password placeholder="请再次输入密码" />
					</view>
				</view>
				<!-- 输入验证码 -->
				<view class="idCode">
					<view>
						<text>验证码：</text>
					</view>
					<view>
						<input class="uni-input" placeholder="请输入验证码" />
					</view>
					<view>
						<button>发送</button>
					</view>
				</view>
			</view>
			<!-- 登录按钮 -->
			<view class="button-login">
				<button>确认注册</button>
			</view>
			
			<!-- 注册按钮 -->
			<view class="button-register" @click="changePage">
				<button>取消注册</button>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				// 显示登录或注册页面,默认true登录
				loginShow:true,
				
				// 显示账号登录或手机登录,默认true账号登录
				wayShow:true,
			}
		},
		methods: {
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
			},
			// 切换登录注册页面
			changePage() {
				this.loginShow = !this.loginShow
			},
			// 切换登录方式
			changeLoginWay() {
				this.wayShow = !this.wayShow
			},
		},
	}
</script>

<style>
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
		margin-right: 195rpx;
	}
	.back-image image {
		width: 50rpx;
		height: 50rpx;
	}
	.header-background-text {
		order: 1;
		
		display: flex;
		justify-content: center;
		flex-basis: 120rpx;
		font-size: 38rpx;
		color: white;
	}
	.login {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 750rpx;
		padding: 50rpx 0 100rpx 0; 
	}
	.login-account {
		display: flex;
		width: 500rpx;
		height: 130rpx;
		align-items: flex-end;
		border-bottom: 1px solid rgb(200, 200, 200);
		padding-bottom: 20rpx;
		margin-bottom: 0rpx;
	}
	.login-account text {
		margin-right: 20rpx;
		font-size: 30rpx;
	}
	.login-account input {
		font-size: 30rpx;
	}
	.login-password {
		display: flex;
		width: 500rpx;
		height: 130rpx;
		align-items: flex-end;
		border-bottom: 1px solid rgb(200, 200, 200);
		padding-bottom: 20rpx;
		margin-bottom: 80rpx;
	}
	.login-password text {
		margin-right: 20rpx;
		font-size: 30rpx;
	}
	.login-password input {
		font-size: 30rpx;
	}
	.button-login {
		width: 500rpx;
		box-shadow: 1px 1px 10px rgb(200, 200, 200);
		margin-bottom: 30rpx;
	}
	.button-login button {
		background-color: #ffb84d;
	}
	.button-register {
		width: 500rpx;
		box-shadow: 1px 1px 10px rgb(200, 200, 200);
	}
	.login-change {
		display: flex;
		flex-direction: row;
		width: 500rpx;
		margin-top: 50rpx;
		margin-left: 550rpx;
	}
	.login-change text {
		width: 200rpx;
		color: orange;
	}
	.login-change image {
		width: 35rpx;
		height: 35rpx;
		margin-left: 20rpx;
	}
	.register-password {
		display: flex;
		width: 500rpx;
		height: 130rpx;
		align-items: flex-end;
		border-bottom: 1px solid rgb(200, 200, 200);
		padding-bottom: 20rpx;
	}
	.register-password text {
		margin-right: 50rpx;
		font-size: 30rpx;
	}
	.register-password input {
		font-size: 30rpx;
	}
	.idCode {
		display: flex;
		width: 500rpx;
		height: 130rpx;
		align-items: flex-end;
		border-bottom: 1px solid rgb(200, 200, 200);
		padding-bottom: 20rpx;
		margin-bottom: 80rpx;
	}
	.idCode text {
		margin-right: 20rpx;
		font-size: 30rpx;
	}
	.idCode input {
		width: 250rpx;
		font-size: 30rpx;
	}
	.idCode button {
		width: 110rpx;
		height: 60rpx;
		font-size: 25rpx;
	}
	#register-password-confirm {
		width: 160rpx;
	}
	#register-password-confirm text{
		margin-right: 0;
	}
</style>