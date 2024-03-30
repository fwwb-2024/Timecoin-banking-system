<template>
	<view class="main">
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
			<view v-show="changeTaskShow">
				<!--任务信息-->
				<view class="detail">
					<text class="detail-title">类别：</text>
					<text class="detail-content" @click="changeLable">{{mission.taskLable}}</text>
				</view>
				
				<view class="detail">
					<text class="detail-title">标题：</text>
					<input class="detail-content" v-model="mission.taskName"></input>
				</view>
				
				<view class="detail">
					<text class="detail-title">简介：</text>
					<input class="detail-content" v-model="mission.taskBrief"></input>
				</view>
				
				<view class="detail">
					<text class="detail-title">详情：</text>
					<textarea class="detail-content" v-model="mission.taskDetail"></textarea>
				</view>
				
				<!-- 图片附件 -->
				<view class="detail-image">
					<text class="detail-image-title">图片：</text>
					<view v-for="(item,index) in mission.taskPhoto">
						<image id="deletepic" @click="deletePic(index)" src="/static/deletepic.png"></image>
						<view class="detail-image-element">
							<image :src="item"></image>
						</view>
					</view>
					<view class="upPic-add" @click="upPic">
						<image src="/static/addpic.png"></image>
					</view>
				</view>
				
				<view class="detail">
					<text class="detail-title">悬赏：</text>
					<input type="number" v-model="mission.taskTimeCoinBounty"></input>
				</view>
				
				<view class="detail">
					<text class="detail-time">开始时间：</text>
					<picker mode="date" :value="mission.taskStartTime" :start="startDate" :end="endDate" @change="bindStartDateChange">
						<view class="detail-content">{{mission.taskStartTime}}</view>
					</picker>
				</view>
				<view class="detail">
					<text class="detail-time">截止时间：</text>
					<picker mode="date" :value="mission.taskEndTime" :start="startDate" :end="endDate" @change="bindEndDateChange">
						<view class="detail-content">{{mission.taskEndTime}}</view>
					</picker>
				</view>
				
				<view class="detail">
					<text class="detail-time">任务状态：</text>
					<text class="detail-content">{{mission.taskStatus}}</text>
				</view>
			</view>
			
			<view v-show="taskDetail">
				<!--任务信息-->
				<view class="detail">
					<text class="detail-title">类别：</text>
					<text class="detail-content">{{mission.taskLable}}</text>
				</view>
				<view class="detail">
					<text class="detail-title">标题：</text>
					<text class="detail-content">{{mission.taskName}}</text>
				</view>
				<view class="detail">
					<text class="detail-title">简介：</text>
					<text class="detail-content">{{mission.taskBrief}}</text>
				</view>
				<view class="detail">
					<text class="detail-title">详情：</text>
					<text class="detail-content">{{mission.taskDetail}}</text>
				</view>
				<view class="detail-image" v-if="taskPhotoShow">
					<text class="detail-image-title">图片：</text>
					<view class="detail-image-element" v-for="(item,index) in mission.taskPhoto">
						<image :src="item"></image>
					</view>
				</view>
				<view class="detail">
					<text class="detail-title">悬赏：</text>
					<text class="detail-content">{{mission.taskTimeCoinBounty}}</text>
				</view>
				<view class="detail" v-show="statusShow">
					<text class="detail-title">状态：</text>
					<text class="detail-content">{{mission.taskStatus}}</text>
				</view>
			</view>
			
			<!-- 显示审核失败原因 -->
			<view class="detail" v-show="remarkShow">
				<text class="detail-time">审核失败：</text>
				<view class="detail-content">{{mission.taskStatusRemark}}</view>
			</view>
			
			<!-- 显示开始任务内容 -->
			<view class="detail" v-if="taskDoingShow>0">
				<view class="detail-start-title">
					<text>志愿者已经开始任务</text>
				</view>
				<view class="detail-start-text" v-if="taskDoingShow==2 || taskDoingShow==4">
					<text>{{mission.taskDoingText}}</text>
				</view>
				<view class="detail-start-image" v-if="taskDoingShow==3 || taskDoingShow==4">
					<image :src="mission.taskDoingPhoto"></image>
				</view>
			</view>
			
			<!-- 切换查看修改按钮 -->
			<button v-if="(!changeTaskShow) && changeTaskStatus" class="detail-button" @click="()=>{changeTaskShow = true;taskDetail=false}">修改任务</button>
			<button v-if="(!changeTaskShow) && (!taskDetail)" class="detail-button" @click="()=>{changeTaskShow = false;taskDetail = true}">查看任务</button>
			<button v-show="taskDetail" class="detail-button" @click="()=>{taskDetail=false}">取消查看</button>
			<button v-show="changeTaskShow" class="detail-button" @click="changeTask">确认修改</button>
			<button v-show="changeTaskShow" class="detail-button" @click="()=>{changeTaskShow=false;taskDetail=true}">取消修改</button>
			<button v-show="changeTaskShow" class="detail-button" @click="deleteTask">删除任务</button>
			
			<view v-if="confirmShow">
				<button class="detail-button" @click="agreeTask">确认任务完成</button>
				<button class="detail-button" @click="refuseTask">拒绝任务完成</button>
			</view>
		</view>
	</view>
