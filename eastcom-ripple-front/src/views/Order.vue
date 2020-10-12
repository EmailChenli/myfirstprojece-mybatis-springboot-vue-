<template>
  <el-container class="info-container">
    <!-- 侧边栏 -->
    <el-aside :width="isCollapse ? '64px' : '200px'">
      <div class="toggle-button" @click="toggleCollapse">
        <i :class="toggleButtonIcon" style="font-size: 21px; line-height: 43px;"></i>
      </div>
      <el-menu
        background-color="#333744"
        style="text-algin:left"
        text-color="#fff"
        active-text-color="#409EFF"
        unique-opened
        :collapse="isCollapse"
        :collapse-transition="false"
        router
        :default-active="activePath"
      >
        <!--一级菜单-->
        <template v-for="item in menuList">
          <el-submenu
            v-if="item.children && item.children.length"
            :index="item.path"
            :key="item.path"
          >
            <template slot="title">
              <i :class="item.icon"></i>
              <span>{{item.name}}</span>
            </template>
            <!--二级菜单-->
            <el-menu-item
              v-for="itemChild in item.children"
              :index="itemChild.path"
              :key="itemChild.path"
            >
              <template slot="title">
                <i :class="itemChild.icon"></i>
                <span>{{itemChild.name}}</span>
              </template>
            </el-menu-item>
          </el-submenu>
          <el-menu-item v-else :index="item.path" :key="item.path">
            <i :class="item.icon"></i>
            <span slot="title">{{item.name}}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>
    <!-- 右侧主题区 -->
    <el-main class="main">
      <!--路由占位符-->
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
          path: "/order/shop",
          name: "商家管理",
          icon: "el-icon-s-shop",
          children: [
            {
              path: "/order/shop/list",
              name: "商家列表",
              icon: "el-icon-menu",
            },
            {
              path: "/order/shop/add",
              name: "添加商家",
              icon: "el-icon-menu",
            },
          ],
        },
        {
          path: "/order/foodType",
          name: "食品分类管理",
          icon: "el-icon-s-help",
          children: [
            {
              path: "/order/foodType/list",
              name: "食品分类列表",
              icon: "el-icon-menu",
            },
            {
              path: "/order/foodType/add",
              name: "添加食品分类",
              icon: "el-icon-menu",
            },
          ],
        },
        {
          path: "/order/food",
          name: "食品管理",
          icon: "el-icon-s-grid",
          children: [
            {
              path: "/order/food/list",
              name: "食品列表",
              icon: "el-icon-menu",
            },
            {
              path: "/order/food/add",
              name: "添加食品",
              icon: "el-icon-menu",
            },
          ],
        },
        {
          path: "/order/orders",
          name: "订单列表",
          icon: "el-icon-s-order",
        },
      ],
      //是否被折叠,true则折叠，false则打开
      isCollapse: false,
      //切换按钮的图标
      toggleButtonIcon: "el-icon-s-fold",
    };
  },
  created() {
    this.activePath = window.sessionStorage.getItem("activePath");
  },
  methods: {
    // //点击按钮，切换菜单的折叠与展开
    toggleCollapse() {
      this.isCollapse = !this.isCollapse;
      console.log(this.isCollapse);
      //切换图标
      this.toggleButtonIcon = this.isCollapse
        ? "el-icon-s-unfold"
        : "el-icon-s-fold";
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
.info-container {
  height: 100%;
}

.el-aside {
  background-color: #23262e;
  .el-menu {
    border-right: none;
  }
}
.el-main {
  background-color: #eaedf1;
  padding: 0px;
}

.toggle-button {
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
