<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.userId"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入用户ID"
      />
      <el-input
        v-model="listQuery.name"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入收货人名称"
      />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="getList">查找</el-button>
      <el-button
        :loading="downloadLoading"
        class="filter-item"
        type="primary"
        icon="el-icon-download"
        @click="handleDownload"
      >导出</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="正在查询中。。。"
      border
      stripe
      fit
      highlight-current-row
    >
      <el-table-column align="center" width="100px" label="地址ID" prop="id" sortable />

      <el-table-column align="center" min-width="100px" label="用户ID" prop="userId" />

      <el-table-column align="center" min-width="100px" label="收货人名称" prop="name" />

      <el-table-column align="center" min-width="100px" label="手机号码" prop="tel" />

      <el-table-column align="center" min-width="300px" label="区域地址">
        <template slot-scope="scope">{{ scope.row.province + scope.row.city + scope.row.county }}</template>
      </el-table-column>

      <el-table-column align="center" min-width="300px" label="详细地址" prop="addressDetail" />

      <el-table-column align="center" min-width="80px" label="默认" prop="isDefault">
        <template slot-scope="scope">{{ scope.row.isDefault ? '是' : '否' }}</template>
      </el-table-column>
    </el-table>

    <div class="pagination">
      <el-pagination
        background
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :page-size="pageSize"
        :page-sizes="[5, 15, 25, 50, 100]"
        :current-page="currentPage"
        layout="total, sizes, prev, pager, next, jumper"
        :total="this.total"
      ></el-pagination>
    </div>
  </div>
</template>

<script>
import * as api from "@/api/index";
export default {
  name: "UserAddress",
  data() {
    return {
      list: null,
      pageSize: 5,
      currentPage: 1,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        name: '',
        userId: '',
        sort: "add_time",
        order: "desc"
      },
      downloadLoading: false
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //每页有多少条数据
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.getList();
    },
    //跳转分页
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      this.getList();
    },
    //获取列表数据
    getList() {
      let params = {
        page: this.currentPage,
        pageSize: this.pageSize,
        name: this.listQuery.name,
        userId: this.listQuery.userId
      };
      this.listLoading = true;
      api
        .getAddressInfo(params)
        .then(res => {
          this.list = res.data.list;
          this.total = res.data.total;
          this.listLoading = false;
        })
        .catch(() => {
          this.list = [];
          this.total = 0;
          this.listLoading = false;
        });
    },
    //筛选方法
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then(excel => {
        const tHeader = [
          "地址ID",
          "用户ID",
          "收货人",
          "手机号",
          "省",
          "市",
          "区",
          "地址",
          "是否默认"
        ];
        const filterVal = [
          "id",
          "userId",
          "name",
          "tel",
          "province",
          "city",
          "county",
          "addressDetail",
          "isDefault"
        ];
        excel.export_json_to_excel2(
          tHeader,
          this.list,
          filterVal,
          "用户地址信息"
        );
        this.downloadLoading = false;
      });
    }
  }
};
</script>

<style scoped>
.pagination {
  float: right;
  margin: 15px 5px;
}
.filter-container {
  margin-bottom: 15px;
}
</style>
