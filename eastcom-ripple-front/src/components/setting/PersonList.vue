<template>
  <div>
    <el-card>
      <div slot="header" class="card-header-text">
        <span>个人信息</span>
      </div>
      <!-- 个人信息表单 -->
      <!-- <el-form ref="modifyForm" :model="modifyForm" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
             <el-form-item label="姓名" prop="employeeName" ><el-input type="employeeName" v-model="modifyForm.employeeName"></el-input></el-form-item> 
          </el-col>
          <el-col :span="12">
             <el-form-item label="头像" ><el-input v-model="modifyForm.employeeImg"></el-input></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
             <el-form-item label="工号" ><el-input v-model="modifyForm.employeeId"></el-input></el-form-item> 
          </el-col>
          <el-col :span="12">
             <el-form-item label="性别" >
                <el-radio-group v-model="modifyForm.employeeSex">
                    <el-radio class="radio" label="男">男</el-radio>
                    <el-radio class="radio" label="女">女</el-radio>
                </el-radio-group>
             </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
             <el-form-item label="部门" ><el-input v-model="modifyForm.departmentName"></el-input></el-form-item> 
          </el-col>
          <el-col :span="12">
             <el-form-item label="职位" ><el-input v-model="modifyForm.jobName"></el-input></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
             <el-form-item label="电话" ><el-input v-model="modifyForm.employeePhone"></el-input></el-form-item> 
          </el-col>
          <el-col :span="12">
             <el-form-item label="邮箱" ><el-input v-model="modifyForm.employeeEmail"></el-input></el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
             <el-form-item label="学历" ><el-input v-model="modifyForm.education" disabled></el-input></el-form-item> 
          </el-col>
          <el-col :span="12">
             <el-form-item label="身份证号"><el-input v-model="modifyForm.employeeIdcard" disabled></el-input></el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="居住地址">
          <el-input v-model="modifyForm.employeeAddress"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button class="btn" type="primary" @click="save">保存</el-button>
        </el-form-item>
      </el-form> -->

      <el-form ref="modifyForm" :model="modifyForm"  label-width="100px">
       
        <el-row>
          <el-col :span="12">
             <el-form-item label="ID" ><el-input  v-model="modifyForm.sysuserId" disabled></el-input></el-form-item> 
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
             <el-form-item label="账号" ><el-input  v-model="modifyForm.sysuserAccount" disabled></el-input></el-form-item> 
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
             <el-form-item label="密码"><el-input v-model="modifyForm.sysuserPassword" disabled></el-input></el-form-item> 
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
             <el-form-item label="状态"><el-input v-model="modifyForm.sysuserStatus" disabled></el-input></el-form-item> 
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
             <el-form-item label="权限ID" ><el-input  v-model="modifyForm.roleId" disabled></el-input></el-form-item> 
          </el-col>
        </el-row>
        
      </el-form>

    </el-card>
  </div>
</template>

<script>

import { sysUserInfo } from '@/components/employee/api/login'

export default {
    data(){
        return {
            // 表单数据
            modifyForm:{
              sysuserId:'',
              sysuserAccount:'',
              sysuserPassword:'',
              sysuserStatus:'',
              roleName:'',
              roleId:'',
              createTime:'',
              modifyTime:''
            },
            // rules: {
            //    employeeName: [
            //         { required: true, message: "请输入姓名", trigger: "blur" },
            //    ]
            // }
        }
    },created(){
      this.getInfo();
    },methods:{
        getInfo(){
            sysUserInfo(localStorage.getItem('info')).then((res)=>{
              this.modifyForm = res.data.data
            });
        },
        // 保存修改
        // save(){
        //     console.log(this.modifyForm);
        //     sysUserInfo(localStorage.getItem('info')).then((res)=>{
        //       console.log(res);
        //     });
        // }
    }
};
</script>

<style lang="less" scoped>

.el-form{
    width: 70%;
    margin: 0 auto;
}

.btn{
    width:120px;
    
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
