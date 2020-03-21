<template>
	<view class="page center">
		<!-- <view class="c-header">
            <view class="h-right">
                <text class="iconfont">&#xe674;</text>
            </view>
        </view> -->

		<view class="c-account" @click="checkLogin">
			<!-- @tap="nav('/pages/login/index') -->
			<view class="a-left">
				<view class="l-avatar" v-if="isLogin">
					<img :src="userInfo.userPhoto">
				</view>

				<view class="l-avatar" v-else>
					<img src="https://m.mi.com/static/img/avatar.76a75b8f17.png" alt="">
				</view>

				<view class="l-name" v-if="isLogin">
					<text>{{userInfo.userName}}</text>
				</view>
				<view class="l-name" v-else>
					<text>请登录</text>
				</view>
			</view>
			<view class="a-right">
				<text class="iconfont">&#xe601;</text>
			</view>
		</view>

		<view class="c-order">
			<view class="o-header">
				<view class="h-left">
					<text>我的订单</text>
				</view>
				<!-- <view class="h-right" @click="toOrderList(-1)">
					<text>全部订单</text>
					<text class="iconfont">&#xe601;</text>
				</view> -->
			</view>
			<view class="o-content border-1px-top">
				<view class="c-item" @click="toOrderList(0)">
					<text class="iconfont">&#xe68d;</text>
					<text class="i-text">待付款</text>
				</view>
				<view class="c-item" @click="toOrderList(1)">
					<text class="iconfont">&#xe68d;</text>
					<text class="i-text">待发货</text>
				</view>
				<view class="c-item" @click="toOrderList(2)">
					<text class="iconfont">&#xe647;</text>
					<text class="i-text">待收货</text>
				</view>
				<view class="c-item" @click="toOrderList(3)">
					<text class="iconfont">&#xe79d;</text>
					<text class="i-text">待评价</text>
				</view>
				<!-- <view class="c-item" @click="toOrderList(4)">
					<text class="iconfont">&#xe622;</text>
					<text class="i-text">退换货</text>
				</view> -->
			</view>
		</view>
		<view class="c-service">
			<view class="s-header">
				<view class="h-left">
					<text>我的服务</text>
				</view>
			</view>
			<view class="s-content">
				<!--                <view class="c-line border-1px-top">
                    <view class="l-item">
                        <text class="iconfont">&#xe69b;</text>
                        <text>拼团订单</text>
                    </view>
                    <view class="l-item">
                        <text class="iconfont">&#xe63c;</text>
                        <text>优惠券</text>
                    </view>
                    <view class="l-item">
                        <text class="iconfont">&#xe608;</text>
                        <text>新人邀请</text>
                        <text>立享红包</text>
                    </view>
                    <view class="l-item">
                        <text class="iconfont">&#xe616;</text>
                        <text>分享</text>
                    </view>
                </view> -->
				<view class="c-line border-1px-top">
					<view class="l-item" @click="toKeep()">
						<text class="iconfont">&#xe609;</text>
						<text>我的收藏</text>
					</view>
					<view class="l-item" @click="toAddress()">
						<text class="iconfont">&#xe638;</text>
						<text>地址管理</text>
					</view>
					<view class="l-item">
						<text class="iconfont" @click="toFeedBack">&#xe742;</text>
						<text>意见反馈</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import request from "@/libs/api.js";
	export default {
		name: "center",
		data() {
			return {
				isOrigin: true,
				isLogin: false,
				userId: '',
				userInfo: {}
			}
		},
		onLoad() {
			this.initData();
			this.isOrigin = false;
			// const app = getApp();
			// console.log('登录状态' + app.globalData.isLogin);
		},
		onShow() {
			let that = this;
			// const app = getApp();
			// console.log('登录状态' + app.globalData.isLogin);
			if (that.isOrigin) {
				that.initData()
			}
			that.isOrigin = true;
			that.userId = uni.getStorageSync("isLogin").userId;
			console.log(that.userId)
			if(that.userId){
				that.isLogin = true;
			}else{
				that.isLogin = false;
			}
			console.log(that.isLogin)
			// uni.getStorage({
			// 	key: 'isLogin',
			// 	success(local) {
			// 		console.log(local);
			// 		if (local.data.isLogin == 1 && local.data.userId) {
			// 			console.log(local.data.userId)
			// 			that.userId = local.data.userId;
			// 			getApp().globalData.isLogin = 1;
			// 			console.log('内存中的登录状态' + getApp().globalData.isLogin)
			// 		}
			// 	}
			// })
			// that.initData();
		},
		methods: {
			toFeedBack(){
				uni.navigateTo({
					url:'/pages/feedback/feedback'
				})
			},
			toOrderList(index) {
				// debugger
				if (this.isLogin) {
					uni.navigateTo({
						url: '/pages/orderList/orderList?tbIndex=' + index
					});
				} else {
					uni.showModal({
						title: "登录提醒",
						content: "您还没有登录，请先登录",
						success(res) {
							if (res.confirm) {
								uni.navigateTo({
									url: "/pages/login/index"
								})
							}
						}
					})
				}
			},
			//跳转用户收藏
			toKeep() {
				if (this.isLogin) {
					uni.navigateTo({
						url: '/pages/keep/keep'
					});
				} else {
					uni.showModal({
						title: "登录提醒",
						content: "您还没有登录，请先登录",
						success(res) {
							if (res.confirm) {
								uni.navigateTo({
									url: "/pages/login/index"
								})
							}
						}
					})
				}
			},
			//跳转用户收货地址
			toAddress() {
				if(this.isLogin){
					uni.navigateTo({
						url: '/pages/address/address'
					});
				}else{
					uni.showModal({
						title: "登录提醒",
						content: "您还没有登录，请先登录",
						success(res) {
							if (res.confirm) {
								uni.navigateTo({
									url: "/pages/login/index"
								})
							}
						}
					})
				}
				// uni.navigateTo({
				// 	url: '/pages/address/address'
				// });
			},
			//检查是否登录
			checkLogin() {
				const app = getApp();
				if (!this.userId) {
					this.isLogin = false;
					uni.navigateTo({
						url: '/pages/login/index'
					});
				} else {
					uni.navigateTo({
						url: '/pages/account/index?userId=' + this.userId
					});
				}
			},
			//初始化数据
			initData() {
				let data = {
					userName: '',
					userId: this.userId,
					page: 0,
					pageSize: 0
				}
				request.get('userController/getUserInfo', data).then((res) => {
					if (res.total > 0) {
						this.userInfo = res.list[0];
						this.isLogin = true;
						console.log(res)
						console.log(this.userInfo)
					} else {
						this.isLogin = false;
						this.userInfo = {}
					}
				})
			}
		},
	}
</script>

<style lang="less">
	@import "./index";
</style>
