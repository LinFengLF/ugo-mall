<template>
	<view class="page login">
		<view class="l-wrap">
			<view class="w-title">
				<text>账号登录</text>
			</view>
			<view class="w-form">
				<view class="f-input">
					<view class="i-item">
						<input type="text" v-model="loginForm.account" placeholder="请输入账号" />
					</view>
					<view class="i-item">
						<input type="password" v-model="loginForm.password" placeholder="请输入密码" />
					</view>
				</view>
				<view class="f-btn">
					<text @click="checkLogin">登录</text>
				</view>
			</view>
			<view class="w-service">
				<view class="s-left">
					<text>没有账号？</text>
					<text>注册</text>
				</view>
				<view class="s-right">
					<text>忘记密码</text>
				</view>
			</view>

			<!--            <view class="w-third">
                <view class="t-title">
                    <text>第三方账号登录：</text>
                </view>
                <view class="t-entry">
                    <view class="e-item">
                        <text class="iconfont" style="color:#FF8200;">&#xe61b;</text>
                    </view>
                    <view class="e-item">
                        <text class="iconfont" style="color:#07c160;">&#xe7e5;</text>
                    </view>
                    <view class="e-item">
                        <text class="iconfont" style="color:#007fff;">&#xe64b;</text>
                    </view>
                </view>
            </view> -->
		</view>
	</view>
</template>

<script>
	import request from "@/libs/api.js";
	export default {

		name: "login",
		data() {
			return {
				loginForm: {
					account: '',
					password: ''
				}
			}
		},
		methods: {
			checkLogin() {
				// debugger
				let data = {
					userName: this.loginForm.account,
					userPwd: this.loginForm.password
				}
				request.get('login/doLogin', data).then((res) => {
					console.log(res);
					if (res.isLogin == 0) {
						uni.showToast({
							icon: "none",
							title: "账号或密码错误，请重试",
							duration: 2000
						})
					} else {
						uni.setStorage({
							key: 'isLogin',
							data: {
								isLogin: 1,
								userId:res.userInfo.userId
							}
						})
						uni.getStorage({
							key: 'isLogin',
							success(local) {
								console.log(local);
								if (local.data.isLogin == 1) {
									getApp().globalData.isLogin = 1;
									console.log('内存中的登录状态' + getApp().globalData.isLogin)
								}
							}
						})
						uni.showToast({
							title: "登录成功",
							duration: 2000
						})
						setTimeout(() => {
							uni.navigateBack({
								delta: 1,
								animationDuration: 300
							});
						}, 3000)
						// // console.log(res)
						// this.productlist = res.list;
						// console.log(this.productlist)
					}

				})
			}
		}
	}
</script>

<style lang="less">
	@import "./index";
</style>
