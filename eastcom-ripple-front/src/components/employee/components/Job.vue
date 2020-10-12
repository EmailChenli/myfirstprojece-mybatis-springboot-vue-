<template>
      <el-main>
        <el-card>
          <div slot="header" class="card-header-text">
            <span>职位信息列表</span>
          </div>
        <el-form :model="QueryForm" ref="QueryForm" label-width="100px" class="demo-ruleForm" size="mini">
            <el-row>
                <el-col style="width: 250px;">
                    <el-form-item label="职位名">
                        <el-input v-model="QueryForm.jobName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col  style="width: 250px;">
                    <el-form-item label="部门名">
                        <el-input v-model="QueryForm.departmentName"></el-input>
                    </el-form-item>
                </el-col>
               
                <el-col :span="5">
                    <el-button type="primary" @click="jobfindlike" icon="el-icon-search">查询</el-button>
                    <el-button type="primary" plain @click="QueryForm.addButton= true;" icon="el-icon-edit">添加</el-button>
                </el-col>
            </el-row>
        </el-form>

        <el-header style="background-color:#C0C0C0"></el-header>

        <div v-if="QueryForm.requestResult">
             <el-table @row-click="handleEdit" :data="QueryForm.job" style="width: 100%">
                <el-table-column prop="jobId" label="职位ID" width="180"></el-table-column>
                <el-table-column prop="jobName" label="职位名" width="180"></el-table-column>
                <el-table-column prop="jobMsg" label="职位简介" width="180"></el-table-column>
                <el-table-column prop="departmentName" label="部门名" width="180"></el-table-column>
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
              <div style="margin-top: 5px;"></div><!--这个只是为了在页面上显示间隔-->
        <el-dialog title="添加职位" :visible.sync="QueryForm.addButton">
	            <el-form :model="modifyForm">
                   <el-form-item  label="职位名" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.jobName" auto-complete="off"></el-input>
		              </el-form-item>
		              <el-form-item  label="职位简介" :label-width="modifyForm.formLabelWidth">
			                <el-input v-model="modifyForm.jobMsg" auto-complete="off"></el-input>
		              </el-form-item>
                  <el-form-item  label="部门ID" :label-width="modifyForm.formLabelWidth">
			                <el-input v-model="modifyForm.departmentId" auto-complete="off"></el-input>
		              </el-form-item>
	            </el-form>
	         <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.addButton = false">取 消</el-button>
		          <el-button type="denger"  @click="jobadd">确 定</el-button>
	         </div>
        </el-dialog>


        <div style="margin-top: 5px;"></div><!--这个只是为了在页面上显示间隔-->
        <el-dialog title="编辑职位" :visible.sync="QueryForm.updateButton">
	            <el-form :model="modifyForm">
                   <el-form-item  label="职位名" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.jobName" auto-complete="off"></el-input>
		              </el-form-item>
		              <el-form-item  label="职位简介" :label-width="modifyForm.formLabelWidth">
			                <el-input v-model="modifyForm.jobMsg" auto-complete="off"></el-input>
		              </el-form-item>
                  <el-form-item  label="部门ID" :label-width="modifyForm.formLabelWidth">
			                <el-input v-model="modifyForm.departmentId" auto-complete="off"></el-input>
		              </el-form-item>
	            </el-form>
	         <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.updateButton = false">取 消</el-button>
		          <el-button type="denger"  @click="jobupdate">确 定</el-button>
	         </div>
        </el-dialog>

         <el-dialog title="提示框" :visible.sync="QueryForm.deleteButton">   
              <p>是否永久删除该职位！！！</p>
           <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.deleteButton = false">取 消</el-button>
		          <el-button type="denger"  @click="jobdelete">确 定</el-button>
	         </div>
        </el-dialog>
        <el-dialog title="提示框" :visible.sync="QueryForm.msgButton">   
              <p>{{msg}}</p>
        </el-dialog>
            
        </div>
               
        <div v-else>
            请求失败！
        </div>
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
                  :total="QueryForm.job.length">

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

import {jobfindall} from '../api/job'
import {jobupdate}  from '../api/job'
import {jobfindlike}  from '../api/job'
import {jobadd}  from '../api/job'
import {jobdelete}  from '../api/job'

export default {
  name: 'Job',
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
                 jobId:'',
                 jobName:'',
                 jobMsg:'',
                 departmentId:'',
                 departmentName:'',
                 createTime:'',
                 modifyTime:'',
                 page: 
                 {
                   currentPage:1,
                   pageSize:15,
                 },
                 job: [{
                       jobId:'',
                       jobName:'',
                       jobMsg:'',
                       departmentId:'',
                       departmentName:'',
                       createTime:'',
                       modifyTime:''
                 }],
                 requestResult: false
          },modifyForm:{
				         formLabelWidth:'120px',
				         jobId:'',
                 jobName:'',
                 jobMsg:'',
                 departmentId:'',
                 departmentName:'',
                 createTime:'',
                 modifyTime:''
          },findlikeForm:{
				         formLabelWidth:'120px',
				         jobId:'',
                 jobName:'',
                 jobMsg:'',
                 departmentId:'',
                 departmentName:'',
                 createTime:'',
                 modifyTime:''
          }
        }
  },created(){
    this.joball()
  },methods:{
      joball() {
            jobfindall(this.QueryForm.page).then((res) => {
              this.QueryForm.job = res.data.data;
              this.QueryForm.requestResult=true;
            });
      },
      handleEdit(currentrow) {
        //获取当前行数据
        this.modifyForm=currentrow;
      },
      jobupdate(){
            jobupdate(this.modifyForm).then((res)=>{
              console.log(res);
              if(res.data.code == 444){
                this.msg='更新失败！格式错误或部门ID不存在！';
                this.QueryForm.updateButton= false
                this.QueryForm.msgButton=true;
              }else{
                this.QueryForm.updateButton= false
                this.QueryForm.msgButton=true;
                this.msg='更新成功！'
                jobfindall(this.QueryForm.page).then((res) => {
                   this.QueryForm.job = res.data.data;
                   this.QueryForm.requestResult=true;
                });  
              }
            });
      },
      jobfindlike(){
         this.findlikeForm.jobName=this.QueryForm.jobName;
         this.findlikeForm.departmentName=this.QueryForm.departmentName;
        jobfindlike(this.findlikeForm).then((res)=>{
            this.QueryForm.job = res.data.data;
        });
      },
      jobdelete(){
         console.log(this.modifyForm.jobId);
         this.QueryForm.deleteButton = false;
      },
      jobadd(){
         jobadd(this.modifyForm).then((res)=>{
            this.QueryForm.addButton=false;
            if(res.data.code == 444){
                this.msg='添加失败！格式错误或部门不存在！';
                this.QueryForm.msgButton=true;
            }else{
                  jobfindall(this.QueryForm.page).then((res) => {
                  this.QueryForm.job = res.data.data;
                  this.QueryForm.requestResult=true;
                  });
            }
         });
      },
      handleSizeChange(val) {
           // console.log('每页 '+val +'条');
            this.QueryForm.page.pageSize=val;
            jobfindall(this.QueryForm.page).then((res) => {
              this.QueryForm.job = res.data.data;
              this.QueryForm.requestResult=true;
            });
      },
      handleCurrentChange(val) {
           // console.log('当前页: '+val);
            this.QueryForm.page.currentPage=val;
            jobfindall(this.QueryForm.page).then((res) => {
              console.log(res);
              this.QueryForm.job = res.data.data;
              this.QueryForm.requestResult=true;
            });
      }
  }
}
</script>
