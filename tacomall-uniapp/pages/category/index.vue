<template>
	<view class="page category">
		<view class="c-header border-1px-bottom">
			<view class="header-search">
				<text class="iconfont">&#xe652;</text>
				<text class="keyword">润米超级品牌日</text>
			</view>
		</view>
		<view class="c-left">
			<scroll-view :scroll-y="true">
				<view class="l-item" v-for="(item,index) in categoryList" :key="index">
					<text>{{item.name}}</text>
				</view>
			</scroll-view>
		</view>
		
		<view class="c-right">
			<scroll-view :scroll-y="true">
				<view class="sub-list-wrap">
					<view class="wrap-banner">
						<image src="http://yanxuan.nosdn.127.net/d723607e0cbb0c4a11c9396612c71d9b.jpg?imageView&thumbnail=0x196&quality=75"></image>
					</view>
					<view class="wrap-type">
						<text>推荐分类</text>
					</view>
					<view class="wrap-list">
						<view class="list-item" :key="key" v-for="(item, key) in 8">
							<view class="item-pic">
								<image src="http://yanxuan.nosdn.127.net/cee41a60a2d4d06426f863aea2395f19.png?imageView&quality=85&thumbnail=144x144"></image>
							</view>
							<view class="item-name">
								<text>员工精选</text>
							</view>
						</view>
					</view>
				</view>
			</scroll-view>
		</view>
	</view>
</template>

<script>
	import request from "@/libs/api.js";
	export default {
		name: "category",
		data() {
			return {
				categoryList: [],
				renderImage:false
			}
		},
		onLoad() {
			this.initData();
			setTimeout(() => {
				this.renderImage = true;
			}, 300);
		},
		onPullDownRefresh() {
			this.initData()('refresh');
			// 实际开发中通常是网络请求，加载完数据后就停止。这里仅做演示，加延迟为了体现出效果。
			setTimeout(() => {
				uni.stopPullDownRefresh();
			}, 500);
		},
		onReachBottom() {
			this.initData()();
		},
		methods: {
			initData() {
				let data = {
					page: 0,
					pageSize: 0,
					name: "",
					id: "",
					goodsSn: ""
				}
				request.get('categoryController/getList', data).then((res) => {
					// console.log(res.list)
					this.categoryList = res.list;
					console.log(this.categoryList)
				})
			},
		}
	}
</script>

<style lang="less">
	@import "./index";
</style>
