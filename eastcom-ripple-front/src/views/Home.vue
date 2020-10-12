<template>
  <el-container class="home-container">
    <!-- 头部区域 -->
    <el-header>
      <div>
        <!-- logo -->
        <img class="logo" src="../assets/img/east.png" />
        <!-- 项目名 -->
        <span>东信门户管理系统</span>
      </div>
      <!-- 菜单 -->
      <el-menu
        class="menu"
        background-color="#23262E"
        style="text-algin:left"
        text-color="#fff"
        active-text-color="#409EFF"
        mode="horizontal"
        unique-opened
        router
        :default-active="activePath"
      >
        <el-menu-item v-for="item in menuList" :index="item.path" :key="item.path">
          <template slot="title">
            <span>{{item.name}}</span>
          </template>
        </el-menu-item>
      </el-menu>
      <!-- 头像 -->
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img src="../assets/logo.png" class="user-avatar" alt="头像" />
        </div>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>
            <span style="display:block;" @click="setting">个人中心</span>
          </el-dropdown-item>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">退出</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </el-header>
    <el-main>
      <!-- 路由占位符-->
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      // 被激活的连接地址
      activePath: "",
      //菜单数据
      menuList: [
        {
          path: "/welcome",
          name: "首页",
        },
        {
          path: "/information",
          name: "信息管理",
        },
        {
          path: "/property",
          name: "资产管理",
        },
        {
          path: "/meeting",
          name: "会议室管理",
        },
        {
          path: "/order",
          name: "订餐管理",
        },
        {
          path: "/network",
          name: "网络管理",
        },
      ],
    };
  },
  created() {
    this.activePath = window.sessionStorage.getItem("activePath");
  },
  methods: {
    // 跳转至设置界面
    setting() {
      console.log("setting");
      this.$router.push("/setting");
    },
    // 退出登录
    logout() {
      localStorage.clear();
      this.$router.push("/login");
    },
    // 保存连接的激活状态
    saveNavState(activePath) {
      window.sessionStorage.setItem("activePath", activePath);
      this.activePath = activePath;
    },
  },
};
</script>

<style lang="less" scoped>
.home-container {
  height: 100%;
}
.el-main {
  background-color: #eaedf1;
  padding: 0;
}

.logo {
  width: 45px;
  height: 45px;
}
.el-header {
  background-color: #23262e;
  display: flex;
  justify-content: space-between;
  padding-left: 10px;
  text-align: right;
  color: #fff;
  font-size: 20px;
  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
}

.menu {
  position: relative;
  left: 250px;
  font-size: 20px;
}
.avatar-container {
  height: 50px;
  display: inline-block;
  position: relative;
  right: 10px;
}
.avatar-wrapper {
  cursor: pointer;
  margin-top: 15px;
  position: relative;
  line-height: initial;
}
.user-avatar {
  width: 45px;
  height: 45px;
  background-color: #fff;
  border-radius: 25px;
}
.el-icon-caret-bottom {
  position: absolute;
  right: -20px;
  top: 25px;
  font-size: 12px;
}
</style>
