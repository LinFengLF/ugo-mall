<template>
	<view>
		<view class="block">
			<view class="content">
				<view class="orderinfo">
					<view class="row">
						<view class="nominal">订单名称:</view>
						<view style="font-weight: bold;" class="text">{{orderName}}</view>
					</view>
					<view class="row">
						<view class="nominal">订单金额:</view>
						<view class="text" style="color: #ED4014;">{{amount}}元</view>
					</view>
				</view>
			</view>
		</view>
		<view class="block">
			<view class="title">
				选择支付方式
			</view>
			<view class="content">
				<view class="pay-list">
					<view class="row" @tap="paytype='alipay'">
						<view class="left">
							<image src="../../../static/img/alipay.png"></image>
						</view>
						<view class="center">
							支付宝支付
						</view>
						<view class="right">
							<radio :checked="paytype=='alipay'" color="#f06c7a" />
						</view>
					</view>
					<view class="row" @tap="paytype='wxpay'">
						<view class="left">
							<image src="../../../static/img/wxpay.png"></image>
						</view>
						<view class="center">
							微信支付
						</view>
						<view class="right">
							<radio :checked="paytype=='wxpay'" color="#f06c7a" />
						</view>
					</view>
				</view>
			</view>
		</view>
		<view class="pay">
			<view class="btn" @tap="doDeposit">立即支付</view>
			<view class="tis">
				点击立即支付，即代表您同意<view class="terms">
					《条款协议》
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import request from "@/libs/api.js";
	export default {
		data() {
			return {
				buyList: [],
				orderId: '',
				name: '',
				tel: '',
				userId: '',
				address: '',
				note: '',
				amount: 0,
				orderName: '',
				paytype: 'alipay' //支付类型
			};
		},
		onShow() {
			this.insertOrder();
			this.deleteBySelectIdFromCart();
		},
		onBackPress() {
			this.updateOrderStatus();
		},
		onLoad(e) {
			debugger;
			this.amount = parseFloat(e.amount).toFixed(2);
			uni.getStorage({
				key: 'paymentOrder',
				success: (e) => {
					if (e.data.length > 1) {
						this.orderName = '多商品合并支付'
					} else {
						this.orderName = e.data[0].name;
					}
					uni.removeStorage({
						key: 'paymentOrder'
					})
				}
			})
			this.address = uni.getStorageSync("orderInfo").address;
			this.note = uni.getStorageSync("orderInfo").note;
			this.tel = uni.getStorageSync("orderInfo").tel;
			this.name = uni.getStorageSync("orderInfo").name;
			this.buyList = uni.getStorageSync("buylist");
			// console.log(address);
		},
		methods: {
			//点击了返回后将订单状态设置为未付款
			updateOrderStatus(){
				let data = {
					id: this.orderId,
					orderStatus: 0, //点击返回，就是未付款
				}
				data = JSON.stringify(data);
				uni.showModal({
					title: '确认返回',
					content: '确认暂时不支付吗？',
					success: (res) => {
						if (res.confirm) {
							request.post('orderController/update', data).then((res) => {
								console.log(res);
							})
						} else if (res.cancel) {
							// console.log('用户点击取消');
						}
					}
				});
			},
			//从购物车中删除选中商品
			deleteBySelectIdFromCart() {
				// debugger
				let ids = uni.getStorageSync("selectId");
				// const params = Object.fromEntries(ids.map(item => [item]));
				// console.log(params);
				uni.removeStorageSync("selectId");
				let data = ids;
				request.post('goodsCartController/deleteByBatch', data).then((res) => {
					console.log(res);
				})
			},
			//添加订单
			insertOrder() {
				let that = this;
				that.userId = uni.getStorageSync("isLogin").userId;
				let data = {
					userId: that.userId,
					orderStatus: 0, //0表示未付款
					consignee: that.name,
					mobile: that.tel,
					address: that.address,
					message: that.note,
					actualPrice: that.amount
				}
				console.log(data);
				data = JSON.stringify(data);
				request.post('orderController/insert', data).then((res) => {
					console.log(res);
					that.orderId = res.orderId;
					that.insertOrderGoods();
				})

			},
			//添加订单商品
			insertOrderGoods() {
				for (let i = 0; i < this.buyList.length; i++) {
					let data = {
						orderId: this.orderId,
						goodsId: this.buyList[i].id,
						goodsName: this.buyList[i].name,
						goodsSn: this.buyList[i].id,
						num: this.buyList[i].number,
						price: this.buyList[i].price,
						specifications: this.buyList[i].spec,
						picUrl: this.buyList[i].img
					}
					data = JSON.stringify(data);
					request.post('orderGoodsController/insert', data).then((res) => {
						console.log(res)
					})
				}
			},
			//调用支付
			doDeposit() {
				// debugger
				var time = new Date();
				//模板模拟支付，实际应用请调起微信/支付宝
				uni.showLoading({
					title: '支付中...'
				});
				let data = {
					id: this.orderId,
					orderStatus: 1,
					// payTime:time.toLocaleString()
				}
				data = JSON.stringify(data);
				request.post('orderController/update', data).then((res) => {
					console.log(res);
				})
				setTimeout(() => {
					uni.hideLoading();
					uni.showToast({
						title: '支付成功'
					});
					setTimeout(() => {
						uni.redirectTo({
							url: '/pages/pay/success/success?amount=' + this.amount
						});
					}, 300);
				}, 700)
			}
		}
	}
</script>

<style lang="scss">
	.block {
		width: 94%;
		padding: 0 3% 40upx 3%;

		.title {
			width: 100%;
			font-size: 34upx;
		}

		.content {
			.orderinfo {
				width: 100%;
				border-bottom: solid 1upx #eee;

				.row {
					width: 100%;
					height: 90upx;
					display: flex;
					align-items: center;

					.nominal {
						flex-shrink: 0;
						font-size: 32upx;
						color: #7d7d7d;
					}

					.text {
						width: 70%;
						margin-left: 10upx;
						overflow: hidden;
						text-overflow: ellipsis;
						white-space: nowrap;
						font-size: 32upx;
					}
				}
			}

			.pay-list {
				width: 100%;
				border-bottom: solid 1upx #eee;

				.row {
					width: 100%;
					height: 120upx;
					display: flex;
					align-items: center;

					.left {
						width: 100upx;
						flex-shrink: 0;
						display: flex;
						align-items: center;

						image {
							width: 80upx;
							height: 80upx;
						}
					}

					.center {
						width: 100%;
						font-size: 30upx;
					}

					.right {
						width: 100upx;
						flex-shrink: 0;
						display: flex;
						justify-content: flex-end;
					}
				}
			}
		}
	}

	.pay {
		margin-top: 20upx;
		width: 100%;
		display: flex;
		justify-content: center;
		flex-wrap: wrap;

		.btn {
			width: 70%;
			height: 80upx;
			border-radius: 80upx;
			display: flex;
			justify-content: center;
			align-items: center;
			color: #fff;
			background-color: #f06c7a;
			box-shadow: 0upx 5upx 10upx rgba(0, 0, 0, 0.2);
		}

		.tis {
			margin-top: 10upx;
			width: 100%;
			font-size: 24upx;
			display: flex;
			justify-content: center;
			align-items: baseline;
			color: #999;

			.terms {
				color: #5a9ef7;
			}
		}
	}
</style>