</template>

<script>
	import editorl from "@/components/editor/index.vue"
	export default {
		data() {
			const currentDate = this.getDate({
			    format: true
			})
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
					taskStartTime:currentDate,
					taskEndTime:currentDate,
					taskStatus:null,
					taskStatusRemark:null,
					taskPhoto:[],
					// 开始任务信息
					taskDoingText:'',
					taskDoingPhoto:'',
				},
				// 显示任务状态
				statusShow:true,
				// 显示审核不通过原因
				remarkShow:false,
				// 任务详情加载完毕
				loading:false,
				// 显示确认完成任务按钮
				confirmShow:false,
				// 显示开始任务信息
				taskDoingShow:0,
				
				// 显示图片
				taskPhotoShow:false,
				// 显示修改任务
				changeTaskShow:false,
				// 显示任务详情
				taskDetail:false,
				// 若未接取任务，则显示修改任务按钮
				changeTaskStatus:true,
			}
		},
		onLoad(options) {
			this.missionId = options.id
			this.$api.getTaskData(this.missionId).then((res)=>{
				console.log(res);
				switch(res.data.taskLable){
					case 1:this.mission.taskLable = '其他';break;
					case 2:this.mission.taskLable = '跑腿';break;
					case 3:this.mission.taskLable = '带货';break;
					case 4:this.mission.taskLable = '打理';break;
					case 5:this.mission.taskLable = '陪伴';break;
					case 6:this.mission.taskLable = '线上';break;
					default:break;
				}
				this.mission.taskID = res.data.taskID
				this.mission.taskName = res.data.taskName
				this.mission.taskBrief = res.data.taskBrief
				this.mission.taskDetail = res.data.taskDetail
				this.loading = true
				this.mission.taskTimeCoinBounty = res.data.taskTimeCoinBounty
				this.mission.taskAddress = res.data.taskAddress
				this.mission.taskBeginTime = res.data.taskBeginTime
				this.mission.taskEndTime = res.data.taskEndTime
				if(res.data.taskStatusRemark !== null){
					this.mission.taskStatusRemark = res.data.taskStatusRemark
					this.remarkShow = true
				}
				if(res.data.taskPhoto[0] != ''){
					this.taskPhotoShow = true
					this.mission.taskPhoto = res.data.taskPhoto
				}
				
				switch(res.data.taskStatus){
					case 1:this.mission.taskStatus = '未审核';this.taskDetail=true;break;
					case 2:this.mission.taskStatus = '未完成';this.taskDetail=true;break;
					case 3:this.mission.taskStatus = '已接取';this.changeTaskStatus=false;break;
					case 4:this.mission.taskStatus = '已处理';this.changeTaskStatus=false;this.confirmShow=true;break;
					case 5:this.mission.taskStatus = '已完成';this.taskDetail=true;this.changeTaskStatus=false;break;
					case 6:this.mission.taskStatus = '已完结';this.changeTaskStatus=false;break;
					case 7:this.mission.taskStatus = '进行中';this.changeTaskStatus=false;this.taskDoingShow=1;break;
					default:break;
				}
				
				if(res.data.taskDoingText != '' && res.data.taskDoingText != null){
					this.mission.taskDoingText = res.data.taskDoingText
					this.taskDoingShow = 2
				}
				if(res.data.taskDoingPhoto != '' && res.data.taskDoingPhoto != null){
					this.mission.taskDoingPhoto = res.data.taskDoingPhoto
					this.taskDoingShow = 3
				}
				if(res.data.taskDoingText != '' && res.data.taskDoingPhoto != '' && res.data.taskDoingText != null && res.data.taskDoingPhoto != null){
					this.taskDoingShow = 4
				}
				if(res.data.taskDoingText == '' && res.data.taskDoingPhoto == ''){
					this.taskDoingShow = 0
				}
			})
		},
		methods: {
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
			},
			// 任务详情修改内容
			getcontent(content) {
				this.mission.taskDetail = content
			},
			// 更改图片类别
			changeLable(){
				let temp = ['跑腿','带货','打理','陪伴','线上','其他']
				let that = this
				uni.showActionSheet({
					title:'请选择任务类别',
				    itemList:temp,
				    success: function (response) {
						if (response.tapIndex > -1) {
							that.mission.taskLable = temp[response.tapIndex]
				        }
				    },
				});
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
								  that.mission.taskPhoto.push(uploadRes.data)
							  },
							});
						}
					}
				})
			},
			// 删除图片
			deletePic(index){
				this.mission.taskPhoto.splice(index,1)
			},
			// 修改任务内容
			changeTask() {
				let taskLable = 1
				switch(this.mission.taskLable){
					case '跑腿':taskLable = 2;break;
					case '带货':taskLable = 3;break;
					case '打理':taskLable = 4;break;
					case '陪伴':taskLable = 5;break;
					case '线上':taskLable = 6;break;
					case '其他':taskLable = 1;break;
				}
				let temp = {
					taskID: this.mission.taskID,
					taskLable: taskLable,
					taskName: this.mission.taskName,
					taskBrief: this.mission.taskBrief,
					taskDetail: this.mission.taskDetail,
					taskTimeCoinBounty: this.mission.taskTimeCoinBounty,
					taskAddress: this.mission.taskAddress,
					taskBeginTime: this.mission.taskBeginTime,
					taskEndTime: this.mission.taskEndTime,
					taskEmployer:uni.getStorageSync('userName'),
					taskEmployerID: uni.getStorageSync('userID'),
					taskPhoto: this.mission.taskPhoto,
				}
				this.$api.changeTaskData(temp).then((res)=>{
					if(res.data == '修改成功'){
						uni.showToast({
							title:'修改成功',
							duration:1000
						})
						setTimeout(function() {
							uni.reLaunch({url: '/pages/mymission/myMission'});
						},1000)
					}
					else{
						uni.showToast({
							title:'修改失败',
							icon:'error',
							duration:1000
						})
					}
				})
			},
			// 删除任务
			deleteTask(){
				if(this.mission.taskStatus == '已接取' || this.mission.taskStatus == '已处理' || this.mission.taskStatus == '已完成'){
					uni.showToast({
						title:'不能删除已被接取的任务',
						icon:'error',
						duration:1000
					})
				}
				else{
					this.$api.deleteTask(this.mission.taskID,uni.getStorageSync('userID'),this.mission.taskTimeCoinBounty).then((res)=>{
						if(res.data == '删除成功'){
							uni.showToast({
								title:'删除成功',
								icon:'success',
								duration:1000
							})
							setTimeout(function() {
								uni.reLaunch({url: '/pages/mymission/myMission'});
							},1000)
						}
						else{
							uni.showToast({
								title:'删除失败',
								icon:'error',
								duration:1000
							})
						}
					})
				}
			},
			// 确认同意任务
			agreeTask(){
				this.$api.agreeTask(this.mission.taskID).then((res)=>{
					if(res.data == '完成成功'){
						uni.showToast({
							title:'确认成功',
							icon:'success',
							duration:1000
						})
						setTimeout(function() {
							uni.reLaunch({url: '/pages/mymission/myMission'});
						},1000)
					}
					else {
						uni.showToast({
							title:'确认失败',
							icon:'error',
							duration:1000
						})
					}
				})
			},
			// 拒绝同意任务
			refuseTask(){
				// 记录根节点
				let that = this
				uni.showModal({
				    title: '输入不同意原因',
					editable: true,
				    success: function (response) {
						let tempRamark = '发布者不同意完成，原因为：'+ response.content
						that.$api.refuseTask(that.mission.taskID,tempRamark).then((res)=>{
							if(res.data == '批改成功'){
								uni.showToast({
									title:'拒绝成功',
									icon:'success',
									duration:1000
								})
								setTimeout(function() {
									uni.reLaunch({url: '/pages/mymission/myMission'});
								},1000)
							}
							else {
								uni.showToast({
									title:'拒绝失败',
									icon:'error',
									duration:1000
								})
							}
						})
					},
				})
			},
			// 与时间计算相关方法
			bindStartDateChange (e) {
			    this.mission.taskStartTime = e.detail.value.replace('-', '.').replace('-', '.');
			},
			bindEndDateChange (e) {
			    this.mission.taskEndTime = e.detail.value.replace('-', '.').replace('-', '.');
			},
			// 时间限制
			getLimtDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
				
				if(type == 'start') {
				    year = year - 20;
				}			
				else if (type == 'end') {
				    year = year + 20;
				}
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
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
		},
		computed: {
		    startDate() {
		        return this.getLimtDate('start');
		    },
		    endDate() {
		        return this.getLimtDate('end');
		    }
		},
		components:{
			editorl,
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
		margin: 170rpx 0 50rpx 0;
		padding: 50rpx 0 50rpx 0;
		border-radius: 10px;
		width: 700rpx;
		background-color: white;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
	}
	.detail {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		width: 650rpx;
		flex-wrap: wrap;
	}
	.detail-title {
		width: 120rpx;
		font-size: 38rpx;
		color: gray;
		margin-bottom: 50rpx;
	}
	.detail-content {
		font-size: 35rpx;
		margin-bottom: 30rpx;
	}
	.detail-time {
		width: 200rpx;
		font-size: 38rpx;
		color: gray;
		margin-bottom: 50rpx;
	}
	.detail-image {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		flex-wrap: wrap;
		width: 650rpx;
		margin: 30rpx 0 30rpx 0;
	}
	.detail-image-title {
		width: 120rpx;
		font-size: 38rpx;
		color: gray;
		margin-bottom: 50rpx;
	}
	.detail-image-element {
		margin: 0 10rpx 10rpx 20rpx;
	}
	.detail-image-element image {
		width: 600rpx;
	}
	.detail-button {
		margin: 50rpx 10rpx 0 10rpx ;
		width: 500rpx;
		padding: 30rpx 30rpx 30rpx 30rpx;
		background: rgba(255, 141, 26, 0.6);
		box-shadow: 0px 2px 4px  rgba(0, 0, 0, 0.25);
		color: rgba(255, 255, 255, 1);
		text-align: center;
		font-size: 38rpx;
		line-height: 38rpx;
	}
	.upPic {
		width: 100%;
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: flex-start;
		margin-top: 50rpx;
		padding-left: 40rpx;
	}
	#deletepic {
		width: 30rpx;
		height: 30rpx;
		position: relative;
		left: 600rpx;
		top: 20rpx;
		z-index: 1;
	}
	.detail-image {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		flex-wrap: wrap;
		width: 650rpx;
		margin: 30rpx 0 30rpx 0;
	}
	.detail-image-title {
		width: 120rpx;
		font-size: 38rpx;
		color: gray;
		margin-bottom: 50rpx;
	}
	.detail-image-element image {
		width: 600rpx;
	}
	.upPic-add {
		width: 200rpx;
		height: 200rpx;
		margin: 40rpx 0 0 20rpx;
	}
	.upPic-add image{
		width: 200rpx;
		height: 200rpx;
	}
	
	.detail-start-title{
		font-size: 38rpx;
		width: 100%;
		color: orange;
	}
	.detail-start-text{
		margin-top: 30rpx;
		font-size: 35rpx;
		width: 100%;
	}
	.detail-start-image {
		margin-top: 30rpx;
		width: 100%;
	}
	.detail-start-image image {
		width: 100%;
		background-color: blue
	}
</style>