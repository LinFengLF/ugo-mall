<template>
	<view>
		<!-- 顶部导航 -->
		<view class="topTabBar" :style="{position:headerPosition,top:headerTop}">
			<view class="grid" v-for="(grid,tbIndex) in orderType" :key="tbIndex" @tap="showType(tbIndex)">
				<view class="text" :class="[tbIndex==tabbarIndex?'on':'']">{{grid}}</view>
			</view>
		</view>
		<!-- 考虑非APP端长列表和复杂的DOM使用scroll-view会卡顿，所以漂浮顶部选项卡使用page本身的滑动 -->
		<view class="order-list">
			<view class="list">
				<view class="onorder" v-if="list.length==0">
					<image src="../../static/img/noorder.png"></image>
					<view class="text">
						没有相关订单
					</view>
				</view>
				<view class="row" v-for="(row,index) in list" :key="index">
					<view class="type">{{orderStatus[row.orderStatus]}} {{row.payTime}}</view>
					<view class="order-info">
						<view class="left">
							<image :src="row.picUrl"></image>
						</view>
						<view class="right">
							<view class="name">
								{{row.goodsName}}
							</view>
							<view class="spec">{{row.specifications}}</view>
							<view class="address">收货地址：{{row.address}}</view>
							<view class="price-number">
								￥<view class="price">{{row.price}}</view>
								x<view class="number">{{row.num}}</view>
							</view>
						</view>

					</view>
					<view class="detail">
						<view class="number">共{{row.num}}件商品</view>
						<view class="sum">合计￥<view class="price">{{row.actualPrice}}</view>
						</view>
						<view class="nominal">(留言： {{row.message}})</view>
					</view>
					<view class="btns">
						<block v-if="row.orderStatus== 0">
							<view class="default" @tap="cancelOrder(row)">取消订单</view>
							<view class="pay" @tap="toPayment(row)">付款</view>
						</block>
						<block v-if="row.orderStatus== 1">
							<view class="default" @tap="remindDeliver(row)">提醒发货</view>
						</block>
						<block v-if="row.orderStatus== 2">
							<view class="default" @tap="showLogistics(row)">查看物流</view>
							<view class="pay" @click="confirmReceipt(row)">确认收货</view>
							<!-- <view class="pay">我要退货</view> -->
						</block>
						<block v-if="row.orderStatus== 3">
							<!-- <button @click="toggleMask('show')">评价</button> -->
							<view class="default" @click="comment(row)">评价</view>
							<!-- <view class="default">再次购买</view> -->
						</block>
						<block v-if="row.orderStatus== 4">
							<!-- <view class="default">再次购买</view> -->
						</block>
						<block v-if="row.orderStatus== 5">
							<view class="default">查看进度</view>
						</block>
						<block v-if="row.orderStatus== 6">
							<view class="default">已取消</view>
						</block>
					</view>
				</view>
				<ygc-comment ref="ygcComment" :placeholder="'发布评论'" @pubComment="pubComment"></ygc-comment>
			</view>
		</view>
	</view>
