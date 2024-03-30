<script>
	export default {
		onLaunch: function() {
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
		padding: 70rpx 0 20rpx 0;
		background: linear-gradient(90deg,#ff0000,#ff4d4d,#ff8533,#ff944d,#ff944d,#ffb380);
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
