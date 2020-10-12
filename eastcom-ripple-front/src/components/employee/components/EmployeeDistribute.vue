<template>
    <el-main>
        <el-card>
          <div slot="header" class="card-header-text">
            <span>员工分配信息列表</span>
          </div>
       <el-form :model="QueryForm" ref="QueryForm" label-width="100px" class="demo-ruleForm" size="mini">
            <el-row>
                <el-col style="width: 250px;">
                    <el-form-item label="员工ID">
                        <el-input v-model="QueryForm.employeeId"></el-input>
                    </el-form-item>
                </el-col>
                
                 <el-col style="width: 250px;">
                    <el-form-item label="员工名">
                        <el-input v-model="QueryForm.employeeName"></el-input>
                    </el-form-item>
                </el-col>
                
                <el-col :span="5">
                    <el-button type="primary" @click="employeeDistributefindlike" icon="el-icon-search">查询</el-button>
               </el-col>
            </el-row>
        </el-form>
        <el-header style="background-color:#C0C0C0"></el-header>
        <div v-if="QueryForm.requestResult">
             <el-table @row-click="handleEdit" :data="QueryForm.employee" style="width: 100%">
                <el-table-column prop="employeeId" label="员工ID" width="180"></el-table-column>
                <el-table-column prop="employeeName" label="员工名" width="180"></el-table-column>
                <el-table-column prop="employeeIdcard" label="身份证号码" width="180"></el-table-column>
                <el-table-column prop="employeeEmail" label="邮箱" width="180"></el-table-column>
                <el-table-column prop="employeeAddress" label="居住地址" width="180"></el-table-column>
                <el-table-column prop="employeePhone" label="手机号码" width="180"></el-table-column>
                <el-table-column prop="employeeSex" label="性别" width="180"></el-table-column>
                <el-table-column prop="jobName" label="职位名" width="180"></el-table-column>
                <el-table-column prop="departmentName" label="部门名" width="180"></el-table-column>
                <el-table-column label="操作" fixed="right" width="200px" style="float=left">
		                <el-col :span="10">
			                <el-tooltip effect="dark" content="编辑当前行" placement="top">
				                <el-button size="mini"  type="primary" @click="QueryForm.updateButton = true" icon="el-icon-edit">编辑</el-button>
			                </el-tooltip>
		                </el-col>           
	            </el-table-column>
            </el-table>
        </div>       
        <div v-else>
            请求失败！
        </div>

         <div style="margin-top: 5px;"></div><!--这个只是为了在页面上显示间隔-->
        <el-dialog title="职位调度" :visible.sync="QueryForm.updateButton">
	            <el-form :model="modifyForm">
                    <el-form-item v-show="false"  label="员工ID" :label-width="modifyForm.formLabelWidth">
			               <el-input v-bind:readonly="isReadOnly=true" v-model="modifyForm.employeeId" auto-complete="off"></el-input>
		            </el-form-item>
		             <el-form-item   label="员工名" :label-width="modifyForm.formLabelWidth">
			               <el-input v-bind:readonly="isReadOnly=true" v-model="modifyForm.employeeName" auto-complete="off"></el-input>
		            </el-form-item>
		             <el-form-item  label="身份证号码" :label-width="modifyForm.formLabelWidth">
			               <el-input v-bind:readonly="isReadOnly=true" v-model="modifyForm.employeeIdcard" auto-complete="off"></el-input>
		            </el-form-item>
                   <el-form-item  label="职位名" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.jobName" auto-complete="off"></el-input>
		            </el-form-item>
                    <el-form-item  label="部门名" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.departmentName" auto-complete="off"></el-input>
		            </el-form-item>
                    
	            </el-form>
	         <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.updateButton = false">取 消</el-button>
		          <el-button type="denger"  @click="employeeDistributeupdate">确 定</el-button>
	         </div>
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
                  :total="QueryForm.employee.length">

                </el-pagination>
        </el-footer>
        </el-card>
    </el-main>              
</template>


<script>
import { employeeDistributefindall } from '../api/employeedistribute'
import { employeeDistributeupdate } from '../api/employeedistribute'
import { employeeDistributefindlike } from '../api/employeedistribute'

export default {
  name: 'EmployeeDistribute',
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
                 employeeAddress: '',
                 employeeEduSchool: '',
                 employeeEmail: '',
                 employeeId: '',
                 employeeIdcard: '',
                 employeeName: '',
                 employeePhone: '',
                 employeeSex: '',
                 jobName: '',
                 departmentName:'',
                 modifyTime: '',
                 employee: [{
                    createTime: '',
                    employeeAddress: '',
                    employeeEduSchool: '',
                    employeeEmail: '',
                    employeeId: '',
                    employeeIdcard: '',
                    employeeName: '',
                    departmentName:'',
                    employeePhone: '',
                    employeeSex: '',
                    jobName: '',
                    departmentName:'',
                    modifyTime: '',
                 }],
                 requestResult: false
          },modifyForm: {
				    formLabelWidth:'120px',
			        createTime: '',
                    employeeAddress: '',
                    employeeEduSchool: '',
                    employeeEmail: '',
                    employeeId: '',
                    employeeIdcard: '',
                    employeeName: '',
                    employeePhone: '',
                    employeeSex: '',
                    jobName: '',
                    departmentName:'',
                    modifyTime: '',
          }
        }
    },created(){
        this.employeeDistributeall();
    },methods:{
        employeeDistributeall() {
            employeeDistributefindall(this.QueryForm.page).then((res) => {
              this.QueryForm.employee = res.data.data;
              this.QueryForm.requestResult=true;
            });
        },
        handleEdit(row){
            this.modifyForm=row;
        },
        employeeDistributeupdate(){
           this.QueryForm.updateButton = false;
           employeeDistributeupdate(this.modifyForm).then((res)=>{
               console.log(res.data.msg);
               employeeDistributefindall(this.QueryForm.page).then((res) => {
                    this.QueryForm.employee = res.data.data;
                    this.QueryForm.requestResult=true;
               });
           })
        },
        employeeDistributefindlike(){
            this.modifyForm.employeeId=this.QueryForm.employeeId;
            if(this.modifyForm.employeeId != 0){
                this.modifyForm.employeeName = this.QueryForm.employeeName;
                employeeDistributefindlike(this.modifyForm).then((res)=>{
                    this.QueryForm.employee= res.data.data;
                })
            }else{
                 employeeDistributefindall(this.QueryForm.page).then((res) => {
                    this.QueryForm.employee = res.data.data;
                    this.QueryForm.requestResult=true;
                 });
            }
        },
        handleSizeChange(val) {
            this.QueryForm.page.pageSize=val;
            employeeDistributefindall(this.QueryForm.page).then((res) => {
              this.QueryForm.employee = res.data.data;
              this.QueryForm.requestResult=true;
            });
        },
        handleCurrentChange(val) {
            this.QueryForm.page.currentPage=val;
            employeeDistributefindall(this.QueryForm.page).then((res) => {
              this.QueryForm.employee = res.data.data;
              this.QueryForm.requestResult=true;
            });
        }
     }
}
</script>
