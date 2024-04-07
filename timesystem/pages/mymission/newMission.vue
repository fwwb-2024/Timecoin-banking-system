<template>
	<view class="main">
		<page-meta :root-font-size="size"></page-meta>
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
		<view class="detail-head">
			<view class="change-lable">
				<text class="change-lable-title">类别：</text>
				<text class="change-lable-content" @click="changeLable">{{taskLable}}</text>
			</view>
			
			<view class="change-title">
				<text class="change-title-title">标题：</text>
				<input class="change-title-content" v-model="title" @input="autoSelect"></input>
			</view>
			
			<!-- 模板显示区域 -->
			<view class="example change-title-content" v-show="exampleShow" v-for="(item,index) in nowexampleName" :key="index">
				<view @click="chooseExample(item)">
					<text>{{item}}</text>
				</view>
			</view>
			
			<view class="change-coins">
				<text class="change-coins-title">悬赏：</text>
				<input class="change-coins-content" type="number" placeholder="0" v-model="coins"></input>
				<text class="change-coins-unit">时间币/时</text>
			</view>

			<view class="change-brief">
				<text class="change-brief-title">简介(不超过50字)：</text>
				<textarea class="change-brief-content" maxlength="50" style="height: 140rpx;" v-model="brief"></textarea>
			</view>
			
			<view class="change-detail">
				<text class="change-detail-title">详情(不超过500字)：</text>
				<view  class="change-detail-content" :style="{'height': detailHeight}">
					<textarea @input="inputDetail" maxlength="500" style="width: 630rpx;" auto-height="true" v-model="detail"></textarea>
				</view>
			</view>
			
			<!-- 图片附件 -->
			<view class="change-image">
				<view v-for="(item,index) in taskPhoto">
					<image id="deletepic" @click="deletePic(index)" src="/static/deletepic.png"></image>
					<view class="change-image-element">
						<image :src="item"></image>
					</view>
				</view>
				<view class="upPic-add" @click="upPic">
					<image src="/static/addpic.png"></image>
				</view>
			</view>
			
			<view class="change-time">
				<text class="change-time-title">开始时间：</text>
				<picker mode="date" :value="startTime" :start="startDate" :end="endDate" @change="bindStartDateChange">
					<view class="change-time-content">{{startTime}}</view>
				</picker>
			</view>
			<view class="change-time">
				<text class="change-time-title">截止时间：</text>
				<picker mode="date" :value="endTime" :start="startDate" :end="endDate" @change="bindEndDateChange">
					<view class="change-time-content">{{endTime}}</view>
				</picker>
			</view>
		</view>
			
		<button class="postMission" @click="postMission">发布</button>
			
		<!-- </view> -->
		
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
				
				<view style="display: flex;flex-direction: row;justify-content: center;margin: 30rpx; 0 20rpx 0">
					<button @click="()=>{$refs.popup.close();familyShow = true;}">取消支付</button>
					<button style="margin-left: 100rpx;background-color: #80aaff;color: white;" @click="pay">确认支付</button>
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
				
				// 支付者ID
				taskEmployerFamilyUserID:null,
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
				
				detailHeight:'380rpx',
				size:'',
				
				// 发布任务的模板
				exampleShow:false,
				example:[
					{lable:'跑腿',coins:10,title:'帮我寄快递',brief:'代寄快递到XX快递点，需在明日前寄送。快递已打包好，轻便易携。',detail:'需要将一件预先打包好的快递送达XX快递点，用XX快递寄送。取件地址位于XX，接单者需在明日前完成送达任务。任务报酬为10时间币，额外产生的交通费用不予报销。联系方式为手机号XX，如有疑问或延迟，请及时电话联系沟通。'},
					{lable:'跑腿',coins:10,title:'帮我拿快递',brief:'代取快递至XX，需要今天内取件并送至指定地址',detail:'要求接单者前往XX快递站点，帮XX代取回一件快递，并将其安全送达至XX。请在今日前完成取件并送达。报酬为10时间币，不包含额外的交通费用。如遇问题或需延迟，请提前通过电话（手机号：XX）联系通知。'},
				],
				// 现在显示的模板名称
				nowexampleName:[],
			}
		},
		created() {
			this.size = uni.getStorageSync("size")
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
			// 输入详情
			inputDetail(value) {
				if(value.detail.cursor >= 160){
					this.detailHeight = 'auto'
				}
				else {
					this.detailHeight = '380rpx'
				}
			},
			// 任务模板匹配
			autoSelect() {
				if(this.title != ''){
					let temp = []
					for(let i=0;i<this.example.length;i++){
						if(this.example[i].title.indexOf(this.title) !== -1){
							temp.push(this.example[i].title)
						}
					}
					if(temp != []){
						this.nowexampleName = temp
						this.exampleShow = true
					}
					else {
						this.nowexampleName = []
						this.exampleShow = false
					}
				}
				else {
					this.nowexampleName = []
					this.exampleShow = false
				}
			},
			// 选中任务模板
			chooseExample(title) {
				for(let i=0;i<this.example.length;i++){
					if(title == this.example[i].title){
						this.taskLable = this.example[i].lable
						this.title = this.example[i].title
						this.brief = this.example[i].brief
						this.detail = this.example[i].detail
						this.coins = this.example[i].coins
						
						this.nowexampleName = []
						this.exampleShow = false
					}
				}
			},			
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
			// 更改任务类别
			changeLable(){
				let temp = ['跑腿','代购','打理','陪伴','线上','其他']
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
			//尺寸约为30cm20 上传图片
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
				this.taskEmployerFamilyUserID = userID
			},
			// 支付
			pay(){
				if(this.taskEmployerFamilyUserID){
					let taskLable = 1
					switch(this.taskLable){
						case '跑腿':taskLable = 2;break;
						case '代购':taskLable = 3;break;
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
							taskEmployerFamilyUserID:this.taskEmployerFamilyUserID
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
				else{
					uni.showToast({
						title: '请选择支付者',
						icon:'error',
						duration: 1000
					});
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
		margin-top: 190rpx;
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
	/* 修改类别 */
	.change-lable {
		display: flex;
		flex-direction: row;
		margin-bottom: 10rpx;
	}
	.change-lable-title {
		font-size: 38rpx;
		font-weight: 600;
	}
	.change-lable-content {
		font-size: 35rpx;
	}
	
	/* 修改标题*/
	.change-title {
		display: flex;
		flex-direction: row;
		margin-bottom: 10rpx;
	}
	.change-title-title {
		font-size: 38rpx;
		font-weight: 600;
	}
	.change-title-content {
		font-size: 35rpx;
		width: 530rpx;
		border: 1px solid #e6e6e6;
	}
	
	/* 任务模板 */
	.example {
		background-color: #f2f2f2;
		width: 530rpx;
		border: #e6e6e6 solid 1px;
		margin-left: 105rpx;
		padding: 5rpx 0 5rpx 3rpx;
	}
	
	/* 修改简介 */
	.change-brief {
		margin-bottom: 10rpx;
	}
	.change-brief-title {
		font-size: 38rpx;
		font-weight: 600;
		margin-bottom: 10rpx;
	}
	.change-brief-content {
		font-size: 35rpx;
		width: 630rpx;
		padding: 5rpx 5rpx 5rpx 5rpx;
		border: 1px solid #e6e6e6;
	}
	/* 修改详情 */
	.change-detail {
		margin-bottom: 10rpx;
	}
	.change-detail-title {
		font-size: 38rpx;
		font-weight: 600;
		margin-bottom: 10rpx;
	}
	.change-detail-content {
		font-size: 35rpx;
		width: 630rpx;
		flex-wrap: wrap;
		padding: 5rpx 5rpx 5rpx 5rpx;
		border: 1px solid #e6e6e6;
	}
	/* 修改悬赏 */
	.change-coins {
		display: flex;
		flex-direction: row;
		margin-bottom: 10rpx;
	}
	.change-coins-title {
		font-size: 38rpx;
		font-weight: 600;
	}
	.change-coins-content {
		font-size: 35rpx;
		width: 70rpx;
	}
	.change-coins-unit {
		font-size: 35rpx;
	}
	/* 修改时间 */
	.change-time {
		display: flex;
		flex-direction: row;
		margin-bottom: 10rpx;
	}
	.change-time-title {
		font-size: 38rpx;
		font-weight: 600;
	}
	.change-time-content {
		font-size: 35rpx;
	}
	/* 修改图片 */
	.change-image {
		display: flex;
		flex-direction: row;
		justify-content: flex-start;
		flex-wrap: wrap;
		width: 650rpx;
		margin: 0 0 30rpx 0;
		border-radius: 15px;
		padding: 0 0 10rpx 20rpx;
	}
	.change-image-title {
		width: 120rpx;
		font-size: 38rpx;
		color: gray;
		margin-bottom: 50rpx;
	}
	.change-image-element {
		margin: 0 10rpx 10rpx 20rpx;
	}
	.change-image-element image {
		width: 600rpx;
	}
	.detail-button {
		margin: 20rpx 0 0 0 ;
		width: 80%;
		height: 80rpx;
		background-color: #80aaff;
		box-shadow: 0px 2px 4px  rgba(0, 0, 0, 0.25);
		color: rgba(255, 255, 255, 1);
		text-align: center;
		font-size: 38rpx;
		line-height: 80rpx;
	}
	.postMission {
		width: 650rpx;
		background-color: #80aaff;
		margin-top: 20rpx;
		margin-bottom: 20rpx;
		color: white;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
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