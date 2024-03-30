<template>
	<view class="main">
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 头像图片 -->
			<view class="header-image" @click="navTo('/pages/personal/accountData')">
				<image :src="headerImage"></image>
			</view>
			<!-- 用户名 -->
			<view class="head-text" @click="navTo('/pages/accountData')">
				<text>{{username}}</text>
			</view>
		</view>
		
		<view class="news-list">
			<view v-for="(item,index) in newlist" @click="navTo('/pages/news/newsDetail?id='+item.newsID)">
				<view class="news-list-element-time">{{item.newsTime}}</view>
				
				<view class="news-list-element">
					<view class="news-list-element-image">
						<image :src="item.newsPhoto"></image>
					</view>
					<view class="news-list-element-text">
						<text>{{item.newsTitle}}</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	export default {
		data(){
			return {
				headerImage:'',
				username:'',
				
				// 懒加载数据
				pages:0,
				
				newlist:[],
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
			
			this.reload()
		},
		methods:{
			navTo(url){
				uni.navigateTo({
					url,
				})
			},
			// 加载数据
			reload(){
				this.$api.getnews(this.pages).then((res)=>{
					for(let i=0;i<res.data.length;i++){
						this.newlist.push({newsID:res.data[i].newsID,newsTitle:res.data[i].newsTitle,newsPhoto:res.data[i].newsPhoto,newsTime:res.data[i].newsTime})
					}
				})
			},
			// 页面下拉事件
			onPullDownRefresh(){
				uni.reLaunch({url:'/pages/news/news'})
			},
			// 页面上拉到顶事件
			onReachBottom(){
				this.pages+=10
				this.reload()
				if(this.newlist.length <= this.pages) {
					this.pages-=10
					uni.showToast({
						title:'已经没有了~',
						icon: "none",
						duration:1000
					})
				}
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
	.news-list {
		margin-top: 210rpx;
	}
	.news-list-element {
		width: 700rpx;
		border-radius: 10px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
		margin-bottom: 30rpx;
	}
	.news-list-element-time {
		display: flex;
		flex-direction: row;
		justify-content: center;
		font-size: 30rpx;
		color: gray;
		margin: 20rpx 0 20rpx 0;
	}
	.news-list-element-image image {
		border-radius: 10px;
		width: 700rpx;
		height: 400rpx;
	}
	.news-list-element-text {
		display: flex;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		padding: 0 10rpx 0 30rpx;
		overflow:hidden;
		text-overflow:ellipsis;
		display:-webkit-box;
		-webkit-box-orient:vertical;
		-webkit-line-clamp:1;	//设置行数1行
	}
	.news-list-element-text text {
		font-size: 35rpx;
	}
</style>