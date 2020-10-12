import axios from 'axios' //引入 axios
import router from "@/router";
//import baseUrl from '../api/baseUrl' //使用环境变量 + 模式的方式定义基础URL

// 创建 axios 实例
const service = axios.create({
  baseURL: 'http://192.168.93.10:8080', // api 的 base_url
  // baseURL: 'http://localhost:8003',
  timeout: 15000, // 请求超时时间
});

service.interceptors.request.use(config => {
  console.log("进入拦截。。。");
  console.log(config);
  config.headers['token'] = localStorage.getItem("token");
  return config;
},function (error) {
  return Promise.reject(error);
});

//axios响应拦截器
// http response 拦截器
service.interceptors.response.use(
  response => {
    //进入拦截
    console.log("进入响应拦截。。。");
    console.log(response);
    //拦截响应，做统一处理
    if (response.data.code === "403") {
      alert(response.data.message);
      router.replace({
        path: '/login'
      });
    }
    return response;
  },
  //接口错误状态处理，也就是说无响应时的处理
  error => {
    return Promise.reject(error.response.status) // 返回接口返回的错误信息
  });

// let loadingInstance; //创建Loading 的实例
//axios.defaults.baseURL = appConfig.xhr.baseURL; // 配置axios请求的地址
// service.defaults.headers.post['Content-Type'] = 'application/json; charset=utf-8';
// service.defaults.crossDomain = true;
// service.defaults.withCredentials = true;  //设置cross跨域 并设置访问权限 允许跨域携带cookie信息
// service.defaults.headers.common['token'] = localStorage.getItem("token"); // 设置请求头为 Authorization


export default service
