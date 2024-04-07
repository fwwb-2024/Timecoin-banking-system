<template>
	<view class="main">
		<page-meta :root-font-size="size"></page-meta>
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
		
		<!-- 分类选择 -->
		<view class="lable">
			<view class="lable-first">
				<view class="lable-image" @click="navTo('/pages/news/missionList?lable=2')">
					<image src="/static/index/index1.png"></image>
					<text>跑腿</text>
				</view>
				<view class="lable-image" @click="navTo('/pages/news/missionList?lable=3')">
					<image src="/static/index/index2.png"></image>
					<text>代购</text>
				</view>
				<view class="lable-image" @click="navTo('/pages/news/missionList?lable=4')">
					<image src="/static/index/index3.png"></image>
					<text>打理</text>
				</view>
			</view>
			
			<view class="lable-second">
				<view class="lable-image" @click="navTo('/pages/news/missionList?lable=5')">
					<image src="/static/index/index4.png"></image>
					<text>陪伴</text>
				</view>
				<view class="lable-image" @click="navTo('/pages/news/missionList?lable=6')">
					<image src="/static/index/index5.png"></image>
					<text>线上</text>
				</view>
				<view class="lable-image" @click="navTo('/pages/news/missionList?lable=1')">
					<image src="/static/index/index6.png"></image>
					<text>其他</text>
				</view>
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
				headerImage:'/static/headpic.jpg',
				username:'登录/注册',
				
				// 懒加载数据
				pages:0,
				
				newlist:[],
				size:"",
			}
		},
		created() {
			this.size = uni.getStorageSync("size")
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
			// 页面跳转
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
		flex-basis: 250rpx;
		height: 60rpx;
	}
	.head-text text {
		align-self: flex-end;
		font-size: 35rpx;
		color: white;
	}
	.lable {
		width: 700rpx;
		height: 300rpx;
		display: flex;
		flex-direction: column;
		justify-content: center;
		background-color: white;
		margin: 240rpx 25rpx 20rpx 25rpx;
		border-radius: 10px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
	}
	.lable-first {
		width: 700rpx;
		height: 150rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
	}
	.lable-second {
		width: 700rpx;
		height: 150rpx;
		display: flex;
		flex-direction: row;
		align-items: center;
	}
	.lable-image {
		flex-grow: 1;
		width: 70rpx;
		height: 150rpx;
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}
	.lable-image image{
		width: 70rpx;
		height: 70rpx;
	}
	.news-list {
		width: 100%;
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	.news-list-element {
		width: 650rpx;
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
		width: 650rpx;
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