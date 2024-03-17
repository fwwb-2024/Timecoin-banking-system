<template>
	<view class="main">
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="header-background-text">
				<text>发布任务</text>
			</view>
		</view>
		
		<!-- 编辑内容 -->
		<view class="mission">
			<view class="mission-title">
				<text>任务标题</text>
				<input type="text" v-model="title"></input>
			</view>
			<view class="mission-brief">
				<text>任务简介</text>
				<textarea v-model="brief"></textarea>
			</view>
			<view class="mission-detail">
				<text>任务详情</text>
				<editerl id="editerl" @content="getcontent"></editerl>
			</view>
			<view class="mission-time">
				<text>任务时间</text>
				<view class="mission-time-time">
					<view class="mission-time-starttime">
						<picker mode="date" :value="startTime" :start="startDate" :end="endDate" @change="bindStartDateChange">
							<view class="uni-input">开始时间：{{startTime}}</view>
						</picker>
					</view>
					<view class="mission-time-endtime">
						<picker mode="date" :value="endTime" :start="startDate" :end="endDate" @change="bindEndDateChange">
							<view class="uni-input">截止时间：{{endTime}}</view>
						</picker>
					</view>
				</view>
			</view>
			<view class="mission-timeCoins">
				<text>任务悬赏</text>
				<input type="number" v-model="coins"></input>
			</view>
			<view>
				<button class="postMission" @click="postMission">发布</button>
			</view>
		</view>
	</view>
</template>

<script>
	import editerl from "@/components/editor/index.vue"
	export default {
		data() {
			const currentDate = this.getDate({
			    format: true
			})
			return {
				title:'',
				brief:'',
				detail:'',
				startTime:currentDate,
				endTime:currentDate,
				coins:0,
			}
		},
		methods:{
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
			},
			// 从子组件获取富文本数据
			getcontent(content){
				this.detail = content;
			},
			// 与时间计算相关方法
			bindStartDateChange (e) {
			    this.startTime = e.detail.value;
			},
			bindEndDateChange (e) {
			    this.endTime = e.detail.value;
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
								
				if (type === 'start') {
				    year = year - 60;
				} else if (type === 'end') {
				    year = year + 2;
				}
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				return `${year}.${month}.${day}`;
			},
			// 发布任务
			postMission() {
				if(this.title == '') {
					uni.showToast({
						title: '请输入标题',
						icon:'error',
						duration: 1000
					});
				}
				else if(this.coins == 0) {
					uni.showToast({
						title: '请设置悬赏',
						icon:'error',
						duration: 1000
					});
				}
				else {
					let temp = {
						  taskName: this.title,
						  taskEmployer: uni.getStorageSync('userName'),
						  taskAddress: "暂无地址",
						  taskDetail: this.detail,
						  taskBrief: this.brief,
						  taskBeginTime: this.startTime,
						  taskEndTime: this.endTime,
						  taskVisitedNumber: "0",
						  taskTimeCoinBounty: this.coins
					}
					this.$api.postTask(temp).then((res)=>{
						console.log(res);
						if(res.data == '新建成功') {
							uni.showToast({
								title: '发布成功',
								duration: 1000
							});
							setTimeout(function() {
							    uni.navigateBack({
							    	delta:1
							    })
							}, 1000);
						}
						else {
							uni.showToast({
								title: '发布失败',
								icon:'error',
								duration: 1000
							});
						}
					})
				}
			}
		},
		computed: {
		    startDate() {
		        return this.getDate('start');
		    },
		    endDate() {
		        return this.getDate('end');
		    }
		},
		components:{
			editerl,
		}
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
	.mission {
		display: flex;
		flex-direction: column;
		align-items: center;
		width: 700rpx;
		margin-top: 30rpx;
		border-radius: 10px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
		padding: 30rpx 0 30rpx 0;
		font-size: 19px;
	}
	.mission-title {
		width: 650rpx;
	}
	.mission-title input {
		height: 80rpx;
		border-radius: 15px;
		border: 1px solid #cccccc;
	}
	.mission-brief {
		margin-top: 20rpx;
		width: 650rpx;
	}
	.mission-brief textarea {
		width: 650rpx;
		height: 150rpx;
		border-radius: 15px;
		border: 1px solid #cccccc;
	}
	.mission-detail {
		margin-top: 20rpx;
		width: 650rpx;
	}
	#editerl {
		width: 650rpx;
		height: 400rpx;
		border-radius: 15px;
		border: 1px solid #cccccc;
	}
	.mission-time {
		width: 650rpx;
	}
	.mission-time-time {
		display: flex;
		width: 650rpx;
		height: 100rpx;
		flex-direction: row;
		justify-content: center;
		align-items: center;
	}
	.mission-time-starttime {
		display: flex;
		justify-content: center;
		width: 50%;
		font-size:15px;
	}
	.mission-time-endtime {
		display: flex;
		justify-content: center;
		width: 50%;
		font-size:15px;
	}
	.mission-timeCoins {
		width: 650rpx;
	}
	.mission-timeCoins input {
		height: 80rpx;
		border-radius: 15px;
		border: 1px solid #cccccc;
	}
	.postMission {
		width: 650rpx;
		background-color: orange;
		margin-top: 20rpx;
	}
</style>