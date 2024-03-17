<template>
	<view>
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 志愿者 -->
			<view class="header-select" v-show="position">
				<!-- 未完成 -->
				<view class="header-select-front" @click="function(){if(!headOption){headOption=!headOption}}">
					<view>
						<image src="/static/myMission/selectpublishing.png" v-if="headOption"></image>
						<image src="/static/myMission/publishing.png" v-if="!headOption"></image>
					</view>
					<view>
						<text>未完成</text>
					</view>
				</view>
				<!-- 历史记录 -->
				<view class="header-select-after" @click="function(){if(headOption){headOption=!headOption}}">
					<view>
						<image src="/static/myMission/selectpublishhistory.png" v-if="!headOption"></image>
						<image src="/static/myMission/publishhistory.png" v-if="headOption"></image>
					</view>
					<view>
						<text>历史记录</text>
					</view>
				</view>
			</view>
			
			
			<!-- 发布者 -->
			<view class="header-select" v-show="!position">
				<!-- 已发布 -->
				<view class="header-select-front" @click="function(){if(!headOption){headOption=!headOption}}">
					<view>
						<image src="/static/myMission/selectpublishing.png" v-if="headOption"></image>
						<image src="/static/myMission/publishing.png" v-if="!headOption"></image>
					</view>
					<view>
						<text>已发布</text>
					</view>
				</view>
				<!-- 任务历史 -->
				<view class="header-select-after" @click="function(){if(headOption){headOption=!headOption}}">
					<view>
						<image src="/static/myMission/selectpublishhistory.png" v-if="!headOption"></image>
						<image src="/static/myMission/publishhistory.png" v-if="headOption"></image>
					</view>
					<view>
						<text>任务历史</text>
					</view>
				</view>
			</view>
		</view>
		<image @click="navTo('/pages/mymission/newMission')" src="@/static/add.png" style="position: fixed; width: 50rpx;height: 50rpx; left: 325rpx;top: 80rpx;" v-show="!position"></image>
		<!-- 任务主体页面 -->
		<!-- 志愿者 -->
		<view v-show="position">
			<!-- 任务列表 -->
			<view v-if="headOption">
				<view class="mission-list" v-for="(item,index) in missionDataList" :key="item.id">
					<view class="mission-list-element" @click="navTo('/pages/missionDetail?id='+item.taskID)">
						<mission :missionData="item"></mission>
					</view>
				</view>
			</view>
			
			<!-- 历史任务列表 -->
			<view v-if="!headOption">
				<view class="mission-list" v-for="(item,index) in historyMissionDataList" :key="item.id">
					<view class="mission-list-element" @click="navTo('/pages/missionDetail?id='+item.taskID)">
						<mission :missionData="item"></mission>
					</view>
				</view>
			</view>
		</view>
		
		
		<!-- 发布者 -->
		<view v-show="!position">
			<!-- 任务列表 -->
			<view v-if="headOption">
				<view class="mission-list" v-for="(item,index) in missionDataList" :key="item.id">
					<view class="mission-list-element" @click="navTo('/pages/missionDetail?id='+item.taskID)">
						<mission :missionData="item"></mission>
					</view>
				</view>
			</view>
			
			<!-- 历史任务列表 -->
			<view v-if="!headOption">
				<view class="mission-list" v-for="(item,index) in historyMissionDataList" :key="item.id">
					<view class="mission-list-element" @click="navTo('/pages/missionDetail?id='+item.taskID)">
						<mission :missionData="item"></mission>
					</view>
				</view>
			</view>
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
				//顶部选项栏的显示，true为显示第一个
				headOption:true,
				// 渲染的任务列表数据
				missionDataList:[],
				// 渲染的历史任务列表数据
				historyMissionDataList:[],
			}
		},
		created: function() {
			// 获取身份并对页面展示选择渲染
			try {
				const value = uni.getStorageSync('position');
				if (value == '志愿者') {
					this.position = true
				}
				else if (value == '发布者') {
					this.position = false
				}
			} catch (e) {
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
			
			this.reload()
		},
		methods: {
			reload() {
				// 如果是发布者
				if(!this.position) {
					// 加载当前任务列表
					this.$api.getTaskNow_2(uni.getStorageSync('userID'),0).then((res)=>{
						console.log(res);
						const length = res.data.length
						let temp = {
							taskID:null,
							title:'',
							introduction:'',
							timeCoins:null,
							publisher:{
								username:'',
								headpicture:'',
							},
							endTime:'',
							statusShow:true,
							status:'',
						}
						for(let i=0;i<length;i++) {
							// temp.taskID = res.data[i].taskID
							temp.taskID = res.data[i].taskID
							temp.title = res.data[i].taskName
							temp.introduction = res.data[i].taskBrief
							temp.timeCoins = res.data[i].taskTimeCoinBounty
							temp.publisher.username = res.data[i].taskEmployer
							// publisher.headpicture = temp.
							temp.endTime = res.data[i].taskEndTime
							temp.status = res.data[i].taskStatus
							this.missionDataList.push(temp)
						}
					})
					// 加载历史任务列表
					this.$api.getTaskHistory_2(uni.getStorageSync('userID'),0).then((res)=>{
						const length = res.data.length
						let temp = {
							taskID:null,
							title:'',
							introduction:'',
							timeCoins:null,
							publisher:{
								username:'',
								headpicture:'',
							},
							endTime:'',
							statusShow:true,
							status:'',
						}
						for(let i=0;i<length;i++) {
							// temp.taskID = res.data[i].taskID
							temp.taskID = res.data[i].taskID
							temp.title = res.data[i].taskName
							temp.introduction = res.data[i].taskBrief
							temp.timeCoins = res.data[i].taskTimeCoinBounty
							temp.publisher.username = res.data[i].taskEmployer
							// publisher.headpicture = temp.
							temp.endTime = res.data[i].taskEndTime
							temp.status = res.data[i].taskStatus
							this.historyMissionDataList.push(temp)
						}
					})
				}
				// 如果是志愿者
				else {
					// 加载当前任务列表
					this.$api.getTaskNow_1(uni.getStorageSync('userID'),0).then((res)=>{
						const length = res.data.length
						let temp = {
							taskID:null,
							title:'',
							introduction:'',
							timeCoins:null,
							publisher:{
								username:'',
								headpicture:'',
							},
							endTime:'',
							statusShow:true,
							status:'',
						}
						for(let i=0;i<length;i++) {
							// temp.taskID = res.data[i].taskID
							temp.taskID = res.data[i].taskID
							temp.title = res.data[i].taskName
							temp.introduction = res.data[i].taskBrief
							temp.timeCoins = res.data[i].taskTimeCoinBounty
							temp.publisher.username = res.data[i].taskEmployer
							// publisher.headpicture = temp.
							temp.endTime = res.data[i].taskEndTime
							temp.status = res.data[i].taskStatus
							this.missionDataList.push(temp)
						}
					})
					// 加载历史任务列表
					this.$api.getTaskHistory_1(uni.getStorageSync('userID'),0).then((res)=>{
						const length = res.data.length
						let temp = {
							title:'',
							introduction:'',
							timeCoins:null,
							publisher:{
								username:'',
								headpicture:'',
							},
							endTime:'',
							statusShow:true,
							status:'',
						}
						for(let i=0;i<length;i++) {
							temp.title = res.data[i].taskName
							temp.introduction = res.data[i].taskBrief
							temp.timeCoins = res.data[i].taskTimeCoinBounty
							temp.publisher.username = res.data[i].taskEmployer
							// publisher.headpicture = temp.
							temp.endTime = res.data[i].taskEndTime
							temp.status = res.data[i].taskStatus
							this.historyMissionDataList.push(temp)
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
	.header-select {
		display: flex;
		width: 700rpx;
	}
	.header-select-front {
		display: flex;
		flex-direction: row;
		align-items: center;
		width: 300rpx;
		margin: 0 40rpx 0 40rpx;
	}
	.header-select-after {
		display: flex;
		flex-direction: row;
		align-items: center;
		width: 300rpx;
		margin:0 20rpx 0 40rpx ;
	}
	.header-select image {
		width: 65rpx;
		height: 65rpx;
	}
	.header-select  text{
		margin-left: 30rpx;
		font-size: 35rpx;
		color: white;
	}
	.mission-list {
		background-color: white;
	}
	.mission-list-element {
		box-shadow: 1px 1px 1px rgb(200, 200, 200);
	}
</style>