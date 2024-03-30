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
		
		<view class="body"></view>
		<!-- 编辑内容 -->
		<view class="mission">
			<view class="mission-title">
				<text class="detail-title">类别:</text>
				<text @click="changeLable">{{taskLable}}</text>
			</view>
			<view class="mission-title">
				<text class="detail-title">标题:</text>
				<input type="text" v-model="title"></input>
			</view>
			<view class="mission-brief">
				<text class="detail-title">简介:</text>
				<textarea v-model="brief"></textarea>
			</view>
			<view class="mission-detail">
				<text class="detail-title">详情:</text>
				<textarea v-model="detail"></textarea>
			</view>
			<view class="mission-time">
				<text class="detail-title">时间:</text>
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
				<text class="detail-title">悬赏</text>
				<input type="number" placeholder="0" v-model="coins"></input>
			</view>
			<!-- 上传图片 -->
			<view class="upPic">
				<view class="upPic-title">
					<text>上传图片</text>
				</view>
				<view v-for="(item,index) in taskPhoto">
					<image id="deletepic" @click="deletePic(index)" src="/static/deletepic.png"></image>
					<view class="upPic-list-element">
						<image :src="item"></image>
					</view>
				</view>
				<view class="upPic-add" @click="upPic">
					<image src="/static/addpic.png"></image>
				</view>
			</view>
			<view>
				<button class="postMission" @click="postMission">发布</button>
			</view>
		</view>
		
		<!-- 支付弹出层 -->
		<uni-popup ref="popup" type="bottom" border-radius="15px 15px 0 0" @maskClick="popClose">
			<view class="pay">
				<text>请选择你的支付方式</text>
				<text>本人支付</text>
				<view class="pay-element">
					<button @click="choosePayer(ownpay.userID)">姓名：{{ownpay.name}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					余额为：{{ownpay.coins}}</button>
				</view>
				
				<text v-if="familyShow">请选择家庭进行支付</text>
				<view v-if="familyShow" class="pay-element" v-for="(item,index) in familylist" :key="item.id">
					<button @click="chooseFamily(item.familyID)">家庭名：{{item.name}}</button>
				</view>
				
				<text v-if="!familyShow">家庭成员支付</text>
				<view v-if="(!familyShow) && (item.familyID == familyIDshow)" class="pay-element" v-for="(item,index) in paylist" :key="item.id">
					<button @click="choosePayer(item.userID)">姓名：{{item.name}}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					余额为：{{item.coins}}</button>
				</view>
			</view>
		</uni-popup>
	</view>
</template>

<script>
	export default {
		data() {
			const currentDate = this.getDate({
			    format: true
			})
			return {
				taskLable:'跑腿',
				title:'',
				brief:'',
				detail:'',
				startTime:currentDate,
				endTime:currentDate,
				coins:null,
				taskPhoto:[],
				
				// 本人支付信息
				ownpay:{},
				// 支付人列表
				paylist:[],
				// 支付家庭列表
				familylist:[],
				
				// 是否显示家庭选择
				familyShow:true,
				// 显示哪个家庭成员
				familyIDshow:null,
			}
		},
		created() {
			this.$api.getCoins(uni.getStorageSync('userName')).then((res)=>{
				this.ownpay.name=uni.getStorageSync('userName')
				this.ownpay.userID=uni.getStorageSync('userID')
				this.ownpay.coins=res.data.userTimeCoin
			})
			
			let name = uni.getStorageSync('userName')
			// 查看家庭
			this.$api.getFamilyList(uni.getStorageSync('userID')).then((res)=>{
				for(let i=0;i<res.data.length;i++){
					this.familylist.push({name:res.data[i].familyName,familyID:res.data[i].familyID})
					// 导入家庭成员
					this.$api.getFamilyCoinsData(res.data[i].familyID).then((response)=>{
						for(let j=0;j<response.data.length;j++){
							if(response.data[j].userName == name){
								continue
							}
							this.paylist.push({familyID:res.data[i].familyID,name:response.data[j].userName,coins:response.data[j].userTimeCoin,userID:response.data[j].userID})
						}
					})
				}
			})
		},
		methods:{
			//返回上一级页面
			back() {
				uni.navigateBack({
					delta:1
				})
			},
			// 与时间计算相关方法
			bindStartDateChange (e) {
			    this.startTime = e.detail.value.replace('-', '.').replace('-', '.');
			},
			bindEndDateChange (e) {
			    this.endTime = e.detail.value.replace('-', '.').replace('-', '.');
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
								
				month = month > 9 ? month : '0' + month;
				day = day > 9 ? day : '0' + day;
				return `${year}.${month}.${day}`;
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
			// 更改图片类别
			changeLable(){
				let temp = ['跑腿','带货','打理','陪伴','线上','其他']
				let that = this
				uni.showActionSheet({
					title:'请选择任务类别',
				    itemList:temp,
				    success: function (response) {
						if (response.tapIndex > -1) {
							that.taskLable = temp[response.tapIndex]
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
								  that.taskPhoto.push(uploadRes.data)
							  },
							});
						}
					}
				})
			},
			// 删除图片
			deletePic(index){
				this.taskPhoto.splice(index,1)
			},
			// 选择支付家庭
			chooseFamily(familyID){
				this.familyIDshow = familyID
				this.familyShow = false
			},
			// 选择支付者并且支付
			choosePayer(userID){
				if(userID){
					let taskLable = 1
					switch(this.taskLable){
						case '跑腿':taskLable = 2;break;
						case '带货':taskLable = 3;break;
						case '打理':taskLable = 4;break;
						case '陪伴':taskLable = 5;break;
						case '线上':taskLable = 6;break;
						case '其他':taskLable = 1;break;
					}
					let temp = {
							taskLable: taskLable,
							taskName: this.title,
							taskEmployer: uni.getStorageSync('userName'),
							taskEmployerID: uni.getStorageSync('userID'),
							taskAddress: "暂无地址",
							taskDetail: this.detail,
							taskBrief: this.brief,
							taskBeginTime: this.startTime,
							taskEndTime: this.endTime,
							taskVisitedNumber: "0",
							taskTimeCoinBounty: this.coins,
							taskPhoto: this.taskPhoto,
							taskEmployerFamilyUserID:userID
					}
					this.$api.postTask(temp).then((res)=>{
						if(res.data == '新建成功') {
							uni.showToast({
								title: '发布成功',
								duration: 1000
							});
							setTimeout(function() {
							    uni.reLaunch({
							    	url:'/pages/mymission/myMission'
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
			},
			//弹出层关闭
			popClose(){
				this.familyShow = true
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
				else if(this.coins == 0 || this.coins == null) {
					uni.showToast({
						title: '请设置悬赏',
						icon:'error',
						duration: 1000
					});
				}
				else {
					this.$refs.popup.open('buttom')
				}
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
	
	.body {
		margin-top: 150rpx;
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
		border: 1px solid #e6e6e6;
	}
	.mission-brief {
		margin-top: 20rpx;
		width: 650rpx;
	}
	.mission-brief textarea {
		width: 650rpx;
		height: 120rpx;
		border-radius: 15px;
		border: 1px solid #e6e6e6;
	}
	.mission-detail {
		margin-top: 20rpx;
		width: 650rpx;
	}
	.mission-detail textarea {
		width: 650rpx;
		height: 150rpx;
		border-radius: 15px;
		border: 1px solid #e6e6e6;
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
		border: 1px solid #e6e6e6;
	}
	.postMission {
		width: 650rpx;
		background-color: orange;
		margin-top: 20rpx;
		color: white;
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
	.upPic-title {
		width: 100%;
		display: flex;
		flex-direction: row;
		margin-left: 10rpx;
	}
	#deletepic {
		width: 30rpx;
		height: 30rpx;
		position: relative;
		left: 180rpx;
		top: 20rpx;
		z-index: 2;
	}
	.upPic-list-element {
		margin-right: 20rpx;
	}
	.upPic-list-element image {
		width: 200rpx;
		height: 200rpx;
	}
	.upPic-add {
		width: 200rpx;
		height: 200rpx;
		margin-top: 40rpx;
	}
	.upPic-add image{
		width: 200rpx;
		height: 200rpx;
	}
	
	/* 支付弹出层样式 */
	.pay {
		background-color: #fff;
		display: flex;
		flex-direction: column;
		align-items: center;
	}
	.pay-element {
		width: 100%;
	}
	.pay-element button {
		background-color: #fff;
	}
</style>