<template>
  <div class="userContainer">
    <el-card class="box-card">
      <el-input
        v-model="searchStr"
        placeholder="请输入用户姓名"
        clearable
        suffix-icon="el-icon-search"
        @keydown.enter.native="getUserInfo"
        style="width:20%;margin-right:10px;"
      ></el-input>
      <el-button size="medium" type="primary" icon="el-icon-search" @click="getUserInfo">搜索</el-button>
      <el-button size="medium" type="primary" @click="exportData" icon="el-icon-download">导出全部列表</el-button>
      <el-button size="medium" type="primary" icon="el-icon-edit" @click="addUserInfo">添加</el-button>
      <el-button
        size="medium"
        style="float:right"
        type="danger"
        icon="el-icon-delete"
        @click="batchDelete"
      >批量删除</el-button>
    </el-card>
    <el-card class="box-card">
      <div class="userList">
        <el-table
          v-loading="isActive"
          element-loading-text="拼命加载中"
          element-loading-spinner="el-icon-loading"
          :data="this.userList"
          @selection-change="handleSelection"
          border
          :row-key="getRowKey"
          stripe
          fit
          style="color: #000000; overflow-x: hidden"
          :header-cell-style="{
        'height':'10px',
        'text-align':'center',
        'background':'#F8F8F9'
        }"
        >
          <el-table-column align="center" type="selection" width="55"></el-table-column>
          <el-table-column label="用户编号" prop="userId" sortable></el-table-column>
          <!-- <el-table-column label="用户头像" prop="userPhoto" sortable></el-table-column> -->
          <el-table-column align="center" label="用户头像" prop="userPhoto">
            <template slot-scope="scope">
              <img v-if="scope.row.userPhoto" :src="scope.row.userPhoto" width="80" />
            </template>
          </el-table-column>
          <el-table-column label="用户姓名" prop="userName" sortable></el-table-column>
          <el-table-column label="用户手机" prop="userPhone" sortable></el-table-column>
          <el-table-column label="用户地址" prop="userAddress" sortable></el-table-column>
          <el-table-column label="用户积分" prop="userScore" sortable></el-table-column>
          <el-table-column label="用户年龄" prop="userAge" sortable></el-table-column>
          <el-table-column label="用户身份" prop="userIdentity" sortable>
            <template slot-scope="scope">
              <p v-if="scope.row.userIdentity=='1'">用户</p>
              <p v-if="scope.row.userIdentity=='2'">管理员</p>
              <p v-if="scope.row.userIdentity=='3'">超级管理员</p>
            </template>
          </el-table-column>
          <el-table-column label="用户昵称" prop="userNickName" sortable></el-table-column>
          <el-table-column label="用户性别" prop="userSex" sortable>
            <template slot-scope="scope">
              <p v-if="scope.row.userSex=='0'">男</p>
              <p v-if="scope.row.userSex=='1'">女</p>
            </template>
          </el-table-column>
          <el-table-column width="150" label="操作">
            <template slot-scope="scope">
              <el-button slot="reference" size="mini" @click="updateUserInfo(scope.row)">编辑</el-button>
              <el-button type="danger" size="mini" @click="deleteUser(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="user_pagination">
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
    </el-card>
    <!-- <el-dialog
      title="新增用户信息"
      :visible="userFormShow"
      size="tiny"
      :modal-append-to-body="true"
      :lock-scroll="true"
      @close="closeDialog"
      style="height:750px;"
    >
      <el-form
        id="addUser"
        :model="addUserForm"
        :rules="this.rules"
        ref="addUser"
        label-width="80px"
      >
        <el-form-item label="用户姓名" prop="user_name">
          <el-input max-length="6" style="width:30%" v-model="addUserForm.user_name"></el-input>
        </el-form-item>
        <el-form-item label="用户昵称" prop="user_nickname">
          <el-input maxlength="7" v-model="addUserForm.user_nickname" style="width:30%"></el-input>
        </el-form-item>
        <el-form-item label="用户地址" prop="user_address">
          <v-distpicker
            :province="addUserForm.user_address.province"
            :city="addUserForm.user_address.city"
            :area="addUserForm.user_address.area"
          ></v-distpicker>
        </el-form-item>
        <el-form-item label="用户性别" prop="user_sex">
          <el-select v-model="addUserForm.user_sex" placeholder="用户性别">
            <el-option label="男" value="0" aria-selected="true"></el-option>
            <el-option label="女" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用户年龄" prop="user_age">
          <el-input v-model="addUserForm.user_age" style="width:30%"></el-input>
        </el-form-item>
        <el-form-item label="用户手机" prop="user_phone">
          <el-input maxlength="11" style="width:30%" v-model="addUserForm.user_phone"></el-input>
        </el-form-item>
        <el-form-item label="用户头像" prop="user_photo">
          <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="addUserForm.user_photo" :src="addUserForm.user_photo" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
    <el-input v-model="addUserForm.user_photo"></el-input>-->
    <!--</el-form-item>
        <el-form-item>
          <el-button type="primary">确定</el-button>
          <el-button type="primary">取消</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>-->
  </div>
</template>

