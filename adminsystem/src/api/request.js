import axios from "axios";
import router from "@/router";

const http = axios.create({
    baseURL: 'http://10.195.28.44:9090', //服务器后端地址
    timeout:10000,  //超时时间
})
//请求拦截器
http.interceptors.request.use(config=>{
    // 请求头加token
    if(localStorage.token){
        config.headers.Authorization = localStorage.token
    }
    return config
})
//响应拦截器
http.interceptors.response.use(res=>{
    // 响应处理
    if(res.status == 201){
        alert('登录已过期')
        router.push('/login');
    }
    else if(res.data.token){
        localStorage.token = res.data.token
    }
    return res
})
export default http
