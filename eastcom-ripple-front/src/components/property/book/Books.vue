<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="card-header-text">
        <span>图书信息列表</span>
      </div>
      <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item label="书名：">
          <el-input placeholder="请输入需要查询的书名" />
        </el-form-item>
        <el-form-item label="图书状态：">
          <el-input placeholder="《1为上架 0为下架》" />
        </el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="getList()">查询</el-button>
        <el-button type="default" @click="resetData()">清空</el-button>
      </el-form>

      <!-- 表格 图书编号，图书名称，图书数量，相关描述，图书状态，操作-->
      <el-table :data="list" border fit highlight-current-row>
        <el-table-column type="index"></el-table-column>
        <el-table-column label="图书编号" prop></el-table-column>
        <el-table-column label="图书名称" prop></el-table-column>
        <el-table-column label="图书数量" prop></el-table-column>
        <el-table-column label="相关描述" prop></el-table-column>>
        <el-table-column label="图书状态" prop></el-table-column>
        <el-table-column label="操作" width="200px">
          <template slot-scope="scope">
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showEditDialog(scope.row.id)"
            ></el-button>
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              @click="removeUserById(scope.row.id)"
            ></el-button>
          </template>
        </el-table-column>
      </el-table>
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
      list: [], // 图书数据列表
      page: 1, //当前页
      limit: 10, //每页记录数
      total: 0, //总记录数
    };
  },
  created() {
    //页面渲染之前执行，一般调用methods定义的方法
    this.getList();
  },
  methods: {
    //商家列表的方法
    getList() {},
    // 监听pagesize改变
    handleSizeChange(newSize) {
      // console.log(newSize)
      this.page = newSize;
      this.getList();
    },
    // 监听页码值改变的事件
    handleCurrentChange(newPage) {
      console.log(newPage);
      this.limit = newPage;
      this.getList();
    },
    //清空的方法
    resetData() {
      this.getList();
    },
    // 删除图书的方法
    removeDataById(sid) {
      const comfirmResult = this.$confirm(
        "此操作将永久删除该图书, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      ).catch((err) => err);
      if (confirmRusult !== "confirm") {
        return this.$message.info("已取消删除");
      }
      //   const { data: res } = await this.$http.delete(
      //     "/orderingSystem/shop/deleteShop/" + id
      //   );
      //   if (res.meta.status !== 200) {
      //     return this.$message.error("删除用户失败！");
      //   }
      //   this.$message.success("删除用户成功！");
      //   this.getList();
    },
  },
};
</script>
<style lang="less" scoped>
.el-card{
    box-shadow: 0 1px 1px rgba(0,0, 0, 0.15)!important;
    width: 98%;
    margin:15px auto;
  }

.card-header-text{
      text-align: left;
  }
</style>
