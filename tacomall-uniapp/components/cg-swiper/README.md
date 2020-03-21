

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
		duration:{
			type: Number,
			default: 500
		},
		// 是否采用衔接滑动
		circular: {
			type: Boolean,
			default: false
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