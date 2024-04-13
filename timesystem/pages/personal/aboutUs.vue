<template>
	<view class="main">
		<page-meta :root-font-size="size"></page-meta>
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="header-background-text">
				<text>关于我们</text>
			</view>
		</view>
		
		<view class="body">
			<text style="font-size: 30rpx;line-height: 50rpx;">
				⚫ 黄英男，男，人工智能专业本科生，擅长前端和后端多项开发技术，参与国家级创训一项。 
				⚫ 屠嘉和，男，人工智能专业本科生，熟练使用java后端开发，参加省级创训一项，曾获ican大学生创新创业大赛江浙赛区二等奖。 
				⚫ 孙锦鹏，男，人工智能专业本科生，熟练使用vue2、axios、echarts、uniapp 等前端技术。 
				⚫ 李宇童，男，智能科学与技术专业本科生，擅长前端开发技术，主持校级创训一项。 
				⚫ 徐赫，男，通信工程专业本科生，擅长PPT制作与文稿书写。
			</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
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
			}
		},
	}
</script>

<style>
	.main {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		width: 750rpx;
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
		margin-right: 140rpx;
	}
	.back-image image {
		width: 50rpx;
		height: 50rpx;
	}
	.header-background-text {
		order: 1;
		
		display: flex;
		justify-content: center;
		flex-basis: 250rpx;
		font-size: 38rpx;
		color: white;
	}
	.body {
		padding: 20rpx 20rpx 20rpx 20rpx;
		margin: 250rpx 0 100rpx 0;
		background-color: white;
		border-radius: 15px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
		width: 650rpx;
	}
</style>