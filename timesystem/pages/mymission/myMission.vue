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
			
			
			<!-- 需求者 -->
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
		
		<!-- 任务主体页面 -->
		<!-- 志愿者 -->
		<view v-show="position">
			<!-- 任务列表 -->
			<view v-if="headOption">
				<view class="mission-list" v-for="(item,index) in missionDataList" :key="item.id">
					<view class="mission-list-element">
						<mission :missionData="item"></mission>
					</view>
				</view>
			</view>
			
			<!-- 历史任务列表 -->
			<view v-if="!headOption">
				<view class="mission-list" v-for="(item,index) in historyMissionDataList" :key="item.id">
					<view class="mission-list-element">
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
					<view class="mission-list-element">
						<mission :missionData="item"></mission>
					</view>
				</view>
			</view>
			
			<!-- 历史任务列表 -->
			<view v-if="!headOption">
				<view class="mission-list" v-for="(item,index) in historyMissionDataList" :key="item.id">
					<view class="mission-list-element">
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
				missionDataList:[
					//传给任务组件的值
					{
						title:'任务标题',
						introduction:'任务简介',
						timeCoins:100,
						publisher:{
							username:'官方发布者',
							headpicture:'/static/headpic.jpg',
						},
						endTime:'2024.3.20',
						statusShow:true,
						status:'未完成',
					},
				],
				// 渲染的历史任务列表数据
				historyMissionDataList:[
					//传给任务组件的值
					{
						title:'任务标题',
						introduction:'任务简介',
						timeCoins:100,
						publisher:{
							username:'官方发布者',
							headpicture:'/static/headpic.jpg',
						},
						endTime:'2024.3.20',
						statusShow:true,
						status:'已完成',
					},{
						title:'任务标题',
						introduction:'任务简介',
						timeCoins:100,
						publisher:{
							username:'官方发布者',
							headpicture:'/static/headpic.jpg',
						},
						endTime:'2024.3.20',
						statusShow:true,
						status:'已取消',
					},
				],
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
				uni.reLaunch({
					url: '/pages/404'
				});
			}
		},
		methods: {
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