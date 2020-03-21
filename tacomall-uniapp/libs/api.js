const commonUrl = "http://127.0.0.1:8080/ugoMall/"; //公共路径 
// const commonUrl = "http://192.168.0.106:8080/ugoMall/"

// post请求封装
function postRequest(url, data) {
	uni.showLoading({
		title: '加载中'
	})
	var promise = new Promise((resolve, reject) => {
		var that = this;
		var postData = data;
		uni.request({
			url: commonUrl + url,
			data: postData,
			method: "POST",
			header: {
				"content-type": "application/json",
				// "content-type": "application/x-www-form-urlencoded",
				token: uni.getStorageSync("token")
			},
			success: function(res) {
				//返回什么就相应的做调整
				if (res.data.code == 1) {
					// uni.showToast({
					// 	title: "数据获取成功",
					// 	duration: 2000
					// })
					uni.hideLoading();
					resolve(res.data);
				} else {
					// 请求服务器成功，但是由于服务器没有数据返回，此时无code。会导致这个空数据
					//接口后面的then执行
					// 不下去，导致报错，所以还是要resolve下，这样起码有个返回值，
					//不会被阻断在那里执行不下去！
					uni.hideLoading();
					uni.showToast({
						icon: "none",
						title: "服务器错误",
						duration: 2000
					})
					resolve(res.data.msg);
				}
			},
			error: function(e) {
				uni.hideLoading();
				reject("网络出错");
			}
		});
	});
	return promise;
}

// get请求封装
function getRequest(url, data) {
	uni.showLoading({
		title: '加载中'
	})
	var promise = new Promise((resolve, reject) => {
		var that = this;
		var postData = data;
		uni.request({
			url: commonUrl + url,
			// data:JSON.stringify(postData),
			data: postData,
			method: "GET",
			dataType: "json",
			// header: {
			// 	'content-type': 'application/x-www-form-urlencoded;charset=utf-8'
			// },
			header: {
				"content-type": "application/json"
			},
			success: function(res) {
				if (res.statusCode == 200) {
					uni.hideLoading();
					resolve(res.data);
				} else {
					uni.hideLoading();
					resolve(res.data);
				}
			},
			error: function(e) {
				uni.hideLoading();
				reject("网络出错");
			}
		});
	});
	return promise;
}

module.exports = {
	post: postRequest,
	get: getRequest
};
