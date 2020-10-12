<template>
  <div>
    <el-card>
      <div slot="header" class="card-header-text">
        <span>历史预订记录</span>
      </div>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入会议室编号、账号或申请人" v-model="keyWord" clearable @clear="clear()">
            <el-button slot="append" icon="el-icon-search" @click="doSearch()"></el-button>
          </el-input>
        </el-col>
        <el-col :span="1.5">
          <el-button icon="el-icon-refresh" @click="refreshRecordList()"></el-button>
        </el-col>
      </el-row>

      <el-table :data="recordList" style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-row>
                <el-col :span="12"><el-form-item label="会议记录编号" label-width="50%"><span>{{ props.row.recordId }}</span></el-form-item></el-col>
                <el-col :span="12"><el-form-item label="会议预订编号" label-width="50%"><span>{{ props.row.bookingId }}</span></el-form-item></el-col>
              </el-row>
              <el-row>
                <el-col :span="12"><el-form-item label="会议名称" label-width="50%"><span>{{ props.row.meetingName }}</span></el-form-item></el-col>
                <el-col :span="12"><el-form-item label="会议室编号" label-width="50%"><span>{{ props.row.roomId }}</span></el-form-item></el-col>
              </el-row>
              <el-row>
                <el-col :span="12"><el-form-item label="会议室名称" label-width="50%"><span>{{ props.row.roomName }}</span></el-form-item></el-col>
                <el-col :span="12"><el-form-item label="参会人数" label-width="50%"><span>{{ props.row.meetingNumber }}</span></el-form-item></el-col>
              </el-row>
              <el-row>
                <el-col :span="12"><el-form-item label="申请账号" label-width="50%"><span>{{ props.row.userName }}</span></el-form-item></el-col>
                <el-col :span="12"><el-form-item label="申请人" label-width="50%"><span>{{ props.row.realname }}</span></el-form-item></el-col>
              </el-row>
              <el-row>
                <el-col :span="12"><el-form-item label="开始时间" label-width="50%"><span>{{ props.row.startTime }}</span></el-form-item></el-col>
                <el-col :span="12"><el-form-item label="结束时间" label-width="50%"><span>{{ props.row.endTime }}</span></el-form-item></el-col>
              </el-row>
              <el-row>
                <el-col :span="12"><el-form-item label="会议详情" label-width="50%"><span>{{ props.row.meetingDescription }}</span></el-form-item></el-col>
                <el-col :span="12"><el-form-item label="申请时间" label-width="50%"><span>{{ props.row.applyTime }}</span></el-form-item></el-col>
              </el-row>
              <el-row>
                <el-col :span="12"><el-form-item label="创建时间" label-width="50%"><span>{{ props.row.createTime }}</span></el-form-item></el-col>
                <el-col :span="12"><el-form-item label="最近修改时间" label-width="50%"><span>{{ props.row.updateTime }}</span></el-form-item></el-col>
              </el-row>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column prop="meetingName" label="会议名称"></el-table-column>
        <el-table-column prop="roomId" label="会议室编号"></el-table-column>
        <el-table-column prop="userName" label="申请账号"></el-table-column>
        <el-table-column prop="realname" label="申请人"></el-table-column>
        <el-table-column prop="startTime" label="开始时间"></el-table-column>
        <el-table-column prop="endTime" label="结束时间"></el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <!-- 删除按钮 -->
            <el-tooltip class="item" effect="dark" content="删除记录" placement="top" :enterable="false">
              <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeRecordById(scope.row.recordId)"></el-button>
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

  </div>
</template>
<script>
  import axios from 'axios'
  export default{
    data(){
      return {
        queryInfo: {
          page: 1,
          rows: 5
        },
        recordList: [],
        total: 0,
        keyWord: '',
      }
    },
    created(){
      this.getRecordList();
    },
    methods: {
      getRecordList() {
        axios.get(`http://192.168.93.10:8080/meetingroom/meetingrecord/findAll/${this.queryInfo.page}/${this.queryInfo.rows}`)
          .then((response) => {
            if (response.data.code == 400) {
              this.$message.error("获取数据失败："+response.data.message)
            } else {
              this.recordList = response.data.data.list
              this.total = response.data.data.count
            }
          }).catch((response) => {
          console.log(response)
        })
      },
      // 监听pageSize改变的事情
      handleSizeChange(newSize) {
        this.queryInfo.rows = newSize
        if (this.keyWord == ''){
          this.getRecordList()
        }else{
          this.searchRecord()
        }
      },
      // 监听页码值改变的事件
      handleCurrentChange(newPage) {
        this.queryInfo.page = newPage
        if (this.keyWord == ''){
          this.getRecordList()
        }else{
          this.searchRecord()
        }
      },
      clear(){
        this.queryInfo.page = 1
        this.queryInfo.rows = 5
        this.getRecordList()
      },
      doSearch(){
        if (this.keyWord == ''){
          this.$message.info("请输入搜索条件")
          return
        }else{
          this.queryInfo.page = 1
          this.queryInfo.rows = 5
          this.searchRecord()
        }
      },
      searchRecord(){
        axios.post(`http://192.168.93.10:8080/meetingroom/meetingrecord/searchRecord/${this.queryInfo.page}/${this.queryInfo.rows}`, {
          para: this.keyWord,

        }).then((response) => {
            if (response.data.code == 400) {
              this.$message.error("查询失败：" + response.data.message)
            } else {
              this.recordList = response.data.data.list
              this.total = response.data.data.count
            }
          }).catch((response) => {
          console.log(response)
        })
      },
      async removeRecordById(recordId){
        const confirmResult = await this.$confirm('此操作将永久删除该记录, 是否继续?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).catch(err => err)
        if (confirmResult !== 'confirm') {
          return this.$message.info('已取消删除')
        }
        axios.get(`http://192.168.93.10:8080/meetingroom/meetingrecord/deleteRecord/${recordId}`)
          .then((response) =>{
          if(response.data.code == 400){
            return this.$message.error("删除失败：" + response.data.message)
          }
          this.$message.success("删除记录成功")
          this.queryInfo.page = 1
          if (this.keyWord == ''){
            this.getRecordList()
          }else{
            this.searchRecord()
          }
        }).catch((response) =>{
          console.log(response)
          this.$message.error("删除失败")
        })
      },
      refreshRecordList(){
        this.queryInfo.page = 1
        this.queryInfo.rows = 5
        if (this.keyWord == ''){
          this.getRecordList()
        }else{
          this.searchRecord()
        }
      }
    }
  }
</script>
<style lang="less" scoped>
  .demo-table-expand {
    font-size: 0;
  }
  .demo-table-expand label {
    width: 90px;
    color: #99a9bf;
  }
  .demo-table-expand .el-form-item {
    margin-right: 0;
    margin-bottom: 0;
    width: 50%;
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

