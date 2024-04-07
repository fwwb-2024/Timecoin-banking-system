<script>
	export default {
		onLaunch: function() {
			uni.setStorageSync("size","3.5px")
			if(!(uni.getStorageSync("size"))){
				uni.setStorageSync("size","3.5px")
			}
			
			// 账号状态
			if(uni.getStorageSync('userStatus')){
				uni.setStorageSync("userStatus",0)
			}
			
			
			try {
				const value = uni.getStorageSync('position');
				if (!value) {
					uni.setStorage({
						key: 'position',
						data: '需求者',
					});
				}
			} catch (e) {
				uni.showToast({
					title: '网络异常',
					icon:'error',
					duration: 2000
				});
			}
		},
		onShow: function() {
		},
		onHide: function() {
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	@import '@/uni_modules/uni-scss/index.scss';
	/* #ifndef APP-NVUE */
	@import '@/static/customicons.css';
	// 设置整个项目的背景色
	page {
		background-color: #f5f5f5;
	}

	/* #endif */
	.example-info {
		font-size: 14px;
		color: #333;
		padding: 10px;
	}
	.headerBackgroundColor {
		padding: 70rpx 0 40rpx 0;
		background: linear-gradient(90deg,#ac3cf6,#7d6cf9,#3ca5f6,#85c7fa);
		position: fixed!important;
		top: 0px!important;
		z-index: 2;
	}
	.body {
		display: flex;
		flex-direction: column;
		align-items: center;
	}
</style>
