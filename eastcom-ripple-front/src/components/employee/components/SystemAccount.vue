<template>
    <el-main>
         <el-card>
          <div slot="header" class="card-header-text">
            <span>系统账号信息列表</span>
          </div>
          <el-form :model="QueryForm" ref="QueryForm" label-width="100px" class="demo-ruleForm" size="mini">
            <el-row>
                <el-col style="width: 250px;">
                    <el-form-item label="账号名">
                        <el-input v-model="QueryForm.sysuserAccount"></el-input>
                    </el-form-item>
                </el-col>
                <el-col  style="width: 250px;">
                    <el-form-item label="权限名">
                        <el-input v-model="QueryForm.roleName"></el-input>
                    </el-form-item>
                </el-col>
               
                <el-col :span="5">
                    <el-button type="primary" @click="sysuserfindlike" icon="el-icon-search">查询</el-button>
                    <el-button type="primary" plain @click="QueryForm.addButton= true;" icon="el-icon-edit">添加</el-button>
                </el-col>
            </el-row>
        </el-form>

        <el-header style="background-color:#C0C0C0"></el-header>
          <div v-if="QueryForm.requestResult">
             <el-table @row-click="handleEdit" :data="QueryForm.systemuser" style="width: 100%">
                <el-table-column prop="sysuserId" label="系统用户ID" width="180"></el-table-column>
                <el-table-column prop="sysuserAccount" label="用户名" width="180"></el-table-column>
                <el-table-column prop="sysuserPassword" label="用户密码" width="180"></el-table-column>
                <el-table-column prop="sysuserStatus" label="账号状态" width="180"></el-table-column>
                <el-table-column v-if="false" prop="roleId" label="权限ID" width="180"></el-table-column>
                <el-table-column prop="roleName" label="权限名" width="180"></el-table-column>
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
        <el-dialog title="编辑账号信息" :visible.sync="QueryForm.updateButton">
	            <el-form :model="modifyForm">
                   <el-form-item  label="账号名" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.sysuserAccount" auto-complete="off"></el-input>
		              </el-form-item>
		              <el-form-item  label="密码" :label-width="modifyForm.formLabelWidth">
			                <el-input v-model="modifyForm.sysuserPassword" auto-complete="off"></el-input>
		              </el-form-item>
                  <el-form-item  label="状态" :label-width="modifyForm.formLabelWidth">
			                <el-input v-model="modifyForm.sysuserStatus" auto-complete="off"></el-input>
		              </el-form-item>
                  <el-form-item  label="权限ID" :label-width="modifyForm.formLabelWidth">
			                <el-input v-model="modifyForm.roleId" auto-complete="off"></el-input>
		              </el-form-item>
	            </el-form>
	         <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.updateButton = false">取 消</el-button>
		          <el-button type="denger"  @click="sysuserupdate">确 定</el-button>
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
                  :total="QueryForm.systemuser.length">
                </el-pagination>
        </el-footer>
      </el-card>
    </el-main>              
</template>


<script>

import {sysuserfindall} from '../api/systemaccount'
import {sysuserupdate}  from '../api/systemaccount'
import {sysuserfindlike}  from '../api/systemaccount'
// import {sysuseradd}  from '../api/systemaccount'
// import {sysuserdelete}  from '../api/systemaccount'

export default {
  name: 'SystemAccount',
  data() {
        return {
          msg:'',
          code:'',
           // 定义一个数据集合
          QueryForm: {
                 msgButton:false,
				         deleteButton: false,
                 updateButton: false,
                 addButton: false,
                 sysuserId:'',
                 sysuserAccount:'',
                 sysuserPassword:'',
                 sysuserStatus:'',
                 roleName:'',
                 roleId:'',
                 createTime:'',
                 modifyTime:'',
                 page: 
                 {
                   currentPage:1,
                   pageSize:15,
                 },
                 systemuser: [{
                       sysuserId:'',
                       sysuserAccount:'',
                       sysuserPassword:'',
                       sysuserStatus:'',
                       roleName:'',
                       roleId:'',
                       createTime:'',
                       modifyTime:''
                 }],
                 requestResult: false
          },modifyForm:{
				        formLabelWidth:'120px',
				        sysuserId:'',
                sysuserAccount:'',
                sysuserPassword:'',
                sysuserStatus:'',
                roleName:'',
                roleId:'',
                createTime:'',
                modifyTime:''
          }
        }
  },created(){
    this.sysuserall()
  },methods:{
      sysuserall() {
         sysuserfindall(this.QueryForm.page).then((res) => {
            this.QueryForm.systemuser = res.data.data;
            this.QueryForm.requestResult=true;
         });
      },
      handleEdit(currentrow) {
        //获取当前行数据
        this.modifyForm=currentrow;
      },
      sysuserupdate(){
            sysuserupdate(this.modifyForm).then((res)=>{
              console.log(res);
              if(res.data.code == 444){
                this.msg='更新失败';
                this.QueryForm.updateButton= false
                this.QueryForm.msgButton=true;
              }else{
                this.QueryForm.updateButton= false
                this.QueryForm.msgButton=true;
                this.msg='更新成功'
                sysuserfindall(this.QueryForm.page).then((res) => {
                    this.QueryForm.systemuser = res.data.data;
                    this.QueryForm.requestResult=true;
                });
              }
            });
      },
      sysuserfindlike(){
         this.modifyForm.sysuserAccount=this.QueryForm.sysuserAccount;
         this.modifyForm.roleName=this.QueryForm.roleName;
        sysuserfindlike(this.modifyForm,this.QueryForm.page).then((res)=>{
            console.log(res);
            this.QueryForm.systemuser = res.data.data;
        })
      },
      // sysuserdelete(){
      //    console.log(this.modifyForm.jobId);
      //    this.QueryForm.deleteButton = false;
      // },
      // sysuseradd(){
      //    sysuseradd(this.modifyForm).then((res)=>{
      //       this.QueryForm.addButton=false;
      //       if(res.data.code == 444){
      //           this.msg='添加失败！格式错误或部门不存在！';
      //           this.QueryForm.msgButton=true;
      //       }else{
      //             jobfindall(this.QueryForm.page).then((res) => {
      //             this.QueryForm.job = res.data.data;
      //             this.QueryForm.requestResult=true;
      //             });
      //       }
      //    });
      // },
      handleSizeChange(val) {
           // console.log('每页 '+val +'条');
            this.QueryForm.page.pageSize=val;
            sysuserfindall(this.QueryForm.page).then((res) => {
              this.QueryForm.systemuser = res.data.data;
              this.QueryForm.requestResult=true;
            });
      },
      handleCurrentChange(val) {
           // console.log('当前页: '+val);
            this.QueryForm.page.currentPage=val;
            sysuserfindall(this.QueryForm.page).then((res) => {
              this.QueryForm.systemuser = res.data.data;
              this.QueryForm.requestResult=true;
            });
      }
  }
}
</script>
