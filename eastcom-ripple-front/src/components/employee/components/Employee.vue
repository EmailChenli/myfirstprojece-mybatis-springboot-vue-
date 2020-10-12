<template>
  <el-main>
    <el-card>
      <div slot="header" class="card-header-text">
        <span>员工信息列表</span>
      </div>
      <el-form
        :model="QueryForm"
        ref="QueryForm"
        label-width="100px"
        class="demo-ruleForm"
        size="mini"
      >
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
            <el-button type="primary" @click="employeefindlike" icon="el-icon-search">查询</el-button>
            <el-button
              type="primary"
              plain
              @click="QueryForm.addButton= true;"
              icon="el-icon-edit"
            >添加</el-button>
          </el-col>

          <el-col :span="5">
            <el-button type="primary" icon="document" @click="exportToExcel">导出Excel</el-button>
          </el-col>
        </el-row>
      </el-form>

      <el-header>
          
      </el-header>

      <div v-if="QueryForm.requestResult">
        <el-table @row-click="handleEdit" :data="QueryForm.employee" style="width: 100%">
          <el-table-column prop="employeeId" label="员工ID" width="180"></el-table-column>
          <el-table-column prop="employeeName" label="员工名" width="180"></el-table-column>
          <el-table-column prop="employeeIdcard" label="身份证号码" width="180"></el-table-column>
          <el-table-column prop="employeeEduSchool" label="员工学历" width="180"></el-table-column>
          <el-table-column prop="employeeEmail" label="邮箱" width="180"></el-table-column>
          <el-table-column prop="employeeAddress" label="居住地址" width="180"></el-table-column>
          <el-table-column prop="employeePhone" label="手机号码" width="180"></el-table-column>
          <el-table-column prop="employeeSex" label="性别" width="180"></el-table-column>
          <el-table-column prop="createTime" label="创建日期" width="180"></el-table-column>
          <el-table-column prop="modifyTime" label="修改日期" width="180"></el-table-column>
          <el-table-column label="操作" fixed="right" width="200px" style="float=left">
            <el-col :span="10">
              <el-tooltip effect="dark" content="编辑当前行" placement="top">
                <el-button
                  size="mini"
                  type="primary"
                  @click="QueryForm.updateButton = true"
                  icon="el-icon-edit"
                >编辑</el-button>
              </el-tooltip>
            </el-col>
            <el-col :span="10">
              <el-tooltip effect="dark" content="删除当前行" placement="top">
                <el-button
                  size="mini"
                  type="danger"
                  @click="QueryForm.deleteButton = true"
                  icon="el-icon-delete"
                >删除</el-button>
              </el-tooltip>
            </el-col>
          </el-table-column>
        </el-table>
      </div>
      <div v-else>请求失败！</div>
      <div style="margin-top: 5px;"></div>
      <!--这个只是为了在页面上显示间隔-->
      <el-dialog title="添加员工" :visible.sync="QueryForm.addButton">
        <el-form :model="QueryForm">
          <el-form-item label="员工名" :label-width="QueryForm.formLabelWidth">
            <el-input v-model="QueryForm.employeeName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="手机号码" :label-width="QueryForm.formLabelWidth">
            <el-input v-model="QueryForm.employeePhone" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="身份证号码" :label-width="QueryForm.formLabelWidth">
            <el-input v-model="QueryForm.employeeIdcard" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="最高学历" :label-width="QueryForm.formLabelWidth">
            <el-input v-model="QueryForm.employeeEduSchool" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" :label-width="QueryForm.formLabelWidth">
            <el-input v-model="QueryForm.employeeEmail" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="性别" :label-width="QueryForm.formLabelWidth">
            <el-input v-model="QueryForm.employeeSex" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="居住地" :label-width="QueryForm.formLabelWidth">
            <el-input v-model="QueryForm.employeeAddress" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="QueryForm.addButton = false">取 消</el-button>
          <el-button type="denger" @click="employeeadd">确 定</el-button>
        </div>
      </el-dialog>

      <div style="margin-top: 5px;"></div>
      <!--这个只是为了在页面上显示间隔-->
      <el-dialog title="编辑入职信息" :visible.sync="QueryForm.updateButton">
        <el-form :model="modifyForm">
          <el-form-item label="员工名" :label-width="modifyForm.formLabelWidth">
            <el-input v-model="modifyForm.employeeName" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="身份证号码" :label-width="modifyForm.formLabelWidth">
            <el-input v-model="modifyForm.employeeIdcard" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="最高学历" :label-width="modifyForm.formLabelWidth">
            <el-input v-model="modifyForm.employeeEduSchool" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="邮箱" :label-width="modifyForm.formLabelWidth">
            <el-input v-model="modifyForm.employeeEmail" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="性别" :label-width="modifyForm.formLabelWidth">
            <el-input v-model="modifyForm.employeeSex" auto-complete="off"></el-input>
          </el-form-item>
          <el-form-item label="居住地" :label-width="modifyForm.formLabelWidth">
            <el-input v-model="modifyForm.employeeAddress" auto-complete="off"></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="QueryForm.updateButton = false">取 消</el-button>
          <el-button type="denger" @click="employeeupdate">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog title="提示框" :visible.sync="QueryForm.deleteButton">
        <p>是否删除</p>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="QueryForm.deleteButton = false">取 消</el-button>
          <el-button type="denger" @click="employeedelete">确 定</el-button>
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
          :total="QueryForm.employee.length"
        ></el-pagination>
      </el-footer>
    </el-card>
  </el-main>
</template>

