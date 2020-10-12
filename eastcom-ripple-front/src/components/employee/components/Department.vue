<template>
      <el-main>
        <el-card>
          <div slot="header" class="card-header-text">
            <span>部门信息列表</span>
          </div>
         <div  style="height=800px;">
            <el-form :model="QueryForm" ref="QueryForm" label-width="100px" class="demo-ruleForm" size="mini">
             <el-row>
                <el-col style="width: 250px;">
                    <el-form-item label="部门名">
                        <el-input v-model="QueryForm.departmentName"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :span="5">
                    <el-button type="primary" @click="departmentfindlike" icon="el-icon-search">查询</el-button>
                    <el-button type="primary" plain @click="QueryForm.addButton= true;" icon="el-icon-edit">添加</el-button>
                </el-col>
            </el-row>
        </el-form>


            <el-header style="background-color:#C0C0C0"></el-header>
            <div v-if="QueryForm.requestResult" >
             <el-table @row-click="handleEdit" :data="QueryForm.department"  style="width: 100%; height=800px;" >
                <el-table-column prop="departmentId" label="部门ID" width="180"></el-table-column>
                <el-table-column prop="departmentName" label="部门名" width="180"></el-table-column>
                <el-table-column prop="departmentMsg" label="部门简介" width="180"></el-table-column>
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
        </div>
        
        <div style="margin-top: 5px;"></div><!--这个只是为了在页面上显示间隔-->
        <el-dialog title="编辑部门" :visible.sync="QueryForm.updateButton">
	            <el-form :model="modifyForm">
                   <el-form-item  label="部门名" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.departmentName" auto-complete="off"></el-input>
		              </el-form-item>
		              <el-form-item  label="部门简介" :label-width="modifyForm.formLabelWidth">
			                <el-input v-model="modifyForm.departmentMsg" auto-complete="off"></el-input>
		              </el-form-item>
	            </el-form>
	         <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.updateButton = false">取 消</el-button>
		          <el-button type="denger"  @click="departmentupdate">确 定</el-button>
	         </div>
        </el-dialog>

        <el-dialog title="添加部门" :visible.sync="QueryForm.addButton">
	            <el-form :model="modifyForm">
                   <el-form-item  label="部门名" :label-width="modifyForm.formLabelWidth">
			               <el-input v-model="modifyForm.departmentName" auto-complete="off"></el-input>
		              </el-form-item>
		              <el-form-item  label="部门简介" :label-width="modifyForm.formLabelWidth">
			                <el-input v-model="modifyForm.departmentMsg" auto-complete="off"></el-input>
		              </el-form-item>
	            </el-form>
	         <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.addButton = false">取 消</el-button>
		          <el-button type="denger"  @click="adddepartment">确 定</el-button>
	         </div>
        </el-dialog>

         <el-dialog title="提示框" :visible.sync="QueryForm.deleteButton">   
              <p>是否永久删除该部门！！！</p>
           <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.deleteButton = false">取 消</el-button>
		          <el-button type="denger"  @click="departmentdeletebyid">确 定</el-button>
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
                  :total="QueryForm.department.length">

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
import {departmentfindall} from '../api/department'
import {departmentfindbyid} from '../api/department'
import {departmentdeletebyid} from '../api/department'
import {departmentupdate}  from '../api/department'
import {departmentfindlike}  from '../api/department'
import {departmentadd}  from '../api/department'
export default {
  name: 'Department',
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
                 departmentId:'',
                 departmentName:'',
                 departmentMsg:'',
                 createTime:'',
                 modifyTime:'',
				         department: [{
                       departmentId:'',
                       departmentName:'',
                       departmentMsg:'',
                       createTime:'',
                       modifyTime:''
                       }],
                 requestResult: false
          },
          modifyForm:{
				        formLabelWidth:'120px',
				        departmentId:'',
                departmentName:'',
                departmentMsg:'',
                createTime:'',
                modifyTime:''
          }
        }
  },created() {
      this.departmentall()
    },
   methods: {
        departmentall() {
            departmentfindall(this.QueryForm.page).then((res) => {
               this.QueryForm.department = res.data.data;
               this.QueryForm.requestResult=true;
            });
        },  
        handleEdit(row) {
          this.modifyForm=row;
        },
        departmentupdate(){
            //console.log(this.modifyForm);时间更新异常
            departmentupdate(this.modifyForm).then((res)=>{
              this.QueryForm.updateButton= false;
              this.QueryForm.msgButton=true;
              departmentfindall(this.QueryForm.page).then((res) => {
               this.QueryForm.department = res.data.data;
               this.QueryForm.requestResult=true;
               });
            });
        },
        departmentfindlike(){
            this.modifyForm.departmentId=this.QueryForm.departmentId;
            this.modifyForm.departmentName=this.QueryForm.departmentName;
            departmentfindlike(this.modifyForm).then((res)=>{
              this.QueryForm.department = res.data.data;
              this.QueryForm.msgButton=true;
            });
        },
        adddepartment(){
            departmentadd(this.modifyForm).then((res)=>{
                   this.QueryForm.addButton= false;
                   if(res.data.code == 200){
                      this.msg='添加成功';
                      this.QueryForm.msgButton= true;
                      departmentfindall(this.QueryForm.page).then((res) => {
                      this.QueryForm.department = res.data.data;
                      this.QueryForm.requestResult=true;
                     });
                   }else{
                     this.msg='添加失败';
                     this.QueryForm.msgButton= true;
                   }
                     
            });
        },
        departmentdeletebyid(){
            console.log(this.modifyForm.departmentId);
            this.QueryForm.deleteButton=false;
        },
        handleSizeChange(val) {
            this.QueryForm.page.pageSize=val;
            departmentfindall(this.QueryForm.page).then((res) => {
               this.QueryForm.department = res.data.data;
               this.QueryForm.requestResult=true;
            });
        },
        handleCurrentChange(val) {
            this.QueryForm.page.currentPage=val;
            departmentfindall(this.QueryForm.page).then((res) => {
               this.QueryForm.department = res.data.data;
               this.QueryForm.requestResult=true;
            });
        }
    }
}
</script>



