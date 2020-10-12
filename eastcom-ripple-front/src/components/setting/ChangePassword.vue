<template>
  <div>
    <el-card>
      <div slot="header" class="card-header-text">
        <span>修改密码</span>
      </div>
      <!-- 修改密码表单 -->
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item label="原密码" prop="oldPassword">
          <el-input type="password" placeholder="请输入原密码" v-model="form.oldPassword"></el-input>
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
          <el-input type="password" placeholder="请设置新密码" v-model="form.newPassword"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="newPassword2">
          <el-input type="password" placeholder="请确认新密码" v-model="form.newPassword2"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button class="btn" type="primary" @click="changePassword">确定修改</el-button>
        </el-form-item>
      </el-form>
      <el-dialog title="提示框" :visible.sync="msgButton">   
              <p>{{msg}}</p>
      </el-dialog>  
    </el-card>
  </div>
</template>

<script>
import { changePassword } from '@/components/employee/api/login'

export default {
  data() {
    //表单发送之前验证
    let validateNewPassword = (rule, value, callback) => {
      if (value === this.form.oldPassword) {
        callback(new Error("新密码不能与原密码相同!"));
      } else {
        callback();
      }
    };
    let validateNewPassword2 = (rule, value, callback) => {
      if (value !== this.form.newPassword) {
        callback(new Error("与新密码不一致!"));
      } else {
        callback();
      }
    };
    return {
      msg:'',
      msgButton:false,
      form: {
        oldPassword:'',
        newPassword:'',
        newPassword2:'',
        accountName:''
      },
      rules: {
        oldPassword: [
          { required: true, message: "请输入原密码", trigger: "blur" },
        ],
        newPassword: [
          { required: true, message: "请设置新密码", trigger: "blur" },
          { validator: validateNewPassword, trigger: "blur" },
        ],
        newPassword2: [
          { required: true, message: "请确认新密码", trigger: "blur" },
          { validator: validateNewPassword2, trigger: "blur" },
        ],
      },
    }
  },
  methods: {
    changePassword() {
      this.form.accountName = localStorage.getItem('info');
      changePassword(this.form).then((res)=>{
        console.log(res);
        this.msg = res.data.msg;
        this.msgButton = true;
      })
    },
  },
};
</script>

<style lang="less" scoped>
.el-form{
    width: 40%;
    margin: 0 auto;
}

.btn{
    width:120px;
    
}
.el-card {
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15) !important;
  width: 98%;
  margin: 15px auto;
}

.card-header-text {
  text-align: left;
}
</style>
