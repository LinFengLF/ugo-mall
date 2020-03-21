<template>
  <div id="login">
    <div class="loginConbox">
      <div class="header">
        <!--<div class="logo">-->
        <!--<img src="../../assets/logo.png">-->
        <!--</div>-->
      </div>
      <div class="loginBox">
        <div class="loginCon">
          <!-- <h1 class="test">test</h1> -->
          <p class="title">U购鲜享购物平台后台管理</p>
          <el-card shadow="always" class="login-module" v-if="smdl">
            <div slot="header" class="clearfix formTitlt">
              <span>密码登录</span>
              <span class="titIconbox">
                <i class="iconfont xu-saomadenglu2 fa-lg iconcolor"></i>
                <i
                  class="iconfont xu-saomadenglu01 el-icon--right fa-lg pointer"
                  @click="smdl = !smdl"
                ></i>
              </span>
            </div>
            <el-form
              :rules="rules"
              :model="loginForm"
              status-icon
              label-width="100px"
              class="demo-ruleForm"
              ref="login"
            >
              <el-form-item prop="username">
                <el-input
                  type="text"
                  v-model="loginForm.username"
                  auto-complete="off"
                  placeholder="请输入登录账号"
                ></el-input>
              </el-form-item>
              <el-form-item prop="password">
                <el-input
                  :type="passwordType"
                  v-model="loginForm.password"
                  auto-complete="off"
                  placeholder="请输入登录密码"
                >
                  <i slot="suffix" class="el-icon-view" @click="showPwd"></i>
                </el-input>
              </el-form-item>
              <el-form-item>
                <el-button class="subBtn" type="primary" @click="submitForm('login')">登录</el-button>
              </el-form-item>
              <p class="smalltxt">
                <router-link class="a" to="#">忘记密码</router-link>
                <router-link class="a" to="#">忘记会员名</router-link>
                <router-link class="a" to="#">免费注册</router-link>
              </p>
            </el-form>
          </el-card>

          <el-card shadow="always" class="login-module" v-else>
            <div slot="header" class="clearfix formTitlt">
              <span>扫码登录</span>
              <span class="titIconbox">
                <i class="iconfont xu-mimadenglu1 fa-lg iconcolor"></i>
                <i
                  class="iconfont xu-imagevector el-icon--right fa-lg pointer"
                  @click="smdl = !smdl"
                ></i>
              </span>
            </div>
            <div class="ewmbox">
              <div class="ewm">
                <img src="https://img.alicdn.com/tfscom/TB1ivYYyHvpK1RjSZFqwu3XUVXa.png" />
              </div>
              <div class="ewmicon">
                <i class="iconfont xu-saomadenglu fa-2x iconcolor"></i>
                <p>打开 微信 扫一扫登录</p>
              </div>
              <!-- <p class="smalltxt">
                <router-link class="a" to="#">免费注册</router-link>
              </p>-->
            </div>
          </el-card>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import * as login from "@/api/login";
