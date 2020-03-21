<template>
  <div>
    <el-row :gutter="20">
      <el-col :span="8">
        <div class="card kjfs">
          <p class="title">
            <i class="fa fa-th-large fa-lg"></i>快捷方式
          </p>
          <ul>
            <li>
              <router-link to="/goodsList" class="kjfs kjfs-bluee">
                <span>
                  <i class="el-icon-goods fa-2x"></i>
                </span>
                <span>商品列表</span>
              </router-link>
            </li>
            <li>
              <router-link to="/userList" class="kjfs kjfs-pinkk">
                <span>
                  <i class="fa fa-hand-stop-o fa-2x"></i>
                </span>
                <span>用户列表</span>
              </router-link>
            </li>
            <li>
              <router-link to="/order" class="kjfs kjfs-yelloww">
                <span>
                  <i class="fa fa-universal-access fa-2x"></i>
                </span>
                <span>订单管理</span>
              </router-link>
            </li>
          </ul>
          <ul>
            <li>
              <router-link to="/category" class="kjfs kjfs-grennn">
                <span>
                  <i class="fa fa-cloud-upload fa-2x"></i>
                </span>
                <span>商品类目</span>
              </router-link>
            </li>
            <li>
              <router-link to="/adList" class="kjfs kjfs-purplee">
                <span>
                  <i class="fa el-icon-menu fa-2x"></i>
                </span>
                <span>广告推广</span>
              </router-link>
            </li>
            <li>
              <router-link to="/comment" class="kjfs kjfs-lightBluee">
                <span>
                  <i class="fa fa-file-code-o fa-2x"></i>
                </span>
                <span>商品评论</span>
              </router-link>
            </li>
          </ul>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="card dbsx">
          <p class="title">
            <i class="fa fa-file-text-o"></i>数据统计
          </p>
          <ul>
            <li>
              <router-link to="/userList">
                <span>用户人数</span>
                <span class="num">{{indexData.userNum}}</span>
              </router-link>
            </li>
            <li>
              <router-link to="/order">
                <span>订单数目</span>
                <span class="num">{{indexData.orderNum}}</span>
              </router-link>
            </li>
          </ul>
          <ul>
            <li>
              <router-link to="/goodsList">
                <span>商品数目</span>
                <span class="num">{{indexData.goodsNum}}</span>
              </router-link>
            </li>
            <li>
              <router-link to="/goodsList">
                <span>评论列表</span>
                <span class="num">{{indexData.commentNum}}</span>
              </router-link>
            </li>
          </ul>
        </div>
      </el-col>
      <el-col :span="8">
        <div class="card bbxx">
          <p class="title">
            <i class="fa fa-server"></i>商城信息
          </p>
          <div class="table">
            <p>
              <span class="tit">U购鲜享</span>当前版本：v1.0.0
            </p>
            <p>
              <span class="tit">商城地址</span>福建省漳州市龙海市
            </p>
            <p>
              <span class="tit">联系QQ</span>670729881
            </p>
            <p>
              <span class="tit">联系电话</span>
              <span>15080074405</span>
            </p>
          </div>
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <line-echarts id="lineEcharts" height="300px" ref="echarts"></line-echarts>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="24">
        <OrderTable id="orderTable"></OrderTable>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import LineEcharts from "../../components/ECharts/lineEcharts";
import OrderTable from "../../views/order/orderTable";
import * as api from "../../api/index";
export default {
  name: "mainIndex",
  components: { OrderTable, LineEcharts },
  data() {
    return {
      indexData: {
        userNum: 0,
        goodsNum: 0,
        orderNum: 0,
        commentNum: 0
      }
    };
  },
  mounted() {
    this.selfAdaption();
    this.initData();
  },
  methods: {
    //初始化数据

    initData() {
      // let params = {};
      api.indexData().then(res => {
        // console.log(res.data);
        this.indexData = res.data;
      });
    },
    // echart自适应
    selfAdaption() {
      let that = this;
      setTimeout(() => {
        window.onresize = function() {
          if (that.$refs.echarts) {
            that.$refs.echarts.chart.resize();
          }
        };
      }, 10);
    }
  }
};
</script>

<style lang="scss">
$top: top;
$bottom: bottom;
$left: left;
$right: right;
$leftright: ($left, $right);
$pinkk: #eec2d3;
$bluee: #409eff;
$yelloww: #f4d884;
$grennn: #89dda0;
$purplee: #78a2ea;
$lightBluee: #b8d6ff;

$list: bluee pinkk yelloww grennn purplee lightBluee;
$list1: $bluee $pinkk $yelloww $grennn $purplee $lightBluee;
%shadow {
  background: #fff;
  -webkit-box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.2);
  box-shadow: 4px 4px 40px rgba(0, 0, 0, 0.2);
  border-color: rgba(0, 0, 0, 0.2);
  .title {
    font-size: 14px;
    padding: 10px;
    i {
      margin-#{$right}: 5px;
    }
  }
}

@mixin flex($direction: row, $content: space-between) {
  display: flex;
  flex-direction: $direction;
  justify-content: $content;
}
.card {
  color: #666;
  @extend %shadow;

  ul {
    @include flex;
    li {
      flex: 1;
      a {
        color: #666666;
        align-items: center;
        padding-#{$top}: 20px;
        padding-#{$bottom}: 20px;
        @include flex(column);
        span {
          height: 44px;
        }
        .num {
          line-height: 44px;
          font-size: 42px;
          color: $bluee;
          margin: 0px;
        }
      }
      .kjfs-bluee {
        color: $bluee;
      }
      .kjfs-pinkk {
        color: $pinkk;
      }
      .kjfs-yelloww {
        color: $yelloww;
      }
      .kjfs-grennn {
        color: $grennn;
      }
      .kjfs-purplee {
        color: $purplee;
      }
      .kjfs-lightBluee {
        color: $lightBluee;
      }
      &:hover {
        .kjfs-bluee {
          color: #ffffff;
          background: $bluee;
        }
        .kjfs-pinkk {
          color: #ffffff;
          background: $pinkk;
        }
        .kjfs-yelloww {
          color: #ffffff;
          background: $yelloww;
        }
        .kjfs-grennn {
          color: #ffffff;
          background: $grennn;
        }
        .kjfs-purplee {
          color: #ffffff;
          background: $purplee;
        }
        .kjfs-lightBluee {
          color: #ffffff;
          background: $lightBluee;
        }
      }
    }
  }
  .table {
    padding: 21px;
    p {
      height: 52px;
      line-height: 52px;
      border: 1px solid #cccccc;
      overflow: hidden;
      border-#{$top}: none;
      @include flex(null, start);
      &:first-child {
        border-#{$top}: 1px solid #cccccc;
      }
      span {
      }
      .tit {
        width: 90px;
        min-width: 90px;
        height: 100%;
        text-align: center;
        border-#{$right}: 1px solid #cccccc;
        margin-#{$right}: 18px;
      }
      span.gitbox {
        flex: 1;
        height: 100%;
        overflow: hidden;
        @include flex(row, start);
        a {
          &:first-child {
            margin-#{$right}: 30px;
          }
        }
      }
    }
  }
}
#lineEcharts {
  margin-#{$top}: 30px;
  padding-#{$top}: 30px;
  @extend %shadow;
}
#orderTable {
  margin-#{$top}: 30px;
  padding-#{$top}: 10px;
  @extend %shadow;
}
</style>
