<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="card-header-text">
        <span>历史记录</span>
      </div>
      <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item class="form-item-layout">
          <el-input v-model="searchData" placeholder="电脑SN码/资产编号/员工名.." autocomplete="off" />
        </el-form-item>
        <el-form-item class="form-item-layout">
          <el-button type="primary" icon="el-icon-search" @click="getHistoricalRecords()">查 询</el-button>
        </el-form-item>
        <el-form-item class="form-item-layout" style="float: right; margin-right: 0;">
          <el-dropdown trigger="click">
            <span class="el-dropdown-link">
              <i class="el-icon-download" style="font-size: 27px; vertical-align: middle;"></i>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item @click.native="exportDataToCsv">导出到Csv文件</el-dropdown-item>
              <el-dropdown-item>导出到Excel文件</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-form-item>
      </el-form>

      <!-- 历史纪录列表 -->
      <el-table
        :data="historicalRecords"
        border
        style="width: 100%;"
        fit
        :header-cell-style="headerCellStyle"
        :cell-style="cellStyle"
        :show-header="total > 0"
        empty-text="No Data"
      >
        <el-table-column prop="historicalId" label="序号"></el-table-column>
        <el-table-column prop="historicalComSn" label="电脑SN码" width="175" :resizable="false">
        </el-table-column>
        <el-table-column prop="historicalAssetNum" label="资产编号" :resizable="false">
        </el-table-column>
        <el-table-column prop="historicalEmpNum" label="工号" :resizable="false">
        </el-table-column>
        <el-table-column prop="historicalEmpName" label="员工姓名" :resizable="false">
        </el-table-column>
        <el-table-column prop="getComTime" label="得到电脑时间" :resizable="false">
        </el-table-column>
        <el-table-column prop="returnComTime" label="归还电脑时间" :resizable="false">
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
  import computer from "@/api/property/computer.js";
  import { createHyperlinkLabel } from '@/utils/simulatedDownLoad.js'

  export default {
    data() {
      return {
        currentPage: 1, //当前页
        pageSize: 10, //每页记录数
        total: 0, //总记录数
        historicalRecords: [], //历史记录集合
        searchData:"",
      };
    },
    created() {
      //获取电脑分配历史记录
      this.getHistoricalRecords();
    },
    methods: {
      //获取电脑分配历史记录
      async getHistoricalRecords() {
        let _this = this;
        //用于做请求参数的query
        let query = {
          keyword: this.searchData,
          currentPage: this.currentPage,
          pageSize: this.pageSize,
        };
        //console.log(query);
        computer
          .getHistoricalRecord(query)
          .then((response) => {
            if (response.data.code == 400) {
              //请求错误
              this.$message.error(response.data.message);
            } else {
              let result = response.data.data;
              _this.historicalRecords = result.list;
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
        this.getAssetList();
      },
      // 监听页码值改变的事件
      handleCurrentChange(newPage) {
        console.log(newPage);
        this.currentPage = newPage;
        this.getAssetList();
      },

      //修改表头样式
      headerCellStyle() {
        return 'text-align: center; font-weight: 500; font-size: 14px';
      },
      //修改表格数据样式
      cellStyle() {
        return 'text-align: center; font-weight: 500; font-size: 14px'
      },

      //导出表格到Csv文件
      exportDataToCsv() {
        let query = {
          keyword: this.searchData,
          currentPage: this.currentPage,
          pageSize: this.pageSize
        };
        this.$confirm('Yes 为导出本页数据，No 则默认导出筛选后的所有数据',
          '提示',
          {
            confirmButtonText: 'Yes',
            cancelButtonText: 'No',
            iconClass: "el-icon-question",
          }
        ).then(() => {
          computer
            .exportToCsv(query)
            .then((res) => {
              console.log(res);
              if (res.status === 200 && res.data) {
                //模拟点击下载，弹出下载框
                createHyperlinkLabel(res);
              }else {// 其它情况
              }
            })
            .catch(err => {
              var enc = new TextDecoder('utf-8');
              var res = JSON.parse(enc.decode(new Uint8Array(err.data))); //转化成json对象
              console.log(res);
            })
        }).catch(()=>{ //默认,导出筛选的所有数据
          query.pageSize = 0;
          query.currentPage = 0;
          computer
            .exportToCsv(query)
            .then((res) => {
              console.log(res);
              if (res.status === 200 && res.data) {
                //模拟点击下载，弹出下载框
                createHyperlinkLabel(res);
              }else {// 其它情况
              }
            })
            .catch(err => {
              var enc = new TextDecoder('utf-8');
              var res = JSON.parse(enc.decode(new Uint8Array(err.data))); //转化成json对象
              console.log(res);
            })
        });
      }
    },
  };
</script>

<style lang="less" scoped>
  /* 查询表单布局 */
  .form-item-layout{
    float: left;
    margin-bottom: 15px;
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

/* 导出表格 */
.el-dropdown-link {
  cursor: pointer;
}
</style>

