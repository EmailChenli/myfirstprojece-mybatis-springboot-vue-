import Vue from 'vue'
import App from './App.vue'
import router from './router'
import './plugins/element.js'
import JsonExcel from 'vue-json-excel'
import './assets/css/global.css'
import axios from 'axios'
import service from "./components/employee/utils/request";
// 导入全局样式表
Vue.component('downloadExcel', JsonExcel);

Vue.config.productionTip = false;

//axios全局拦截器
axios.interceptors.request.use(config => {
  console.log("进入请求拦截。。。");
  console.log(config);
  config.headers['token'] = localStorage.getItem("token");
  return config;
},function (error) {
  return Promise.reject(error);
});

// http response 拦截器
axios.interceptors.response.use(
  response => {
    //进入拦截
    console.log("进入响应拦截。。。");
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

new Vue({
  router,
  render: h => h(App)
}).$mount('#app');
