<template>
	<view class="page account">
		<cell-group title="基础资料">
			<cell title="头像" :is-link="true">
				<div class="a-avatar">
					<image :src="userInfo.userPhoto"></image>
				</div>
			</cell>
			<cell title="账号" :value="userInfo.userPhone"></cell>
			<cell title="昵称" :value="userInfo.userNickName" :is-link="true"></cell>
		</cell-group>
		<cell-group title="保密资料">
			<cell title="手机号" :value="userInfo.userPhone" :is-link="true"></cell>
			<cell title="密码" :value="userInfo.userPwd" :is-link="true"></cell>
		</cell-group>
	</view>
</template>

<script>
	import request from "@/libs/api.js";
	import cell from '../../components/cell'
	import cellGroup from '../../components/cell-group'
	export default {
		data() {
			return {
				userInfo: {},
				userId:'  '
			}

		},
		components: {
			cell,
			cellGroup
		},
		onLoad(options) {
			console.log(options.userId);
			this.userId = options.userId;
			this.initData();
		},
		methods: {
			initData() {
				let data = {
					userName: '',
					userId: this.userId,
					page: 0,
					pageSize: 0
				}
				request.get('userController/getUserInfo', data).then((res) => {
					console.log(res)
					this.userInfo = res.list[0];
					console.log(this.userInfo)
				})
			}
		}
	}
</script>

<style lang="less" scoped>
	.account {
		.a-avatar {
			display: inline-block;
			height: 104rpx;
			width: 104rpx;
			margin-right: 54rpx;

			image {
				width: 100%;
				height: 100%;
				border-radius: 50%;
			}
		}
	}
</style>
