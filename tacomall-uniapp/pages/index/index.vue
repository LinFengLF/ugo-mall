<template>
	<view class="page index">
		<view class="i-header">
			<view class="h-top">
				<view class="t-logo">
					<image src="../../static/image/indexLogo.png"></image>
				</view>
				<view class="t-search" @tap="nav('/pages/searchGoods/searchGoods')">
					<view class="s-left">
						<text class="iconfont">&#xe617;</text>
						<text class="l-text">搜索好品，共有{{goodsNum}}件商品</text>
					</view>
				</view>
			</view>
		</view>

		<!-- 轮播组件，差点击跳转详情页面 -->
		<view class="i-banner">
			<swiper class="b-swiper" :indicator-dots="true" :autoplay="true" interval="2000" duration="3000">
				<swiper-item v-for="(item, index) in swiperList" :key="index" class="s-item">
					<view class="s-item">
						<image :src="item.url"></image>
					</view>
				</swiper-item>
			</swiper>
		</view>

		<view class="i-service">
			<view class="s-item">
				<text class="iconfont">&#xe645;</text>
				<text class="i-text">新鲜保障</text>
			</view>
			<view class="s-item">
				<text class="iconfont">&#xe655;</text>
				<text class="i-text">健康安全</text>
			</view>
			<view class="s-item">
				<text class="iconfont">&#xe622;</text>
				<text class="i-text">急速发货</text>
			</view>
		</view>

		<!-- 种类视图 -->
		<cg-swiper class="category-container" :swiperList="cateGoryList" @clickItem="clickItem"></cg-swiper>
		<!-- <scroll-view scroll-x="true" class="category-content">
			<view class="i-category">
				<cg-swiper :swiperList="this.cateGoryList" @clickItem="clickItem"></cg-swiper>
				<!-- <view class="c-item" :key="key" v-for="(item, key) in cateGoryList"> -->
		<!-- 				<image src="http://yanxuan.nosdn.127.net/fede8b110c502ec5799702d5ec824792.png"></image>
							<text>居家生活</text> -->
		<!-- <image :src="item.iconUrl"></image> -->
		<!-- <text>{{item.name}}</text> -->
		<!-- </view> -->
		<!-- </view> -->
		<!-- </scroll-view> -->

		<!-- 热卖商品 -->
		<view class="i-flash">
			<view class="f-header">
				<div class="h-left">
					<view class="l-text">
						<text class="iconfont">&#xe600;</text>
						<text>热卖商品</text>
						<text class="iconfont">&#xe600;</text>
					</view>
				</div>
				<div class="h-right">
					<text @click="moreHotGoods">更多</text>
					<text class="iconfont">&#xe601;</text>
				</div>
			</view>

			<view class="f-content" style="background-color: #F8F8F9;">
				<view @click="productDetail(item)" style="background-color: #fefefe;border-radius: 25rpx;margin-bottom: 15rpx;border: 1rpx solid #dcdfe6;"
				 class="c-item" :key="key" v-for="(item, key) in hotGoods">
					<view class="i-image">
						<image :src="item.picUrl"></image>
					</view>
					<view class="i-name">
						<text style="color: #000000;">{{item.name}}</text>
					</view>
					<view class="i-price">
						<text style="color: red;padding-right: 10rpx;">￥{{item.retailPrice}}</text>
						<text style="text-decoration: line-through;font-style: italic;color: #d3d3d3;">￥{{item.counterPrice}}</text>
					</view>
				</view>
			</view>

		</view>
		<!-- 新品上线 -->
		<view v-show="true" class="i-direct-supply">
			<view class="s-header">
				<div class="h-left">
					<text class="iconfont">&#xe606;</text>
					<text>新品上线</text>
					<text class="iconfont">&#xe606;</text>
				</div>
				<div class="h-right">
					<text @click="moreNewGoods">更多</text>
					<text class="iconfont">&#xe601;</text>
				</div>
			</view>

			<view class="s-content">
				<view @click="productDetail(item)" class="c-item" :key="key" v-for="(item, key) in newGoods">
					<view>
						<image :src="item.picUrl" />
					</view>
					<image :src="item.picUrl" />
					<view class="i-desc">
						<text>{{item.name}}</text>
						<text style="color: #ED4014;">￥{{item.retailPrice}}</text>
					</view>
				</view>
			</view>

		</view>

		<view class="i-newest">
			<view class="n-header">
				<div class="h-left">
					<text class="iconfont">&#xe6f3;</text>
					<text>全部商品</text>
				</div>
				<!-- <div class="h-right">
					<text>更多</text>
					<text class="iconfont">&#xe601;</text>
				</div> -->
			</view>
			<view class="n-content">
				<view @click="productDetail(item)" class="c-item" :key="key" v-for="(item, key) in productList">
					<view class="i-image">
						<image :src="item.picUrl"></image>
					</view>
					<view class="i-name">
						<text>{{item.name}}</text>
					</view>
					<view class="i-price">
						<text>￥{{item.retailPrice}}</text>
						<text>￥{{item.counterPrice}}</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import cgSwiper from '@/components/cg-swiper/cg-swiper.vue';
	import uniIcon from "@/components/uni-icons/uni-icons.vue";
	import request from "@/libs/api.js";
	export default {
		components: {
			cgSwiper
		},
		name: "index",
		data() {
			return {
				isOrigin: true,
				goodsNum: "126",
				swiperList: [],
				titleNViewBackground: '',
				swiperCurrent: 0,
				swiperLength: 0,
				cateGoryList: [],
				cateGoryPage: 1,
				cateGoryPageSize: 10,
				hotGoods: [],
				newGoods: [],
				renderImage: false,
				newFlag: true,
				productList: []
			}
		},
		onLoad() {
			this.initData();
			this.isOrigin = false;
			this.getSwiper();
			this.getCateGory();
			this.getHotGoods();
			this.getNewGoods();
			setTimeout(() => {
				this.renderImage = true;
			}, 300);
		},
		onShow() {
			if (this.isOrigin) {
				this.initData()
			}
			this.isOrigin = true;
		},
		// onPullDownRefresh() {
		// 	this.getNewGoods('refresh');
		// 	// 实际开发中通常是网络请求，加载完数据后就停止。这里仅做演示，加延迟为了体现出效果。
		// 	setTimeout(() => {
		// 		uni.stopPullDownRefresh();
		// 	}, 2000);
		// },
		// onReachBottom() {
		// 	this.getNewGoods();
		// },
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
			//点击分类
			clickItem(data) {
				console.log(data);
				let id = data.id;
				let titleText = data.name;
				console.log(id);
				//在起始页面跳转分类商品页面并传递参数
				// uni.navigateTo({
				//     url: '/pages/categoryGoods/categoryGoods?id=' + id
				// });
				uni.navigateTo({
					url: '/pages/productlist/productlist?id=' + id + '&titleText=' + titleText
				});
			},
			//跳转更多热卖商品页面
			moreHotGoods() {
				uni.navigateTo({
					url: '/pages/more/more'
				})
			},
			// 跳转更多新品
			moreNewGoods() {
				uni.navigateTo({
					url: '/pages/moreNewGoods/moreNewGoods'
				})
			},
			//轮播图切换修改背景色
			swiperChange(e) {
				const index = e.detail.current;
				this.swiperCurrent = index;
				this.titleNViewBackground = this.carouselList[index].color;
			},
			initData() {
				uni.request({
					url: "http://127.0.0.1:8080/ugoMall/goodsController/getGoodsList",
					data: {
						page: 0,
						pageSize: 0,
						name: "",
						id: "",
						goodsSn: ""
					},
					success: (res) => {
						// console.log(res.data)
						uni.hideLoading();
						this.goodsNum = res.data.total;
						this.productList = res.data.list;
						// for (let i = 0; i < res.data.list.length; i++) {
						// 	if (res.data.list[i].isHot == 1) {
						// 		this.hotGoods.push(res.data.list[i])
						// 	}
						// }
						// console.log(this.hotGoods);
					}
				})
			},
			//获得轮播图
			getSwiper() {
				uni.request({
					url: "http://127.0.0.1:8080/ugoMall/adController/getAdInfo",
					data: {
						page: 0,
						pageSize: 0,
						name: "",
						content: ""
					},
					success: (res) => {
						// console.log(res.data);
						// console.log(res.data.list.length);
						uni.hideLoading();
						for (var i = 0; i < res.data.list.length; i++) {
							// console.log(res.data.list[i]);
							if (res.data.list[i].enabled == 1) {
								this.swiperList.push(res.data.list[i]);
							}
						}
						// console.log(this.swiperList)
					}
				})
			},
			//获得种类类目
			getCateGory() {
				uni.request({
					url: "http://127.0.0.1:8080/ugoMall/categoryController/getList",
					data: {
						page: 0,
						pageSize: 0,
					},
					success: (res) => {
						// console.log(res.data);
						// console.log(res.data.list.length);
						uni.hideLoading();
						this.cateGoryList = res.data.list;
					}
				})
			},
			getHotGoods() {
				uni.request({
					url: "http://127.0.0.1:8080/ugoMall/goodsController/getGoodsList",
					data: {
						page: 1,
						pageSize: 20,
						name: "",
						id: "",
						goodsSn: ""
					},
					success: (res) => {
						// console.log(res.data)
						uni.hideLoading();
						for (let i = 0; i < res.data.list.length; i++) {
							if (res.data.list[i].isHot == 1) {
								// || res.data.list[i].name.length > 20
								// res.data.list[i].name = res.data.list[i].name.substring(0, 20) + '...'
								this.hotGoods.push(res.data.list[i])
							}
						}
						// console.log(this.hotGoods);
					}
				})
			},
			//获取上线新品
			getNewGoods() {
				uni.request({
					url: "http://127.0.0.1:8080/ugoMall/goodsController/getGoodsList",
					data: {
						page: 1,
						pageSize: 20,
						name: "",
						id: "",
						goodsSn: ""
					},
					success: (res) => {
						// console.log(res.data)
						uni.hideLoading();
						if (res.data.list.length > 0) {
							this.newFlag = true;
						}
						for (let i = 0; i < res.data.list.length; i++) {
							// console.log(this.newFlag)
							if (res.data.list[i].isNew == 1) {
								this.newGoods.push(res.data.list[i])
							}
						}
						// console.log(this.newGoods);
					}
				})
			}
		}
	}
