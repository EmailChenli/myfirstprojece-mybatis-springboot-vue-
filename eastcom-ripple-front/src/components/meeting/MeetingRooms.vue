<template>
  <div>
    <el-card>
      <div slot="header" class="card-header-text">
        <span>会议室信息列表</span>
      </div>
      <el-row :gutter="20">
        <el-col :span="7">
          <el-input placeholder="请输入会议室编号或名称" v-model="keyWord" clearable @clear="clear()">
            <el-button slot="append" icon="el-icon-search" @click="doSearch()"></el-button>
          </el-input>
        </el-col>
        <el-col :span="1.5">
          <el-button icon="el-icon-refresh" @click="refreshRoomList()"></el-button>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" icon="el-icon-plus" @click="addDialogVisible = true">添加会议室</el-button>
        </el-col>
      </el-row>

      <el-table :data="roomList" border style="width: 100%">
        <el-table-column prop="roomId" label="会议室编号" width="100"></el-table-column>
        <el-table-column prop="roomName" label="名称"></el-table-column>
        <el-table-column prop="roomSize" label="可容纳人数" width="100"></el-table-column>
        <el-table-column prop="description" label="详情（设备）"></el-table-column>
        <el-table-column prop="createTime" label="创建时间"></el-table-column>
        <el-table-column prop="updateTime" label="最近修改时间"></el-table-column>
        <el-table-column label="操作" width="180px">
          <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-tooltip class="item" effect="dark" content="修改会议室信息" placement="top" :enterable="false">
              <el-button size="mini" type="primary" icon="el-icon-edit" @click="showEditDialog(scope.row)"></el-button>
            </el-tooltip>
            <!-- 删除按钮 -->
            <el-tooltip class="item" effect="dark" content="删除会议室" placement="top" :enterable="false">
              <el-button size="mini" type="danger" icon="el-icon-delete" @click="removeRoomById(scope.row.roomId)"></el-button>
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

    <!-- 添加会议室对话框 -->
    <el-dialog
      title="添加会议室"
      :visible.sync="addDialogVisible"
      width="50%"
      @close="addDialogClosed">
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="100px">
        <el-form-item label="会议室编号" prop="roomId">
          <el-input v-model="addForm.roomId"></el-input>
        </el-form-item>
        <el-form-item label="会议室名称" prop="roomName">
          <el-input v-model="addForm.roomName"></el-input>
        </el-form-item>
        <el-form-item label="可容纳人数" prop="roomSize">
          <el-input type="number" oninput="if(value<1)value=1" v-model="addForm.roomSize"></el-input>
        </el-form-item>
        <el-form-item label="详情（设备）" prop="description">
          <el-input v-model="addForm.description"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
            <el-button @click="addDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="addRoom">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改会议室信息对话框 -->
    <el-dialog
      title="修改会议室信息"
      :visible.sync="editDialogVisible"
      width="50%"
      @close="editDialogClosed">
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="100px">
        <el-form-item label="会议室编号" prop="roomId">
          <el-input v-model="editForm.roomId" readonly="true"></el-input>
        </el-form-item>
        <el-form-item label="会议室名称" prop="roomName">
          <el-input v-model="editForm.roomName"></el-input>
        </el-form-item>
        <el-form-item label="可容纳人数" prop="roomSize">
          <el-input type="number" oninput="if(value<1)value=1" v-model="editForm.roomSize"></el-input>
        </el-form-item>
        <el-form-item label="详情（设备）" prop="description">
          <el-input v-model="editForm.description"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="eidtRoom">确 定</el-button>
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
          roomList: [],
          total: 0,
          keyWord: '',
          addDialogVisible: false,
          editDialogVisible: false,
          addForm: {
            roomId: '',
            roomName:'',
            roomSize: 1,
            description: ''
          },
          addFormRules: {
            roomId: [
              { required: true, message: '请输入会议室编号', trigger: 'blur' }
            ],
            roomName: [
              {required: true, message: '请输入会议室名称', trigger: 'blur'}
            ],
            roomSize: [
              { required: true, message: '请输入可容纳人数', trigger: 'blur' }
            ]
          },
          editForm: {
            roomId: '',
            roomName:'',
            roomSize: 1,
            description: ''
          },
          editFormRules: {
            roomId: [
              { required: true, message: '请输入会议室编号', trigger: 'blur' },
            ],
            roomName: [
              {required: true, message: '请输入会议室名称', trigger: 'blur'}
            ],
            roomSize: [
              { required: true, message: '请输入可容纳人数', trigger: 'blur' }
            ]
          }
        }
    },
    created(){
      this.getRoomList();
    },
    methods: {
      getRoomList () {
        axios.get(`http://192.168.93.10:8080/meetingroom/room/findAll/${this.queryInfo.page}/${this.queryInfo.rows}`)
          .then((response) => {
            if(response.data.code == 400){
              this.$message.error("获取数据失败："+response.data.message)
            }else{
              this.roomList = response.data.data.list
              this.total = response.data.data.count
            }
          }).catch((response) =>{
            console.log(response)
        })
      },
      // 监听pageSize改变的事情
      handleSizeChange (newSize) {
        this.queryInfo.rows = newSize
        if (this.keyWord == ''){
          this.getRoomList()
        }else{
          this.searchRoom()
        }
      },
      // 监听页码值改变的事件
      handleCurrentChange (newPage) {
        this.queryInfo.page = newPage
        if (this.keyWord == ''){
          this.getRoomList()
        }else{
          this.searchRoom()
        }
      },
      doSearch(){
        if(this.keyWord == ''){
          this.$message.info("请输入搜索条件")
          return
        }else{
          this.queryInfo.page = 1
          this.queryInfo.rows = 5
          this.searchRoom()
        }
      },
      searchRoom () {
        axios.post(`http://192.168.93.10:8080/meetingroom/room/searchRoom/${this.queryInfo.page}/${this.queryInfo.rows}`, {
            para: this.keyWord

          }).then((response) => {
            if(response.data.code == 400){
              this.$message.error("查询失败：" + response.data.message)
            } else {
              this.roomList = response.data.data.list
              this.total = response.data.data.count
            }
          }).catch((response) =>{
          console.log(response)
        })
      },
      addDialogClosed () {
        this.$refs.addFormRef.resetFields()
      },
      editDialogClosed () {
        this.$refs.editFormRef.resetFields()
      },
      addRoom () {
        this.$refs.addFormRef.validate(async valid => {
          if (!valid) return
          axios.post(`http://192.168.93.10:8080/meetingroom/room/saveRoom`, {
            roomId: this.addForm.roomId,
            roomName: this.addForm.roomName,
            roomSize: this.addForm.roomSize,
            description: this.addForm.description })
            .then((response) => {
              if(response.data.code == 400){
                this.$message.error("添加失败："+ response.data.message)
              }else{
                this.$message.success("添加成功")
                this.addDialogVisible = false
                this.getRoomList()
              }
            }).catch((response) =>{
              console.log(response)
              this.$message.error("添加错误")
          })
        })
      },
      showEditDialog(row){
        this.editForm.roomId = row.roomId
        this.editForm.roomName = row.roomName
        this.editForm.roomSize = row.roomSize
        this.editForm.description = row.description
        this.editDialogVisible = true
      },
      eidtRoom () {
        this.$refs.editFormRef.validate(async valid => {
          if (!valid) return
          axios.post(`http://192.168.93.10:8080/meetingroom/room/updateRoom`, {
            roomId: this.editForm.roomId,
            roomName: this.editForm.roomName,
            roomSize: this.editForm.roomSize,
            description: this.editForm.description })
            .then((response) => {
              if(response.data.code == 400){
                return this.$message.error("修改失败："+ response.data.message)
              }
                this.$message.success("修改成功")
                this.editDialogVisible = false
                this.getRoomList()

            }).catch((response) =>{
            console.log(response)
            this.$message.error("修改失败")
          })
        })
      },
      async removeRoomById(roomId){
        const confirmResult = await this.$confirm('此操作将永久删除该会议室, 是否继续?',
          '提示',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).catch(err => err)
        if (confirmResult !== 'confirm') {
          return this.$message.info('已取消')
        }
        axios.get(`http://192.168.93.10:8080/meetingroom/room/deleteRoom/${roomId}`).then((response) =>{
          if(response.data.code == 400){
            return this.$message.error("删除失败：" + response.data.message)
          }
          this.$message.success("删除会议室成功")
          this.queryInfo.page = 1
          this.getRoomList()
        }).catch((response) =>{
          console.log(response)
          this.$message.error("删除失败")
        })
      },
      refreshRoomList(){
        if (this.keyWord == ''){
          this.getRoomList()
        }else{
          this.searchRoom()
        }
      },
      clear(){
        this.queryInfo.page = 1
        this.queryInfo.rows = 5
        this.getRoomList()
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
