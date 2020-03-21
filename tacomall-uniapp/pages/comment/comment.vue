<template>
	<view>
		<!-- 打星 -->
		<view class="goodsInfo">
			<image :src="goodsInfo.picUrl"></image>
			<text class="example-info">商品名：{{goodsInfo.goodsName}}</text>
		</view>
		<uni-section title="综合感受" type="line"></uni-section>
		<view class="example-body">
			<uni-rate :value="startNum" @change="onChange" />
		</view>
		<!-- 文字评论区 -->
		<view>
			<uni-section title="说说你对商品的看法" type="line"></uni-section>
			<view class="mask-content-input">
				<textarea class="textarea" v-model="comment.content" :placeholder="placeholder" :cursor-spacing="100"
				 :show-confirm-bar="false" :focus="focus" maxlength="140" @blur="bindTextAreaBlur"></textarea>
			</view>
		</view>
		<view class="publishBtn">
			<button class="mini-btn" type="default" size="mini" @click="back()">取消</button>
			<button class="mini-btn" type="primary" size="mini" @click="publishComment()">发布</button>
		</view>
	</view>
</template>

<script>
	import request from "@/libs/api.js";
	import uniRate from '@/components/uni-rate/uni-rate.vue'
	import uniSection from '@/components/uni-section/uni-section.vue'
	export default {
		components: {
			uniRate,
			uniSection
		},
		data() {
			return {
				startNum:5,
				focus: true,
				goodsInfo: {},
				placeholder: "宝贝满足你的期待吗？说说你的综合感受，让商家的服务越来越好吧！",
				comment: {
					orderId: '',
					content: '',
					goodsId: '',
					userId: '',
					star: ''
				}
			}
		},
		onLoad() {
			this.goodsInfo = uni.getStorageSync("goodsInfo");
			uni.removeStorageSync("goodsInfo")
		},
		methods: {
			//跳转回去商品页面
			back(){
				uni.navigateBack({
					delta:1
				})
			},
			//发布评论
			publishComment() {
				debugger
				this.comment.orderId = this.goodsInfo.id;
				this.comment.goodsId = this.goodsInfo.goodsId;
				this.comment.userId = uni.getStorageSync("isLogin").userId;
				let data = {
					valueId:this.comment.goodsId,
					type:0,
					content:this.comment.content,
					userId:this.comment.userId,
					star:this.startNum
				}
				data = JSON.stringify(data)
				request.post('commentController/insert',data).then((res) => {
					if(res.code == 1){
						uni.showToast({
							title:"评论成功",
							icon:"success",
							duration:5000
						})
						uni.navigateBack({
							delta:2
						})
					}
					console.log(res);
				})
				let param = {
					id: this.comment.orderId,
					orderStatus: 4,
				}
				param = JSON.stringify(param);
				request.post('orderController/update', param).then((res) => {
					console.log(res);
				})
			},
			//监听星星变化
			onChange(e) {
				// debugger
				this.startNum = e.value;
				console.log('rate发生改变:' + JSON.stringify(e))
				console.log(this.startNum)
			},
			//监听文本框输入
			bindTextAreaBlur(e) {
				this.comment.content = e.detail.value;
				console.log(e.detail.value)
			}
		}
	}
</script>

<style lang="scss">
	/* 头条小程序组件内不能引入字体 */
	/* #ifdef MP-TOUTIAO */
	@font-face {
		font-family: uniicons;
		font-weight: normal;
		font-style: normal;
		src: url('~@/static/uni.ttf') format('truetype');
	}

	/* #endif */

	/* #ifndef APP-NVUE */
	page {
		display: flex;
		flex-direction: column;
		box-sizing: border-box;
		background-color: #efeff4;
		min-height: 100%;
		height: auto;
	}

	view {
		font-size: 28rpx;
		line-height: inherit;
	}

	.goodsInfo {
		margin: 0 0 20rpx 0;
		border-radius: 25rpx;
	}

	.example {
		padding: 0 30rpx 30rpx;
	}

	.publishBtn {
		text-align: center;
	}

	.mask-content-input {
		width: 718upx; //如果textarea的宽带有问题可以把width改为100%试试
		// width: 100%;
		padding: 10upx 16upx 20upx;

		.textarea {
			height: 100px;
			width: 686upx; //如果textarea的宽带有问题可以把width改为100%试试
			// width: 100%;
			padding: 16upx;
			border: 2upx solid #d5d5d6;
			border-radius: 8upx;
		}
	}

	.example-info {
		padding: 30rpx;
		color: #3b4144;
		background: #ffffff;
	}

	.example-body {
		flex-direction: row;
		flex-wrap: wrap;
		justify-content: center;
		padding: 0;
		font-size: 14rpx;
		background-color: #ffffff;
	}

	/* #endif */
	.example {
		padding: 0 30rpx;
	}

	.example-info {
		/* #ifndef APP-NVUE */
		display: block;
		/* #endif */
		padding: 30rpx;
		color: #3b4144;
		background-color: #ffffff;
		font-size: 30rpx;
	}

	.example-info-text {
		font-size: 28rpx;
		line-height: 36rpx;
	}


	.example-body {
		flex-direction: column;
		padding: 30rpx;
		background-color: #ffffff;
	}

	.word-btn-white {
		font-size: 18px;
		color: #FFFFFF;
	}

	.word-btn {
		/* #ifndef APP-NVUE */
		display: flex;
		/* #endif */
		flex-direction: row;
		align-items: center;
		justify-content: center;
		border-radius: 6px;
		height: 48px;
		margin: 15px;
		background-color: #007AFF;
	}

	.word-btn--hover {
		background-color: #4ca2ff;
	}
</style>