<script>
import { validatePhone, isOneToNinetyNine } from "@/utils/validate";
import * as api from "@/api/index";
import { export2Excel } from "@/common/js/util";
export default {
  name: "userList",
  data() {
    return {
      addUserForm: {
        user_name: "",
        user_nickname: "",
        user_address: {
          province: "福建省",
          city: "漳州市",
          area: "龙海市"
        },
        user_sex: "",
        user_age: "",
        user_phone: "",
        user_photo: ""
      },
      multiSelection: [],
      // data: [],
      userList: [],
      pageSize: 5,
      currentPage: 1,
      total: 0,
      searchStr: "",
      userFormShow: false,
      isActive: true,
      columns: [
        {
          title: "用户编号",
          key: "userId"
        },
        {
          title: "用户姓名",
          key: "userName"
        },
        {
          title: "用户地址",
          key: "userAddress"
        },
        {
          title: "用户性别(0为男，1为女)",
          key: "userSex"
        },
        {
          title: "用户积分",
          key: "userScore"
        },
        {
          title: "用户昵称",
          key: "userNickName"
        },
        {
          title: "用户年龄",
          key: "userAge"
        },
        {
          title: "用户身份",
          key: "userIdentity"
        },
        {
          title: "用户手机",
          key: "userPhone"
        }
      ],
      rules: {
        user_name: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 2, max: 5, message: "长度在2到5个字符之间", trigger: "blur" }
        ],
        user_nickname: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 2, max: 5, message: "长度在2到5个字符之间", trigger: "blur" }
        ],
        user_address: [
          { required: true, message: "请输入用户地址", trigger: "blur" }
        ],
        user_age: [
          { required: true, message: "请输入用户年龄", trigger: "blur" },
          { validator: isOneToNinetyNine, trigger: "blur" }
        ],
        user_phone: [
          { required: true, message: "请输入您的电话号码", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" }
        ],
        user_sex: [{ required: true, message: "请选择性别", trigger: "blur" }]
      }
    };
  },
  methods: {
    //更新用户信息
    updateUserInfo(val) {
      this.$router.push({ path: "editUserInfo", query: { id: val.userId } });
    },
    //获取选择框的值
    handleSelection(val) {
      this.multiSelection = val;
    },
    //批量删除
    batchDelete() {
      let ids = [];
      ids = this.multiSelection.map(item => item.userId).join();
      let params = {
        ids: ids
      };
      if (this.multiSelection.length > 0) {
        this.$confirm("确认删除选中的用户吗？", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        })
          .then(() => {
            api.deleteUser(params).then(res => {
              if (res.data.code === "1") {
                this.$message({
                  message: "删除成功",
                  type: "success"
                });
                this.$forceUpdate();
                this.getUserInfo();
              } else {
                this.$message({
                  message: "删除失败",
                  type: "error"
                });
                this.getUserInfo();
              }
            });
          })
          .catch(() => {
            this.$message({
              type: "info",
              message: "已取消删除"
            });
          });
      } else {
        this.$message({
          message: "请至少选择一条记录",
          type: "info"
        });
      }
    },
    //点击翻页保留选择
    getRowKey(row) {
      return row.user_id;
    },
    //用户删除
    deleteUser(val) {
      // console.log(val.user_id);
      let params = {
        ids: val.userId
      };
      this.$confirm("确认删除选中的用户吗？", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          api.deleteUser(params).then(res => {
            if (res.data.code === "1") {
              this.$message({
                message: "删除成功",
                type: "success"
              });
              this.getUserInfo();
            } else {
              this.$message({
                message: "删除失败",
                type: "error"
              });
              this.getUserInfo();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消删除"
          });
        });
    },
    //获取用户数据
    getUserInfo() {
      let params = {
        userName: this.searchStr,
        page: this.currentPage,
        pageSize: this.pageSize
      };
      api
        .getUserInfo(params)
        .then(res => {
          setTimeout(() => {
            this.isActive = false;
          }, 500);
          // console.log(res);
          this.userList = res.data.list;
          this.total = res.data.total;
        })
        .catch(err => {
          this.isActive = false;
          this.$message({
            message: "数据获取失败",
            type: "error"
          });
        });
    },
    //每页有多少条数据
    handleSizeChange(pageSize) {
      this.pageSize = pageSize;
      this.getUserInfo();
    },
    //跳转分页
    handleCurrentChange(currentPage) {
      this.currentPage = currentPage;
      this.getUserInfo();
    },
    //添加用户信息
    addUserInfo() {
      this.$router.replace("/addUserInfo");
      // this.userFormShow = true;
    },
    //关闭dialog弹窗
    closeDialog() {
      this.userFormShow = false;
    },
    //实现数据导出
    exportData() {
      let exportData = [];
      let params = {
        userName: this.searchStr,
        page: 0,
        pageSize: 0
      };
      api
        .getUserInfo(params)
        .then(res => {
          for (let i = 0; i < res.data.list.length; i++) {
            if (res.data.list[i].userSex == 1) {
              res.data.list[i].userSex = "女";
            }
            if (res.data.list[i].userSex == 0) {
              res.data.list[i].userSex = "男";
            }
          }
          for (let i = 0; i < res.data.list.length; i++) {
            if (res.data.list[i].userIdentity == 1) {
              res.data.list[i].userIdentity = "超级管理员";
            }
            if (res.data.list[i].userIdentity == 2) {
              res.data.list[i].userIdentity = "用户";
            }
          }
          exportData = res.data.list;
          if (res.data.list.length > 5000) {
            this.$notify.info("数据量过大，请联系管理员");
          } else {
            export2Excel(this.columns, exportData);
          }
        })
        .catch(err => {
          this.$notify.error("网络错误，数据导出失败!");
        });
    }
  },
  mounted() {
    this.getUserInfo();
  },
  watch: {}
};
</script>

<style lang="scss">
.el-table--scrollable-x .el-table__body-wrapper {
  overflow-x: hidden;
}
.userContainer {
  width: 100%;
  height: 100%;
  margin-bottom: 15px;
}
.userList {
  text-align: center;
}
.el-table .cell {
  text-align: center;
  white-space: nowrap;
}
.user_pagination {
  float: right;
  margin: 15px 5px;
}
.el-dialog__header {
  background: #3bc5ff;
  text-align: center;
  .el-icon-close {
    color: black;
  }
}
.el-dialog__title {
  color: black;
  font: bold;
}
.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
