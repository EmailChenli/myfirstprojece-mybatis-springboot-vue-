<template>
  <div class="app-container">
    <el-card>
      <div slot="header" class="card-header-text">
        <span>资产信息列表</span>
      </div>
      <!--查询表单-->
      <el-form :inline="true" class="demo-form-inline">
        <el-form-item class="form-item-layout">
          <el-input
            v-model="searchData"
            name="searchAsset"
            placeholder="资产编号/生产厂商/购买人.."
            autocomplete="off"
          />
        </el-form-item>
        <!--资产类型下拉框-->
        <el-form-item class="form-item-layout select-box">
          <el-select v-model="searchAssetType">
            <el-option
              v-for="(item,index) in assetTypeOptions"
              :key="index+''"
              :label="item.assetTypeName"
              v-bind:value="item.typeId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="form-item-layout">
          <el-button type="primary" icon="el-icon-search" @click="getAssetList()">查 询</el-button>
        </el-form-item>
        <el-form-item class="form-item-layout" style="float: right; margin-right: 0;">
          <el-button type="primary" @click="addDialogVisible=true">添 加</el-button>
        </el-form-item>
      </el-form>

      <!-- 资产信息列表 -->
      <el-table
        :data="assetInfoList"
        border
        style="width: 100%;"
        fit
        :header-cell-style="headerCellStyle"
        :cell-style="cellStyle"
        :show-header="total > 0"
        empty-text="No Data"
      >
        <!--<el-table-column prop="id" label="序号" width="100" :resizable="false"></el-table-column>-->
        <el-table-column prop="assetNum" label="资产编号" width="150" :resizable="false"></el-table-column>
        <el-table-column prop="assetName" label="资产名称" :resizable="false"></el-table-column>
        <el-table-column prop="unitPrice" label="单价" :resizable="false"></el-table-column>
        <el-table-column prop="producer" label="生产厂商" :resizable="false"></el-table-column>
        <el-table-column prop="productionDate" label="生产日期" :resizable="false"></el-table-column>
        <el-table-column prop="storageTime" label="入库时间" :resizable="false"></el-table-column>
        <el-table-column prop="purchaser" label="购买人" :resizable="false"></el-table-column>
        <el-table-column prop="assetTypeName" label="资产类型" :resizable="false"></el-table-column>
        <el-table-column label="操作" width="176" :resizable="false">
          <template v-slot="scope">
            <el-button
              type="primary"
              size="mini"
              icon="el-icon-edit"
              style="font-size: 10px"
              @click="showEditDialog(scope.row)"
            >修改</el-button>
            <el-button
              type="danger"
              size="mini"
              icon="el-icon-delete"
              style="margin-left: 3px; font-size: 10px"
              @click="deleteAssetInfo(scope.row.id)"
            >删除</el-button>
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

    <!--添加资产对话框-->
    <el-dialog title="添加资产" :visible.sync="addDialogVisible" width="35%" @close="addDialogClosed" top="5vh" center>
      <el-form :model="addForm" :rules="addFormRules" ref="addAssetForm" label-width="100px">
        <el-form-item label="资产编号" prop="assetNum">
          <el-input v-model="addForm.assetNum"></el-input>
        </el-form-item>
        <el-form-item label="资产名称" prop="assetName">
          <el-input v-model="addForm.assetName"></el-input>
        </el-form-item>
        <el-form-item label="单价" prop="unitPrice">
          <el-input v-model="addForm.unitPrice"></el-input>
        </el-form-item>
        <el-form-item label="生产厂商" prop="producer">
          <el-input v-model="addForm.producer"></el-input>
        </el-form-item>
        <el-form-item label="生产日期" prop="productionData">
          <el-date-picker type="date" placeholder="选择日期" v-model="addForm.productionDate" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="入库时间" prop="storageTime">
          <el-date-picker type="date" placeholder="选择日期" v-model="addForm.storageTime" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="购买人" prop="purchaser">
          <el-input v-model="addForm.purchaser"></el-input>
        </el-form-item>
        <el-form-item label="资产类型" prop="assetTypeId">
          <el-select v-model="addForm.assetTypeId" placeholder="请选择资产类型" style="float: left;">
            <el-option
              v-for="(item,index) in assetTypeList"
              :key="index+''"
              :label="item.assetTypeName"
              v-bind:value="item.typeId"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <span slot="footer" class="addDialogVisible">
        <el-button type="primary" @click="addAsset" class="dialog-btn">添 加</el-button>
      </span>
    </el-dialog>
    <!--修改资产信息对话框-->
    <el-dialog title="修改资产信息" :visible.sync="editDialogVisible" width="35%" @close="editDialogClosed" top="5vh" center>
      <el-form :model="editForm" :rules="editFormRules" ref="editAssetForm" label-width="100px">
        <el-form-item label="资产编号" prop="assetNum">
          <el-input v-model="editForm.assetNum" disabled></el-input>
        </el-form-item>
        <el-form-item label="单价" prop="unitPrice">
          <el-input v-model="editForm.unitPrice"></el-input>
        </el-form-item>
        <el-form-item label="生产厂商" prop="producer">
          <el-input v-model="editForm.producer"></el-input>
        </el-form-item>
        <el-form-item label="生产日期" prop="productionDate">
          <el-date-picker type="date" placeholder="选择日期" v-model="editForm.productionDate" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="入库时间" prop="storageTime">
          <el-date-picker type="date" placeholder="选择日期" v-model="editForm.storageTime" style="width: 100%;"></el-date-picker>
        </el-form-item>
        <el-form-item label="购买人" prop="purchaser">
          <el-input v-model="editForm.purchaser"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="editAsset" class="dialog-btn">修 改</el-button>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import asset from "@/api/property/asset.js";

