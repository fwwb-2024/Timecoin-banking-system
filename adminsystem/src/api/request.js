import axios from "axios";
import router from "@/router";
import { Message } from 'element-ui';

const http = axios.create({
    baseURL: 'http://123.249.5.46:9090', //服务器后端地址
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
    let url = location.href
    let route = (url).substring((url).lastIndexOf("/")+1)
    if(route != 'login'){
        if(res.status == 201){
            Message({
                message: '登录已过期',
                type: 'warning'
            });
            router.push('/login');
        }
        else if(res.data.token){
            localStorage.token = res.data.token
        }
    }
    return res
})
export default http
