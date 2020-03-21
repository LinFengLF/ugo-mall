<template>
  <div>
    <el-card class="box-card">
      <h3>用户信息填写</h3>
      <div class="formContainer">
        <el-form
          label-position="right"
          id="addUser"
          :model="addUserForm"
          :rules="rules"
          ref="addUserForm"
          label-width="80px"
        >
          <el-form-item label="用户姓名" style="font-weight:bold" prop="userName">
            <el-input max-length="6" style="width:30%;" v-model="addUserForm.userName"></el-input>
          </el-form-item>
          <el-form-item label="用户昵称" style="font-weight:bold" prop="userNickName">
            <el-input maxlength="7" v-model="addUserForm.userNickName" style="width:30%"></el-input>
          </el-form-item>
          <el-form-item label="用户地址" style="font-weight:bold" prop="userAddress">
            <template>
              <v-distpicker province="福建省" city="漳州市" area="龙海市" @selected="addrCallBack"></v-distpicker>
              <!-- <el-input placeholder="请输入详细地址" style="width:30%;margin-top: 15px;"></el-input> -->
            </template>
          </el-form-item>
          <el-form-item label="用户性别" style="font-weight:bold" prop="userSex">
            <el-select v-model="addUserForm.userSex" placeholder="用户性别" @change="change()">
              <el-option label="男" value="0" aria-selected="true"></el-option>
              <el-option label="女" value="1"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="用户年龄" style="font-weight:bold" prop="userAge">
            <el-input v-model="addUserForm.userAge" style="width:30%"></el-input>
          </el-form-item>
          <el-form-item label="用户手机" style="font-weight:bold" prop="userPhone">
            <el-input maxlength="11" style="width:30%" v-model="addUserForm.userPhone"></el-input>
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
              <img v-if="addUserForm.userPhoto" :src="addUserForm.userPhoto" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon" />
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过1024kb</div>
            </el-upload>
            <!-- <el-upload
              action="api/file/upload"
              v-model="addUserForm.userPhoto"
              list-type="picture-card"
              :auto-upload="true"
              :on-change="handleLimit"
              :limit="1"
              name="userHeaderPicture"
              :on-success="handleAvatarSuccess"
              :class="{disabled:uploadDisabled}"
              :on-remove="handleRemove"
            >
              <i slot="default" class="el-icon-plus"></i>
              <div slot="file" slot-scope="{file}">
                <img class="el-upload-list__item-thumbnail" :src="file.url" alt="头像" />
                <span class="el-upload-list__item-actions">
                  <span
                    class="el-upload-list__item-preview"
                    @click="handlePictureCardPreview(file)"
                  >
                    <i class="el-icon-zoom-in"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleDownload(file)"
                  >
                    <i class="el-icon-download"></i>
                  </span>
                  <span
                    v-if="!disabled"
                    class="el-upload-list__item-delete"
                    @click="handleRemove(file)"
                  >
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
              </div>
            </el-upload>-->
            <el-dialog :visible.sync="dialogVisible">
              <img width="100%" :src="dialogImageUrl" alt />
            </el-dialog>
            <!-- <el-upload
              class="avatar-uploader"
              action="api/ugoMall/userController/imageUpload"
              name="userHeaderPicture"
              v-model="addUserForm.userPhoto"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :show-file-list="false"
              :on-success="handleAvatarSuccess"
              :before-upload="beforeAvatarUpload"
              :on-progress="uploading"
            >
              <img v-if="addUserForm.userPhoto" :src="addUserForm.userPhoto" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>-->
          </el-form-item>
        </el-form>
      </div>
    </el-card>
    <div class="confirm_cancel_btn">
      <el-button size="medium" type="primary" plain @click="insertUser">确定</el-button>
      <el-button size="medium" plain @click="resetForm('addUserForm')">重置</el-button>
    </div>
    <!-- <li>
      <h3>添加新图：</h3>
      <input
        type="file"
        id="saveImage"
        name="myphoto"
        accept="image/png, image/gif, image/jpeg"
        ref="new_image"
      />
      <el-button @click="addImage">确认添加</el-button>
    </li>-->
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
      uploadDisabled: false,
      dialogImageUrl: "",
      dialogVisible: false,
      disabled: false,
      userInfo: {},
      userId: "",
      addUserForm: {
        userName: "",
        userNickName: "",
        userAddress: {
          province: "福建省",
          city: "漳州市",
          area: "龙海市"
        },
        userSex: "0",
        userAge: "",
        userPhone: "",
        userPhoto: ""
      },
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
      this.addUserForm.userPhoto =  res.url;
      console.log(this.addUserForm);
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
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handleLimit(file, fileList) {
      console.log("**************");
      if ((fileList = 1)) {
        console.log("--------------");
        this.uploadDisabled = true;
      }
    },
    uploading() {
      console.log("图片上传中...");
    },
    handlePreview(file) {
      console.log(file);
    },
    handleRemove(file, fileList) {
      this.uploadDisabled = false;
      this.$forceUpdate();
      console.log(file, fileList);
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
      console.log(res);
      if (res.code == 200) {
        this.$message.success(res.msg);
      } else {
        this.$message.error(res.msg);
      }
      this.addUserForm.userPhoto = res.url;
    },
    //图片上传
    // addImage: function() {
    //   let self = this;
    //   if (self.$refs.new_image.files.length !== 0) {
    //     var formData = new FormData();
    //     formData.append("image_data", self.$refs.new_image.files[0]);
    //     //单个文件进行上传
    //     self.$ajax
    //       .post("/addImage", formData, {
    //         headers: {
    //           "Content-Type": "multipart/form-data"
    //         }
    //       })
    //       .then(response => {});
    //   }
    // },
    //监听地址改变
    addrCallBack(e) {
      console.log(e);
      this.addUserForm.userAddress.province = e.province.value;
      this.addUserForm.userAddress.city = e.city.value;
      this.addUserForm.userAddress.area = e.area.value;
    },
    //重新渲染下拉框的值
    change() {
      console.log(this.addUserForm.userSex);
      this.$forceUpdate();
    },
    //重置表单信息
    resetForm(formName) {
      this.$refs[formName].resetFields();
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
    //用户添加方法
    insertUser() {
      console.log("添加方法");
      // console.log(this.address.province + this.address.city + this.address.area);
      this.$refs["addUserForm"].validate(valid => {
        console.log(valid);
        if (!valid) {
          return;
        }
        //转换地址类型，json复杂类型数据后台不容易解析
        let userAddress =
          this.addUserForm.userAddress.province +
          this.addUserForm.userAddress.city +
          this.addUserForm.userAddress.area;
        console.log("拼接后的地址为：");
        console.log(userAddress);
        let params = {
          userName: this.addUserForm.userName,
          userNickName: this.addUserForm.userNickName,
          userAddress: userAddress,
          userSex: this.addUserForm.userSex,
          userAge: this.addUserForm.userAge,
          userPhoto: this.addUserForm.userPhoto,
          userPhone: this.addUserForm.userPhone
        };
        api.insertUser(params).then(res => {
          if (res.data.code === "1") {
            this.$message({
              message: "添加成功",
              type: "success"
            });
          } else if (res.data.code === "0") {
            this.$message({
              message: "添加失败，请重试！",
              type: "error"
            });
          } else {
            this.$message({
              message: "添加失败，请重试！",
              type: "error"
            });
          }
        });
        this.$forceUpdate();
        setTimeout(() => {
          // console.log(this.$router);
          this.$router.push({ path: "/userList" });
        }, 500);
        this.removeTab($store.getters.rightNav);
      });
    },
    //关闭标签
    removeTab(tabItem) {
      this.$store.dispatch("removeTab", {
        tabItem,
        fullPath: this.$route.fullPath,
        router: this.$router
      });
    }
  },
  mounted() {
    // this.updateUserInfo();
  },
  created() {
    if (this.$route.query != null) {
      this.userInfo = this.$route.query;
    } else {
      this.userInfo = {};
    }
  }
};
</script>

<style lang="scss">
// .el-upload--picture-card 控制加号部分
.disabled .el-upload--picture-card {
  display: none !important;
}

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