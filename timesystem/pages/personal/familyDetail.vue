<template>
	<view class="main">
		<!-- 顶部栏 -->
		<view class="headerBackgroundColor header-background">
			<!-- 返回上一级图片 -->
			<view class="back-image" @click="back">
				<image src="/static/backpic.png"></image>
			</view>
			<view class="header-background-text">
				<text>家庭详情</text>
			</view>
		</view>
		
		<!-- 家庭成员列表 -->
		<view class="familyDetail">
			<!-- 家庭名称 -->
			<view class="familyDetail-familyname">
				<text>{{familyName}}</text>
				<image src="@/static/add.png" v-if="addview" @click="add"></image>
			</view>
			<view  class="familyDetail-familylist">
				<!-- 家主姓名 -->
				<view class="familyDetail-familylist-familyMaster">
					<view class="familyDetail-familylist-familyMaster-text">
						<text>{{familyMaster}}</text>
					</view>				
					<image src="@/static/familyMaster.png"></image>
				</view>
				<!-- 其他成员 -->
				<view class="familyDetail-familylist-element" v-for="(item,index) in familylist" :key="item.id">
					<view class="familyDetail-familylist-element-text">
						<text>{{item.userName}}</text>
					</view>
					<image v-if="addview" src="@/static/delete.png" @click="deleteMember(item.userID)"></image>
				</view>
			</view>
		</view>
		
		<!-- 退出家庭按钮 -->
		<view>
			<button class="exit-family"  @click="exitFamily">退出家庭</button>
			<button v-if="addview" class="exit-family" @click="changeMaster">修改家主</button>
			<button v-if="addview" class="exit-family" @click="deleteFamily">解散家庭</button>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				familyID:null,
				familylist:[],
				familyName:'',
				familyMaster:'',
				// 是否是家主
				addview:false,
			}
		},
		onLoad(options) {
			this.familyID = options.familyID
			this.$api.getFamilyMember(this.familyID).then((res)=>{
				this.familyName = res.data[0].familyName
				this.familyMaster = res.data[0].houseHolderName
				if(this.familyMaster == uni.getStorageSync('userName')){
					this.addview = true
				}
				for(let i=0;i<res.data.length;i++){
					if(this.familyMaster == res.data[i].userName) {
						continue
					}
					this.familylist.push({userName:res.data[i].userName,userID:res.data[i].userID})
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
			// 增加成员
			add(){
				// 记录根节点
				let that = this
				uni.showModal({
				    title: '输入新成员账号',
					editable: true,
				    success: function (response) {
						// 用户点击确定按钮
				        if (response.confirm) {
							let temp = {
								userName:response.content,
								familyID:that.familyID
							}
							that.$api.addFamilyMember(temp).then((res)=>{
								if(res.data == '添加成功'){
									uni.showToast({
										title:'添加成功',
										duration:1000
									})
									setTimeout(function() {
										uni.redirectTo({
										    url: '/pages/personal/familyList'
										})
									},1000)
								}
								else if(res.data == '该用户不存在'){
									uni.showToast({
										title:'该用户不存在',
										icon:'error',
										duration:1000
									})
								}
								else {
									uni.showToast({
										title:'添加失败',
										icon:'error',
										duration:1000
									})
								}
							})
				        }
				    }
				});
			},
			// 删除成员
			deleteMember(userID){
				this.$api.deleteFamilyMember(this.familyID,userID).then((res)=>{
					if(res.data == '删除成功'){
						uni.showToast({
							title:'删除成功',
							duration:1000
						})
						setTimeout(function() {
							uni.redirectTo({
							    url: '/pages/personal/familyList'
							})
						},1000)
					}
				})
			},
			// 转让家主
			changeMaster(){
				// 记录根节点
				let that = this
				let list=[]
				for(let i=0;i<this.familylist.length;i++){
					list.push(this.familylist[i].userName)
				}
				uni.showActionSheet({
					title:'请选择的新家主',
				    itemList: list,
				    success: function (response) {
						if (response.tapIndex > -1) {
							// 用户点击了某个选项
							let temp = {
								familyID:that.familyID,
								houseHolder:that.familylist[response.tapIndex].userID,
								houseHolderName:that.familylist[response.tapIndex].userName
							}
							that.$api.changeFamilyMaster(temp).then((res)=>{
								if(res.data == '修改成功'){
									uni.showToast({
										title:'修改成功',
										duration:1000
									})
								}
								setTimeout(function() {
									uni.redirectTo({
									    url: '/pages/personal/familyList'
									})
								},1000)
							})
				        }
				    }
				});
			},
			// 退出家庭
			exitFamily(){
				if(uni.getStorageSync('userName') == this.familyMaster){
					uni.showToast({
						title:'请先转让家主',
						icon:'error',
						duration:1000
					})
				}
				else {
					this.$api.deleteFamilyMember(this.familyID,uni.getStorageSync('userID')).then((res)=>{
						if(res.data == '删除成功'){
							uni.showToast({
								title:'退出成功',
								duration:1000
							})
							setTimeout(function() {
								uni.redirectTo({
								    url: '/pages/personal/familyList'
								})
							},1000)
						}
						else{
							uni.showToast({
								title:'退出失败',
								icon:'error',
								duration:1000
							})
						}
					})
				}
			},
			deleteFamily(){
				let that = this
				uni.showModal({
					title: '提示',
					// 提示文字
					content: '确认解散家庭吗？',
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
							that.$api.deleteFamily(that.familyID).then((res)=>{
								if(res.data == '删除成功'){
									uni.showToast({
										title:'解散成功',
										duration:1000
									})
									setTimeout(function() {
										uni.redirectTo({
										    url: '/pages/personal/familyList'
										})
									},1000)
								}
								else {
									uni.showToast({
										title:'解散失败',
										icon:'error',
										duration:1000
									})
								}
							})
						}
					},
				})
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
		margin-right: 140rpx;
	}
	.back-image image {
		width: 50rpx;
		height: 50rpx;
	}
	.header-background-text {
		order: 1;
		
		display: flex;
		justify-content: center;
		flex-basis: 250rpx;
		font-size: 38rpx;
		color: white;
	}
	.familyDetail {
		display: flex;
		width: 700rpx;
		flex-direction: column;
		align-items: center;
		margin-top: 210rpx;
		padding: 50rpx 0 20rpx 0;
		background-color: white;
		border-radius: 10px;
		box-shadow: 2px 4px 20px rgb(200, 200, 200);
	}
	.familyDetail-familyname {
		display: flex;
		width: 100%;
		flex-direction: row;
		justify-content: center;
		align-items: center;
		margin-bottom: 40rpx;
		font-size: 40rpx;
	}
	.familyDetail-familyname image {
		width: 45rpx;
		height: 45rpx;
		margin-left: 20rpx;
	}
	.familyDetail-familylist {
		display: flex;
		flex-direction: column;
		align-items: center;
		flex-warp:warp;
		padding-left: 100rpx;
	}
	.familyDetail-familylist-familyMaster {
		display: flex;
		flex-direction: row;
		align-items: center;
		width: 100%;
		margin-bottom: 40rpx;
	}
	.familyDetail-familylist-familyMaster-text {
		font-size: 40rpx;
		width: 300rpx;
		margin-right: 240rpx;
	}
	.familyDetail-familylist-familyMaster image {
		width: 45rpx;
		height: 45rpx;
		margin-right: 50rpx;
	}
	.familyDetail-familylist-element {
		display: flex;
		width: 690rpx;
		flex-direction: row;
		justify-content: flex-start;
		align-items: center;
		margin-bottom: 40rpx;
	}
	.familyDetail-familylist-element-text {
		width: 300rpx;
		font-size: 40rpx;
		margin-right: 245rpx;
	}
	.familyDetail-familylist-element image {
		width: 45rpx;
		height: 45rpx;
	}
	.exit-family{
		width: 700rpx;
		box-shadow: 1px 1px 10px rgb(200, 200, 200);
		margin-top: 30rpx;
	}
</style>