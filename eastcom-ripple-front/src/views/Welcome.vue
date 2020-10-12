<template>
  <div>
    <el-form :inline="true" class="from">
      <el-form-item >
        <el-input placeholder="请输入工号/资产编号/IP地址" style="width:300px;"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="search()">搜索</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {};
  },
  methods: {
    search() {},
  },
};
</script>

<style lang="less" scoped>
.from{
  position: relative;
  margin:100px auto;
}
</style>
