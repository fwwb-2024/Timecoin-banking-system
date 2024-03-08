const http = {
	baseurl: "",
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
	console.log('这是请求拦截器')
	return config
}
//响应拦截器
const beforeResponse = (response) => {
	console.log('这是响应拦截器')
	return response
}
//异常处理器
const errorHandle = (err) => {
	console.log('网络异常，请求失败',err)
}

export default http