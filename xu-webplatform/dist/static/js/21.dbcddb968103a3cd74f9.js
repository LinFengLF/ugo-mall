webpackJsonp([21],{EMb3:function(e,t){},"iav/":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var l={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",[a("h3",[e._v("多选")]),e._v(" "),a("p",[e._v("选择多行数据时使用 Checkbox。")]),e._v(" "),[a("el-table",{ref:"multipleTable",staticStyle:{width:"100%"},attrs:{data:e.tableData4,"tooltip-effect":"dark"},on:{"selection-change":e.handleSelectionChange}},[a("el-table-column",{attrs:{type:"selection",width:"55"}}),e._v(" "),a("el-table-column",{attrs:{label:"日期",width:"120"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v(e._s(t.row.date))]}}])}),e._v(" "),a("el-table-column",{attrs:{prop:"name",label:"姓名",width:"120"}}),e._v(" "),a("el-table-column",{attrs:{prop:"address",label:"地址","show-overflow-tooltip":""}})],1),e._v(" "),a("div",{staticStyle:{"margin-top":"20px"}},[a("el-button",{on:{click:function(t){e.toggleSelection([e.tableData4[1],e.tableData4[2]])}}},[e._v("切换第二、第三行的选中状态")]),e._v(" "),a("el-button",{on:{click:function(t){e.toggleSelection()}}},[e._v("取消选择")])],1)]],2)},staticRenderFns:[]};var n=a("VU/8")({name:"dataTables",data:function(){return{tableData4:[{date:"2016-05-03",name:"王小虎",address:"上海市普陀区金沙江路 1518 弄"},{date:"2016-05-02",name:"王小虎",address:"上海市普陀区金沙江路 1518 弄"},{date:"2016-05-04",name:"王小虎",address:"上海市普陀区金沙江路 1518 弄"},{date:"2016-05-01",name:"王小虎",address:"上海市普陀区金沙江路 1518 弄"},{date:"2016-05-08",name:"王小虎",address:"上海市普陀区金沙江路 1518 弄"},{date:"2016-05-06",name:"王小虎",address:"上海市普陀区金沙江路 1518 弄"},{date:"2016-05-07",name:"王小虎",address:"上海市普陀区金沙江路 1518 弄"}],multipleSelection:[]}},methods:{toggleSelection:function(e){var t=this;e?e.forEach(function(e){t.$refs.multipleTable.toggleRowSelection(e)}):this.$refs.multipleTable.clearSelection()},handleSelectionChange:function(e){this.multipleSelection=e}}},l,!1,function(e){a("EMb3")},"data-v-06a13b32",null);t.default=n.exports}});
//# sourceMappingURL=21.dbcddb968103a3cd74f9.js.map