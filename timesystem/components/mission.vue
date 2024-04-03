<template>
	<view>
		<!-- 消息提示 -->
		<view class="red-dot" v-if="missionData.dotShow"></view>
		<view class="mission">
			<!-- 任务标题 -->
			<view class="mission-title">
				<text>{{missionData.title}}</text>
			</view>
			<!-- 任务悬赏 -->
			<view class="mission-coins">
				<view>
					<image src="/static/coins.png"></image>
				</view>
				<view>
					<text>{{missionData.timeCoins}}</text>
				</view>
			</view>
			<!-- 任务简介 -->
			<view class="mission-introduction">
				<text>{{missionData.introduction}}</text>
			</view>
			<!-- 任务状态 -->
			<view class="mission-status" v-show="missionData.statusShow">
				<text>{{status}}</text>
			</view>
			<!-- 任务发布者信息 -->
			<view class="mission-publisher">
				<view class="mission-publisher-headpicture">
					<image :src="missionData.publisher.headpicture"></image>
				</view>
				<view class="mission-publisher-username">
					<text>{{missionData.publisher.username}}</text>
				</view>
			</view >
			<!-- 任务接取截止日期 -->
			<view class="mission-endtime">
				<text>截至{{missionData.endTime}}</text>
			</view>
		</view>
	</view>
</template>

<script scope>
	export default {
		props:['missionData'],
		data() {
			return {
				status:'',
				// missionData内容说明
				
				// missionData: {
				// 	title:'任务标题',
				// 	introduction:'任务简介',
				// 	timeCoins:100,
				// 	publisher:{
				// 		username:'发布者',
				// 		headpicture:'头像',
				// 	}
				// 	endTime:'截止时间',
				//  statusShow:true 是否显示任务状态
				//  status:'任务状态'
				//  dotShow:false,
				// }
			}
		},
		created:function(){
			switch(this.missionData.status) {
				case 1:this.status = '未审核';break;
				case 2:this.status = '未完成';break;
				case 3:this.status = '已接取';break;
				case 4:this.status = '已处理';break;
				case 5:this.status = '已完成';break;
				case 6:this.status = '已完结';break;
				case 7:this.status = '进行中';break;
				default: break;
			}
		}
	}
</script>

<style>
	.mission {
		display:flex;
		justify-content: flex-start;
		align-items: center;
		margin: 10rpx 0 0 20rpx;
		padding: 10rpx 10rpx 10rpx 10rpx;
		flex-wrap:wrap;
	}
	.mission-title {
		width: 80%;
		height: 50rpx;
	}
	.mission-title {
		font-size: 30rpx;
	}
	.mission-introduction {
		display: flex;
		align-items: center;
		width: 100%;
		height: 100rpx;
		margin: 5rpx 0 0 10rpx;
	}
	.mission-introduction text {
		font-size: 20rpx;
		color: gray;
		line-height: 35rpx;	//设置行高
		overflow:hidden;
		text-overflow:ellipsis;
		display:-webkit-box;
		-webkit-box-orient:vertical;
		-webkit-line-clamp:2;	//设置行数未2行
	}
	.mission-status {
		width: 100%;
		margin-left: 75%;
		color: orange;
		font-size: 25rpx;
		font-weight: bold;
	}
	.mission-coins {
		width: 20%;
		display:flex;
		justify-content: flex-start;
		align-items: center;
	}
	.mission-coins view {
		margin-left: 10rpx;
	}
	.mission-coins image {
		width: 40rpx;
		height: 40rpx;
	}
	.mission-coins text {
		font-size: 30rpx;
	}
	.mission-publisher {
		display: flex;
		width: 75%;
	}
	.mission-publisher-headpicture {
		display: flex;
		align-self: flex-end; 
	}
	.mission-publisher-headpicture image {
		width: 50rpx;
		height: 50rpx;
		border-radius: 50px;
		border: 1px solid rgb(184, 184, 184);
	}
	.mission-publisher-username {
		display: flex;
		align-self: flex-end;
		margin: 0 0 10rpx 10rpx;
	}
	.mission-publisher-username text {
		font-size: 27rpx;
		color: gray;
	}
	.mission-endtime {
		display: flex;
		align-self: flex-end;
		width: 25%;
	}
	.mission-endtime text {
		font-size: 25rpx;
		color: gray;
	}
	.red-dot {
		position: absolute;
		right: 17px;
	    width: 10px; /* 小红点的大小 */
	    height: 10px; /* 小红点的大小 */
	    background-color: red; /* 小红点的颜色 */
	    border-radius: 50%; /* 使小红点变成圆形 */
	  }
</style>