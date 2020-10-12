<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="card-header-text">
        <span>食品列表</span>
      </div>
      <el-form label-width="120px">
        <el-form-item label="食品名称">
          <el-input  placeholder=" 示例：麦当劳" />
        </el-form-item>
        <!-- 所属分类 TODO -->
        <el-form-item label="商家分类">
          <el-select placeholder="商家分类" @change="showType">
            <!-- <el-option
              v-for="shop in shopList"
              :key="shop.sid"
              :label="shop.shopName"
              :value="shop.sid" -->
            />
          </el-select>

          <!-- 食品分类 -->
          <el-select clearable placeholder="食品分类">
            <!-- <el-option
              v-for="foodType in foodTypeList"
              :key="foodType.ftid"
              :label="foodType.typeName"
              :value="foodType.ftid" -->
            />
          </el-select>
        </el-form-item>
        <el-form-item label="食品描述">
          <el-input  :rows="10" type="textarea" />
        </el-form-item>
        <!-- 食品图片-->
        <el-form-item label="食品图片">
          <el-upload
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
            :action="BASE_API+'/orderingSystem/food/upload'"
            class="avatar-uploader"
          >
            <!-- <img v-if="food.cover" :src="food.cover" class="avatar" /> -->
            <i class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="食品价格">
          <el-input-number :min="0" v controls-position="right" />元
        </el-form-item>
        <el-form-item>
          <el-button :disabled="saveBtnDisabled" type="primary" @click="saveOrUpdate">保存</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
export default {
        data() {
        return {
            saveBtnDisabled:false,
            food:{
                foodName: '',
                shopId: '',//商家分类id
                ftid:'',//食品分类id
                description: '',
                cover: '',
                price: 0
            },
            BASE_API: 'http://192.168.93.66:8080', // 接口API地址
            shopList:[],//商家分类
            foodTypeList:[],//食品分类
            message:''
        }
    },
    created() {

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
            this.food.cover = res.data.url
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
              this.getShop()
              this.getFoodType()
              this.message = "更新食品"
          } else { //路径没有id值，做添加
            this.getShop()
            //清空表单
            // this.food = ''
             this.message = "新增食品"
          }
        },
        //点击某个商家分类，触发change，显示对应食品分类
        showType(value) {
            //value就是商家分类id值
           foodType.getAllTypeByShopId(value)
            .then(response => {
              this.foodTypeList = response.data
              this.food.ftid = ''
            })
        },
        //查询所有的商家分类
        getShop() {
            shop.getAllShop()
                .then(response => {
                    this.shopList = response.data
                })
        },
        getFoodType(id){
          foodType.getAllTypeByShopId(id)
            .then(response => {
              this.foodTypeList = response.data
            })
        },
        //添加食品
        addFood() {
            food.addFood(this.food)
                .then(response => {
                    //提示
                    this.$message({
                        type: 'success',
                        message: '添加食品信息成功!'
                    });
                    //回到列表页面 路由跳转
                    this.$router.push({path:'/food/table'})
                })
        },
        //根据Id查询食品信息
        getInfo(id){
            food.getFoodById(id)
              .then(response => {
                this.food = response.data
                this.getFoodType(response.data.shopId)
              })
        },
        //更新食品信息
        updateFood(){
          food.updateFood(this.food)
            .then(response => {
              //提示
                    this.$message({
                        type: 'success',
                        message: '更新食品信息成功!'
                    });
                    //回到列表页面 路由跳转
                    this.$router.push({path:'/food/table'})
            })
        },
        saveOrUpdate() {
        //判断修改还是添加
        //根据shop是否有id
        if(!this.food.fid) {
          //添加
          this.addFood()
        } else {
          //修改
          this.updateFood()
        }
      },
    }
};
</script>

<style lang="less" scoped>
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
