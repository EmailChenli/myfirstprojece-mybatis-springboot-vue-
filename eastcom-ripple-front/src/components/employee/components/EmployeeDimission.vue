<template>
      <el-main>
        <el-card>
          <div slot="header" class="card-header-text">
            <span>员工信息列表</span>
          </div>
        <el-form :model="QueryForm" ref="QueryForm" label-width="100px" class="demo-ruleForm" size="mini">
            <el-row>
              
                <el-col style="width: 250px;">
                    <el-form-item label="员工ID">
                        <el-input v-model="QueryForm.employeeDimissionId"></el-input>
                    </el-form-item>
                </el-col>
                <el-col style="width: 250px;">
                    <el-form-item label="员工名">
                        <el-input v-model="QueryForm.employeeDimissionName"></el-input>
                    </el-form-item>
                </el-col>
                
                <el-col :span="5">
                    <el-button type="primary" @click="employeeDimissionfindlike" icon="el-icon-search">查询</el-button>
                </el-col>
            </el-row>
        </el-form>

        <el-header style="background-color:#C0C0C0"></el-header>

        <div v-if="QueryForm.requestResult">

             <el-table @row-click="handleEdit" :data="QueryForm.employeeDimission" style="width: 100%">
                <el-table-column prop="employeeDimissionId" label="员工ID" width="180"></el-table-column>
                <el-table-column prop="employeeDimissionName" label="员工名" width="180"></el-table-column>
                <el-table-column prop="employeeDimissionIdcard" label="身份证号码" width="180"></el-table-column>
                <el-table-column prop="employeeDimissionEduSchool" label="员工学历" width="180"></el-table-column>
                <el-table-column prop="employeeDimissionEmail" label="邮箱" width="180"></el-table-column>
                <el-table-column prop="employeeDimissionAddress" label="居住地址" width="180"></el-table-column>
                <el-table-column prop="employeeDimissionPhone" label="手机号码" width="180"></el-table-column>
                <el-table-column prop="employeeDimissionSex" label="性别" width="180"></el-table-column>
                <el-table-column prop="jobName" label="职位名" width="180"></el-table-column>
                <el-table-column prop="departmentName" label="部门名" width="180"></el-table-column>
                <el-table-column prop="createTime" label="创建日期" width="180"></el-table-column>
                <el-table-column prop="modifyTime" label="修改日期" width="180"></el-table-column>
                <el-table-column label="操作" fixed="right" width="200px" style="float=left">
		                      <el-col :span="10">
			                      <el-tooltip effect="dark" content="删除当前行" placement="top">
				                      <el-button size="mini"  type="danger" @click="QueryForm.deleteButton = true" icon="el-icon-edit">删除</el-button>
			                      </el-tooltip>
		                      </el-col>
	              </el-table-column>
            </el-table>
        </div>       
        <div v-else>
            请求失败！
        </div>
        
        <el-dialog title="提示框" :visible.sync="QueryForm.deleteButton">   
              <p>是否删除</p>
              <div slot="footer" class="dialog-footer">
		            <el-button type="primary" @click="QueryForm.deleteButton = false">取 消</el-button>
		            <el-button type="denger"  @click="employeeDimissiondelete">确 定</el-button>
	            </div>
        </el-dialog>
      
        <el-dialog title="提示框" :visible.sync="QueryForm.msgButton">   
              <p>操作成功！！！</p>
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
                  :total="QueryForm.employeeDimission.length">

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

import {employeeDimissionfindall} from '../api/employeedimission'
import {employeeDimissionfindlike}  from '../api/employeedimission'
import {employeeDimissiondelete}  from '../api/employeedimission'

export default {
  name: 'EmployeeDimission',
  data() {
        return {
          msg:'',
          code:'',
          // 定义一个数据集合
          QueryForm: {
                 page:{
                   currentPage: 1,
                   pageSize: 15
                 },  
                 msgButton:false,
				         deleteButton: false,
                 updateButton: false,
                 addButton: false,
                 createTime: '',
                 employeeDimissionAddress: '',
                 employeeDimissionEduSchool: '',
                 employeeDimissionEmail: '',
                 employeeDimissionId: '',
                 employeeDimissionIdcard: '',
                 employeeDimissionName: '',
                 employeeDimissionPhone: '',
                 employeeDimissionSex: '',
                 jobName: '',
                 departmentName:'',
                 modifyTime: '',
                 employeeDimission: [{
                     createTime: '',
                     employeeDimissionAddress: '',
                     employeeDimissionEduSchool: '',
                     employeeDimissionEmail: '',
                     employeeDimissionId: '',
                     employeeDimissionIdcard: '',
                     employeeDimissionName: '',
                     employeeDimissionPhone: '',
                     employeeDimissionSex: '',
                     jobName: '',
                     departmentName:'',
                     modifyTime: '',
                 }],
                 requestResult: false
          },modifyForm:{
				     formLabelWidth:'120px',
				     createTime: '',
             employeeDimissionAddress: '',
             employeeDimissionEduSchool: '',
             employeeDimissionEmail: '',
             employeeDimissionId: '',
             employeeDimissionIdcard: '',
             employeeDimissionName: '',
             employeeDimissionPhone: '',
             employeeDimissionSex: '',
             modifyTime: '',
          }
        }
  },created(){
    this.employeeDimissionfindall();
  },methods:{
      employeeDimissionfindall() {
          employeeDimissionfindall(this.QueryForm.page).then((res) => {
              this.QueryForm.employeeDimission = res.data.data;
              this.QueryForm.requestResult=true;
          });
      },
      handleEdit(currentrow) {
        this.modifyForm=currentrow;
      },
      employeeDimissionfindlike(){
         if(this.QueryForm.employeeDimissionId == 0){
            employeeDimissionfindall(this.QueryForm.page).then((res) => {
              this.QueryForm.employeeDimission = res.data.data;
              this.QueryForm.requestResult=true;
            });
         }else{
            this.modifyForm.employeeDimissionId=this.QueryForm.employeeDimissionId;
            this.modifyForm.employeeDimissionName=this.QueryForm.employeeDimissionName;
            employeeDimissionfindlike(this.modifyForm).then((res)=>{
              this.QueryForm.employeeDimission= res.data.data;
              this.QueryForm.msgButton=true;
            });
        } 
      },
      employeeDimissiondelete(){
         console.log(this.modifyForm);
         this.QueryForm.deleteButton = false;
      },
      handleSizeChange(val) {
            this.QueryForm.page.pageSize=val;
            employeeDimissionfindall(this.QueryForm.page).then((res) => {
              this.QueryForm.employee = res.data.data;
              this.QueryForm.requestResult=true;
            });
      },
      handleCurrentChange(val) {
            this.QueryForm.page.currentPage=val;
            employeeDimissionfindall(this.QueryForm.page).then((res) => {
              this.QueryForm.employee = res.data.data;
              this.QueryForm.requestResult=true;
            });
      }
  }
}
</script>
