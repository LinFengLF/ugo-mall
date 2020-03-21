<template>
  <div>
    <el-card class="box-card">
      <h3>用户信息填写</h3>
      <div class="formContainer">
        <el-form
          label-position="right"
          id="editUserInfo"
          :model="userInfo"
          :rules="rules"
          ref="editUserForm"
          label-width="80px"
        >
          <el-form-item label="用户姓名" style="font-weight:bold" prop="userName">
            <el-input max-length="6" style="width:30%;" v-model="userInfo.userName"></el-input>
          </el-form-item>
          <el-form-item label="用户昵称" style="font-weight:bold" prop="userNickName">
            <el-input maxlength="7" v-model="userInfo.userNickName" style="width:30%"></el-input>
          </el-form-item>
          <el-form-item
            label="用户地址"
            style="font-weight:bold"
            prop="userAddress"
            @change="addressChange"
          >
            <template>
              <v-distpicker province="福建省" city="漳州市" area="龙海市" @selected="addrCallBack"></v-distpicker>
            </template>
          </el-form-item>
          <el-form-item label="用户性别" style="font-weight:bold" prop="userSex">
            <el-select v-model="userInfo.userSex" placeholder="用户性别">
              <el-option label="男" value="0" aria-selected="true"></el-option>
              <el-option label="女" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用户年龄" style="font-weight:bold" prop="userAge">
            <el-input v-model="userInfo.userAge" style="width:30%"></el-input>
          </el-form-item>
          <el-form-item label="用户手机" style="font-weight:bold" prop="userPhone">
            <el-input maxlength="11" style="width:30%" v-model="userInfo.userPhone"></el-input>
          </el-form-item>
          <el-form-item label="用户头像" style="font-weight:bold" prop="userPhoto">
            <el-upload
              action="api/file/upload"
              :show-file-list="false"
              :on-success="uploadUrl"
              :before-upload="checkFileSize"
              class="avatar-uploader"
              accept=".jpg, .jpeg, .png, .gif"
            >
              <img v-if="userInfo.userPhoto" :src="userInfo.userPhoto" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon" />
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过1024kb</div>
            </el-upload>
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <div class="confirm_cancel_btn">
      <el-button size="medium" type="primary" plain @click="updateUserInfo">确定</el-button>
      <el-button size="medium" plain @click="resetForm('editUserForm')">重置</el-button>
    </div>
  </div>
</template>

<script>
import { validatePhone, isOneToNinetyNine } from "@/utils/validate";
import * as api from "@/api/index";
import VDistpicker from "v-distpicker";
export default {
  components: { VDistpicker },
  data() {
    return {
      userInfo: {},
      userId: "",
      // addUserForm: {
      //   userName: "",
      //   userNickName: "",
      //   userAddress: {
      //     province: "福建省",
      //     city: "漳州市",
      //     area: "龙海市"
      //   },
      //   userSex: "0",
      //   userAge: "",
      //   userPhone: "",
      //   userPhoto: ""
      // },
      rules: {
        userName: [
          { required: true, message: "请输入用户名", trigger: "blur" },
          { min: 2, max: 5, message: "长度在2到5个字符之间", trigger: "blur" }
        ],
        userNickName: [
          { required: true, message: "请输入用户昵称", trigger: "blur" },
          { min: 2, max: 7, message: "长度在2到5个字符之间", trigger: "blur" }
        ],
        userAddress: [
          { required: true, message: "请输入用户地址", trigger: "blur" }
        ],
        userAge: [
          { required: true, message: "请输入用户年龄", trigger: "blur" },
          { validator: isOneToNinetyNine, trigger: "blur" }
        ],
        userPhone: [
          { required: true, message: "请输入您的电话号码", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" }
        ],
        userSex: [{ required: true, message: "请选择性别", trigger: "blur" }]
      }
    };
  },
  methods: {
    //图片上传成功的回调函数
    uploadUrl(res) {
      console.log(res.url);
      this.userInfo.userPhoto = res.url;
      console.log(this.userInfo);
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
    addressChange() {
      console.log(this.address);
    },
    addrCallBack(e) {
      console.log(e);
      this.userInfo.userAddress.province = e.province.value;
      this.userInfo.userAddress.city = e.city.value;
      this.userInfo.userAddress.area = e.area.value;
    },
    // 用户更新
    updateUserInfo() {
      // debugger;
      // let area = this.$route.query.userAddress;
      // this.userInfo = this.$route.query;
      if (this.userInfo != null) {
        let params = {
          userId: this.$route.query.id,
          userName: this.userInfo.userName,
          userNickName: this.userInfo.userNickName,
          userAddress: this.userInfo.userAddress,
          userSex: this.userInfo.userSex,
          userAge: this.userInfo.userAge,
          userPhone: this.userInfo.userPhone,
          userPhoto: this.userInfo.userPhoto
        };
        api.updateUserInfo(params).then(res => {
          console.log("返回的code为：*****************" + res.data.code);
          if (res.data.code == 1) {
            this.$message.success("用户信息更新成功");
          }
          if (res.data.code == 0) {
            this.$message.error("更新失败，请重试！");
          }
        });
        // this.$route.go(-1);
        this.$router.push({ path: "/userList" });
      }
    },
    //重置表单信息
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg";
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error("上传头像图片只能是 JPG 格式!");
      }
      if (!isLt2M) {
        this.$message.error("上传头像图片大小不能超过 2MB!");
      }
      return isJPG && isLt2M;
    },
    //在页面跳转时执行查询
    getUserInfo() {
      let params = {
        userId: this.$route.query.id,
        userName: "",
        page: 1,
        pageSize: 5
      };
      api
        .getUserInfo(params)
        .then(res => {
          this.userInfo = res.data.list[0];
          console.log(this.userInfo);
        })
        .catch(error => {
          console.log(error);
        });
    }
  },
  mounted() {
    this.getUserInfo();
  }
};
</script>

<style scoped lang="scss">
.box-card {
  width: 100%;
}
.formContainer {
  margin-left: 65px;
}
.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader {
  border-color: #409eff;
  width: 145px;
  height: 145px;
}
.el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 145px;
  height: 145px;
  line-height: 145px;
  text-align: center;
}
.avatar {
  width: 145px;
  height: 145px;
  display: block;
}
.confirm_cancel_btn {
  margin: 20px 15px;
}
</style>