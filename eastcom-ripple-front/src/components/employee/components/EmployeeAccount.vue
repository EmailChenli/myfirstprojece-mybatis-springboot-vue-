<template>
      <el-main>
        <el-card>
          <div slot="header" class="card-header-text">
            <span>员工账号信息列表</span>
          </div>
        <el-form :model="QueryForm" ref="QueryForm" label-width="100px" class="demo-ruleForm" size="mini">
            <el-row>     
                <el-col style="width: 250px;">
                    <el-form-item label="账号ID">
                        <el-input v-model="QueryForm.accountId"></el-input>
                    </el-form-item>
                </el-col>
                <el-col style="width: 250px;">
                    <el-form-item label="账号名">
                        <el-input v-model="QueryForm.accountName"></el-input>
                    </el-form-item>
                </el-col>
                
                <el-col :span="5">
                    <el-button type="primary" @click="accountfindlike" icon="el-icon-search">查询</el-button>
                    <el-button type="primary" plain @click="QueryForm.addButton= true;" icon="el-icon-edit">添加</el-button>
                </el-col>
            </el-row>
        </el-form>

        <el-header style="background-color:#C0C0C0"></el-header>
          <div v-if="QueryForm.requestResult">
             <el-table @row-click="handleEdit" :data="QueryForm.employeeaccount" style="width: 100%">       
                <el-table-column prop="accountId" label="员工账号ID" width="180"></el-table-column>
                <el-table-column prop="accountName" label="员工账号" width="180"></el-table-column>
                <el-table-column prop="accountPassword" label="账号密码" width="180"></el-table-column>
                <el-table-column prop="employeeId" label="员工ID" width="180"></el-table-column>
                <el-table-column prop="employeeName" label="员工名" width="180"></el-table-column>
                <el-table-column prop="createTime" label="创建日期" width="180"></el-table-column>
                <el-table-column prop="modifyTime" label="修改日期" width="180"></el-table-column>
                <el-table-column label="操作" fixed="right" width="200px" style="float=left">
		                <el-col :span="10">
			                 <el-tooltip effect="dark" content="编辑当前行" placement="top">
				                      <el-button size="mini"  type="primary" @click="QueryForm.updateButton = true" icon="el-icon-edit">编辑</el-button>
			                 </el-tooltip>
		                </el-col>
                    <el-col :span="10">
                       <el-tooltip effect="dark" content="删除当前行" placement="top">
				                       <el-button size="mini"  type="danger"  @click="QueryForm.deleteButton = true" icon="el-icon-delete">删除</el-button>
			                 </el-tooltip>
                    </el-col>
	              </el-table-column>
            </el-table>
        </div>       
        <div v-else>
            请求失败！
        </div>
        <div style="margin-top: 5px;"></div><!--这个只是为了在页面上显示间隔-->
        <el-dialog title="添加员工账号" :visible.sync="QueryForm.addButton">
            <el-form :model="modifyForm">
                <el-form-item  label="账号名" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.accountName" auto-complete="off"></el-input>
		            </el-form-item>
		             <el-form-item  label="账号密码" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.accountPassword" auto-complete="off"></el-input>
		            </el-form-item>
                <el-form-item  label="员工ID" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.employeeId" auto-complete="off"></el-input>
		            </el-form-item>
	          </el-form>      
	         <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.addButton = false">取 消</el-button>
		          <el-button type="denger"  @click="accountadd">确 定</el-button>
	         </div>
        </el-dialog>

        <div style="margin-top: 5px;"></div><!--这个只是为了在页面上显示间隔-->
        <el-dialog title="编辑账号" :visible.sync="QueryForm.updateButton">
	            <el-form :model="modifyForm">
                 <el-form-item  label="账号名" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.accountName" auto-complete="off"></el-input>
		            </el-form-item>
		             <el-form-item  label="账号密码" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.accountPassword" auto-complete="off"></el-input>
		            </el-form-item>
                <el-form-item  label="员工ID" :label-width="modifyForm.formLabelWidth">
			               <el-input v-bind:readonly="true" v-model="modifyForm.employeeId" auto-complete="off"></el-input>
		            </el-form-item>
	            </el-form>
	         <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.updateButton = false">取 消</el-button>
		          <el-button type="denger"  @click="accountupdate">确 定</el-button>
	         </div>
        </el-dialog>

         <el-dialog title="提示框" :visible.sync="QueryForm.deleteButton">   
              <p>是否永久删除！！！</p>
           <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.deleteButton = false">取 消</el-button>
		          <el-button type="denger"  @click="accountdelete">确 定</el-button>
	         </div>
        </el-dialog>
        <el-dialog title="提示框" :visible.sync="QueryForm.msgButton">   
              <p>{{msg}}</p>
        </el-dialog>
        <el-footer>
           <div style="margin-top: 5px;"></div>
                <!-- total总共有多少条 -->
                <el-pagination
                  prev-text="上一页"
                  next-text="下一页"
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="QueryForm.page.currentPage"
                  :page-sizes="[15, 30, 50, 100]"
                  :page-size="15"
                  layout="total, sizes, prev, pager, next, jumper"
                  :total="QueryForm.employeeaccount.length">

                </el-pagination>
        </el-footer>
        </el-card>
      </el-main>
