<template>
	<view class="main">
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="header-background-text">
				<text>家庭管理</text>
			</view>
		</view>
		
		<!-- 家庭管理 -->
		<view class="familyList">
			<view v-for="(item,index) in familyList" :key="item.id">
				<view class="familyList-element" @click="navTo('/pages/personal/familyDetail?familyID='+item.familyID)">
					<view class="familyList-element-title">
						<text>家庭名称:</text>
					</view>
					<view class="familyList-element-familyName">
						<text>{{item.familyName}}</text>
					</view>
					<view class="familyList-element-image">
						<image src="/static/imbackpic.png"></image>
					</view>
				</view>
			</view>
			
		</view>
		<button style="width: 90%; margin-top: 30rpx;box-shadow: 2px 4px 20px rgb(200, 200, 200);" @click="newFamily">新建家庭</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				familyList:[],
				reload:true
			}
		},
		created:function (){
			this.$api.getFamilyList(uni.getStorageSync('userID')).then((res)=>{
				for(let i=0;i<res.data.length;i++){
					this.familyList.push({familyName:res.data[i].familyName,familyID:res.data[i].familyID})
				}
			})
		},
		methods: {
			//返回上一级页面
			back() {
				uni.reLaunch({
					url:'/pages/personal/personal'
				})
			},
			// 跳转
			navTo(url){
				uni.navigateTo({
					url,
				})
			},
			// 新建家庭
			newFamily(){
				let that = this
				uni.showModal({
					title:'请输入家庭名称',
					editable: true,
					success: function (response) {
						// 用户点击确定按钮
					    if (response.confirm) {
							let temp = {
								houseHolder:uni.getStorageSync('userID'),
								familyName:response.content,
								houseHolderName:uni.getStorageSync('userName')
							}
							that.$api.newFamily(temp).then((res)=>{
								if(res.data){
									uni.showToast({
										title:'创建家庭成功',
										duration:1000
									})
									setTimeout(function() {
										uni.reLaunch({
											url:'/pages/personal/familyList'
										})
									},1000)
								}
								else{
									uni.showToast({
										title:'创建家庭失败',
										icon:'error',
										duration:1000
									})
								}
							})
					    }
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
	.familyList {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 700rpx;
		margin-top: 30rpx;
		padding: 50rpx 0 20rpx 0;
		background-color: white;
		border-radius: 10px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
	}
	.familyList-element {
		display: flex;
		width: 690rpx;
		flex-direction: row;
		justify-content: flex-start;
		align-items: center;
		margin-bottom: 40rpx;
	}
	.familyList-element-title {
		font-size: 40rpx;
		width: 250rpx;
		margin:0 20rpx 0 60rpx;
	}
	.familyList-element-familyName {
		width: 400rpx;
		font-size: 35rpx;
		margin-right: 50rpx;
		overflow:hidden;
		text-overflow:ellipsis;
		display:-webkit-box;
		-webkit-box-orient:vertical;
		-webkit-line-clamp:1;	//设置行数1行
		
	}
	.familyList-element-image {
		margin-right: 0;
	}
	.familyList-element-image image{
		width: 50rpx;
		height: 50rpx;
	}
</style>