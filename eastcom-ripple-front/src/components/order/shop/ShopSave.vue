<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="card-header-text">
        <span>添加商家</span>
      </div>
     <el-form label-width="120px">
      <el-form-item label="商家名称">
        <el-input v-model="shop.shopName"/>
      </el-form-item>
      <el-form-item label="商家描述">
        <el-input v-model="shop.description" :rows="10" type="textarea"/>
      </el-form-item>

      <!--商家图片 -->
      <el-form-item label="商家图片">

            <el-upload
                :show-file-list="false"
                :on-success="handleAvatarSuccess"
                :before-upload="beforeAvatarUpload"
                :action="BASE_API+'/orderingSystem/shop/upload'"
                class="avatar-uploader">
                <img v-if="shop.cover" :src="shop.cover" class="avatar">
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>

        </el-form-item>


      <el-form-item>
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
      </el-form-item>
    </el-form>
    </el-card>
  </div>
</template>
<script>
// dx import shop from '@/api/orderingsystem/shop'
export default {
  data() {
    return {
      shop:{
        shopName: '',
        description: '',
        cover:''
      },
      BASE_API:'http://192.168.93.66:8080', //获取dev.env.js里面地址
      saveBtnDisabled:false  // 保存按钮是否禁用,
    }
  },
  created() { //页面渲染之前执行
    this.init()
  },
  watch: {  //监听
    $route(to, from) { //路由变化方式，路由发生变化，方法就会执行
      this.init()
    }
  },
  methods:{
    //上传封面成功调用的方法
        handleAvatarSuccess(res, file) {
            this.shop.cover = res.data.url
            console.log(this.shop.cover)
        },
        //上传之前调用的方法
        beforeAvatarUpload(file) {
            const isJPG = file.type === 'image/jpeg'
            const isLt2M = file.size / 1024 / 1024 < 2

            if (!isJPG) {
                this.$message.error('上传头像图片只能是 JPG 格式!')
            }
            if (!isLt2M) {
                this.$message.error('上传头像图片大小不能超过 2MB!')
            }
            return isJPG && isLt2M
        },
    init() {
      //判断路径有id值,做修改
      if(this.$route.params && this.$route.params.id) {
          //从路径获取id值
          const id = this.$route.params.id
          //调用根据id查询的方法
          this.getInfo(id)
      } else { //路径没有id值，做添加
        //清空表单
        this.shop = {}
      }
    },
    //根据商家id查询的方法
    getInfo(id) {
      shop.getShopById(id)
        .then(response => {
          this.shop = response.data
        })
    },
    saveOrUpdate() {
      //判断修改还是添加
      //根据shop是否有id
      if(!this.shop.sid) {
        //添加
        this.saveShop()
      } else {
        //修改
        this.updateShop()
      }
    },
    //修改商家的方法
    updateShop() {
      shop.updateShop(this.shop)
        .then(response => {
          //提示信息
          this.$message({
              type: 'success',
              message: '修改成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/shop/table'})
        })
    },
    //添加商家的方法
    saveShop() {
      shop.addShop(this.shop)
        .then(response => {//添加成功
          //提示信息
          this.$message({
              type: 'success',
              message: '添加成功!'
          });
          //回到列表页面 路由跳转
          this.$router.push({path:'/shop/table'})
        })
    }

  }
}
</script>
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
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

