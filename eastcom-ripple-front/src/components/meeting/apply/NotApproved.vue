<template>
  <div>
    <el-card>
      <div slot="header" class="card-header-text">
        <span>未审批信息列表</span>
      </div>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入会议名称、会议室编号或账号" v-model="keyWord" clearable @clear="clear()">
            <el-button slot="append" icon="el-icon-search" @click="doSearch()"></el-button>
          </el-input>
        </el-col>
        <el-col :span="1.5">
          <el-button icon="el-icon-refresh" @click="refreshNotApList()"></el-button>
        </el-col>
      </el-row>

      <el-table :data="notApprovedList" border style="width: 100%">
        <el-table-column prop="bookingId" label="预订编号" width="80"></el-table-column>
        <el-table-column prop="meetingName" label="会议名称"></el-table-column>
        <el-table-column prop="roomId" label="会议室编号" width="90"></el-table-column>
        <el-table-column prop="meetingDescription" label="会议详情"></el-table-column>
        <el-table-column prop="userName" label="申请账号" width="80"></el-table-column>
        <el-table-column prop="meetingNumber" label="人数" width="50"></el-table-column>
        <el-table-column prop="applyTime" label="申请时间" width="150"></el-table-column>
        <el-table-column prop="startTime" label="会议开始时间" width="150"></el-table-column>
        <el-table-column prop="endTime" label="会议结束时间" width="150"></el-table-column>
        <el-table-column label="状态">未审批</el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <!-- 同意按钮 -->
            <el-tooltip class="item" effect="dark" content="同意申请" placement="top" :enterable="false">
              <el-button size="mini" type="primary" icon="el-icon-check" @click="accept(scope.row.bookingId)"></el-button>
            </el-tooltip>
            <!-- 拒绝按钮 -->
            <el-tooltip class="item" effect="dark" content="拒绝申请" placement="top" :enterable="false">
              <el-button size="mini" type="primary" icon="el-icon-close" @click="showRejectDialog(scope.row.bookingId)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页区域 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.page"
        :page-sizes="[5, 10, 15, 20]"
        :page-size="queryInfo.rows"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total">
      </el-pagination>

    </el-card>

    <el-dialog
      title="拒绝理由"
      :visible.sync="rejectDialogVisible"
      width="50%"
      @close="rejectDialogClosed">
      <el-form :model="rejectForm" :rules="rejectFormRules" ref="rejectFormRef" label-width="100px">
        <el-form-item prop="rejectReason" label="拒绝理由">
          <el-input v-model="rejectForm.rejectReason" ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="rejectDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="reject">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
  import axios from 'axios'
  export default {
    data() {
      return {
        queryInfo: {
          page: 1,
          rows: 5
        },
        notApprovedList: [],
        total: 0,
        keyWord: '',
        rejectDialogVisible: false,
        rejectForm: {
          bookingId: '',
          rejectReason: ''
        },
        rejectFormRules: {
          rejectReason: [
            { required: true, message: '请填写拒绝理由', trigger: 'blur' }
          ]
        }
      }
    },
    created(){
      this.getNotApprovedList();
    },
    methods: {
      getNotApprovedList() {
        axios.post(`http://192.168.93.10:8080/meetingroom/meetingbooking/findByStatus/${this.queryInfo.page}/${this.queryInfo.rows}`, {
          status: 0

        }).then((response) => {
            if (response.data.code == 400) {
              this.$message.error("查询失败：" + response.data.message)
            } else {
              this.notApprovedList = response.data.data.list
              this.total = response.data.data.count
            }
          }).catch((response) => {
          console.log(response)
        })
      },
      handleSizeChange(newSize) {
        this.queryInfo.rows = newSize
        if (this.keyWord == ''){
          this.getNotApprovedList()
        }else{
          this.searchNotApproved()
        }
      },
      handleCurrentChange(newPage) {
        this.queryInfo.page = newPage
        if (this.keyWord == ''){
          this.getNotApprovedList()
        }else{
          this.searchNotApproved()
        }
      },
      rejectDialogClosed() {
        this.$refs.rejectFormRef.resetFields()
      },
      clear(){
        this.queryInfo.page = 1
        this.queryInfo.rows = 5
        this.getNotApprovedList()
      },
      doSearch(){
        if (this.keyWord == ''){
          this.$message.info("请输入搜索条件")
          return
        }else{
          this.queryInfo.page = 1
          this.queryInfo.rows = 5
          this.searchNotApproved()
        }
      },
      searchNotApproved() {
        axios.post(`http://192.168.93.10:8080/meetingroom/meetingbooking/searchNotApproved/${this.queryInfo.page}/${this.queryInfo.rows}`, {
          status: 0,
          para: this.keyWord
        }).then((response) => {
            if (response.data.code == 400) {
              this.$message.error("查询失败：" + response.data.message)
            } else {
              this.notApprovedList = response.data.data.list
              this.total = response.data.data.count
            }
          }).catch((response) => {
          console.log(response)
        })
      },
      showRejectDialog(id) {
        this.rejectForm.bookingId = id
        this.rejectDialogVisible = true
      },
      async accept(id) {
        const confirmResult = await this.$confirm('确定同意申请吗?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).catch(err => err)
        if (confirmResult !== 'confirm') {
          return this.$message.info('已取消')
        }
        axios.post(`http://192.168.93.10:8080/meetingroom/meetingbooking/updateBookingStatus`, {
          bookingId: id,
          status: 1,
          rejectReason: ''
        }).then((response) => {
          if (response.data.code == 400) {
            return this.$message.error("操作失败：" + response.data.message)
          }
          this.$message.success("审批成功")
          if (this.keyWord == ''){
            this.getNotApprovedList()
          }else{
            this.searchNotApproved()
          }
        }).catch((response) => {
          console.log(response)
          this.$message.error("操作失败")
        })
      },
      reject() {
        if(this.rejectForm.rejectReason == ""){
          this.$message("请填写拒绝理由")
          return
        }
        axios.post(`http://192.168.93.10:8080/meetingroom/meetingbooking/updateBookingStatus`, {
          bookingId: this.rejectForm.bookingId,
          status: 2,
          rejectReason: this.rejectForm.rejectReason
        }).then((response) => {
          if (response.data.code == 400) {
            this.$message.error("操作失败：" + response.data.message)
          } else {
            this.$message.success("审批成功")
            this.rejectDialogVisible = false
            if (this.keyWord == ''){
              this.getNotApprovedList()
            }else{
              this.searchNotApproved()
            }
          }
        }).catch((response) => {
          console.log(response)
        })
      },
      refreshNotApList(){
          this.queryInfo.page = 1
          this.queryInfo.rows = 5
          if (this.keyWord == ''){
            this.getNotApprovedList()
          }else{
            this.searchNotApproved()
          }

      }
    }
    }

</script>
<style lang="less" scoped>
.el-card{
    box-shadow: 0 1px 1px rgba(0,0, 0, 0.15)!important;
    width: 98%;
    margin:15px auto;
  }

.card-header-text{
      text-align: left;
  }
</style>