import jwt from "jwt-decode";
export default {
  data() {
    return {
      passwordType: "password",
      eyeStatus: "",
      smdl: true,
      loginForm: {
        username: "admin",
        password: "123"
      },
      rules: {
        username: [
          { required: true, message: "请输入登录名", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入登录密码", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    //点击查看密码
    showPwd() {
      if (this.passwordType === "password") {
        this.passwordType = "";
        this.eyeStatus = "color:#1296db";
      } else {
        this.passwordType = "password";
        this.eyeStatus = "";
      }
    },
    //提交登录
    submitForm(formName) {
      let that = this;
      let params = {
        userName: that.loginForm.username.trim(),
        userPwd: that.loginForm.password
      };
      this.$refs[formName].validate(valid => {
        console.log(valid);
        if (!valid) {
          this.$message.info("账号、密码不能为空");
          return false;
        } else {
          console.log(params);
          login
            .adminLogin(params)
            .then(res => {
              console.log(res.data.token)
              if (res.data.code == 200) {
                const token = res.data.token;
                that.$store
                  .dispatch("setToken", token)
                  .then(() => {
                    this.$router.push({ path: "/" });
                  })
                  .catch(res => {
                    if (res.data.code == 300) {
                      this.$message({
                        showClose: true,
                        type: "error",
                        message: res.data.msg
                      });
                    }
                  });
              } else {
                this.$message({
                  showClose: true,
                  type: "error",
                  message: res.data.msg
                });
              }
            })
            .catch(error => {
              that.$message.error(res.data.msg);
            });
        }
      });
    },
    //页面刷新显示消息
    message() {
      const h = this.$createElement;
      this.$notify({
        title: "账号密码",
        message: h(
          "i",
          { style: "color: teal" },
          "账号密码可以随意填写，为了测试效果填写的账号将会被存储为临时假 token"
        ),
        duration: 6000
      });
    },
    isEmpty(value) {
      return (
        value === undefined ||
        value === null ||
        (typeof value === "object" && Object.keys(value).length === 0) ||
        (typeof value === "string" && value.trim().length === 0)
      );
    }
  },
  mounted() {
    this.message();
  }
};
</script>
<style lang="scss">
#login {
  width: 100%;
  height: 100%;
  background-color: #2d3a4b;
  .loginConbox {
    background: #2d3a4b;
  }
  .header {
    height: 60px;
    position: relative;
    background: #2d3a4b;
    /*border-bottom: 1px solid rgba(255, 255, 255, 0.3);*/
    .logo {
      margin-left: 30px;
      width: 500px;
      float: left;
      height: 40px;
      padding-top: 10px;
      img {
        height: 100%;
      }
    }
  }

  .loginBox {
    border-radius: 15px;
    .iconcolor {
      color: #409eff;
    }

    padding: 74px 0 118px;

    .loginCon {
      width: 990px;
      margin: auto;
      position: relative;
      height: 388px;

      .test {
        font-size: 20px large;
        color: white;
      }

      .el-card__header {
        border-bottom: 0px;
      }
      .title {
        font-size: 36px;
        font-weight: 600;
        color: #ffffff;
        width: 500px;
        float: left;
        margin-top: 0px;
        &:first-child {
          justify-content: center;
          font-size: 34px;
          margin-top: 190px;
          margin-bottom: 30px;
        }
      }
      .login-module {
        width: 380px;
        height: 325px;
        margin-top: 60px;
        border: none;
        position: absolute;
        right: 0;

        .formTitlt {
          font-size: 18px;
          font-weight: 400;

          .titIconbox {
            float: right;

            .pointer {
              cursor: pointer;
            }
          }
        }

        .smalltxt {
          text-align: right;

          .a {
            text-decoration: none;
            color: #999999;
            font-size: 12px;
            margin-left: 8px;
          }
        }
      }

      .el-form-item__content {
        margin-left: 0px !important;

        .subBtn {
          width: 100%;
        }
      }
    }

    .el-input__inner,
    .el-button,
    .el-card,
    .el-message {
      border-radius: 0px !important;
    }

    .el-form-item__content .ico {
      position: absolute;
      top: 0px;
      left: 0px;
      z-index: 999;
      width: 40px;
      height: 39px;
      text-align: center;
      border-right: 1px solid #ccc;
    }

    .ewmbox {
      width: 100%;
      height: 240px;
      margin-top: -25px;

      .ewm {
        width: 140px;
        height: 140px;
        margin: 20px auto;

        p {
          font-size: 12px;
          padding-left: 40px;
          margin: 0;
        }
      }

      .ewmicon {
        width: 140px;
        margin: 20px auto 0;

        .iconfont {
          float: left;
        }

        p {
          font-size: 12px;
          padding-left: 40px;
          margin: 0;
        }
      }
    }
  }
}
</style>
