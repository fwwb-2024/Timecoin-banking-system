<template>
	<view>
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 志愿者 -->
			<view class="header-background-text">
				<text v-if="!position">我的发布</text>
				<text v-if="position">我的接取</text>
			</view>
		</view>
				
		<!-- 任务主体页面 -->
		<!-- 志愿者 -->
		<view v-show="position">
			<!-- 任务列表 -->
			<view class="mission-list" v-for="(item,index) in missionDataList" :key="item.id">
				<view class="mission-list-element" @click="navTo('/pages/mymission/nowmission?id='+item.taskID)">
					<mission :missionData="item"></mission>
				</view>
			</view>
		</view>
		
		<!-- 发布者 -->
		<view v-show="!position">
			<!-- 任务列表 -->
			<view class="mission-list" v-for="(item,index) in missionDataList" :key="item.id">
				<view class="mission-list-element" @click="navTo('/pages/mymission/changemission?id='+item.taskID)">
					<mission :missionData="item"></mission>
				</view>
			</view>
		</view>
		
		<view class="newMission">
			<button @click="navTo('/pages/mymission/newMission')" v-show="!position">发布任务</button>
		</view>
	</view>
</template>

<script>
	import mission from '@/components/mission.vue'
	export default {
		data() {
			return {
				//身份转换的组件显示,true为志愿者
				position:true,
				// 渲染的任务列表数据
				missionDataList:[],
				// 加载的任务页数
				pages:0
			}
		},
		created: function() {
			// 获取身份并对页面展示选择渲染
			const value = uni.getStorageSync('position');
			if (value == '志愿者') {
				this.position = true
			}
			else if (value == '发布者') {
				this.position = false
			}
			
			this.reload()
		},
		// 页面下拉事件
		onPullDownRefresh(){
			uni.reLaunch({url:'/pages/mymission/myMission'})
		},
		// 页面上拉到顶事件
		onReachBottom(){
			this.pages+=10
			this.reload()
			if(this.missionDataList.length <= this.pages) {
				this.pages-=10
				uni.showToast({
					title:'已经没有了~',
					icon: "none",
					duration:1000
				})
			}
		},
		methods: {
			reload() {
				// 如果是发布者
				if(!this.position) {
					// 加载当前任务列表
					this.$api.getTaskNow_2(uni.getStorageSync('userID'),this.pages).then((res)=>{
						const length = res.data.length
						for(let i=0;i<length;i++) {
							let dotShow = false
							if(res.data[i].taskStatusRemark !== null){
								dotShow = true
							}
							this.missionDataList.push({
								taskID: res.data[i].taskID,
								title: res.data[i].taskName,
								introduction: res.data[i].taskBrief,
								timeCoins: res.data[i].taskTimeCoinBounty,
								publisher:{
									username: res.data[i].taskEmployerName,
									headpicture: res.data[i].userPhoto,
								},
								endTime: res.data[i].taskEndTime,
								status: res.data[i].taskStatus,
								statusShow:true,
								dotShow:dotShow,
							})
						}
					})
				}
				// 如果是志愿者
				else {
					// 加载当前任务列表
					this.$api.getTaskNow_1(uni.getStorageSync('userID'),this.pages).then((res)=>{
						const length = res.data.length
						for(let i=0;i<length;i++) {
							let dotShow = false
							if(res.data[i].taskStatusRemark !== null){
								dotShow = true
							}
							this.missionDataList.push({
								taskID: res.data[i].taskID,
								title: res.data[i].taskName,
								introduction: res.data[i].taskBrief,
								timeCoins: res.data[i].taskTimeCoinBounty,
								publisher:{
									username: res.data[i].taskEmployerName,
									headpicture: res.data[i].userPhoto,
								},
								endTime: res.data[i].taskEndTime,
								status: res.data[i].taskStatus,
								statusShow:true,
								dotShow:dotShow,
							})
						}
					})
				}
			},
			navTo(url) {
				uni.navigateTo({
					url,
				})
			}
		},
		components: {
			mission,
		},
	}
</script>

<style>
	.header-background {
		display: flex;
		justify-content: center;
		align-items: center;
		width: 750rpx;
	}
	.header-background-text {
		order: 1;
		
		display: flex;
		justify-content: center;
		flex-basis: 160rpx;
		font-size: 38rpx;
		color: white;
	}
	.mission-list {
		background-color: white;
	}
	.mission-list-element {
		box-shadow: 1px 1px 1px rgb(200, 200, 200);
	}
	.newMission {
		position: fixed;
		bottom: 99rpx;
		width: 100%;
		box-shadow: 1px 1px 1px rgb(200, 200, 200);
	}
</style>