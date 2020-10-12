import axios from "axios"
import router from "@/router";

// 资产管理
export function requestAsset(config){
  // 创建axios实例
  const instance = axios.create({
    baseURL:"http://192.168.93.10:8080",
    //baseURL:"http://localhost:8002",
    timeout:5000
  });

  // request拦截器，axios局部拦截器
  instance.interceptors.request.use(
    config => {
      console.log("进入拦截....");
      //请求头添加token
      config.headers['token'] = localStorage.getItem("token");
      return config
    },
    err => {
      return Promise.reject(err);
    });

  //response拦截器,axios局部响应拦截器
  instance.interceptors.response.use(
    response => {
      //进入拦截
      console.log("进入资产响应拦截。。。");
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

  // 发送真正的网络请求
  return instance(config)
}

export function request2(config){
  // 创建axios实例
  const instance = axios.create({
    baseURL:"http://httpbin.org/get",
    timeout:5000
  });
  // 发送真正的网络请求
  return instance(config)
}