<style>
.el-main .el-header {
  background-color: #ffffff;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>

<script>
import { employeefindall } from "../api/employee";
import { employeeupdate } from "../api/employee";
import { employeefindlike } from "../api/employee";
import { employeeadd } from "../api/employee";
import { employeedelete } from "../api/employee";

export default {
  name: "Employee",
  data() {
    return {
      msg: "",
      code: "",
      
      // 定义一个数据集合
      QueryForm: {
        page: {
          currentPage: 1,
          pageSize: 15,
        },
        msgButton: false,
        deleteButton: false,
        updateButton: false,
        addButton: false,
        formLabelWidth: "120px",
        createTime: "",
        employeeAddress: "",
        employeeEduSchool: "",
        employeeEmail: "",
        employeeId: "",
        employeeIdcard: "",
        employeeName: "",
        employeePhone: "",
        employeeSex: "",
        modifyTime: "",
        employee: [
          {
            createTime: "",
            employeeAddress: "",
            employeeEduSchool: "",
            employeeEmail: "",
            employeeId: "",
            employeeIdcard: "",
            employeeName: "",
            employeePhone: "",
            employeeSex: "",
            modifyTime: "",
          },
        ],
        requestResult: false,
      },
      modifyForm: {
        formLabelWidth: "120px",
        createTime: "",
        employeeAddress: "",
        employeeEduSchool: "",
        employeeEmail: "",
        employeeId: "",
        employeeIdcard: "",
        employeeName: "",
        employeePhone: "",
        employeeSex: "",
        modifyTime: "",
      },
    };
  },
  created() {
    this.employeeall();
  },
  updated(){
    console.log("更新成功后");
  },
  methods: {
    employeeall() {
      console.log(this.QueryForm.page);
      employeefindall(this.QueryForm.page).then((res) => {
        this.QueryForm.employee = res.data.data;
        this.QueryForm.requestResult = true;
      });
    },
    handleEdit(currentrow) {
      this.modifyForm = currentrow;
    },
    employeeupdate() {
      console.log(this.modifyForm);
      employeeupdate(this.modifyForm).then((res) => {
        this.QueryForm.updateButton = false;
        employeefindall(this.QueryForm.page).then((res) => {
          this.QueryForm.employee = res.data.data;
          this.QueryForm.requestResult = true;
        });
      });
    },
    employeefindlike() {
      this.modifyForm.employeeId = this.QueryForm.employeeId;
      if (this.modifyForm.employeeId == 0) {
        employeefindall(this.QueryForm.page).then((res) => {
          this.QueryForm.employee = res.data.data;
          this.QueryForm.requestResult = true;
        });
      } else {
        this.modifyForm.employeeName = this.QueryForm.employeeName;
        employeefindlike(this.modifyForm,this.QueryForm.page).then((res) => {
          this.QueryForm.employee = res.data.data;
          console.log(res);
        });
      }
    },
    employeedelete() {
      console.log(this.modifyForm.employeeId);
      employeedelete(this.modifyForm.employeeId).then((res) => {
        this.QueryForm.msgButton = true;
        this.QueryForm.deleteButton = false;
        this.msg = res.data.msg;
        employeefindall(this.QueryForm.page).then((res) => {
          this.QueryForm.employee = res.data.data;
          this.QueryForm.requestResult = true;
        });
      });
    },
    employeeadd() {
      this.modifyForm.employeeAddress = this.QueryForm.employeeAddress;
      this.modifyForm.employeeEduSchool = this.QueryForm.employeeEduSchool;
      this.modifyForm.employeeEmail = this.QueryForm.employeeEmail;
      this.modifyForm.employeeIdcard = this.QueryForm.employeeIdcard;
      this.modifyForm.employeeName = this.QueryForm.employeeName;
      this.modifyForm.employeePhone = this.QueryForm.employeePhone;
      this.modifyForm.employeeSex = this.QueryForm.employeeSex;
      this.QueryForm.addButton = false;
      employeeadd(this.modifyForm).then((res) => {
        this.msg = res.data.msg;
        this.QueryForm.msgButton = true;
        employeefindall(this.QueryForm.page).then((res) => {
          this.QueryForm.employee = res.data.data;
          this.QueryForm.requestResult = true;
        });
      });
    },
    handleSizeChange(val) {
      this.QueryForm.page.pageSize = val;
      employeefindall(this.QueryForm.page).then((res) => {
        this.QueryForm.employee = res.data.data;
        this.QueryForm.requestResult = true;
      });
    },
    handleCurrentChange(val) {
      this.QueryForm.page.currentPage = val;
      employeefindall(this.QueryForm.page).then((res) => {
        this.QueryForm.employee = res.data.data;
        this.QueryForm.requestResult = true;
      });
    },
    exportToExcel() {
        //excel数据导出
        require.ensure([], () => {
            const {
                export_json_to_excel
            } = require('@/components/employee/api/Export2Excel');
 
            const tHeader = ['员工ID','员工名','身份证号码','员工学历','邮箱','居住地址','手机号码','性别','创建日期','修改日期'];
            const filterVal = ['employeeId','employeeName', 'employeeIdcard', 'employeeEduSchool','employeeEmail','employeeAddress','employeePhone','employeeSex','createTime','modifyTime'];
            
            const list = this.QueryForm.employee;
            const data = this.formatJson(filterVal, list);
            // console.log(list);
            // console.log(data);
            export_json_to_excel(tHeader, data, '列表excel');
        })
    },
    formatJson(filterVal, jsonData) {
        return jsonData.map(v => filterVal.map(j => v[j]))
    }
  }
};
</script>
