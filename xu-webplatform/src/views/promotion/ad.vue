<template>
  <div class="app-container">
    <!-- 查询和其他操作 -->
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        suffix-icon="el-icon-search"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入广告标题"
      />
      <el-input
        v-model="listQuery.content"
        suffix-icon="el-icon-search"
        clearable
        class="filter-item"
        style="width: 200px;"
        placeholder="请输入广告内容"
      />
      <el-button class="filter-item" type="primary" icon="el-icon-search" @click="getList">查找</el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-edit" @click="handleCreate">添加</el-button>
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
      <el-table-column align="center" label="广告ID" prop="id" sortable />

      <el-table-column align="center" label="广告标题" prop="name" />

      <el-table-column align="center" label="广告内容" prop="content" />

      <el-table-column align="center" label="广告图片" prop="url">
        <template slot-scope="scope">
          <img v-if="scope.row.url" :src="scope.row.url" width="80" />
        </template>
      </el-table-column>

      <el-table-column align="center" label="广告位置" prop="position">
        <template slot-scope="scope">
          <el-tag type="success">{{ scope.row.position == 1 ? '首页' : '其他' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="活动链接" prop="link" />

      <el-table-column align="center" label="是否启用" prop="enabled">
        <template slot-scope="scope">
          <el-tag
            :type="scope.row.enabled ? 'success' : 'error' "
          >{{ scope.row.enabled ? '启用' : '不启用' }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)">编辑</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <div class="ad_pagination">
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
        <el-form-item label="广告标题" prop="name">
          <el-input v-model="dataForm.name" />
        </el-form-item>
        <el-form-item label="广告内容" prop="content">
          <el-input v-model="dataForm.content" />
        </el-form-item>
        <el-form-item label="广告图片" prop="url">
          <el-upload
            action="api/file/upload"
            :show-file-list="false"
            :on-success="uploadUrl"
            :before-upload="checkFileSize"
            class="avatar-uploader"
            accept=".jpg, .jpeg, .png, .gif"
          >
            <img v-if="dataForm.url" :src="dataForm.url" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon" />
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过1024kb</div>
          </el-upload>
        </el-form-item>
        <el-form-item label="广告位置" prop="position">
          <el-select v-model="dataForm.position" placeholder="请选择">
            <el-option :value="1" label="首页" />
          </el-select>
        </el-form-item>
        <el-form-item label="活动链接" prop="link">
          <el-input v-model="dataForm.link" />
        </el-form-item>
        <el-form-item label="是否启用" prop="enabled">
          <el-select v-model="dataForm.enabled" placeholder="请选择">
            <el-option :value="1" label="启用" />
            <el-option :value="0" label="不启用" />
          </el-select>
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
import * as promotionApi from "@/api/promotion";
// import { listAd, createAd, updateAd, deleteAd } from '@/api/ad'
// import { uploadPath } from '@/api/storage'
// import { getToken } from '@/utils/auth'
// import Pagination from '@/components/Pagination' // Secondary package based on el-pagination

export default {
  name: "Ad",
  //   components: { Pagination },
  data() {
    return {
      //   uploadPath,
      list: [],
      exportData: [],
      total: 0,
      listLoading: true,
      currentPage: 1,
      pageSize: 5,
      name: "",
      content: "",
      listQuery: {
        page: 1,
        pageSize: 5,
        name: "",
        content: ""
      },
      dataForm: {
        id: undefined,
        name: undefined,
        content: undefined,
        url: undefined,
        link: undefined,
        position: 1,
        enabled: 1
      },
      dialogFormVisible: false,
      dialogStatus: "",
      textMap: {
        update: "编辑",
        create: "创建"
      },
      rules: {
        name: [
          { required: true, message: "广告标题不能为空", trigger: "blur" }
        ],
        content: [
          { required: true, message: "广告内容不能为空", trigger: "blur" }
        ],
        url: [
          { required: false, message: "广告宣传图不能为空", trigger: "blur" }
        ]
      },
      downloadLoading: false
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
    //获取所有广告信息
    getList() {
      this.listLoading = true;
      let params = {
        name: this.listQuery.name,
        content: this.listQuery.content,
        page: this.currentPage,
        pageSize: this.pageSize
      };
      promotionApi
        .adList(params)
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

    //重置表单信息
    resetForm() {
      this.dataForm = {
        id: undefined,
        name: undefined,
        content: undefined,
        url: "",
        link: undefined,
        position: 1,
        enabled: 1
      };
    },
    //清空表单，打开弹窗
    handleCreate() {
      this.resetForm();
      this.dialogStatus = "create";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    //图片上传成功的回调函数
    uploadUrl(res) {
      console.log(res.url);
      this.dataForm.url =  res.url;
      console.log(this.dataForm);
    },
    //上传图片检查文件大小
    checkFileSize: function(file) {
      if (file.size > 1048576) {
        this.$message.error(
          `${file.name}文件大于1024KB，请选择小于1024KB大小的图片`
        );
        return false;
      }
      return true;
    },
    //新建广告
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          let params = {
            name: this.dataForm.name,
            link: this.dataForm.link,
            url: this.dataForm.url,
            position: this.dataForm.position,
            content: this.dataForm.content,
            enabled: this.dataForm.enabled
          };
          promotionApi
            .createAd(params)
            .then(res => {
              console.log(">>>>>>>>>>>>>>>>>>>");
              console.log(res);
              // this.list.unshift(res.data.data);
              this.dialogFormVisible = false;
              this.$notify.success({
                title: "成功",
                message: res.data.msg
              });
              this.getList();
            })
            .catch(res => {
              this.$notify.error({
                title: "失败",
                message: res.data.msg
              });
              this.getList();
            });
        }
      });
    },
    //编辑广告信息
    handleUpdate(row) {
      this.dataForm = Object.assign({}, row);
      this.dialogStatus = "update";
      this.dialogFormVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    //编辑时点击确定触发
    updateData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          let params = {
            id: this.dataForm.id,
            name: this.dataForm.name,
            link: this.dataForm.link,
            url: this.dataForm.url,
            position: this.dataForm.position,
            content: this.dataForm.content,
            enabled: this.dataForm.enabled
          };
          promotionApi
            .editAd(params)
            .then(() => {
              for (const v of this.list) {
                if (v.id === this.dataForm.id) {
                  const index = this.list.indexOf(v);
                  this.list.splice(index, 1, this.dataForm);
                  break;
                }
              }
              this.dialogFormVisible = false;
              this.$notify.success({
                title: "成功",
                message: "更新广告成功"
              });
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
          promotionApi
            .deleteAd(params)
            .then(res => {
              this.$notify.success({
                title: "成功",
                message: "删除成功"
              });
              const index = this.list.indexOf(row);
              this.list.splice(index, 1);
            })
            .catch(releaseEvents => {
              this.$notify.error({
                title: "失败",
                message: res.data.msg
              });
            });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消删除"
          });
        });
    },
    //导出所有广告数据
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then(excel => {
        const tHeader = [
          "广告ID",
          "广告标题",
          "广告内容",
          "广告图片",
          "广告位置",
          "活动链接",
          "是否启用"
        ];
        const filterVal = [
          "id",
          "name",
          "content",
          "url",
          "position",
          "link",
          "enabled"
        ];
        let params = {
          page: 0,
          pageSize: 0,
          name: this.listQuery.name,
          content: this.listQuery.content
        };
        promotionApi.adList(params).then(res => {
          for (let i = 0; i < res.data.list.length; i++) {
            if (res.data.list[i].position == 1) {
              res.data.list[i].position = "首页";
            }
            if (res.data.list[i].position == 0) {
              res.data.list[i].position = "其他";
            }
          }
          for (let i = 0; i < res.data.list.length; i++) {
            if (res.data.list[i].enabled == 1) {
              res.data.list[i].enabled = "启用";
            }
            if (res.data.list[i].enabled == 0) {
              res.data.list[i].enabled = "未启用";
            }
          }
          this.exportData = res.data.list;
          //   console.log(this.exportData);

          excel.export_json_to_excel2(
            tHeader,
            this.exportData,
            filterVal,
            "广告信息"
          );
        });

        this.downloadLoading = false;
      });
    }
  }
};
</script>

<style>
.filter-container {
  margin-bottom: 10px;
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
.ad_pagination {
  float: right;
  margin: 15px 5px;
}
</style>