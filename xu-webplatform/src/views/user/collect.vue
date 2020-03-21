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
        v-model="listQuery.valueId"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入商品ID"
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
      :header-cell-style="{
        'height':'10px',
        'text-align':'center',
        'background':'#F8F8F9'
        }"
    >
      <el-table-column align="center" width="100px" label="收藏ID" prop="id" sortable />

      <el-table-column align="center" min-width="100px" label="用户ID" prop="userId" />

      <el-table-column align="center" min-width="100px" label="商品ID" prop="valueId" />

      <el-table-column align="center" min-width="100px" label="添加时间" prop="addTime" />
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
import * as collectApi from "@/api/collect";
// import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: "Collect",
  //   components: { Pagination },
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20,
        userId: "",
        valueId: "",
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
    //获取所有信息
    getList() {
      this.listLoading = true;
      let params = {
        page: this.currentPage,
        pageSize: this.pageSize,
        userId: this.listQuery.userId,
        goodsId: this.listQuery.valueId
      };
      console.log(params);
      collectApi
        .listCollections(params)
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
    //表单重置
    resetForm() {
      this.dataForm = {
        id: undefined,
        userId: "",
        valueId: "",
        addTime: undefined
      };
    },
    //表格导出
    handleDownload() {
      this.downloadLoading = true;

      import("@/vendor/Export2Excel").then(excel => {
        const tHeader = ["用户ID", "商品ID", "添加时间"];
        const filterVal = ["userId", "valueId", "addTime"];
        let exportData = [];
        let params = {
          page: 0,
          pageSize: 0,
          userId: this.listQuery.userId,
          goodsId: this.listQuery.valueId
        };
        collectApi.listCollections(params).then(res => {
          exportData = res.data.list;
          excel.export_json_to_excel2(
            tHeader,
            exportData,
            filterVal,
            "用户收藏信息"
          );
        });
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
