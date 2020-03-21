<template>
	<view>
		<view class="content">
			<view class="row">
				<view class="nominal">
					收件人*
				</view>
				<view class="input">
					<input maxlength="6" @change="nameChange" placeholder="请输入收件人姓名" type="text" :value="address.name" />
				</view>
			</view>
			<view class="row">
				<view class="nominal">
					电话号码*
				</view>
				<view class="input">
					<input maxlength="11" @change="phoneChange" placeholder="请输入收件人电话号码" type="number" :value="address.tel" />
				</view>
			</view>
			<view class="row">
				<view class="nominal">
					所在地区*
				</view>
				<view class="input" @tap="chooseCity">
					{{region.label}}
				</view>

			</view>
			<view class="row">
				<view class="nominal">
					详细地址*
				</view>
				<view class="input">
					<input @change="detailedChange" :value="address.detailed" auto-height="true" placeholder="输入详细地址"></input>
				</view>
			</view>
			<view class="row">
				<view class="nominal">
					设置默认地址
				</view>
				<view class="input switch">
					<switch color="#f06c7a" :checked="address.isDefault" @change="isDefaultChange" />
				</view>
			</view>
			<view class="row" v-if="editType=='edit'" @tap="del">
				<view class="del">
					删除收货地址
				</view>
			</view>
		</view>
		<view class="save" @tap="save">
			<view class="btn">
				保存地址
			</view>
		</view>
		<mpvue-city-picker :themeColor="themeColor" ref="mpvueCityPicker" :pickerValueDefault="cityPickerValue" @onCancel="onCancel"
		 @onConfirm="onConfirm"></mpvue-city-picker>
	</view>
</template>