</template>

<style>
.el-header {
  background-color: #c1cbd8;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>

<script>

import {accountfindall} from '../api/employeeaccount'
import {accountupdate}  from '../api/employeeaccount'
import {accountfindlike}  from '../api/employeeaccount'
import {accountadd}  from '../api/employeeaccount'
import {accountdelete}  from '../api/employeeaccount'

export default {
  name: 'EmployeeAccount',
  data() {
        return {
          msg:'',
          code:'',
          // 定义一个数据集合
          QueryForm: {
                 page:{
                   currentPage:1,
                   pageSize:15
                 },
                 msgButton:false,
				         deleteButton: false,
                 updateButton: false,
                 addButton: false,
                 accountId: '',
                 accountName: '',
                 accountPassword: '',
                 createTime: '',
                 employeeId: '',
                 modifyTime: '',
                 employeeaccount: [{
                    accountId: '',
                    accountName: '',
                    accountPassword: '',
                    createTime: '',
                    employeeId: '',
                    modifyTime: '',
                 }],
                 requestResult: false
          },modifyForm:{
				            formLabelWidth:'120px',
			              accountId: '',
                    accountName: '',
                    accountPassword: '',
                    createTime: '',
                    employeeId: '',
                    modifyTime: '',
          },findlikeForm:{
				            formLabelWidth:'120px',
				            accountId: '',
                    accountName: '',
                    accountPassword: '',
                    createTime: '',
                    employeeId: '',
                    modifyTime: '',
          },
          addForm:{
				            formLabelWidth:'120px',
				            accountId: '',
                    accountName: '',
                    accountPassword: '',
                    createTime: '',
                    employeeId: '',
                    modifyTime: '',
          }
        }
  },created(){
    this.accountall()
  },methods:{
      accountall() {
        accountfindall(this.QueryForm.page).then((res) => {
            this.QueryForm.employeeaccount = res.data.data;
            this.QueryForm.requestResult=true;
        });
      },
      handleEdit(currentrow) {
        this.modifyForm=currentrow;
      },
      accountupdate(){
            console.log(this.modifyForm);
            accountupdate(this.modifyForm).then((res)=>{
              if(res.data.code = 200){
                  this.QueryForm.updateButton= false;
                  this.QueryForm.msgButton=true;
                  this.msg= res.data.msg;
              }else{
                  this.QueryForm.msgButton=true;
                  this.msg= res.data.msg;
              }
            });
      },
      accountfindlike(){
         this.findlikeForm.accountId=this.QueryForm.accountId;
         this.findlikeForm.accountName=this.QueryForm.accountName;
        accountfindlike(this.findlikeForm).then((res)=>{
            this.msg = res.data.msg;
            this.QueryForm.employeeaccount= res.data.data;
            this.QueryForm.msgButton=true;
        });
      },
      accountdelete(){
         console.log(this.modifyForm);
         this.QueryForm.deleteButton = false;
         //accountdelete(this.modifyForm.accountId).then((res)=>{
         //});
      },
      accountadd(){
        //需要添加账号验证员工
        //信息重置
        //console.log(this.modifyForm);
         this.QueryForm.addButton = false
         accountadd(this.modifyForm).then((res)=>{
            if(res.data.code = 444){
               console.log(res);
               this.msg = res.data.msg;
               this.QueryForm.msgButton = true;
            }else{
              accountfindall(this.QueryForm.page).then((res) => {
              this.QueryForm.employeeaccount = res.data.data;
              this.QueryForm.requestResult=true;
              });
            }
         })
      },
      handleSizeChange(val) {
            //添加模糊查询判定分页console.log(this.modifyForm.employeeId=this.QueryForm.employeeId);
            this.QueryForm.page.pageSize=val;
           accountfindall(this.QueryForm.page).then((res) => {
              this.QueryForm.employeeaccount = res.data.data;
              this.QueryForm.requestResult=true;
            });
      },
      handleCurrentChange(val) {
            this.QueryForm.page.currentPage=val;
            accountfindall(this.QueryForm.page).then((res) => {
              this.QueryForm.employeeaccount = res.data.data;
              this.QueryForm.requestResult=true;
            });
      }
  }
}
</script>
