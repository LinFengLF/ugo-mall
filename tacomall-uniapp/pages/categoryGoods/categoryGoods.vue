<template>
	<view>
		<view class="msgContainer">
			<text>{{msg}}</text>
		</view>
		<glanceProductList :goodsData="productlist"></glanceProductList>
	</view>
</template>

<script>
	import glanceProductList from '@/components/glance-ProductList/glance-ProductList.vue';
	import request from "@/libs/api.js";
	export default {
		name: "categoryGoods",
		data() {
			return {
				id: '',
				renderImage: false,
				productlist: [],
				msg: ''
			};
		},
		onLoad(options) {
			// console.log(options.id);
			this.id = options.id;
			console.log(this.id);
			this.initData();
		},
		methods: {
			initData() {
				let data = {
					page: 0,
					pageSize: 0,
					name: "",
					id: "",
					goodsSn: "",
					categoryId: this.id
				}
				request.get('goodsController/getGoodsList', data).then((res) => {
					if (res.total == 0) {
						uni.showToast({
							icon: "none",
							title: "该分类下没有商品",
							duration: 2000
						})
						// setTimeout(() => {
						// 	uni.navigateBack({
						// 		delta: 1,
						// 		animationDuration: 300
						// 	});
						// }, 5000)

						this.msg = "该分类下没有商品";
					} else {
						// console.log(res)
						this.productlist = res.list;
						console.log(this.productlist)
					}

				})
			},
		}
	}
</script>

<style lang="less">
	.msgContainer {
		font-size: 35rpx;
		text-align: center;
		border-radius: 25rpx;
		color: #07C160;
	}
</style>
