<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="card-header-text">
        <span>归还信息列表</span>
      </div>
      <!--查询表单-->
      <el-form :model="assignComForm" :rules="assignComRules" ref="assignComForm" :inline="true" class="demo-form-inline">
        <el-form-item label="电脑SN码：" class="form-item-layout" prop="comSn">
          <el-input v-model="assignComForm.comSn" placeholder="请输入电脑SN码" autocomplete="off" />
        </el-form-item>
        <el-form-item label="工号：" class="form-item-layout" prop="employeeNum">
          <el-input v-model="assignComForm.employeeNum" placeholder="请输入工号" autocomplete="off" />
        </el-form-item>
        <el-form-item class="form-item-layout">
          <el-button type="primary" @click="assignComputer">分 配</el-button>
        </el-form-item>
      </el-form>

      <!-- 电脑分配记录列表 -->
      <el-table
        :data="assignRecords"
        border
        style="width: 100%;"
        fit
        :header-cell-style="headerCellStyle"
        :cell-style="cellStyle"
        :show-header="total > 0"
        empty-text="No Data"
      >
        <el-table-column prop="recordId" label="序号"></el-table-column>
        <el-table-column prop="recordComSn" label="电脑SN码" width="150" :resizable="false">
        </el-table-column>
        <el-table-column prop="recordAssetNum" label="资产编号" :resizable="false">
        </el-table-column>
        <el-table-column prop="recordEmpNum" label="持有人工号" :resizable="false">
        </el-table-column>
        <el-table-column prop="getComTime" label="分配时间" :resizable="false">
        </el-table-column>
        <el-table-column prop="returnComTime" label="归还时间" :resizable="false">
        </el-table-column>
        <el-table-column label="操作" width="176" :resizable="false">
          <template v-slot="scope">
            <el-button
              type="danger" size="mini" style="margin-left: 3px; font-size: 10px"
               @click="returnComputer(scope.row)"
            >归还</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!--分页区域-->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pageSize"
        background
        :page-sizes="[5,10,12,15]"
        layout="total, sizes, prev, pager, next"
        :total="total"
        v-show="total>0"
      ></el-pagination>
    </el-card>
  </div>
</template>
<script>
  //引入电脑api
  import computer from "@/api/property/computer.js";

  export default {
    data() {
      return {
        currentPage: 1, //当前页
        pageSize: 10, //每页记录数
        total: 1, //总记录数
        assignRecords: [], //电脑分配记录数组
        assignComForm:{
            comSn: "",
            employeeNum: ""
        },
        assignComRules: {
          comSn: [
            { required: true, message: "电脑SN码不能为空", trigger: 'blur' },
          ],
          employeeNum: [
            { required: true, message: "工号不能为空", trigger: "blur" }
          ],
        }
      };
    },
    created() {
      //获取分配电脑记录信息
      this.getAssignComRecords();
    },
    methods: {
      //获取电脑分配记录信息
      async getAssignComRecords() {
        let _this = this;
        //用于做请求参数的query
        let query = {
          currentPage: this.currentPage,
          pageSize: this.pageSize
        };
        //console.log(query);
        computer
          .getAssignComRecord(query)
          .then((response) => {
            if (response.data.code === 400) {
              //请求错误
              this.$message.error(response.data.message);
            } else {
              let result = response.data.data;
              console.log(result);
              _this.assignRecords = result.list;
              _this.total = result.total;
              //打印结果信息
              console.log(response.data.message);
            }
          })
          .catch((error) => {
            console.log(error); //异常
          });
      },

      //分页监听事件
      // 监听每页记录数pageSize改变的时间
      handleSizeChange(newSize) {
        console.log(newSize);
        this.pageSize = newSize;
        this.getAssignComRecords();
      },
      // 监听页码值改变的事件
      handleCurrentChange(newPage) {
        console.log(newPage);
        this.currentPage = newPage;
        this.getAssignComRecords();
      },

      //分配电脑
      assignComputer() {
        this.$refs.assignComForm.validate(async (valid) => {
          if (!valid) return;
          computer
            .assignComputer(this.assignComForm)
            .then((response) => {
              if (response.data.code === 400) {
                this.$message.error(response.data.message);
              } else {
                this.$message.success(response.data.message);
                this.getAssignComRecords();
              }
            })
            .catch((error) => {
              console.log(error); //异常
            });
        });
      },
      // 归还电脑
      async returnComputer(row) {
        //用于做请求参数的query
          let query = {
            recordId: row.recordId,
            recordComSn: row.recordComSn,
            recordEmpNum: row.recordEmpNum,
            getComTime: row.getComTime,
            returnComTime: row.returnComTime
          };
          console.log(query);
          //询问用户是否删除数据
          await this.$confirm(
            "您确定要归还电脑？",
            "温馨提示",
            {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "error",
            }
          ).then(()=>{ //confirm
            //console.log("开始请求");
            computer
              .returnComputer(query)
              .then((response) => {
                if (response.data.code === 400) {
                  this.$message.error(response.data.message);
                }else {
                  this.$message.success(response.data.message);
                  this.getAssignComRecords();
                }
              })
              .catch((error) => {
                console.log(error); //异常
              });
          }).catch(()=>{
            console.log("取消归还");
          });
      },
      //修改表头样式
      headerCellStyle() {
        return 'text-align: center; font-weight: 500; font-size: 14px';
      },
      //修改表格数据样式
      cellStyle() {
        return 'text-align: center; font-weight: 500; font-size: 14px'
      }
    },
  };
</script>

<style lang="less" scoped>
  /* 查询表单布局 */
  .form-item-layout{
    float: left;
    margin-bottom: 25px;
  }
  /* 下拉框 */
  .select-box{
    width: 100px;
  }
  /* 表格 */
  .el-table{
    margin-top: 0;
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

