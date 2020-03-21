<template>
	<view class="uni-product-list">
		<!-- <view v-if="productList.length == 0">没找到相关商品</view> -->
		<hr noshade="noshade" />
		<view class="uni-product" @click="productDetail(product)" v-for="(product,index) in productList" :key="index">
			<view class="image-view">
				<image v-if="renderImage" class="uni-product-image" :src="product.picUrl"></image>
			</view>
			<view class="uni-product-title">{{product.name}}</view>
			<view class="uni-product-price">
				<text class="uni-product-price-favour">￥{{product.retailPrice}}</text>
				<text class="uni-product-price-original">￥{{product.counterPrice}}</text>
				<text class="uni-product-tip">{{product.unit}}</text>
			</view>
		</view>
	</view>
</template>

<script>
	import request from "@/libs/api.js";
	export default {
		data() {
			return {
				name: '',
				title: 'product-list',
				productList: [],
				renderImage: false
			};
		},
		methods: {
			//跳转商品详情
			productDetail(item) {
				// debugger
				console.log(item);
				let id = item.id;
				uni.navigateTo({
					url: '/pages/detail/detail?id=' + id
				});
			},
			//查询商品
			getGoodListByName(action = 'add') {
				let data = {
					name: this.name,
					page: 0,
					pageSize: 0
				}
				request.get('goodsController/getGoodsList', data).then((res) => {
					console.log(res);
					if (res.list.length > 0) {
						this.productList = res.list;
					}else{
						this.productList = [];
						uni.showToast({
							icon:"none",
							title:"没找到相关商品",
							duration:2000
						})
						setTimeout(() => {
							uni.navigateBack({
								delta:1
							})
						},2000)
					}

				})
			}
		},
		onLoad(options) {
			this.name = options.name;
			this.getGoodListByName();
			setTimeout(() => {
				this.renderImage = true;
			}, 300);
		},
		onPullDownRefresh() {
			this.getGoodListByName('refresh');
			// 实际开发中通常是网络请求，加载完数据后就停止。这里仅做演示，加延迟为了体现出效果。
			setTimeout(() => {
				uni.stopPullDownRefresh();
			}, 2000);
		},
		onReachBottom() {
			this.getGoodListByName();
		}
	};
</script>

<style lang="less">
	/* product */
	page {
		background: #F8F8F8;
	}

	view {
		font-size: 28upx;
	}

	.uni-product-list {
		display: flex;
		width: 100%;
		flex-wrap: wrap;
		flex-direction: row;
	}

	.imageContainer {
		width: 750rpx;
		height: 500rpx;
		border-radius: 35upx;
		margin-bottom: 20rpx;

		image {
			width: 100%;
			height: 100%;
		}
	}

	.uni-product {
		border-radius: 35rpx;
		padding: 20upx;
		display: flex;
		flex-direction: column;
		border: 1rpx solid #DCDEE2;
		margin: 3rpx 3rpx 3rpx 3rpx;
	}

	.image-view {
		height: 330upx;
		width: 330upx;
		margin: 12upx 0;
	}

	.uni-product-image {
		height: 330upx;
		width: 330upx;
	}

	.uni-product-title {
		width: 300upx;
		word-break: break-all;
		display: -webkit-box;
		overflow: hidden;
		line-height: 1.5;
		text-overflow: ellipsis;
		-webkit-box-orient: vertical;
		-webkit-line-clamp: 2;
	}

	.uni-product-price {
		margin-top: 10upx;
		font-size: 28upx;
		line-height: 1.5;
		position: relative;
	}

	.uni-product-price-original {
		color: #e80080;
	}

	.uni-product-price-favour {
		color: #888888;
		text-decoration: line-through;
		margin-left: 10upx;
	}

	.uni-product-tip {
		position: absolute;
		right: 10upx;
		background-color: #ff3333;
		color: #ffffff;
		padding: 0 10upx;
		border-radius: 5upx;
	}
</style>