</template>
<script>
	import request from "@/libs/api.js";
	import ygcComment from '@/components/ygc-comment/ygc-comment.vue';
	export default {
		components: {
			ygcComment
		},
		data() {
			return {
				count: 0,
				userId: '',
				headerPosition: "fixed",
				headerTop: "0px",
				orderStatus: {
					0: '等待付款',
					1: '等待商家发货',
					2: '商家已发货',
					3: '等待用户评价',
					4: '交易已完成',
					5: '商品退货处理中',
					6: '用户已取消订单',
					7: '系统已取消订单',
					8: '系统自动收货'
				},
				orderType: ['全部', '待付款', '待发货', '待收货', '待评价'],
				//订单列表 演示数据
				orderList: [
					//数组1——全部订单
					[],
					//数组2——待付款
					[],
					//数组3——待发货
					[],
					//数组4——待收货
					[],
					//数组5——待评价
					[],
					//数组6——退货退款
					[]

				],
				list: [],
				tabbarIndex: 0
			};
		},
		onLoad(option) {
			// debugger
			this.userId = uni.getStorageSync("isLogin").userId;
			//option为object类型，会序列化上个页面传递的参数
			console.log("option: " + JSON.stringify(option));
			let tbIndex = parseInt(option.tbIndex) + 1;
			this.list = this.orderList[tbIndex];
			this.tabbarIndex = tbIndex;
			//兼容H5下排序栏位置
			// #ifdef H5
			let Timer = setInterval(() => {
				let uniHead = document.getElementsByTagName('uni-page-head');
				if (uniHead.length > 0) {
					this.headerTop = uniHead[0].offsetHeight + 'px';
					clearInterval(Timer); //清除定时器
				}
			}, 1);
			// #endif
		},
		onShow() {
			this.initData();
		},
		onPageScroll(e) {
			return;
			//兼容iOS端下拉时顶部漂移
			this.headerPosition = e.scrollTop >= 0 ? "fixed" : "absolute";
		},
		methods: {
			//跳转发表评论页面
			comment(row){
				console.log(row);
				uni.setStorage({
					key:"goodsInfo",
					data:row,
					success() {
						uni.navigateTo({
							url: '/pages/comment/comment'
						})
					},
					fail() {
						console.log(缓存设置失败)
					}
				})
			},
			//确认收货
			confirmReceipt(row) {
				let data = {
					id: row.id,
					orderStatus: 3, //3表示确认收货，待评价
				}
				data = JSON.stringify(data);
				uni.showModal({
					title: '确认收货',
					content: '是否确认收货？',
					success: (res) => {
						if (res.confirm) {
							request.post('orderController/update', data).then((res) => {
								console.log(res);
								this.initData();
							})
						} else if (res.cancel) {
							// console.log('用户点击取消');
						}
					}
				});
			},
			//初始化数据
			initData() {
				let data = {
					page: 0,
					pageSize: 0,
					userId: this.userId,
				}
				request.get('orderController/getList', data).then((res) => {
					for (let i = 0; i < res.list.length; i++) {
						if (res.list[i].orderStatus == 0) {
							this.orderList[1].push(res.list[i]);
						} else if (res.list[i].orderStatus == 1) {
							this.orderList[2].push(res.list[i]);
						} else if (res.list[i].orderStatus == 2) {
							this.orderList[3].push(res.list[i]);
						} else if (res.list[i].orderStatus == 3) {
							this.orderList[4].push(res.list[i]);
						} else if (res.list[i].orderStatus == 4) {
							this.orderList[5].push(res.list[i]);
						} else {
							this.orderList[0].push(res.list[i]);
						}
					}
					this.orderList[0] = res.list;
					console.log(res)
				})
			},
			showType(tbIndex) {
				this.tabbarIndex = tbIndex;
				this.list = this.orderList[tbIndex];
			},
			//即时物流追踪
			showLogistics(row) {
				// debugger
				uni.setStorage({
					key:'orderInfo',
					data:row,
					success() {
						uni.navigateTo({
							url:'/pages/logisticsTrack/logisticsTrack'
						})
					}
				})
			},
			//提醒发货
			remindDeliver(row) {
				if (this.count <= 3) {
					uni.showToast({
						title: '已提醒商家发货'
					})
				} else {
					uni.showToast({
						title: '已达到提醒上限'
					})
				}
				this.count++;
			},
			//取消订单
			cancelOrder(row) {
				// debugger
				console.log(row);
				uni.showModal({
					title: '取消订单',
					content: '确定取消此订单？',
					success: (res) => {
						if (res.confirm) {
							console.log('用户点击确定');
							this.doCancelOrder(row.id);
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});
			},
			//取消订单
			doCancelOrder(id) {
				// debugger
				let data = {
					id: id,
					orderStatus: 6, //6为订单已取消
				}
				data = JSON.stringify(data);
				request.post('orderController/update', data).then((res) => {
					console.log(res);
					this.initData();
				})
				// let typeNum = this.orderList.length;
				// for (let i = 0; i < typeNum; i++) {
				// 	let list = this.orderList[i];
				// 	let orderNum = list.length;
				// 	if (orderNum > 0 && list[0].orderStatus == 0) {
				// 		for (let j = 0; j < orderNum; j++) {
				// 			if (this.orderList[i][j].id == id) {
				// 				this.orderList[i][j].orderStatus = 6;
				// 				break;
				// 			}
				// 		}
				// 	}
				// }
			},
			//去支付
			toPayment(row) {
				// debugger
				//本地模拟订单提交UI效果
				uni.showLoading({
					title: '正在获取订单...'
				})
				let paymentOrder = [];
				paymentOrder.push(row);
				row.name = row.goodsName;
				let payment = row.num * row.price;
				setTimeout(() => {
					uni.setStorage({
						key: 'paymentOrder',
						data: paymentOrder,
						success: () => {
							uni.hideLoading();
							uni.navigateTo({
								url: '/pages/pay/payment/paymentTwice?amount=' + payment
							})
						}
					})
				}, 500)
			}
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #f3f3f3;
	}

	.topTabBar {
		width: 100%;
		position: fixed;
		top: 0;
		z-index: 10;
		background-color: #f8f8f8;
		height: 80upx;
		display: flex;
		justify-content: space-around;

		.grid {
			width: 20%;
			height: 80upx;
			display: flex;
			justify-content: center;
			align-items: center;
			color: #444;
			font-size: 28upx;

			.text {
				height: 76upx;
				display: flex;
				align-items: center;

				&.on {
					color: #f06c7a;
					border-bottom: solid 4upx #f06c7a;
				}
			}

		}
	}

	.order-list {
		margin-top: 80upx;
		padding-top: 20upx;
		width: 100%;

		.list {
			width: 94%;
			margin: 0 auto;

			.onorder {
				width: 100%;
				height: 50vw;
				display: flex;
				justify-content: center;
				align-content: center;
				flex-wrap: wrap;

				image {
					width: 20vw;
					height: 20vw;
					border-radius: 100%;
				}

				.text {
					width: 100%;
					height: 60upx;
					font-size: 28upx;
					color: #444;
					display: flex;
					justify-content: center;
					align-items: center;
				}
			}

			.row {
				width: calc(100% - 40upx);
				padding: 10upx 20upx;
				border-radius: 10upx;
				background-color: #fff;
				margin-bottom: 20upx;

				.type {
					font-size: 26upx;
					color: #ec652f;
					height: 50upx;
					display: flex;
					align-items: center;
				}

				.order-info {
					width: 100%;
					display: flex;

					.left {
						flex-shrink: 0;
						width: 25vw;
						height: 25vw;

						image {
							width: 25vw;
							height: 25vw;
							border-radius: 10upx;
						}
					}

					.right {
						width: 100%;
						margin-left: 10upx;
						position: relative;

						.name {
							width: 100%;
							font-size: 28upx;
							display: -webkit-box;
							-webkit-box-orient: vertical;
							-webkit-line-clamp: 2;
							overflow: hidden;
						}

						.spec {
							color: #a7a7a7;
							font-size: 22upx;
						}

						.address {
							color: #6f6f6f;
							font-size: 26upx;
						}

						.price-number {
							position: absolute;
							bottom: 0;
							width: 100%;
							display: flex;
							justify-content: flex-end;
							font-size: 22upx;
							color: #333;
							display: flex;
							align-items: flex-end;

							.price {
								font-size: 24upx;
								margin-right: 5upx;
							}

						}
					}
				}

				.detail {
					display: flex;
					justify-content: flex-end;
					align-items: flex-end;
					height: 60upx;
					font-size: 26upx;

					.sum {
						padding: 0 8upx;
						display: flex;
						align-items: flex-end;

						.price {
							font-size: 30upx;
						}
					}

				}

				.btns {
					height: 80upx;
					display: flex;
					align-items: center;
					justify-content: flex-end;

					view {
						min-width: 120upx;
						height: 50upx;
						padding: 0 20upx;
						border-radius: 50upx;
						display: flex;
						justify-content: center;
						align-items: center;
						font-size: 28upx;
						margin-left: 20upx;
					}

					.default {
						border: solid 1upx #ccc;
						color: #666;
					}

					.pay {
						border: solid 1upx #ec652f;
						color: #ec652f;
					}
				}
			}
		}
	}
</style>
