<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="card-header-text">
        <span>订单信息列表</span>
      </div>
      <!-- 查询表单 -->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input placeholder="请输入商家全称" />
        </el-form-item>

        <el-form-item>
          <el-input placeholder="食品名称" />
        </el-form-item>

        <el-form-item label="添加时间">
          <el-date-picker
            v-model="orderQuery.begin"
            type="datetime"
            placeholder="选择开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="00:00:00"
          />
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="orderQuery.end"
            type="datetime"
            placeholder="选择截止时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="00:00:00"
          />
        </el-form-item>

        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form>

      <!-- 表格 -->
      <el-table :data="list" border fit highlight-current-row>
        <el-table-column type="index"></el-table-column>
        <el-table-column prop="realName" label="用户名称" width="120" />
        <el-table-column prop="shopName" label="商家名称" width="120" />
        <el-table-column prop="foodName" label="点餐" />
        <el-table-column prop="phone" label="手机号码" />
        <el-table-column prop="addr" label="地址" />
        <el-table-column label="状态">
          <template slot-scope="scope">{{ scope.row.status===1?'已送达':'未送达' }}</template>
        </el-table-column>
        <el-table-column prop="total" label="总计/元" />
        <el-table-column prop="gmtCreate" label="添加时间" width="200" />
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-edit"
              @click="showEditDialog(scope.row.id)"
            >查看详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--修改查看详情对话框-->
      <el-dialog
        title="订单详情"
        :visible.sync="editDialogVisible"
        width="50%"
        @close="editDialogClosed"
      >
          <el-table
      :data="list"
      border
      fit
      highlight-current-column>
<el-table-column
        label="序号"
        width="200"
        align="center">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="foodName" label="食品名称"  />

      <el-table-column prop="price" label="食品价格" />

      <el-table-column prop="count" label="食品数量" />

      <el-table-column prop="subTotal" label="小计/元" />
    </el-table>
      </el-dialog>

      <!--分页区域-->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="page"
        :page-size="limit"
        :page-sizes="[5, 10, 12,15]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      ></el-pagination>
    </el-card>
  </div>
</template>

<script>
export default {
  //写核心代码位置
  // data:{
  // },
  data() {
    //定义变量和初始值
    return {
      list: null, //查询之后接口返回集合
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数
      editList:'',
      src: "",
      orderQuery: {}, //条件封装对象
      // 控制修改食品对话框的现实与隐藏
      editDialogVisible: false,
    };
  },
  created() {
    //页面渲染之前执行，一般调用methods定义的方法
    //调用
    this.getList();
  },
  methods: {
    //创建具体的方法，调用shop.js定义的方法
    //商家列表的方法
    getList(page = 1) {
      this.page = page;
      this.list = [
        {
          fid: 1,
          realName:'张三',
          shopName: "银记肠粉",
          foodName: "肠粉",
          phone:18776543456,
          addr: "财富广场",
          status: "未送达",
          total:19,
          gmtCreate: "2020-09-07 ",
        },
      ];
    },
    // 监听page改变
    handleSizeChange(newSize) {
      this.page = newSize;
      this.getList();
    },
    // 监听页码值改变的事件
    handleCurrentChange(newPage) {
      this.limit = newPage;
      this.getList();
    },
    resetData() {
      //清空的方法
      //表单输入项数据清空
      this.orderQuery = {};
      //查询所有商家数据
      this.getList();
    },
    //展示订单详情对话框
    showEditDialog(id) {
      // 显示修改食品对话框
      this.editDialogVisible = true;
    },
    // 监听订单详情对话框的关闭事件
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
    },
  },
};
</script>

<style lang="less" scoped>
.tinymce-container {
  line-height: 29px;
}
.input {
  width: 150px;
}
.el-card{
    box-shadow: 0 1px 1px rgba(0,0, 0, 0.15)!important;
    width: 98%;
    margin:15px auto;
  }

.card-header-text{
      text-align: left;
  }
</style>
