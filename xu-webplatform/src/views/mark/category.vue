<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-button class="filter-item" size="small" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
    </div>

    <!-- 查询结果 -->
    <el-table
      stripe
      v-loading="listLoading"
      :data="list"
      element-loading-text="正在查询中。。。"
      border
      fit
      highlight-current-row
      row-key="id"
      :header-cell-style="{
        'height':'10px',
        'text-align':'center',
        'background':'#F8F8F9'
        }"
    >
      <el-table-column align="center" label="类目ID" prop="id" />

      <el-table-column align="center" label="类目名" prop="name" />

      <el-table-column align="center" property="iconUrl" label="类目图标">
        <template slot-scope="scope">
          <img v-if="scope.row.iconUrl" :src="scope.row.iconUrl" width="40" />
        </template>
      </el-table-column>

      <el-table-column align="center" property="picUrl" label="类目图片">
        <template slot-scope="scope">
          <img v-if="scope.row.picUrl" :src="scope.row.picUrl" width="80" />
        </template>
      </el-table-column>

      <!-- <el-table-column align="center" label="关键字" prop="keywords" /> -->

      <el-table-column align="center" min-width="100" label="简介" prop="description" />

      <!-- <el-table-column align="center" label="级别" prop="level">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.level === 'L1' ? 'primary' : 'info' "
          >{{ scope.row.level === 'L1' ? '一级类目' : '二级类目' }}</el-tag>
        </template>
      </el-table-column>-->

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
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

    <!-- 添加或修改对话框 -->
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="dataForm"
        status-icon
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="类目名称" prop="name">
          <el-input v-model="dataForm.name" />
        </el-form-item>
        <!-- <el-form-item label="关键字" prop="keywords">
          <el-input v-model="dataForm.keywords" />
        </el-form-item> -->

        <el-form-item label="类目图标" prop="iconUrl">
          <el-upload
            action="api/file/upload"
            :show-file-list="false"
            :on-success="uploadIconUrl"
            class="avatar-uploader"
            accept=".jpg, .jpeg, .png, .gif"
          >
            <img v-if="dataForm.iconUrl" :src="dataForm.iconUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>
        <el-form-item label="类目图片" prop="picUrl">
          <el-upload
            action="api/file/upload"
            :show-file-list="false"
            :on-success="uploadPicUrl"
            class="avatar-uploader"
            accept=".jpg, .jpeg, .png, .gif"
          >
            <img v-if="dataForm.picUrl" :src="dataForm.picUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>

        <el-form-item label="类目简介" prop="description">
          <el-input v-model="dataForm.description" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取消</el-button>
        <el-button v-if="dialogStatus=='create'" type="primary" @click="createData">确定</el-button>
        <el-button v-else type="primary" @click="updateData">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>



<script>
// import { listCategory, listCatL1, createCategory, updateCategory, deleteCategory } from '@/api/category'
// import { uploadPath } from '@/api/storage'
// import { getToken } from '@/utils/auth'
import * as markApi from "@/api/mark";

export default {
  name: "Category",
  data() {
    return {
      currentPage: 1,
      pageSize: 5,
      list: [],
      total: 0,
      listLoading: true,
      //   catL1: {},
      dataForm: {
        id: undefined,
        name: "",
        keywords: "",
        level: "L2",
        pid: 0,
        description: "",
        iconUrl: "",
        picUrl: ""
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "创建"
      },
      rules: {
        name: [{ required: true, message: "类目名不能为空", trigger: "blur" }]
      }
    };
  },
  computed: {
    // headers() {
    //   return {
    //     "X-Litemall-Admin-Token": getToken()
    //   };
    // }
  },
  created() {
    this.getList();
    // this.getCatL1();
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
    //获取列表
    getList() {
      this.listLoading = true;
      let params = {
        page: this.currentPage,
        pageSize: this.pageSize
      };
      markApi
        .listCategory(params)
        .then(res => {
          this.list = res.data.list;
          this.listLoading = false;
          this.total = res.data.total;
        })
        .catch(() => {
          this.list = [];
          this.listLoading = false;
        });
    },
    getCatL1() {
      listCatL1().then(response => {
        this.catL1 = response.data.data.list;
      });
    },
    resetForm() {
      this.dataForm = {
        id: undefined,
        name: "",
        keywords: "",
        pid: 0,
        description: "",
        iconUrl: "123",
        picUrl: "123"
      };
    },
    //创建
    handleCreate() {
      this.resetForm();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    //图片上传成功回调
    uploadIconUrl(res) {
      console.log(res.url);
      this.dataForm.iconUrl = res.url;
      console.log(this.dataForm);
    },
    //类目图片上传成功的回调函数
    uploadPicUrl(res) {
      this.dataForm.picUrl = res.url;
      console.log(this.dataForm);
    },
    //新建类目
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          let params = {
            name: this.dataForm.name,
            keywords: this.dataForm.keywords,
            description: this.dataForm.description,
            iconUrl: this.dataForm.iconUrl,
            picUrl: this.dataForm.picUrl
          };
          markApi
            .createCategory(params)
            .then(res => {
              if (res.data.code == 1) {
                this.getList();
                this.dialogFormVisible = false;
                this.$notify.success({
                  title: "成功",
                  message: "创建成功"
                });
              } else {
                this.$notify.error({
                  title: "失败",
                  message: res.data.msg
                });
              }
            })
            .catch(res => {
              this.$notify.error({
                title: "失败",
                message: res.data.msg
              });
            });
        }
      });
    },
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    //更新类目
    updateData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          let params = {
            id: this.dataForm.id,
            name: this.dataForm.name,
            keywords: this.dataForm.keywords,
            description: this.dataForm.description,
            iconUrl: this.dataForm.iconUrl,
            picUrl: this.dataForm.picUrl
          };
          console.log(params);
          markApi
            .editCategory(params)
            .then(res => {
              if (res.data.code == 1) {
                this.getList();
                this.dialogFormVisible = false;
                this.$notify.success({
                  title: "成功",
                  message: "更新成功"
                });
              } else {
                this.$notify.error({
                  title: "失败",
                  message: res.data.msg
                });
              }
            })
            .catch(res => {
              this.$notify.error({
                title: "失败",
                message: res.data.msg
              });
            });
        }
      });
    },
    //删除方法
    handleDelete(row) {
      console.log(row.id);
      let params = {
        ids: row.id
      };
      markApi
        .deleteCategory(params)
        .then(res => {
          if (res.data.code == 1) {
            this.getList();
            this.$notify.success({
              title: "成功",
              message: "删除成功"
            });
          } else {
            this.$notify.error({
              title: "失败",
              message: res.data.msg
            });
          }
        })
        .catch(res => {
          this.$notify.error({
            title: "失败",
            message: res.data.msg
          });
        });
    }
  }
};
</script>

<style scoped>
.filter-item {
  margin-left: 100px;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #20a0ff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 120px;
  height: 120px;
  line-height: 120px;
  text-align: center;
}
.avatar {
  width: 145px;
  height: 145px;
  display: block;
}
.pagination {
  float: right;
  margin: 15px 5px;
}
.filter-container {
  margin-bottom: 15px;
}
.filter-item {
  margin-left: 0px;
}
</style>