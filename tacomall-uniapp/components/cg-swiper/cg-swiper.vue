<template>
	<view class="" style="position: relative;">
		<swiper class="swiper" :indicator-dots="swiperDots" :autoplay="autoplay" :indicator-color="swiperColor" :circular="circular"
		 :interval="interval" :duration="duration" :indicator-active-color="swiperActiveColor" @change="swiperChange">
			<swiper-item style="width: 100%;height: 360upx;">
				<view class="swiper-item">
					<view class="swiper-item-view" v-for="(item, index) in swiperList" :key="index" v-if="index < 10" @click="clickItem(item)">
						<image class="swiper-img" :src="item.iconUrl"></image>
						<text>{{ item.name }}</text>
					</view>
				</view>
			</swiper-item>
			<block v-if="swiperList.length > 10">
				<swiper-item style="width: 100%;height: 360upx;">
					<view class="swiper-item">
						<view class="swiper-item-view" v-for="(item, index) in swiperList" :key="index" v-if="index > 9 && index < 20"
						 @click="clickItem(item)">
							<image class="swiper-img" :src="item.iconUrl"></image>
							<text>{{ item.name }}</text>
						</view>
					</view>
				</swiper-item>
			</block>
			<block v-if="swiperList.length > 20">
				<swiper-item style="width: 100%;height: 360upx;">
					<view class="swiper-item">
						<view class="swiper-item-view" v-for="(item, index) in swiperList" :key="index" v-if="index > 19 && index < 30"
						 @click="clickItem(item)">
							<image class="swiper-img" :src="item.iconUrl"></image>
							<text>{{ item.name }}</text>
						</view>
					</view>
				</swiper-item>
			</block>
			<block v-if="swiperList.length > 30">
				<swiper-item style="width: 100%;height: 360upx;">
					<view class="swiper-item">
						<view class="swiper-item-view" v-for="(item, index) in swiperList" :key="index" v-if="index > 29 && index < 40"
						 @click="clickItem(item)">
							<image class="swiper-img" :src="item.iconUrl"></image>
							<text>{{ item.name }}</text>
						</view>
					</view>
				</swiper-item>
			</block>
			<block v-if="swiperList.length > 40">
				<swiper-item style="width: 100%;height: 360upx;">
					<view class="swiper-item">
						<view class="swiper-item-view" v-for="(item, index) in swiperList" :key="index" v-if="index > 39 && index < 50"
						 @click="clickItem(item)">
							<image class="swiper-img" :src="item.iconUrl"></image>
							<text>{{ item.name }}</text>
						</view>
					</view>
				</swiper-item>
			</block>
		</swiper>
		<view class="dots" v-if="customize == true && swiperDots == false">
			<block v-for="(item, index) in swiperList" :key="index" v-if="index < 5 && swiperList.length > 10">
				<view class="dot" :class="[index == swiperCurrent ? 'active' : '']" :style="[index == swiperCurrent ? { background: swiperActiveColor } : { background: swiperColor }]"></view>
			</block>
		</view>
	</view>
</template>

<script>
	export default {
		name: 'cg-swiper',
		props: {
			// 面板指示点
			swiperDots: {
				type: Boolean,
				default: false
			},
			// 指示点颜色
			swiperColor: {
				type: String,
				default: '#999999'
			},
			// 选中指示点颜色
			swiperActiveColor: {
				type: String,
				default: '#FFCC00'
			},
			// 是否自动切换
			autoplay: {
				type: Boolean,
				default: false
			},
			// 自动切换时长
			interval: {
				type: Number,
				default: 5000
			},
			// 滑动动画时长
			duration: {
				type: Number,
				default: 500
			},
			// 是否采用衔接滑动
			circular: {
				type: Boolean,
				default: true
			},
			// 滑动块视图容器
			swiperList: {
				type: Array,
				default: function() {
					return [];
				}
			},
			// 是否开启自定义指示点
			customize: {
				type: Boolean,
				default: true
			}
		},
		data() {
			return {
				swiperCurrent: 0,
			};
		},
		methods: {
			swiperChange(e) {
				this.swiperCurrent = e.detail.current;
			},

			clickItem(item) {
				console.log("监听到了父组件的点击事件了");
				// console.log(item)
				this.$emit('clickItem', item);
			}
		},
		watch: {
			swiperList: function(newVal) {
				this.swiperList = newVal;
				// console.log(newVal)
			}
		}
	};
</script>

<style>
	.swiper {
		width: 100%;
		height: 370upx;
		padding-top: 20upx;
	}

	.swiper-item {
		display: flex;
		flex-wrap: wrap;
	}

	.swiper-item-view {
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		width: 20%;
		height: 170upx;
	}

	.swiper-img {
		width: 100upx;
		height: 100upx;
	}

	/* 轮播圆点样式修改 */
	.dots {
		position: absolute;
		left: 0;
		right: 0;
		bottom: 20upx;
		display: flex;
		justify-content: center;
	}

	.dots .dot {
		margin: 0 5upx;
		width: 40upx;
		height: 6upx;
		border-radius: 20%;
		transition: all 0.6s;
	}

	.dots .dot.active {
		width: 40upx;
	}
</style>
