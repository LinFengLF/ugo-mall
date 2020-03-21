<template>
  <div class="app-container">
    <!-- 查询结果 -->
    <p class="title"><i class="el-icon-tickets"></i>待处理订单</p>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="正在查询中。。。"
      border
      fit
      highlight-current-row
    >
      <el-table-column align="center" min-width="100" label="订单编号" prop="orderSn" />

      <el-table-column align="center" label="用户ID" prop="userId" />

      <el-table-column align="center" label="订单状态" prop="orderStatus">
        <template slot-scope="scope">
          <el-tag>{{ scope.row.orderStatus | orderStatusFilter }}</el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="订单金额" prop="actualPrice" />

      <el-table-column align="center" label="支付金额" prop="actualPrice" />

      <el-table-column align="center" label="支付时间" prop="payTime" />

      <el-table-column align="center" label="物流单号" prop="shipSn" />
>
      <el-table-column align="center" label="物流渠道" prop="shipChannel">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.shipChannel">{{ scope.row.shipChannel | orderShipChannel }}</el-tag>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import * as orderApi from "@/api/order";

const statusMap = {
  0: "未付款",
  1: "已付款",
  2: "已发货",
  3: "用户收货",
  4: "交易完成",
  6: "用户取消",
  7: "系统取消",
  8: "系统收货"
  //   202: "申请退款",
  //   203: "已退款"
};
const channels = {
  ZTO: "中通快递",
  YTO: "圆通快递",
  STO: "申通快递"
};

export default {
  name: "Order",
  filters: {
    orderStatusFilter(status) {
      return statusMap[status];
    },
    orderShipChannel(channel) {
      return channels[channel];
    }
  },
  data() {
    return {
      pageSize: 5,
      currentPage: 1,
      list: [],
      total: 0,
      listLoading: true,
      listQuery: {
        userId: "",
        orderSn: "",
        orderStatus: ""
      },
      statusMap,
      channels,
      orderDialogVisible: false,
      orderDetail: {
        order: {},
        user: {},
        orderGoods: []
      },
      shipForm: {
        orderId: "",
        shipChannel: "",
        shipSn: ""
      },
      shipDialogVisible: false,
      refundForm: {
        orderId: undefined,
        refundMoney: undefined
      },
      cancelForm: {
        orderId: "",
        orderStatus: ""
      },
      refundDialogVisible: false,
      downloadLoading: false,
      channels: []
    };
  },
  created() {
    this.getOrderList();
  },
  methods: {
    //获取订单数据
    getOrderList() {
      this.listLoading = true;
      let params = {
        orderStatus: this.listQuery.orderStatus,
        userId: this.listQuery.userId,
        orderSn: this.listQuery.orderSn,
        page: this.currentPage,
        pageSize: this.pageSize
      };
      orderApi.orderList(params).then(res => {
        console.log(res);
        this.total = res.data.total;
        this.listLoading = false;
        this.list = res.data.list;
      });
    },
    //导出订单
    handleDownload() {
      this.downloadLoading = true;
      import("@/vendor/Export2Excel").then(excel => {
        const tHeader = [
          "订单ID",
          "订单编号",
          "用户ID",
          "订单状态",
          "是否删除",
          "收货人",
          "收货联系电话",
          "收货地址"
        ];
        const filterVal = [
          "id",
          "orderSn",
          "userId",
          "orderStatus",
          "isDelete",
          "consignee",
          "mobile",
          "address"
        ];
        excel.export_json_to_excel2(tHeader, this.list, filterVal, "订单信息");
        this.downloadLoading = false;
      });
    },
    //打印订单
    printOrder() {
      this.$print(this.$refs.print);
      this.orderDialogVisible = false;
    }
  }
};
</script>
<style lang="less" scoped>
.filter-container {
  margin-bottom: 10px;
}
.pagination {
  float: right;
  margin: 15px 5px;
}
</style>