</script>

<style lang="less">
	@import "./index";

	.index .i-banner {
		padding-top: 90rpx;
	}

	// .i-banner {
	// 	padding-top: 0rpx;
	// }

	// .c-item hotGood {
	// 	border: 1px solid #dcdfe6;
	// }

	/* 头部 轮播图 */
	.carousel-section {
		position: relative;
		padding-top: 10px;

		.titleNview-placing {
			height: var(--status-bar-height);
			padding-top: 44px;
			box-sizing: content-box;
		}

		.titleNview-background {
			position: absolute;
			top: 0;
			left: 0;
			width: 100%;
			height: 426upx;
			transition: .4s;
		}
	}

	.carousel {
		width: 100%;
		height: 350upx;

		.carousel-item {
			width: 100%;
			height: 100%;
			padding: 0 28upx;
			overflow: hidden;
		}

		image {
			width: 100%;
			height: 100%;
			border-radius: 10upx;
		}
	}

	.swiper-dots {
		display: flex;
		position: absolute;
		left: 60upx;
		bottom: 15upx;
		width: 72upx;
		height: 36upx;
		background-image: url(data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAMgAAABkCAYAAADDhn8LAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAyZpVFh0WE1MOmNvbS5hZG9iZS54bXAAAAAAADw/eHBhY2tldCBiZWdpbj0i77u/IiBpZD0iVzVNME1wQ2VoaUh6cmVTek5UY3prYzlkIj8+IDx4OnhtcG1ldGEgeG1sbnM6eD0iYWRvYmU6bnM6bWV0YS8iIHg6eG1wdGs9IkFkb2JlIFhNUCBDb3JlIDUuNi1jMTMyIDc5LjE1OTI4NCwgMjAxNi8wNC8xOS0xMzoxMzo0MCAgICAgICAgIj4gPHJkZjpSREYgeG1sbnM6cmRmPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5LzAyLzIyLXJkZi1zeW50YXgtbnMjIj4gPHJkZjpEZXNjcmlwdGlvbiByZGY6YWJvdXQ9IiIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bWxuczp4bXA9Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC8iIHhtcE1NOkRvY3VtZW50SUQ9InhtcC5kaWQ6OTk4MzlBNjE0NjU1MTFFOUExNjRFQ0I3RTQ0NEExQjMiIHhtcE1NOkluc3RhbmNlSUQ9InhtcC5paWQ6OTk4MzlBNjA0NjU1MTFFOUExNjRFQ0I3RTQ0NEExQjMiIHhtcDpDcmVhdG9yVG9vbD0iQWRvYmUgUGhvdG9zaG9wIENDIDIwMTcgKFdpbmRvd3MpIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6Q0E3RUNERkE0NjExMTFFOTg5NzI4MTM2Rjg0OUQwOEUiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6Q0E3RUNERkI0NjExMTFFOTg5NzI4MTM2Rjg0OUQwOEUiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz4Gh5BPAAACTUlEQVR42uzcQW7jQAwFUdN306l1uWwNww5kqdsmm6/2MwtVCp8CosQtP9vg/2+/gY+DRAMBgqnjIp2PaCxCLLldpPARRIiFj1yBbMV+cHZh9PURRLQNhY8kgWyL/WDtwujjI8hoE8rKLqb5CDJaRMJHokC6yKgSCR9JAukmokIknCQJpLOIrJFwMsBJELFcKHwM9BFkLBMKFxNcBCHlQ+FhoocgpVwwnv0Xn30QBJGMC0QcaBVJiAMiec/dcwKuL4j1QMsVCXFAJE4s4NQA3K/8Y6DzO4g40P7UcmIBJxbEesCKWBDg8wWxHrAiFgT4fEGsB/CwIhYE+AeBAAdPLOcV8HRmWRDAiQVcO7GcV8CLM8uCAE4sQCDAlHcQ7x+ABQEEAggEEAggEEAggEAAgQACASAQQCCAQACBAAIBBAIIBBAIIBBAIABe4e9iAe/xd7EAJxYgEGDeO4j3EODp/cOCAE4sYMyJ5cwCHs4rCwI4sYBxJ5YzC84rCwKcXxArAuthQYDzC2JF0H49LAhwYUGsCFqvx5EF2T07dMaJBetx4cRyaqFtHJ8EIhK0i8OJBQxcECuCVutxJhCRoE0cZwMRyRcFefa/ffZBVPogePihhyCnbBhcfMFFEFM+DD4m+ghSlgmDkwlOgpAl4+BkkJMgZdk4+EgaSCcpVX7bmY9kgXQQU+1TgE0c+QJZUUz1b2T4SBbIKmJW+3iMj2SBVBWz+leVfCQLpIqYbp8b85EskIxyfIOfK5Sf+wiCRJEsllQ+oqEkQfBxmD8BBgA5hVjXyrBNUQAAAABJRU5ErkJggg==);
		background-size: 100% 100%;

		.num {
			width: 36upx;
			height: 36upx;
			border-radius: 50px;
			font-size: 24upx;
			color: #fff;
			text-align: center;
			line-height: 36upx;
		}

		.sign {
			position: absolute;
			top: 0;
			left: 50%;
			line-height: 36upx;
			font-size: 12upx;
			color: #fff;
			transform: translateX(-50%);
		}
	}

	.uni-product-list {
		display: flex;
		width: 100%;
		flex-wrap: wrap;
		flex-direction: row;
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

	.category-container {
		view {
			background: white;
		}
	}
</style>
