<template>
    <el-main>
     <el-card>
          <div slot="header" class="card-header-text">
            <span>系统权限列表</span>
          </div>
       <el-form :model="QueryForm" ref="QueryForm" label-width="100px" class="demo-ruleForm" size="mini">
            <el-row>
                
                 <el-col style="width: 250px;">
                    <el-form-item label="权限名">
                        <el-input v-model="QueryForm.roleName"></el-input>
                    </el-form-item>
                </el-col>
                
                <el-col :span="5">
                    <el-button type="primary" @click="rolelike" icon="el-icon-search">查询</el-button>
               </el-col>
            </el-row>
        </el-form>
        <el-header style="background-color:#C0C0C0"></el-header>
        <div v-if="QueryForm.requestResult">
             <el-table @row-click="handleEdit" :data="QueryForm.role" style="width: 100%">
                <el-table-column prop="roleId" label="权限ID" width="180"></el-table-column>
                <el-table-column prop="roleName" label="权限名" width="180"></el-table-column>
                <el-table-column prop="createTime" label="创建时间" width="180"></el-table-column>
                <el-table-column prop="modifyTime" label="修改时间" width="180"></el-table-column>
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
        <el-dialog title="权限修改" :visible.sync="QueryForm.updateButton">
	            <el-form :model="modifyForm">
                    <el-form-item v-show="false"  label="权限ID" :label-width="modifyForm.formLabelWidth">
			               <el-input v-bind:readonly="isReadOnly=true" v-model="modifyForm.roleId" auto-complete="off"></el-input>
		            </el-form-item>
		             <el-form-item   label="权限名" :label-width="modifyForm.formLabelWidth">
			               <el-input  v-model="modifyForm.roleName" auto-complete="off"></el-input>
		            </el-form-item>
		             <el-form-item  label="创建时间" :label-width="modifyForm.formLabelWidth">
			               <el-input v-bind:readonly="isReadOnly=true" v-model="modifyForm.createTime" auto-complete="off"></el-input>
		            </el-form-item>
                    <el-form-item  label="修改时间" :label-width="modifyForm.formLabelWidth">
			               <el-input v-bind:readonly="isReadOnly=true" v-model="modifyForm.modifyTime" auto-complete="off"></el-input>
		            </el-form-item>
	            </el-form>
	         <div slot="footer" class="dialog-footer">
		          <el-button type="primary" @click="QueryForm.updateButton = false">取 消</el-button>
		          <el-button type="denger"  @click="roleupdate">确 定</el-button>
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
                  :total="QueryForm.role.length">

                </el-pagination>
        </el-footer>
        </el-card>
    </el-main>                         
</template>


<script>
import { rolefindall } from '../api/systemrole'
import { roleupdate } from '../api/systemrole'
import { rolefindlike } from '../api/systemrole'

export default {
  name: 'SystemRole',
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
                 roleId:'',
                 roleName:'',
                 modifyTime: '',
                 role: [{
                    createTime: '',
                    roleId:'',
                    roleName:'',
                    modifyTime: '',
                 }],
                 requestResult: false
          },modifyForm: {
				    formLabelWidth:'120px',
			        createTime: '',
                    roleId:'',
                    roleName:'',
                    modifyTime: '',
          }
        }
    },created(){
        this.rolefindall();
    },methods:{
        rolefindall() {
            rolefindall(this.QueryForm.page).then((res) => {
              this.QueryForm.role = res.data.data;
              this.QueryForm.requestResult=true;
            });
        },
        handleEdit(row){
            this.modifyForm=row;
        },
        roleupdate(){
           this.QueryForm.updateButton = false;
           roleupdate(this.modifyForm).then((res)=>{
               console.log(res.data);
               rolefindall(this.QueryForm.page).then((res) => {
                    this.QueryForm.role = res.data.data;
                    this.QueryForm.requestResult=true;
               });
           })
        },
        rolelike(){
            this.modifyForm.roleName = this.QueryForm.roleName;
            rolefindlike(this.modifyForm,this.QueryForm.page).then((res)=>{
                 this.QueryForm.role= res.data.data;
            });
        },
        handleSizeChange(val) {
            this.QueryForm.page.pageSize=val;
            rolefindall(this.QueryForm.page).then((res) => {
              this.QueryForm.role = res.data.data;
              this.QueryForm.requestResult=true;
            });
        },
        handleCurrentChange(val) {
            this.QueryForm.page.currentPage=val;
            rolefindall(this.QueryForm.page).then((res) => {
              this.QueryForm.role = res.data.data;
              this.QueryForm.requestResult=true;
            });
        }
     }
}
</script>
