const http = {
	baseurl: "http://10.195.28.44:9090",
	//请求方法
	request(config) {
		config = beforeRequest(config)
		config.url = this.baseurl + config.url
		//创建一个promise对象
		return new Promise((resolve,reject) => {
			uni.request(config).then(res => {
				let [error, resp] = res
				const response = beforeResponse(resp)
				resolve(response)
			}).catch(err => {
				errorHandle()
				reject(err)
			})
		})
	},
	get(url,data,auth){
		return this.request({
			url: url,
			data: data,
			auth: auth,
			method:'GET'
		})
	},
	post(url,data,auth){
		return this.request({
			url: url,
			data: data,
			auth: auth,
			method: 'POST'
		})
	}
}

//请求拦截器
const beforeRequest = (config) => {
	// 添加token请求头
	if(config.auth) {
		const value = uni.getStorageSync('token');
		if (value) {
			config.header = {}
			config.header['Authorization'] = value
		}
		else {
			uni.showToast({
				title: '未登录',
				icon:'error',
				duration: 500
			});
			setTimeout(function() {
				let routes = getCurrentPages(); // 获取当前打开过的页面路由数组
				let curRoute = routes[routes.length - 1].route // 获取当前页面路由，也就是最后一个打开的页面路由
				if(curRoute != '/pages/login'){
					uni.reLaunch({
						url:'/pages/login'
					})
				}
			}, 500);
		}
	}
	return config
}
//响应拦截器
const beforeResponse = (response) => {
	// token正确则更新token
	if(response.statusCode == 200) {
		if(response.data.token){
			uni.setStorage({
				key: 'token',
				data: response.data.token,
			});
		}
	}
	// 否则重新登陆
	else if(response.statusCode == 201) {
		uni.showToast({
			title: '登录已过期',
			icon:'error',
			duration: 1000
		});
		setTimeout(function() {
		    uni.reLaunch({
		    	url:'/pages/login'
		    })
		}, 1000);
	}
	return response
}
//异常处理器
const errorHandle = (err) => {
	uni.showToast({
		title: '网络异常',
		icon:'error',
		duration: 1000
	});
}

export default http