export default {
  data() {
    return {
      currentPage: 1, //当前页
      pageSize: 10, //每页记录数
      total: 0, //总记录数
      assetInfoList: [], //资产信息集合
      searchData: "", //查询框的数据
      searchAssetType: "0", //查询资产类型栏被选中的值，默认为0，代表选择全部
      assetTypeOptions: [{ assetTypeName: "全部", typeId: "0" }],
      assetTypeList: [],  //资产类型集合
      //控制添加资产对话框的显示与隐藏
      addDialogVisible: false,
      // 添加资产表单数据对象
      addForm: {
        assetNum: "",
        assetName: "",
        unitPrice: "",
        producer: "",
        productionDate: "",
        storageTime: "",
        purchaser: "",
        assetTypeId: ""
      },
      // 添加资产表单验证规则
      addFormRules: {
        assetNum: [
          { required: true, message: "资产编号不能为空", trigger: "blur" },
        ],
        assetTypeId: [
          { required: true, message: "请选择资产类型！", trigger: "blur" },
        ]
      },
      // 控制修改电脑信息对话框的现实与隐藏
      editDialogVisible: false,
      // 查询到的电脑信息
      editForm: {
        id: "",
        assetNum: "",
        unitPrice: "",
        producer: "",
        productionDate: "",
        storageTime: "",
        purchaser: ""
      },
      // 修改电脑信息表单规则
      editFormRules: {
        assetNum: [
          { required: true },
        ]
      }
    };
  },
  created() {
    //获取资产类型
    this.getAssetTypeList();
    //获取资产列表
    this.getAssetList();
  },
  methods: {
    //获取所有资产类型
    async getAssetTypeList() {
      let _this = this;
      asset
        .getAssetType()
        .then((response) => {
          console.log(response);
          if (response.data.code == 400) {
            //请求错误
            this.$message.error(response.data.message);
          } else {
            //将查出来的资产类型数组和assetTypeOptions合并
            _this.assetTypeOptions = _this.assetTypeOptions.concat(
              response.data.data
            );
            //赋值给资产类型集合
            _this.assetTypeList = response.data.data;
            console.log(_this.assetTypeList);
          }
        })
        .catch((error) => {
          console.log(error); //异常
        });
    },
    //获取资产列表
    async getAssetList() {
      let _this = this;
      //用于做请求参数的query
      let query = {
        keyword: this.searchData,
        assetType: this.searchAssetType,
        currentPage: this.currentPage,
        pageSize: this.pageSize,
      };
      //console.log(query);
      asset
        .getAsset(query)
        .then((response) => {
          if (response.data.code == 400) {
            //请求错误
            this.$message.error(response.data.message);
          } else {
            let result = response.data.data;
            _this.assetInfoList = result.list;
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

    //展示修改资产信息对话框
    async showEditDialog(row) {
      //console.log(row.id);
      //初始化表单
      this.editForm.id = row.id;
      this.editForm.assetNum = row.assetNum;
      this.editForm.unitPrice = row.unitPrice;
      this.editForm.producer = row.producer;
      this.editForm.productionDate = row.productionDate;
      this.editForm.storageTime = row.storageTime;
      this.editForm.purchaser = row.purchaser;
      this.editDialogVisible = true;
    },
    // 监听添加资产对话框的关闭事件
    addDialogClosed() {
      this.$refs.addAssetForm.resetFields();
    },
    // 点击确定按钮，添加资产
    addAsset() {
      this.$refs.addAssetForm.validate(async (valid) => {
        if (!valid) return;
        asset
          .addAsset(this.addForm)
          .then((response) => {
            if (response.data.code == 400) {
              this.$message.error(response.data.message);
            } else {
              this.$message.success(response.data.message);
              this.addDialogVisible = false;
              this.getAssetList();
            }
          })
          .catch((error) => {
          console.log(error); //异常
        });
      });
    },
    // 监听修改电脑对话框的关闭事件
    editDialogClosed() {
      this.$refs.editAssetForm.resetFields();
    },
    // 修改电脑信息并提交
    editAsset() {
      this.$refs.editAssetForm.validate(async (valid) => {
        if (!valid) return;
        // else {
        //   console.log(this.editForm);
        //   return false;
        // }
        asset
          .updateAsset(this.editForm)
          .then((response) => {
            if (response.data.code == 400) {
              this.$message.error(response.data.message);
            }else {
              this.$message.success(response.data.message);
              this.editDialogVisible = false;
              this.getAssetList();
            }
          })
          .catch((error) => {
            console.log(error); //异常
          });
      });
    },
    // 根据资产ID删除资产
    async deleteAssetInfo(assetId) {
      console.log(assetId);
      //询问用户是否删除数据
      await this.$confirm(
        "此操作将永久删除该资产信息, 是否继续?",
        "温馨提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "error",
        }
      ).then(()=>{ //confirm
        console.log("开始请求");
        asset
          .deleteAsset(assetId)
          .then((response) => {
            if (response.data.code === 400) {
              this.$message.error(response.data.message);
            }else {
              this.$message.success(response.data.message);
              this.getAssetList();
            }
          })
          .catch((error) => {
            console.log(error); //异常
          });
      }).catch(()=>{
        console.log("取消删除");
      });
    },
    //修改表头样式
    headerCellStyle() {
      return "text-align: center; font-weight: 500; font-size: 14px";
    },
    //修改表格数据样式
    cellStyle() {
      return "text-align: center; font-weight: 500; font-size: 14px";
    },
  },
};
</script>

<style lang="less" scoped>
/* 查询表单布局 */
.form-item-layout {
  float: left;
  margin-bottom: 15px;
}
/* 下拉框 */
.select-box {
  width: 100px;
}
/* 表格 */
.el-table {
  margin-top: 0;
}

.el-card {
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.15) !important;
  width: 98%;
  margin: 15px auto;
}

.card-header-text {
  text-align: left;
}

/* 弹出框样式 */
/*按钮*/
.dialog-btn{
  width: 200px;
}
</style>

