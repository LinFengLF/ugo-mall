<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.userId"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入用户编号"
      />
      <el-select
        clearable
        v-model="listQuery.type"
        style="width: 200px"
        class="filter-item"
        placeholder="请选择反馈类型"
      >
        <el-option v-for="(key, value) in feedType" :key="key" :label="key" :value="value" />
      </el-select>
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
      fit
      highlight-current-row
    >
      <el-table-column align="center" label="反馈ID" prop="id" />

      <el-table-column align="center" label="用户编号" prop="userId" />

      <el-table-column align="center" label="用户名" prop="username" />

      <el-table-column align="center" label="手机号码" prop="contact" />

      <el-table-column align="center" label="反馈类型" prop="feedType" />

      <el-table-column align="center" label="反馈内容" prop="content" />

      <el-table-column align="center" label="反馈图片" prop="picUrls">
        <template slot-scope="scope">
          <el-image
            v-for="item in scope.row.picUrls"
            :key="item"
            :src="item"
            :preview-src-list="scope.row.picUrls"
            :lazy="true"
            style="width: 40px; height: 40px; margin-right: 5px;"
          />
        </template>
      </el-table-column>

      <el-table-column align="center" label="反馈时间" prop="addTime" />
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

    <!-- <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" /> -->
  </div>
</template>

<script>
// import { listFeedback } from '@/api/user'
// import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import * as api from "@/api/index";
const feedType = {
  功能异常: "功能异常",
  页面显示: "页面显示",
  优化建议: "优化建议"
};
export default {
  name: "Feedback",
  //   components: { Pagination },
  data() {
    return {
      feedType,
      pageSize: 15,
      currentPage: 1,
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        type: "",
        userId: undefined,
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
    //反馈列表获取
    getList() {
      this.listLoading = true;
      let params = {
        page: this.currentPage,
        pageSize: this.pageSize,
        userId: this.listQuery.userId,
        type: this.listQuery.type
      };
      api
        .getFeedBackList(params)
        .then(response => {
          this.list = response.data.list;
          this.total = response.data.total;
          this.listLoading = false;
        })
        .catch(() => {
          this.list = [];
          this.total = 0;
          this.listLoading = false;
        });
    },
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then(excel => {
        const tHeader = [
          "反馈ID",
          "用户名称",
          "反馈内容",
          "反馈图片列表",
          "反馈时间"
        ];
        const filterVal = ["id", "username", "content", "picUrls", "addTime"];
        excel.export_json_to_excel2(
          tHeader,
          this.list,
          filterVal,
          "意见反馈信息"
        );
        this.downloadLoading = false;
      });
    }
  }
};
</script>
<style lang="less" scoped>
.pagination {
  float: right;
  margin: 15px 5px;
}
.filter-container {
  margin-bottom: 20px;
}
</style>