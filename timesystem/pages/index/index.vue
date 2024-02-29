<template>
	<view class="content">
		<view class="text-area">
			<text class="title">这是任务大厅页面</text>
			<text class="title">{{a}}</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return{
				a:'123456',
			}
		},
		onLoad() {

		},
		methods: {
		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}
</style>