<script>
	import request from "@/libs/api.js";
	import mpvueCityPicker from '@/components/mpvue-citypicker/mpvueCityPicker.vue';
	// import sempCity from "@/components/semp-city/semp-city.vue";
	export default {
		components: {
			mpvueCityPicker,
			// sempCity
		},
		data() {
			return {
				address: {
					userId: "",
					name: "",
					tel: "",
					detailed: "",
					isDefault: false
				},
				userId: '',
				editType: '',
				id: '',
				name: '',
				tel: '',
				detailed: '',
				isDefault: false,
				cityPickerValue: [0, 0, 1],
				themeColor: '#007AFF',
				region: {
					label: "请点击选择地址",
					value: [],
					cityCode: ""
				}
			};
		},
		methods: {
			nameChange(e) {
				this.address.name = e.detail.value;
			},
			phoneChange(e) {
				this.address.tel = e.detail.value;
			},
			detailedChange(e) {
				this.address.detailed = e.detail.value;
			},
			focus(e) {
				console.log("键盘聚焦")
			},
			//校验手机号
			onBlur(e) {
				console.log("键盘失焦")
				let value = e.detail.value;
				if (!value) {
					uni.showToast({
						title: "每项都是必填项",
						icon: "none"
					})
				}
			},
			//校验手机号
			validatePhone(e) {
				this.tel = e.detail.value;
				console.log(this.tel)
				if (this.tel && !(/^1[3|4|5|6|7|8][0-9]\d{8,8}$/.test(this.tel))) {
					uni.showToast({
						title: "请填写正确的手机号",
						icon: "none"
					})
				}
				if (!this.tel) {
					uni.showToast({
						title: "请填写手机号",
						icon: "none"
					})
				}
			},
			onCancel(e) {
				console.log(e)
			},
			chooseCity() {
				this.$refs.mpvueCityPicker.show()
			},
			onConfirm(e) {
				this.region = e;
				this.cityPickerValue = e.value;
			},
			isDefaultChange(e) {
				this.address.isDefault = e.detail.value;
			},
			//删除收货地址
			del() {
				debugger
				let data = {
					ids: this.id
				}
				uni.showModal({
					title: '删除提示',
					content: '你将删除这个收货地址',
					success: (res) => {
						if (res.confirm) {
							request.get('addressController/delete', data).then((res) => {
								if (res.code == 1) {
									uni.showToast({
										title: "删除成功",
										duration: 500,
									})
									setTimeout(() => {
										uni.navigateBack({
											delta: 1
										})
									}, 1500)
								}
								console.log(res);
							})
							// uni.setStorage({
							// 	key: 'delAddress',
							// 	data: {
							// 		id: this.id
							// 	},
							// 	success() {
							// 		uni.navigateBack();
							// 	}
							// })
						} else if (res.cancel) {
							console.log('用户点击取消');
						}
					}
				});

			},
			// 用户保存收货地址
			save() {
				// debugger
				if (this.editType == 'edit') {
					this.address.userId = this.userId;
					this.address.isDefault == true ? this.address.isDefault = '1' : this.address.isDefault = '0';
					this.address.addressDetail = this.address.detailed;
					this.address.id = this.id;
					let data = {
						"address": this.address,
						"addressDetail": this.region,
					}
					if (!data.address.name) {
						uni.showToast({
							title: '请输入收件人姓名',
							icon: 'none'
						});
						return;
					}
					if (!data.address.tel) {
						uni.showToast({
							title: '请输入收件人电话号码',
							icon: 'none'
						});
						return;
					}
					if (!data.address.detailed) {
						uni.showToast({
							title: '请输入收件人详细地址',
							icon: 'none'
						});
						return;
					}
					if (data.addressDetail.value.length == 0) {
						uni.showToast({
							title: '请选择收件地址',
							icon: 'none'
						});
						return;
					}
					request.post('addressController/update', data).then((res) => {
						if (res.code == 1) {
							uni.showToast({
								title: res.msg,
								icon: "none",
								duration: 1000
							})
							setTimeout(() => {
								uni.navigateBack({
									delta: 1
								})
							},2000)
						} else {
							uni.showToast({
								title: "地址更新失败",
								icon: "none"
							})
						}
					})
				} else {
					this.address.userId = this.userId;
					this.address.isDefault == true ? this.address.isDefault = '1' : this.address.isDefault = '0';
					this.address.addressDetail = this.address.detailed;
					let data = {
						"address": this.address,
						"addressDetail": this.region,
					}
					if (this.editType == 'edit') {
						data.id = this.id
					}
					if (!data.address.name) {
						uni.showToast({
							title: '请输入收件人姓名',
							icon: 'none'
						});
						return;
					}
					if (!data.address.tel) {
						uni.showToast({
							title: '请输入收件人电话号码',
							icon: 'none'
						});
						return;
					}
					if (!data.address.detailed) {
						uni.showToast({
							title: '请输入收件人详细地址',
							icon: 'none'
						});
						return;
					}
					if (data.addressDetail.value.length == 0) {
						uni.showToast({
							title: '请选择收件地址',
							icon: 'none'
						});
						return;
					}
					request.post('addressController/insert', data).then((res) => {
						if (res.code == 1) {
							uni.showToast({
								title: res.msg,
								icon: "none",
								duration: 5000
							})
							uni.navigateBack({
								delta: 1
							})
						} else {
							uni.showToast({
								title: res.msg,
								icon: "none"
							})
						}
						console.log(res)
						console.log(this.isDefault)
					})
				}
			}


		},
		onLoad(e) {
			//获取传递过来的参数
			debugger
			this.editType = e.type;
			if (e.type == 'edit') {
				uni.getStorage({
					key: 'address',
					success: (e) => {
						this.id = e.data.id;
						this.address.name = e.data.name;
						this.address.tel = e.data.tel;
						this.address.detailed = e.data.addressDetail;
						this.address.isDefault = e.data.isDefault == 1 ? true : false;
						this.cityPickerValue = e.data.cityValue;
						this.region.label = e.data.label;
						this.region.cityCode = e.data.areaCode;
						this.region.value = e.data.cityValue;
					}
				})
			}
		},
		onBackPress() {
			if (this.$refs.mpvueCityPicker.showPicker) {
				this.$refs.mpvueCityPicker.pickerCancel();
				return true;
			}
		},
		onShow() {
			this.userId = uni.getStorageSync("isLogin").userId;
		},
		onUnload() {
			if (this.$refs.mpvueCityPicker.showPicker) {
				this.$refs.mpvueCityPicker.pickerCancel()
			}
		}
	};
</script>
<style lang="scss">
	.save {
		view {
			display: flex;
		}

		position: fixed;
		bottom: 0;
		width: 100%;
		height: 120upx;
		display: flex;
		justify-content: center;
		align-items: center;

		.btn {
			box-shadow: 0upx 5upx 10upx rgba(0, 0, 0, 0.4);
			width: 70%;
			height: 80upx;
			border-radius: 80upx;
			background-color: #f06c7a;
			color: #fff;
			justify-content: center;
			align-items: center;

			.icon {
				height: 80upx;
				color: #fff;
				font-size: 30upx;
				justify-content: center;
				align-items: center;
			}

			font-size: 30upx;
		}
	}

	.content {
		display: flex;
		flex-wrap: wrap;

		view {
			display: flex;
		}

		.row {
			width: 94%;

			margin: 0 3%;
			border-top: solid 1upx #eee;

			.nominal {
				width: 30%;
				height: 120upx;
				font-weight: 200;
				font-size: 30upx;
				align-items: center;
			}

			.input {
				width: 70%;
				padding: 20upx 0;
				align-items: center;
				font-size: 30upx;

				&.switch {
					justify-content: flex-end;
				}

				.textarea {
					margin: 20upx 0;
					min-height: 120upx;
				}
			}

			.del {
				width: 100%;
				height: 100upx;
				justify-content: center;
				align-items: center;
				font-size: 36upx;
				color: #f06c7a;
				background-color: rgba(255, 0, 0, 0.05);
				border-bottom: solid 1upx #eee;
			}
		}
	}
</style>
