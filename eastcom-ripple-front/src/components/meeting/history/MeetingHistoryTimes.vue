<template>
  <div>
    <el-card>
      <div slot="header" class="card-header-text">
        <span>预订次数记录</span>
      </div>

        <el-row>
          <el-col :span="2">
            <el-button type="info" plain icon="el-icon-printer" @click="export2Excel">导出</el-button>
          </el-col>
        </el-row>

      <el-table :data="timesList" border style="width: 100%">
        <el-table-column prop="employeeId" label="员工id"></el-table-column>
        <el-table-column prop="employeeName" label="姓名"></el-table-column>
        <el-table-column prop="employeePhone" label="电话"></el-table-column>
        <el-table-column prop="employeeEmail" label="电子邮件"></el-table-column>
        <el-table-column prop="reserveTimes" label="总申请次数"></el-table-column>
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
  export default {
    data() {
      return {
        queryInfo: {
          page: 1,
          rows: 5
        },
        timesList: [],
        total: 0,
        keyWord: ''
      }
    },
    created(){
      this.getTimesList();
    },
    methods: {
      getTimesList() {
        axios.get(`http://192.168.93.10:8080/meetingroom/meetingrecord/findReserveTimes/${this.queryInfo.page}/${this.queryInfo.rows}`)
          .then((response) => {
            if (response.data.code == 400) {
              this.$message.error("获取数据失败")
            } else {
              this.timesList = response.data.data.list
              this.total = response.data.data.count
            }
          }).catch((response) => {
          console.log(response)
        })
      },
      // 监听pageSize改变的事情
      handleSizeChange (newSize) {
        this.queryInfo.rows = newSize
        this.getTimesList()
      },
      // 监听页码值改变的事件
      handleCurrentChange (newPage) {
        this.queryInfo.page = newPage
        this.getTimesList()
      },
      //导出excle的2个方法
      export2Excel(){
        require.ensure([], () => {
          const { export_json_to_excel } = require('../../../vendor/Export2Excel');
          const tHeader = [ '员工id', '姓名', '电话', '电子邮件', '总申请次数'];
          const filterVal = ['employeeId', 'employeeName', 'employeePhone', 'employeeEmail', 'reserveTimes'];
          const list = this.timesList;
          const data = this.formatJson(filterVal, list);
          export_json_to_excel(tHeader, data, '会议室预订次数统计');
        })
      },
      formatJson(filterVal, jsonData){
        return jsonData.map(v => filterVal.map(j => v[j]))
      },

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
