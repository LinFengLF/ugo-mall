<template>
	<div class="total-wrap">
		<div class="logistics-title">{{orderInfo.goodsName}}</div>
		<div class="channelContainer">
			<div>
				物流公司：{{orderInfo.shipChannel}}
			</div>
			<div>
				物流单号：{{orderInfo.shipSn}}
			</div>
		</div>
		<div class="logistics-title">物流跟踪</div>
		<block v-for="(item, index) in tracesData" :key="index">
			<trackNode :is-first="index===tracesData.length-1" :is-newest="index===0" :is-main-node="item.isMainNode" :node-data="item"></trackNode>
		</block>
	</div>
</template>

<script>
	import trackNode from '@/components/trackNode/trackNode.vue';
	import request from "@/libs/api.js";
	export default {
		components: {
			trackNode
		},
		data() {
			return {
				orderInfo: {},
				tracesData: [
					// {
					// 	acceptStation: '包裹已被吴亦发同学签收', // 节点描述
					// 	createTime: '2019-10-24 15: 27: 16',
					// 	status: 'COMPLETE', // 节点状态
					// 	phone: '', // 电话
					// 	statusName: '已签收', // 节点标题
					// 	isMainNode: true // 是否主节点，主节点前方展示icon
					// },
					// {
					// 	acceptStation: '由派送员蔡小坤同志配送，电话：',
					// 	createTime: '2019-10-24 15: 26: 41',
					// 	status: 'DELIVERING',
					// 	phone: '16677778888',
					// 	statusName: '运输中',
					// 	isMainNode: true
					// },
					// {
					// 	acceptStation: '已到XXX小区快递点',
					// 	createTime: '2019-10-24 15: 26: 41',
					// 	status: 'DELIVERING',
					// 	phone: '',
					// 	statusName: '运输中',
					// 	isMainNode: false
					// },
					// {
					// 	acceptStation: '已到海宁集散中心',
					// 	createTime: '2019-10-24 15: 26: 18',
					// 	status: 'DELIVERING',
					// 	phone: '',
					// 	statusName: '运输中',
					// 	isMainNode: false
					// },
					// {
					// 	acceptStation: '已到杭州集散中心',
					// 	createTime: '2019-10-24 15: 26: 07',
					// 	status: 'DELIVERING',
					// 	phone: '',
					// 	statusName: '运输中',
					// 	isMainNode: false
					// },
					// {
					// 	acceptStation: '包裹已到达余杭区集散中心',
					// 	createTime: '2019-10-24 15: 25: 54',
					// 	status: 'DELIVERING',
					// 	phone: '',
					// 	statusName: '运输中',
					// 	isMainNode: false
					// },
					// {
					// 	acceptStation: '快递员已上门取件',
					// 	createTime: '2019-10-24 15: 25: 17',
					// 	status: 'DELIVERING',
					// 	phone: '',
					// 	statusName: '已揽收',
					// 	isMainNode: false
					// },
					// {
					// 	acceptStation: '等待快递员上门揽件',
					// 	createTime: '2019-10-24 15: 25: 00',
					// 	status: 'WATTING_DELIVER',
					// 	phone: '',
					// 	statusName: '已发货',
					// 	isMainNode: true
					// },
					// {
					// 	acceptStation: '您的包裹正在打包',
					// 	createTime: '2019-10-24 15: 24: 00',
					// 	status: 'WATTING_DELIVER',
					// 	phone: '',
					// 	statusName: '待发货',
					// 	isMainNode: false
					// },
					// {
					// 	acceptStation: '订单支付成功，等待商家发货',
					// 	createTime: '2019-10-24 15: 22: 30',
					// 	status: 'PAYED',
					// 	statusName: '已支付',
					// 	isMainNode: true
					// },
					// {
					// 	acceptStation: '订单提交成功',
					// 	createTime: '2019-10-24 15: 22: 00',
					// 	status: 'WATTING_PAY',
					// 	statusName: '已下单',
					// 	isMainNode: true
					// }
				]
			}
		},
		onLoad() {
			// debugger
			this.orderInfo = uni.getStorageSync('orderInfo');
			uni.removeStorageSync('orderInfo');
			console.log(this.orderInfo);
			this.initData()
		},
		methods: {
			initData() {
				let data = {
					expCode: this.orderInfo.shipChannel,
					expNo: this.orderInfo.shipSn
				}
				request.get('orderController/queryLogistics', data).then((res) => {
					if (res.Success == true) {
						for(let i = res.Traces.length - 1;i >= 0; i--){
							this.tracesData.push(res.Traces[i])
						}
						console.log(this.tracesData)
						// this.tracesData = res.Traces;
						console.log(res);
					} else {
						uni.showToast({
							title:"物流信息获取失败",
							icon:"none"
						})
					}
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.channelContainer {
		display: flex;
	}

	.total-wrap {
		width: 100vw;
		height: auto;
		box-sizing: border-box;
		padding: 20rpx 40rpx 200rpx;

		.logistics-title {
			height: 72rpx;
			box-sizing: border-box;
			padding: 36rpx 0 8rpx;
			line-height: 28rpx;
			color: #4B4B4B;
			font-size: 26rpx;
			font-family: 'PingFangSC-Medium';
			text-align: left;
		}
	}
</style>
