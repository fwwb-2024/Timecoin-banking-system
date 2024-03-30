<template>
	<view>
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="header-background-text">
				<text v-if="!position">发布历史</text>
				<text v-if="position">接取历史</text>
			</view>
		</view>
		
		
		<view class="body">
			<!-- 任务主体页面 -->
			<!-- 志愿者 -->
			<view v-show="position">
				<!-- 历史任务列表 -->
				<view class="mission-list" v-for="(item,index) in historyMissionDataList" :key="item.id">
					<view class="mission-list-element" @click="navTo('/pages/mymission/historymission?id='+item.taskID)">
						<mission :missionData="item"></mission>
					</view>
				</view>
			</view>
			
			
			<!-- 发布者 -->
			<view v-show="!position">
				<!-- 历史任务列表 -->
				<view class="mission-list" v-for="(item,index) in historyMissionDataList" :key="item.id">
					<view class="mission-list-element" @click="navTo('/pages/mymission/historymission?id='+item.taskID)">
						<mission :missionData="item"></mission>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import mission from '@/components/mission.vue'
	export default{
		data(){
			return{
				// 判断身份
				position:true,
				// 渲染的历史任务列表数据
				historyMissionDataList:[],
				// 加载的任务页数
				pages:0
			}
		},
		created: function(){
			// 判断身份
			const value = uni.getStorageSync('position');
			if (value == '志愿者') {
				this.position = true
			}
			else if (value == '发布者') {
				this.position = false
			}
			// 加载数据
			this.reload()
		},
		methods:{
			// 返回上一级
			back(){
				uni.navigateBack({
					delta:1,
				})
			},
			// 页面下拉事件
			onPullDownRefresh(){
				uni.redirectTo({url:'/pages/mymission/historyTask'})
			},
			// 页面上拉到顶事件
			onReachBottom(){
				this.pages+=10
				this.reload()
				if(this.historyMissionDataList.length <= this.pages) {
					this.pages-=10
					uni.showToast({
						title:'已经没有了~',
						icon: "none",
						duration:1000
					})
				}
			},
			// 加载数据
			reload(){
				// 如果是发布者
				if(!this.position) {
					// 加载历史任务列表
					this.$api.getTaskHistory_2(uni.getStorageSync('userID'),this.pages).then((res)=>{
						const length = res.data.length
						for(let i=0;i<length;i++) {
							let dotShow = false
							if(res.data[i].taskStatusRemark !== null){
								dotShow = true
							}
							this.historyMissionDataList.push({
								taskID: res.data[i].taskID,
								title: res.data[i].taskName,
								introduction: res.data[i].taskBrief,
								timeCoins: res.data[i].taskTimeCoinBounty,
								publisher:{
									username: res.data[i].taskEmployerName,
									headpicture: res.data[i].userPhoto,
								},
								endTime: res.data[i].taskEndTime,
								status: res.data[i].taskHistoryStatus,
								statusShow:true,
								dotShow:dotShow,
							})
						}
					})
				}
				// 如果是志愿者
				else {
					// 加载历史任务列表
					this.$api.getTaskHistory_1(uni.getStorageSync('userID'),this.pages).then((res)=>{
						const length = res.data.length
						for(let i=0;i<length;i++) {
							let dotShow = false
							if(res.data[i].taskStatusRemark !== null){
								dotShow = true
							}
							this.historyMissionDataList.push({
								taskID: res.data[i].taskID,
								title: res.data[i].taskName,
								introduction: res.data[i].taskBrief,
								timeCoins: res.data[i].taskTimeCoinBounty,
								publisher:{
									username: res.data[i].taskEmployerName,
									headpicture: res.data[i].userPhoto,
								},
								endTime: res.data[i].taskEndTime,
								status: res.data[i].taskHistoryStatus,
								statusShow:true,
								dotShow:dotShow,
							})
						}
					})
				}
			},
			// 跳转
			navTo(url) {
				uni.navigateTo({
					url,
				})
			}
		},
		components:{
			mission,
		}
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
	
	.body {
		margin-top: 160rpx;
	}
	.mission-list-element {
		padding-top: 10rpx;
		width: 700rpx;
		background-color: white;
		margin-top: 30rpx;
		border-radius: 10px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
	}
</style>