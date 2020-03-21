<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="id"
        clearable
        class="filter-item"
        style="width: 160px;"
        suffix-icon="el-icon-search"
        placeholder="请输入商品ID"
      />
      <el-input
        v-model="goodsSn"
        clearable
        class="filter-item"
        style="width: 160px;"
        suffix-icon="el-icon-search"
        placeholder="请输入商品编号"
      />
      <el-input
        v-model="name"
        clearable
        class="filter-item"
        style="width: 160px;"
        suffix-icon="el-icon-search"
        placeholder="请输入商品名称"
      />
      <el-button
        style="margin-left:5px;"
        class="filter-item"
        size="medium"
        type="primary"
        icon="el-icon-search"
        @click="getList"
      >查找</el-button>
      <el-button
        class="filter-item"
        size="medium"
        type="primary"
        icon="el-icon-edit"
        @click="handleCreate"
      >添加</el-button>
      <el-button
        :loading="downloadLoading"
        size="medium"
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
      stripe
    >
      <el-table-column type="expand">
        <template slot-scope="props">
          <el-form label-position="left" class="table-expand">
            <el-form-item label="商品编号">
              <span>{{ props.row.goodsSn }}</span>
            </el-form-item>
            <el-form-item label="宣传画廊">
              <img v-for="pic in props.row.gallery" :key="pic" :src="pic" class="gallery" />
            </el-form-item>
            <el-form-item label="商品介绍">
              <span>{{ props.row.brief }}</span>
            </el-form-item>
            <el-form-item label="商品单位">
              <span>{{ props.row.unit }}</span>
            </el-form-item>
            <el-form-item label="关键字">
              <span>{{ props.row.keywords }}</span>
            </el-form-item>
            <el-form-item label="类目ID">
              <span>{{ props.row.categoryId }}</span>
            </el-form-item>
          </el-form>
        </template>
      </el-table-column>

      <el-table-column align="center" label="商品ID" prop="id" />

      <el-table-column align="center" min-width="100" label="名称" prop="name" />

      <el-table-column align="center" property="iconUrl" label="图片">
        <template slot-scope="scope">
          <img :src="scope.row.picUrl" width="40" />
        </template>
      </el-table-column>

      <el-table-column align="center" property="iconUrl" label="分享图">
        <template slot-scope="scope">
          <img :src="scope.row.shareUrl" width="40" />
        </template>
      </el-table-column>

      <el-table-column align="center" label="详情" prop="detail">
        <template slot-scope="scope">
          <el-dialog :visible.sync="detailDialogVisible" title="商品详情">
            <div v-html="goodsDetail" />
          </el-dialog>
          <el-button type="primary" size="mini" @click="showDetail(scope.row.detail)">查看</el-button>
        </template>
      </el-table-column>

      <el-table-column align="center" label="专柜价格" prop="counterPrice" />

      <el-table-column align="center" label="当前价格" prop="retailPrice" />

      <el-table-column align="center" label="是否新品" prop="isNew">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.isNew ? 'success' : 'error' "
          >{{ scope.row.isNew ? '新品' : '非新品' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="是否热品" prop="isHot">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.isHot ? 'success' : 'error' "
          >{{ scope.row.isHot ? '热品' : '非热品' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="是否在售" prop="isOnSale">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.isOnSale ? 'success' : 'error' "
          >{{ scope.row.isOnSale ? '在售' : '未售' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="goods_pagination">
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

    <el-tooltip placement="top" content="返回顶部">
      <back-to-top :visibility-height="100" />
    </el-tooltip>
  </div>
</template>


<script>
import { listGoods } from "@/api/goods";
import BackToTop from "@/components/BackToTop";
import * as goodsApi from "@/api/goods";
export default {
  name: "GoodsList",
  components: { BackToTop },
  data() {
    return {
      exportData: [],
      list: [],
      total: 0,
      listLoading: true,
      currentPage: 1,
      id: "",
      pageSize: 5,
      goodsSn: "",
      name: "",
      listQuery: {
        page: 1,
        pageSize: 5,
        goodsSn: "",
        name: ""
      },
      goodsDetail: "",
      detailDialogVisible: false,
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
    //获取所有商品信息
    getList() {
      this.listLoading = true;
      let params = {
        page: this.currentPage,
        pageSize: this.pageSize,
        name: this.name,
        id: this.id,
        goodsSn: this.goodsSn
      };
      // console.log(params);
      goodsApi
        .listGoods(params)
        .then(res => {
          this.list = res.data.list;
          this.total = res.data.total;
          this.listLoading = false;
        })
        .catch(err => {
          this.$notify.error("网络错误,商品加载失败");
          this.list = [];
          this.total = 0;
          this.listLoading = false;
        });
    },
    //搜索方法
    handleFilter() {
      this.listQuery.page = 1;
      this.getList();
    },
    handleCreate() {
      this.$router.push({ path: "/createGood" });
    },
    handleUpdate(row) {
      this.$router.push({ path: "/editGood", query: { id: row.id } });
    },
    showDetail(detail) {
      this.goodsDetail = detail;
      this.detailDialogVisible = true;
    },
    //删除方法
    handleDelete(row) {
      //   console.log(row);
      let params = {
        ids: row.id
      };
      this.$confirm("确认删除选中的商品吗？", "提示", {
        title: "警告",
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          goodsApi
            .deleteGoods(params)
            .then(res => {
              if (res.data.code == 1) {
                this.$notify.success({
                  title: "成功",
                  message: res.data.msg
                });
              } else {
                this.$notify.error({
                  title: "失败",
                  message: res.data.msg
                });
              }
              const index = this.list.indexOf(row);
              this.list.splice(index, 1);
              this.getList();
            })
            .catch(res => {
              this.$notify.error({
                title: "失败",
                message: res.data.msg
              });
              this.getList();
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消删除"
          });
        });
    },
    //数据导出
    handleDownload() {
      //   debugger;
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then(excel => {
        const tHeader = [
          "商品ID",
          "商品编号",
          "名称",
          "专柜价格",
          "当前价格",
          "是否新品",
          "是否热品",
          "是否在售",
          "首页主图",
          "宣传图片列表",
          "商品介绍",
          "详细介绍",
          "商品图片",
          "商品单位",
          "关键字",
          "类目ID",
          "品牌商ID"
        ];
        const filterVal = [
          "id",
          "goodsSn",
          "name",
          "counterPrice",
          "retailPrice",
          "isNew",
          "isHot",
          "isOnSale",
          "listPicUrl",
          "gallery",
          "brief",
          "detail",
          "picUrl",
          "unit",
          "keywords",
          "categoryId",
          "brandId"
        ];
        let exportData = [];
        let params = {
          page: 0,
          pageSize: 0,
          name: this.name,
          id: this.id,
          goodsSn: this.goodsSn
        };
        goodsApi.listGoods(params).then(res => {
          for (let i = 0; i < res.data.list.length; i++) {
            if (res.data.list[i].isNew == 1) {
              res.data.list[i].isNew = "新品";
            }
            if (res.data.list[i].isNew == 0) {
              res.data.list[i].isNew = "非新品";
            }
          }
          for (let i = 0; i < res.data.list.length; i++) {
            if (res.data.list[i].isHot == 1) {
              res.data.list[i].isHot = "热门商品";
            }
            if (res.data.list[i].isHot == 0) {
              res.data.list[i].isHot = "非热门商品";
            }
          }
          for (let i = 0; i < res.data.list.length; i++) {
            if (res.data.list[i].isOnSale  == 1) {
              res.data.list[i].isOnSale  = "在售";
            }
            if (res.data.list[i].isOnSale  == 0) {
              res.data.list[i].isOnSale  = "非在售";
            }
          }
          this.exportData = res.data.list;
          excel.export_json_to_excel2(
            tHeader,
            this.exportData,
            filterVal,
            "商品信息"
          );
        });
        this.downloadLoading = false;
      });
    }
  }
};
</script>


<style>
.table-expand {
  font-size: 0;
}
.table-expand label {
  width: 100px;
  color: #99a9bf;
}
.table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
}
.gallery {
  width: 80px;
  margin-right: 10px;
}
.filter-container {
  margin-bottom: 10px;
}
.goods_pagination {
  float: right;
  margin: 15px 5px;
}
.el-dialog__body {
  padding: 0px 0px 0px 0px;
}
</style>