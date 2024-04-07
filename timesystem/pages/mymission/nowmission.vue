<template>
	<view class="main">
		<page-meta :root-font-size="size"></page-meta>
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="back-text">
				<text>任务详情</text>
			</view>		
		</view>
		
		<view class="body">
			<!--任务信息-->
			<view class="detail-head">
				<view class="detail-title">
					<text>{{mission.taskName}}</text>
				</view>
				
				<text class="detail-coins">{{mission.taskTimeCoinBounty}}时间币/时</text>
				<view class="detail-lable">
					<text class="detail-lable-title">类别：</text>
					<text class="detail-lable-content">{{mission.taskLable}}</text>
				</view>
			</view>
			
			<view class="detail-body">
				<view class="detail-status" v-show="statusShow">
					<text class="detail-status-title">状态：</text>
					<text class="detail-status-content">{{mission.taskStatus}}</text>
				</view>
				
				<text class="detail-breif-title">任务简介：</text>
				<text class="detail-breif-content">{{mission.taskBrief}}</text>
				
				<text class="detail-detail-title">任务详情：</text>
				<text class="detail-detail-content">{{mission.taskDetail}}</text>
				<view class="detail-image" v-if="taskPhotoShow">
					<view class="detail-image-element" v-for="(item,index) in mission.taskPhoto">
						<image :src="item"></image>
					</view>
				</view>
				
				<view class="detail-time">
					<text class="detail-time-title">截止时间：</text>
					<text class="detail-time-content">{{mission.taskEndTime}}</text>
				</view>
				
				<!-- 显示审核失败原因 -->
				<text class="detail-fail-title" v-show="remarkShow">审核失败：</text>
				<view class="detail-fail-content" v-show="remarkShow">{{mission.taskStatusRemark}}</view>
			</view>
			
			<view v-if="cancelMissionShow" class="detail-end">
				<!--取消任务按钮-->
				<button v-if="cancelMissionShow" class="detail-button" @click="canelMission">取消</button>
				<!-- 任务打卡按钮 -->
				<button v-if="startMissionShow==1" class="detail-button" @click="startMission">开始</button>
				<!--完成任务按钮-->
				<button v-if="startMissionShow==2" class="detail-button" @click="completeMission">完成</button>
			</view>
			
			<!-- 开始任务弹出层 -->
			<uni-popup ref="popup" type="bottom" border-radius="15px 15px 0 0" @maskClick="popClose">
				<view class="start">
					<text class="start-title">确认打卡?</text>
					<view class="start-time">
						<text>打卡时间:</text>
						<text>{{startTime}}</text>
					</view>
					<!-- <view class="start-text">
						<text>请输入文字信息：</text>
						<input v-model="taskDoingText"></input>
					</view>
					<view class="start-image">
						<text>请上传打卡图片：</text>
						<image @click="upPic" :src="taskDoingPhoto"></image>
					</view> -->
					
					<view style="display: flex;flex-direction: row;justify-content: center;">
						<button @click="cancel" style="margin:20rpx 100rpx 0 0;background-color: whitesmoke;width: 250rpx;">取消</button>
						<button @click="start" style="margin:20rpx 0 0 0;background-color: orange;color: white;width: 250rpx;">确认</button>
					</view>
				</view>
			</uni-popup>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				missionId:null,
				mission:{
					taskID:null,
					taskLable:'',
					taskName:'',
					taskBrief:'',
					taskDetail:'',
					taskTimeCoinBounty:null,
					taskAddress:'',
					taskStartTime:'',
					taskEndTime:'',
					taskStatus:null,
					taskPhoto:[],
				},
				
				// 开始任务信息
				startTime:'',
				taskDoingText:'',
				taskDoingPhoto:'',
				
				remarkShow:false,
				statusShow:true,
				// 显示图片
				taskPhotoShow:false,
				// 显示开始任务
				startMissionShow:0,
				// 显示取消任务
				cancelMissionShow:false,
				
				size:'',
			}
		},
		onLoad(options) {
			this.size = uni.getStorageSync("size")
			this.missionId = options.id
			this.$api.getTaskData(this.missionId).then((res)=>{
				this.mission.taskID = res.data.taskID
				switch(res.data.taskLable){
					case 1:this.mission.taskLable = '其他';break;
					case 2:this.mission.taskLable = '跑腿';break;
					case 3:this.mission.taskLable = '代购';break;
					case 4:this.mission.taskLable = '打理';break;
					case 5:this.mission.taskLable = '陪伴';break;
					case 6:this.mission.taskLable = '线上';break;
					default:break;
				}
				this.mission.taskName = res.data.taskName
				this.mission.taskBrief = res.data.taskBrief
				this.mission.taskDetail = res.data.taskDetail
				this.mission.taskTimeCoinBounty = res.data.taskTimeCoinBounty
				this.mission.taskAddress = res.data.taskAddress
				this.mission.taskEndTime = res.data.taskEndTime
				if(res.data.taskPhoto != null && res.data.taskPhoto != '' && res.data.taskPhoto[0] != '' && res.data.taskPhoto[0] != null){
					this.taskPhotoShow = true
					this.mission.taskPhoto = res.data.taskPhoto
				}
				if(res.data.taskStatusRemark !== null && res.data.taskStatusRemark !== ''){
					this.mission.taskStatusRemark = res.data.taskStatusRemark
					this.remarkShow = true
				}
				switch(res.data.taskStatus){
					case 1:this.mission.taskStatus = '未审核';break;
					case 2:this.mission.taskStatus = '未完成';break;
					case 3:this.mission.taskStatus = '已接取';this.cancelMissionShow=true;this.startMissionShow=1;break;
					case 4:this.mission.taskStatus = '已处理(请等待发布者同意)';break;
					case 5:this.mission.taskStatus = '已完成(请等待管理员审核)';break;
					case 6:this.mission.taskStatus = '已完结';break;
					case 7:this.mission.taskStatus = '进行中';this.cancelMissionShow=true;this.startMissionShow=2;break;
				}
			})
		},
		mounted() {
			this.getNowTime()
		    let timer = setInterval(() => {
				this.getNowTime()
		    },60000)
		},
		methods: {
			//返回上一级页面
			back() {
				uni.reLaunch({
					url:'/pages/mymission/myMission'
				})
			},
			// 开始任务
			startMission(){
				this.$refs.popup.open('buttom')
			},
			// 上传图片
			upPic(){
				let that = this
				// 选择图片并且上传
				uni.chooseImage({
					count: 1,
					sizeType: ['original'],
					sourceType: ['album','camera'], 
					success: (chooseImageRes)=>{
						// 如果图片过大
						if(chooseImageRes.tempFiles[0].size > 1048576){
							uni.showToast({
								title: '图片大于1M!',
								icon:'error',
								duration: 1000
							});
						}
						else {
							// 上传图片
							uni.uploadFile({
							  url: 'http://10.195.28.44:9090/tasks/taskCenter/uploadimage', 
							  filePath: chooseImageRes.tempFilePaths[0],
							  name: 'file', 
							  header: {
								  'Authorization': uni.getStorageSync('token')
							  },
							  success: (uploadRes) => {
								  that.taskDoingPhoto = uploadRes.data
							  },
							});
						}
					}
				})
			},
			// 关闭弹出层
			popClose(){
				this.taskDoingText = ''
				this.taskDoingPhoto = ''
			},
			// 取消确认
			cancel(){
				this.taskDoingText = ''
				this.taskDoingPhoto = ''
				this.$refs.popup.close()
			},
			// 确认开始任务
			start(){
				let that = this
				let temp = {
					taskID:this.missionId,
					taskDoingText: this.taskDoingText,
					taskDoingPhoto: this.taskDoingPhoto,
				}
				this.$api.startMission(temp).then((res)=>{
					if(res.data == '进行成功'){
						uni.showToast({
							title: '任务开始',
							duration: 1000
						});
						setTimeout(function() {
							that.back()
						},1000)
					}
					else {
						uni.showToast({
							title: '网络错误',
							duration: 1000
						});
					}
				})
			},
			//获取当前时间
			getNowTime() {
				let date = new Date();
				//年 getFullYear()：四位数字返回年份
				let year = date.getFullYear(); //getFullYear()代替getYear()
				//月 getMonth()：0 ~ 11
				let month = date.getMonth() + 1;
				//日 getDate()：(1 ~ 31)
				let day = date.getDate();
				//时 getHours()：(0 ~ 23)
				let hour = date.getHours();
				//分 getMinutes()： (0 ~ 59)
				let minute = date.getMinutes();
				let time = year + '-' + this.addZero(month) + '-' + this.addZero(day) + ' ' + this.addZero(hour) + ":" +this.addZero(minute);
			    this.startTime = time
			},
			//小于10的拼接上0字符串
			addZero(s) {
			      return s < 10 ? ('0' + s) : s;
			},
			// 完成任务
			completeMission(){
				this.$api.completeTask(this.missionId,uni.getStorageSync('userID')).then((res)=>{
					if(res.data == '完成成功'){
						uni.showToast({
							title:'任务已完成',
							duration:1000
						})
						setTimeout(function() {
							uni.reLaunch({url: '/pages/mymission/myMission'});
						},1000)
					}
					else{
						uni.showToast({
							title:'任务未完成',
							icon:'error',
							duration:1000
						})
					}
				})
			},
			// 取消任务
			canelMission(){
				let that = this
				uni.showModal({
					title: '提示',
					// 提示文字
					content: '确认取消该任务吗？',
					// 取消按钮的文字自定义
					cancelText: "取消",
					// 确认按钮的文字自定义
					confirmText: "确认",
					//确认字体的颜色
					confirmColor:'red',
					//取消字体的颜色
					cancelColor:'#000000',
					success: function(res) {
						if (res.confirm) {
							that.$api.canelTask(that.missionId,uni.getStorageSync('userID')).then((res)=>{
								if(res.data == '取消成功'){
								uni.showToast({
										title:'已取消',
										duration:1000
									})
									setTimeout(function() {
										uni.reLaunch({url: '/pages/mymission/myMission'});
									},1000)
								}
								else{
									uni.showToast({
										title:'取消失败',
										icon:'error',
										duration:1000
									})
								}
							})
						}
					}
				})
			}
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
		margin-right: 195rpx;
	}
	.back-image image {
		width: 50rpx;
		height: 50rpx;
	}
	.back-text {
		order: 1;		
		justify-content: center;
	}
	.back-text text {
		flex-basis: 120rpx;
		font-size: 38rpx;
		color: white;
	}
	
	.body {
		padding: 190rpx 0 50rpx 0;
	}
	/* 任务首部 */
	.detail-head {
		width: 680rpx;
		border-radius: 15px;
		background-color: white;
		padding: 50rpx 0 30rpx 20rpx;
		display: flex;
		flex-direction: column;
		align-items: flex-start;
		margin-bottom: 50rpx;
	}
	/* 任务标题 */
	.detail-title {
		width: 100%;
		font-size: 38rpx;
		font-weight: bold;
		margin-bottom: 10rpx;
	}
	/* 时间币悬赏 */
	.detail-coins {
		width: 650rpx;
		font-size: 36rpx;
		color: orange;
		padding-bottom: 40rpx;
		border-bottom: #e6e6e6 solid 1px;
	}
	
	/* 类别详情 */
	.detail-lable {
		margin-top: 20rpx;
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
	}
	.detail-lable-title {
		font-size: 30rpx;
		color: gray;
	}
	.detail-lable-content {
		font-size: 30rpx;
	}
	
	/* 任务主体 */
	.detail-body {
		width: 660rpx;
		border-radius: 15px;
		background-color: white;
		padding: 20rpx 20rpx 30rpx 20rpx;
		margin-bottom: 100rpx;
		display: flex;
		flex-direction: column;
		align-items: flex-start;
	}
	
	/* 任务状态 */
	.detail-status {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-bottom: 20rpx;
	}
	.detail-status-title {
		font-size: 35rpx;
		font-weight: 600;
	}
	.detail-status-content {
		margin-top: 10rpx;
		font-size: 33rpx;
		margin-bottom: 5rpx;
	}
	
	/* 任务简介 */
	.detail-breif-title {
		font-size: 35rpx;
		font-weight: 600;
	}
	.detail-breif-content {
		margin-top: 10rpx;
		font-size: 33rpx;
		margin-bottom: 20rpx;
	}
	/* 任务详情 */
	.detail-detail-title {
		font-size: 35rpx;
		font-weight: 600;
	}
	.detail-detail-content {
		margin-top: 10rpx;
		font-size: 33rpx;
	}
	/* 任务图片 */
	.detail-image {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		flex-wrap: wrap;
		width: 650rpx;
		margin: 30rpx 0 30rpx 0;
		padding: 20rpx 0 10rpx 20rpx;
	}
	.detail-image-element {
		margin: 0 10rpx 10rpx 20rpx;
	}
	.detail-image-element image {
		width: 600rpx;
	}
	
	/* 截止时间 */
	.detail-time {
		display: flex;
		flex-direction: row;
		align-items: center;
		margin-bottom: 10rpx;
	}
	.detail-time-title {
		font-size: 35rpx;
		font-weight: 600;
	}
	.detail-time-content {
		margin-top: 10rpx;
		font-size: 33rpx;
		margin-bottom: 5rpx;
	}
	
	/* 开始打卡 */
	.start {
		/* height: 900rpx; */
		height: 300rpx;
		display: flex;
		flex-direction: column;
		/* align-items: flex-start; */
		align-items: center;
		background-color: white;
		padding: 20rpx 0 20rpx 60rpx;
	}
	.start-title {
		font-size: 37rpx;
	}
	.start-time {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		align-items: center;
		height: 100rpx;
	}
	.start-time text {
		font-size: 35rpx;
		margin-right: 10rpx;
	}
	.start-text {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		align-items: center;
		height: 100rpx;
	}
	.start-text text {
		font-size: 35rpx;
	}
	.start-text input {
		border-radius: 15px;
		border: 1px solid #e6e6e6;
		height: 70rpx;
	}
	.start-image {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		flex-wrap: wrap;
	}
	.start-image text {
		font-size: 35rpx;
		margin-bottom: 10rpx;
	}
	.start-image image {
		width: 600rpx;
		height: 500rpx;
		border: 1px solid #e6e6e6;
	}
	
	/* 审核失败原因 */
	.detail-fail-title {
		font-size: 35rpx;
		font-weight: 600;
	}
	.detail-fail-content {
		margin-top: 10rpx;
		font-size: 33rpx;
	}
	
	/* 任务底部 */
	.detail-end {
		display: flex;
		flex-direction: row;
		position: fixed;
		background-color: white;
		bottom: 0;
		width: 750rpx;
		height: 100rpx;
		padding-top: 10rpx;
	}
	/* 按钮 */
	.detail-button {
		width: 260rpx;
		height: 80rpx;
		background-color: #80aaff;
		box-shadow: 0px 2px 4px  rgba(0, 0, 0, 0.25);
		color: rgba(255, 255, 255, 1);
		text-align: center;
		font-size: 38rpx;
		line-height: 80rpx;
	}
</style>