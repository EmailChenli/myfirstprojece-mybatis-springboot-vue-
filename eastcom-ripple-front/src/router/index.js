import Vue from 'vue'


import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import Home from '../views/Home.vue'
import Welcome from '../views/Welcome.vue'
import Information from '../views/Information.vue'
import Property from '../views/Property.vue'
import Meeting from '../views/Meeting.vue'
import Order from '../views/Order.vue'
import Network from '../views/Network.vue'
import Setting from '@/views/Setting.vue'
// 设置
import PersonList from '@/components/setting/PersonList.vue'
import ChangePassword from '@/components/setting/ChangePassword.vue'
// 信息管理
import Department from '../components/employee/components/Department'
import Job from '../components/employee/components/Job'
import Employee from '../components/employee/components/Employee'
import EmployeeAccount from '../components/employee/components/EmployeeAccount'
import EmployeeDistribute from '../components/employee/components/EmployeeDistribute'
import EmployeeDimission from '../components/employee/components/EmployeeDimission'
import SystemRole from '../components/employee/components/SystemRole'
import SystemAccount from '../components/employee/components/SystemAccount'
//会议管理
import MeetingHistory from '../components/meeting/history/MeetingHistory.vue'
import MeetingHistoryTimes from '../components/meeting/history/MeetingHistoryTimes.vue'
import MeetingRooms from '../components/meeting/MeetingRooms.vue'
import Approved from '../components/meeting/apply/Approved.vue'
import NotApproved from '../components/meeting/apply/NotApproved.vue'
// 资产管理
import PropertyList from '../components/property/PropertyList.vue'
import Books from '../components/property/book/Books.vue'
import BookHistory from '../components/property/book/BookHistory.vue'
import BookRent from '../components/property/book/BookRent.vue'
import Computers from '../components/property/computer/Computers.vue'
import History from '../components/property/computer/History.vue'
import Rent from '../components/property/computer/Rent.vue'
// 订餐管理
import Food from '../components/order/food/Food.vue'
import FoodSave from '../components/order/food/FoodSave.vue'
import FoodType from '../components/order/foodType/FoodType.vue'
import FoodTypeSave from '../components/order/foodType/FoodTypeSave.vue'
import Orders from '../components/order/order/Orders.vue'
import Shops from '../components/order/shop/Shops.vue'
import ShopSave from '../components/order/shop/ShopSave.vue'
// 网络管理
import IP from '../components/network/IP.vue'

Vue.use(VueRouter);

const router = new VueRouter({
  //去掉路径上的#
  mode: 'hash',
  routes:[
  {path: '/', redirect:'/login'},
  {path: '/login', component: Login},
  {
    path: '/home',
    component: Home,
    redirect: '/welcome',
    children:[
      {path:'/welcome',component:Welcome,meta: { requireAuth: true }},
      // 设置
      {
        path: '/setting',
        component: Setting,
        redirect: '/setting/personList',
        children:[
          {path:'/setting/personList',component:PersonList},
          {path:'/setting/changePassword',component:ChangePassword},
        ]
      },
      // 信息管理
      {
        path:'/information',
        component:Information,
        redirect: '/employee/list/employee',
        children: [
          // 员工管理
          {path:'/employee/list/employee',component:Employee},
          {path:'/employee/list/employeeaccount',component:EmployeeAccount},
          {path:'/employee/list/employeedistribute',component:EmployeeDistribute},
          {path:'/employee/list/employeedimission',component:EmployeeDimission},
          // 公司管理
          {path:'/employee/company/deparment',component:Department},
          {path:'/employee/company/job',component:Job},
          // 系统管理
          {path:'/employee/system/role',component:SystemRole},
          {path:'/employee/system/systemaccount',component:SystemAccount}
        ]
      },
      // 资产管理
      {
        path:'/property',
        component:Property,
        redirect: '/property/propertyList',
        children:[
          // 资产列表
          {path:'/property/propertyList',component:PropertyList},
          // 电脑管理
          {path:'/property/computer/list',component:Computers},
          {path:'/property/computer/history',component:History},
          {path:'/property/computer/rent',component:Rent},
          // 图书管理
          {path:'/property/book/list',component:Books},
          {path:'/property/book/history',component:BookHistory},
          {path:'/property/book/rent',component:BookRent},
        ]
      },
      //会议室管理
      {
        path:'/meeting',
        component:Meeting,
        redirect: '/meeting/list',
        children:[
          {path:'/meeting/list',component:MeetingRooms},
          {path:'/meeting/history/reserve',component:MeetingHistory},
          {path:'/meeting/history/reservetimes',component:MeetingHistoryTimes},
          {path:'/meeting/apply/approved',component:Approved},
          {path:'/meeting/apply/notapproved',component:NotApproved},
        ]
      },
      // 订餐管理
      {
        path:'/order',
        component:Order,
        redirect: '/order/food/list',
        children:[
          {path:'/order/food/list',component:Food},
          {path:'/order/food/add',component:FoodSave},
          {path:'/order/foodType/list',component:FoodType},
          {path:'/order/foodType/add',component:FoodTypeSave},
          {path:'/order/orders',component:Orders},
          {path:'/order/shop/list',component:Shops},
          {path:'/order/shop/add',component:ShopSave},
        ]
      },
      // 网络管理
      {
        path:'/network',
        component:Network,
        redirect: '/network/ip',
        children:[
          {path:'/network/ip',component:IP}
        ]
      },
    ],
    meta: { requireAuth: true }
  }
]
});
//进入路由前做的事
router.beforeEach((to, from, next) => {
  if (to.matched.some(res => res.meta.requireAuth)) {// 判断是否需要登录权限
    if (localStorage.getItem('token')) {// 判断是否登录token应该是随机数
      //localStorage.getItem('token') !== null &&
      next()
    } else {// 没登录则跳转到登录界面
      next({
        path: '/login',
        //query: {redirect: to.fullPath}
      })
    }
  } else {
    next()
  }
});
export default router
