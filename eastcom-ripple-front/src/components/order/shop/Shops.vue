<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="card-header-text">
        <span>商家信息列表</span>
      </div>
      <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item>
          <el-input v-model="shopQuery.name" placeholder="商家名字" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="shopQuery.desc" placeholder="商家描述" />
        </el-form-item>
        <el-form-item label="添加时间">
          <el-date-picker
            v-model="shopQuery.begin"
            type="datetime"
            placeholder="选择开始时间"
            value-format="yyyy-MM-dd HH:mm:ss"
            default-time="00:00:00"
          />
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="shopQuery.end"
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
        <el-table-column prop="shopName" label="名称" width="120" />
        <el-table-column prop="description" label="描述" />
        <el-table-column label="图片" width="200px">
          <template slot-scope="scope">
            <img :src="scope.row.cover" style="height: 50px" />
          </template>
        </el-table-column>
        <el-table-column prop="gmtCreate" label="添加时间" width="200" />
        <el-table-column label="操作" width="200px" align="center">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-edit"
              @click="showEditDialog(scope.row.id)"
            >修改</el-button>

            <el-button
              type="danger"
              size="mini"
              icon="el-icon-delete"
              @click="removeDataById(scope.row.sid)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--修改商家对话框-->
      <el-dialog
        title="修改商家"
        :visible.sync="editDialogVisible"
        width="50%"
        @close="editDialogClosed"
      >
        <el-form :model="editList" ref="editFormRef" label-width="70px">
          <el-form-item label="商家名称">
            <el-input v-model="list.shopName" />
          </el-form-item>
          <el-form-item label="商家描述">
            <el-input v-model="list.description" :rows="10" type="textarea" />
          </el-form-item>

          <!--商家图片 -->
          <el-form-item label="商家图片">
            <el-upload
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              :action="BASE_API+'/orderingSystem/shop/upload'"
              class="avatar-uploader"
            >
              <img class="avatar" />
            </el-upload>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="editDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="editUserInfo">确 定</el-button>
        </span>
      </el-dialog>
      <!-- 分页 -->
      <!-- <el-pagination
      :current-page="page"
      :page-size="limit"
      :total="total"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="getList"
      />-->
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
      page: 1, //当前页
      limit: 10, //每页记录数
      shopQuery: {}, //条件封装对象
      list: null, //查询之后接口返回集合
      total: 0, //总记录数
      src: "",
      editList: [],
      // 控制修改用户对话框的现实与隐藏
      editDialogVisible: false,
      BASE_API: "http://192.168.93.66:8080", //获取dev.env.js里面地址
    };
  },
  created() {
    //页面渲染之前执行，一般调用methods定义的方法
    //调用

    this.getList();
  },
  methods: {
    //上传封面成功调用的方法
    handleAvatarSuccess(res, file) {
      // this.shop.cover = res.data.url
      // console.log(this.shop.cover)
    },
    //上传之前调用的方法
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    //创建具体的方法，调用shop.js定义的方法
    //商家列表的方法

    getList(page = 1) {
      this.page = page;

      this.list = [
        {
          sid: 1,
          shopName: "银记肠粉",
          description: "广州老字号",
          img: "",
          gmtCreate: "2020-09-07 ",
        },
      ];
    },
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
      //表单输入项数据清空
      this.shopQuery = {};
      //查询所有商家数据
      this.getList();
    },
    //展示修改商家对话框
    showEditDialog(id) {
      // 显示修改商家对话框
      this.editDialogVisible = true;
    },
    // 监听添加用户对话框的关闭事件
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
    },
    // 修改用户信息并提交
    editUserInfo() {
      this.$refs.editFormRef.validate(async (valid) => {
        if (!valid) return;
        // // 发起修改用户性信息的网络请求
        // const { data: res } = await this.$http.put(
        //   "users/" + this.editForm.id,
        //   {
        //     email: this.editForm.email,
        //     mobile: this.editForm.mobile,
        //   }
        // );
        // console.log(res);
        // if (res.meta.status !== 200) {
        //   return this.$message.error("更新用户信息失败！");
        // }
        // //隐藏修改用户的对话框
        // this.editDialogVisible = false;
        // // 刷新数据列表
        this.list();
        this.$message.success("更新商家信息成功");
      });
    },
    // 删除商家的方法
    async removeDataById(sid) {
      const comfirmResult = this.$confirm(
        "此操作将永久删除该商家, 是否继续?",
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
      const { data: res } = await this.$http.delete(
        "/orderingSystem/shop/deleteShop/" + id
      );
      if (res.meta.status !== 200) {
        return this.$message.error("删除用户失败！");
      }
      this.$message.success("删除用户成功！");
      this.getList();
    },
  },
};
</script>
<style lang="less" scoped>
.avatar {
  width: 178px;
  height: 178px;
  display: block;
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