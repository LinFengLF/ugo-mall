<template>
  <div class="app-container">
    <el-card class="box-card">
      <h3>商品介绍</h3>
      <el-form ref="goods" :rules="rules" :model="goods" label-width="150px">
        <el-form-item label="商品编号" prop="goodsSn">
          <el-input v-model="goods.goodsSn" />
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="goods.name" />
        </el-form-item>
        <el-form-item label="专柜价格" prop="counterPrice">
          <el-input v-model="goods.counterPrice" placeholder="0.00">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="当前价格" prop="retailPrice">
          <el-input v-model="goods.retailPrice" placeholder="0.00">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="是否新品" prop="isNew">
          <el-radio-group v-model="goods.isNew">
            <el-radio :label="true">新品</el-radio>
            <el-radio :label="false">非新品</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否热卖" prop="isHot">
          <el-radio-group v-model="goods.isHot">
            <el-radio :label="false">普通</el-radio>
            <el-radio :label="true">热卖</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否在售" prop="isOnSale">
          <el-radio-group v-model="goods.isOnSale">
            <el-radio :label="true">在售</el-radio>
            <el-radio :label="false">未售</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="商品图片">
          <el-upload
            action="api/file/upload"
            :show-file-list="false"
            :on-success="uploadUrl"
            :before-upload="checkFileSize"
            class="avatar-uploader"
            accept=".jpg, .jpeg, .png, .gif"
          >
            <img v-if="goods.picUrl" :src="goods.picUrl" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon" />
            <div slot="tip" class="el-upload__tip">只能上传jpg/png/gif文件，且不超过1024kb</div>
          </el-upload>
        </el-form-item>

        <el-form-item label="宣传画廊">
          <el-upload
            action="api/file/upload"
            :limit="5"
            :on-exceed="uploadOverrun"
            :on-success="handleGalleryUrl"
            :on-remove="handleRemove"
            multiple
            accept=".jpg, .jpeg, .png, .gif"
            list-type="picture-card"
          >
            <i class="el-icon-plus" />
          </el-upload>
        </el-form-item>

        <el-form-item label="商品单位">
          <el-input v-model="goods.unit" placeholder="件 / 个 / 盒" />
        </el-form-item>

        <!-- <el-form-item label="关键字">
          <el-tag
            v-for="tag in keywords"
            :key="tag"
            closable
            type="primary"
            @close="handleClose(tag)"
          >{{ tag }}</el-tag>
          <el-input
            v-if="newKeywordVisible"
            ref="newKeywordInput"
            v-model="newKeyword"
            class="input-new-keyword"
            @keyup.enter.native="handleInputConfirm"
            @blur="handleInputConfirm"
          />
          <el-button v-else class="button-new-keyword" type="primary" @click="showInput">+ 增加</el-button>
        </el-form-item> -->

        <el-form-item label="所属分类">
          <el-cascader
            :options="categoryList"
            expand-trigger="hover"
            clearable
            @change="handleCategoryChange"
          />
        </el-form-item>

        <!-- <el-form-item label="所属品牌商">
          <el-select v-model="goods.brandId" clearable>
            <el-option
              v-for="item in brandList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>-->

        <el-form-item label="商品简介">
          <el-input v-model="goods.brief" />
        </el-form-item>

        <el-form-item label="商品详细介绍">
          <editor v-model="goods.detail" :init="editorInit" />
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="box-card">
      <h3>商品规格(如需添加可删除示例)</h3>
      <el-row :gutter="20" type="flex" align="middle" style="padding:20px 0;">
        <el-col :span="10">
          <el-radio-group v-model="multipleSpec" @change="specChanged">
            <!-- <el-radio-button :label="false">默认标准规格</el-radio-button> -->
            <el-radio-button :label="true">支持多规格</el-radio-button>
          </el-radio-group>
        </el-col>
        <el-col v-if="multipleSpec" :span="24">
          <el-button
            style="float:right;"
            :plain="true"
            type="primary"
            @click="handleSpecificationShow"
          >添加</el-button>
        </el-col>
      </el-row>

      <el-table :data="specifications">
        <el-table-column property="specification" label="规格名" />
        <el-table-column property="value" label="规格值">
          <template slot-scope="scope">
            <el-tag type="primary">{{ scope.row.value }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column property="price" label="价格">
          <template slot-scope="scope">
            <el-tag type="primary">￥{{ scope.row.price }}元</el-tag>
          </template>
        </el-table-column>
        <el-table-column property="picUrl" label="规格图片">
          <template slot-scope="scope">
            <img v-if="scope.row.picUrl" :src="scope.row.picUrl" width="40" />
          </template>
        </el-table-column>
        <el-table-column property="num" label="库存">
          <template slot-scope="scope">
            <el-tag type="primary">{{ scope.row.num }}</el-tag>
          </template>
        </el-table-column>
        <!-- <el-table-column property="price" label="价格">
          <template slot-scope="scope">
            <img v-if="scope.row.num" :src="scope.row.num" width="40" />
          </template>
        </el-table-column>-->
        <el-table-column
          v-if="multipleSpec"
          align="center"
          label="操作"
          width="250"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button type="danger" size="mini" @click="handleSpecificationDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :visible.sync="specVisiable" title="设置商品规格">
        <el-form
          ref="specForm"
          :rules="rules"
          :model="specForm"
          status-icon
          label-position="left"
          label-width="100px"
          style="width: 400px; margin-left:50px;"
        >
          <el-form-item label="规格名" prop="specification">
            <el-input v-model="specForm.specification" />
          </el-form-item>
          <el-form-item label="规格值" prop="value">
            <el-input v-model="specForm.value" />
          </el-form-item>
          <el-form-item label="库存" prop="num">
            <el-input v-model="specForm.num" />
          </el-form-item>
          <el-form-item label="价格" prop="price">
            <el-input v-model="specForm.price" />
          </el-form-item>
          <el-form-item label="规格图片" prop="picUrl">
            <el-upload
              action="api/file/upload"
              :show-file-list="false"
              :on-success="uploadSpecPicUrl"
              class="avatar-uploader"
              accept=".jpg, .jpeg, .png, .gif"
            >
              <img v-if="specForm.picUrl" :src="specForm.picUrl" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon" />
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="specVisiable = false">取消</el-button>
          <el-button type="primary" @click="handleSpecificationAdd">确定</el-button>
        </div>
      </el-dialog>
    </el-card>

    <!-- <el-card class="box-card">
      <h3>商品库存</h3>
      <el-table :data="products">
        <el-table-column property="value" label="货品规格">
          <template slot-scope="scope">
            <el-tag v-for="tag in scope.row.specifications" :key="tag">{{ tag }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column property="price" width="100" label="货品售价" />
        <el-table-column property="number" width="100" label="货品数量" />
        <el-table-column property="url" width="100" label="货品图片">
          <template slot-scope="scope">
            <img v-if="scope.row.url" :src="scope.row.url" width="40" />
          </template>
        </el-table-column>
        <el-table-column
          align="center"
          label="操作"
          width="100"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleProductShow(scope.row)">设置</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :visible.sync="productVisiable" title="添加货品">
        <el-form
          ref="productForm"
          :model="productForm"
          status-icon
          label-position="left"
          label-width="100px"
          style="width: 400px; margin-left:50px;"
        >
          <el-form-item label="货品规格列" prop="specifications">
            <el-tag v-for="tag in productForm.specifications" :key="tag">{{ tag }}</el-tag>
          </el-form-item>
          <el-form-item label="货品售价" prop="price">
            <el-input v-model="productForm.price" />
          </el-form-item>
          <el-form-item label="货品数量" prop="number">
            <el-input v-model="productForm.number" />
          </el-form-item>
          <el-form-item label="货品图片" prop="url">
            <el-upload
              action
              :show-file-list="false"
              :on-success="uploadProductUrl"
              class="avatar-uploader"
              accept=".jpg, .jpeg, .png, .gif"
            >
              <img v-if="productForm.url" :src="productForm.url" class="avatar" />
              <i v-else class="el-icon-plus avatar-uploader-icon" />
            </el-upload>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="productVisiable = false">取消</el-button>
          <el-button type="primary" @click="handleProductEdit">确定</el-button>
        </div>
      </el-dialog>
    </el-card>-->

    <!-- <el-card class="box-card">
      <div class="goodsAttribute">
        <h3>商品参数</h3>
        <el-button style="float:right" type="primary" @click="handleAttributeShow">添加</el-button>
      </div>

      <el-table :data="attributes">
        <el-table-column property="attribute" label="商品参数名称" />
        <el-table-column property="value" label="商品参数值" />
        <el-table-column
          align="center"
          label="操作"
          width="100"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button type="danger" size="mini" @click="handleAttributeDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :visible.sync="attributeVisiable" title="添加商品参数">
        <el-form
          ref="attributeForm"
          :model="attributeForm"
          status-icon
          label-position="left"
          label-width="100px"
          style="width: 400px; margin-left:50px;"
        >
          <el-form-item label="商品参数名称" prop="attribute">
            <el-input v-model="attributeForm.attribute" />
          </el-form-item>
          <el-form-item label="商品参数值" prop="value">
            <el-input v-model="attributeForm.value" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="attributeVisiable = false">取消</el-button>
          <el-button type="primary" @click="handleAttributeAdd">确定</el-button>
        </div>
      </el-dialog>
    </el-card> -->

    <div class="op-container">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handlePublish">上架</el-button>
    </div>
  </div>
</template>

<script>
// import { createGood, listCatAndBrand } from "@/api/goods";
import * as goodApi from "@/api/goods";
// import { createStorage, uploadPath } from "@/api/storage";
import Editor from "@tinymce/tinymce-vue";
import { MessageBox } from "element-ui";
import * as markApi from "@/api/mark";
// import { getToken } from "@/utils/auth";

export default {
  name: "GoodsCreate",
  components: { Editor },
  data() {
    return {
      attributeBtn: true,
      newKeywordVisible: false,
      newKeyword: "",
      keywords: [],
      categoryList: [],
      brandList: [],
      goods: {
        picUrl: "",
        gallery: [],
        isHot: false,
        isNew: true,
        isOnSale: true
      },
      specVisiable: false,
      specForm: { specification: "", value: "", picUrl: "", num: "" },
      multipleSpec: true,
      specifications: [
        {
          specification: "颜色(实例)",
          value: "白色",
          picUrl:
            "https://fuss10.elemecdn.com/a/3f/3302e58f9a181d2509f3dc0fa68b0jpeg.jpeg",
          num: 99,
          price: 99
        }
      ],
      productVisiable: false,
      productForm: {
        id: 0,
        specifications: [],
        price: 0.0,
        number: 0,
        url: ""
      },
      products: [
        { id: 0, specifications: ["标准"], price: 0.0, number: 0, url: "" }
      ],
      attributeVisiable: false,
      attributeForm: { attribute: "", value: "" },
      attributes: [],
      rules: {
        goodsSn: [
          { required: true, message: "商品编号不能为空", trigger: "blur" }
        ],
        name: [{ required: true, message: "商品名称不能为空", trigger: "blur" }]
      },
      editorInit: {
        language: "zh_CN",
        height: 500,
        convert_urls: false,
        plugins: [
          "advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount"
        ],
        toolbar: [
          "searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample",
          "hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen"
        ],
        images_upload_handler: function(blobInfo, success, failure) {
          const formData = new FormData();
          formData.append("file", blobInfo.blob());
          goodApi.createStorage(formData)
            .then(res => {
              success(res.data.url);
            })
            .catch(() => {
              failure("上传失败，请重新上传");
            });
        }
      }
    };
  },
  //   computed: {
  //     headers() {
  //       return {
  //         "X-Litemall-Admin-Token": getToken()
  //       };
  //     }
  //   },
  created() {
    // this.init();
    this.getCateGory();
  },

  methods: {
    //获取类目信息
    getCateGory() {
      let params = {};
      markApi.getCategoryList(params).then(res => {
        this.categoryList = res.data.data;
        // for(let i = 0;i < res.data.list.length; i++){
        //   this.categoryList[i] = res.data.list.name;
        // }
        // this.categoryList = res.data.list;
        // console.log(this.categoryList);
      });
    },
    //图片上传成功的回调函数
    uploadUrl(res) {
      console.log(res.url);
      this.goods.picUrl = res.url;
      console.log(this.goods);
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
    // init: function() {
    //   listCatAndBrand().then(response => {
    //     this.categoryList = response.data.data.categoryList;
    //     this.brandList = response.data.data.brandList;
    //   });
    // },
    //初始化商品种类
    // init() {
    //   this.categoryList = [
    //     {
    //       value: "vegetable",
    //       label: "新鲜水果",
    //       children: [
    //         {
    //           value: "进口水果",
    //           label: "进口水果"
    //         },
    //         {
    //           value: "国产水果",
    //           label: "国产水果"
    //         }
    //       ]
    //     },
    //     {
    //       value: "海鲜",
    //       label: "海鲜",
    //       children: [
    //         {
    //           value: "进口海鲜",
    //           label: "进口海鲜"
    //         },
    //         {
    //           value: "国产海鲜",
    //           label: "国产海鲜"
    //         }
    //       ]
    //     },
    //     {
    //       value: "肉类",
    //       label: "肉类",
    //       children: [
    //         {
    //           value: "牛肉",
    //           label: "牛肉"
    //         },
    //         {
    //           value: "猪肉",
    //           label: "猪肉"
    //         }
    //       ]
    //     },
    //     {
    //       value: "禽蛋",
    //       label: "禽蛋",
    //       children: [
    //         {
    //           value: "鸡",
    //           label: "鸡"
    //         },
    //         {
    //           value: "鸭",
    //           label: "鸭"
    //         },
    //         {
    //           value: "鸡蛋",
    //           label: "鸡蛋"
    //         }
    //       ]
    //     }
    //   ];
    // },
    //商品种类选择
    handleCategoryChange(value) {
      debugger
      console.log(value);
      console.log(value[value.length - 1]);
      this.goods.categoryId = value[value.length - 1];
    },
    //点击取消返回商品列表
    handleCancel: function() {
      this.$router.push({ path: "/goodsList" });
    },
    //上架
    handlePublish() {
      this.goods.isOnSale == true
        ? (this.goods.isOnSale = 1)
        : (this.goods.isOnSale = 0);
      this.goods.isNew == true
        ? (this.goods.isNew = 1)
        : (this.goods.isNew = 0);
      this.goods.isHot == true
        ? (this.goods.isHot = 1)
        : (this.goods.isHot = 0);
      console.log(this.goods);
      // let params = {
      //   goodsSn: this.goods.goodsSn,
      //   name: this.goods.name,
      //   categoryId: this.goods.categoryId,
      //   gallery: this.goods.gallery,
      //   keywords: this.goods.keywords,
      //   brief: this.goods.brief,
      //   isOnSale:
      //     this.goods.isOnSale == true
      //       ? (this.goods.isOnSale = 1)
      //       : (this.goods.isOnSale = 0),
      //   picUrl: this.goods.picUrl,
      //   shareUrl: this.goods.shareUrl,
      //   isNew:
      //     this.goods.isNew == true
      //       ? (this.goods.isNew = 1)
      //       : (this.goods.isNew = 0),
      //   isHot:
      //     this.goods.isHot == true
      //       ? (this.goods.isHot = 1)
      //       : (this.goods.isHot = 0),
      //   unit: this.goods.unit,
      //   counterPrice: this.goods.counterPrice,
      //   retailPrice: this.goods.retailPrice,
      //   detail: this.goods.detail,
      //   addTime: this.goods.addTime,
      //   updateTime: this.goods.updateTime
      // };
      let params = {
        goods: this.goods,
        specifications: this.specifications
      };
      console.log(params);
      goodApi
        .publishGoods(params)
        .then(res => {
          if (res.data.code == 1) {
            this.$notify.success({
              title: "成功",
              message: "创建成功"
            });
            this.$router.push({ path: "/goodsList" });
          } else {
            this.$notify.error({
              title: "失败",
              message: "商品上架失败，请重试"
            });
          }
        })
        .catch(res => {
          MessageBox.alert("业务错误：添加失败", "警告", {
            confirmButtonText: "确定",
            type: "error"
          });
        });
    },
    //关闭关键字标签
    handleClose(tag) {
      this.keywords.splice(this.keywords.indexOf(tag), 1);
      this.goods.keywords = this.keywords.toString();
    },
    showInput() {
      this.newKeywordVisible = true;
      this.$nextTick(_ => {
        this.$refs.newKeywordInput.$refs.input.focus();
      });
    },
    handleInputConfirm() {
      const newKeyword = this.newKeyword;
      if (newKeyword) {
        this.keywords.push(newKeyword);
        this.goods.keywords = this.keywords.toString();
      }
      this.newKeywordVisible = false;
      this.newKeyword = "";
    },
    //图片上传成功的回调函数
    uploadPicUrl: function(response) {
      this.goods.picUrl = response.data.url;
    },
    //图片上传时的回调函数
    uploadOverrun: function() {
      this.$message({
        type: "error",
        message: "上传文件个数超出限制!最多上传5张图片!"
      });
    },
    //商品上架
    insert() {
      this.$ref["goods"].validate(valid => {
        console.log(valid);
        if (!valid) {
          this.$message.info("所有带*的都是必填项");
        }
      });
    },
    //图片列表上传成功的回调函数
    handleGalleryUrl(res, file, fileList) {
      if (res.msg == "上传成功") {
        this.goods.gallery.push(res.url);
        console.log(this.goods.gallery);
      }
    },
    //照片移除的回调函数
    handleRemove: function(file, fileList) {
      for (var i = 0; i < this.goods.gallery.length; i++) {
        // 这里存在两种情况
        // 1. 如果所删除图片是刚刚上传的图片，那么图片地址是file.response.data.url
        //    此时的file.url虽然存在，但是是本机地址，而不是远程地址。
        // 2. 如果所删除图片是后台返回的已有图片，那么图片地址是file.url
        var url;
        if (file.response === undefined) {
          url = file.url;
        } else {
          url = file.response.data.url;
        }

        if (this.goods.gallery[i] === url) {
          this.goods.gallery.splice(i, 1);
        }
      }
    },
    specChanged: function(label) {
      if (label === false) {
        this.specifications = [
          { specification: "规格", value: "标准", picUrl: "" }
        ];
        this.products = [
          { id: 0, specifications: ["标准"], price: 0.0, number: 0, url: "" }
        ];
      } else {
        this.specifications = [];
        this.products = [];
      }res
    },
    uploadSpecPicUrl(res) {
      this.specForm.picUrl = res.url;
    },
    //设置商品规格dialog打开
    handleSpecificationShow() {
      this.specForm = {
        specification: "",
        value: "",
        picUrl: "",
        price: "",
        num: ""
      };
      this.specVisiable = true;
    },
    //设置商品规格
    handleSpecificationAdd() {
      debugger;
      var index = this.specifications.length - 1;
      for (var i = 0; i < this.specifications.length; i++) {
        const v = this.specifications[i];
        if (v.specification === this.specForm.specification) {
          if (v.value === this.specForm.value) {
            this.$message({
              type: "warning",
              message: "已经存在规格值:" + v.value
            });
            this.specForm = {};
            this.specVisiable = false;
            return;
          } else {
            index = i;
          }
        }
      }

      this.specifications.splice(index + 1, 0, this.specForm);
      this.specVisiable = false;

      this.specToProduct();
    },
    handleSpecificationDelete(row) {
      const index = this.specifications.indexOf(row);
      this.specifications.splice(index, 1);
      this.specToProduct();
    },

    specToProduct() {
      debugger;
      if (this.specifications.length === 0) {
        return;
      }
      // 根据specifications创建临时规格列表
      var specValues = [];
      var spec = this.specifications[0].specification;
      var values = [];
      values.push(0);

      for (var i = 1; i < this.specifications.length; i++) {
        const aspec = this.specifications[i].specification;

        if (aspec === spec) {
          values.push(i);
        } else {
          specValues.push(values);
          spec = aspec;
          values = [];
          values.push(i);
        }
      }
      specValues.push(values);
      console.log(specValues);
      console.log(this.specifications);

      // 根据临时规格列表生产货品规格
      // 算法基于 https://blog.csdn.net/tyhj_sf/article/details/53893125
      // var productsIndex = 0;
      // var products = [];
      // var combination = [];
      // var n = specValues.length;
      // for (var s = 0; s < n; s++) {
      //   combination[s] = 0;
      // }
      // var index = 0;
      // var isContinue = false;
      // do {
      //   var specifications = [];
      //   for (var x = 0; x < n; x++) {
      //     var z = specValues[x][combination[x]];
      //     specifications.push(this.specifications[z].value);
      //   }
      //   products[productsIndex] = {
      //     id: productsIndex,
      //     specifications: specifications,
      //     price: 0.0,
      //     number: 0,
      //     url: ""
      //   };
      //   productsIndex++;

      //   index++;
      //   combination[n - 1] = index;
      //   for (var j = n - 1; j >= 0; j--) {
      //     if (combination[j] >= specValues[j].length) {
      //       combination[j] = 0;
      //       index = 0;
      //       if (j - 1 >= 0) {
      //         combination[j - 1] = combination[j - 1] + 1;
      //       }
      //     }
      //   }
      //   isContinue = false;
      //   for (var p = 0; p < n; p++) {
      //     if (combination[p] !== 0) {
      //       isContinue = true;
      //     }
      //   }
      // } while (isContinue);

      // this.products = products;
    },
    handleProductShow(row) {
      this.productForm = Object.assign({}, row);
      this.productVisiable = true;
    },
    uploadProductUrl: function(response) {
      this.productForm.url = response.data.url;
    },
    handleProductEdit() {
      for (var i = 0; i < this.products.length; i++) {
        const v = this.products[i];
        if (v.id === this.productForm.id) {
          this.products.splice(i, 1, this.productForm);
          break;
        }
      }
      this.productVisiable = false;
    },
    handleAttributeShow() {
      this.attributeForm = {};
      this.attributeVisiable = true;
    },
    //添加商品参数
    handleAttributeAdd() {
      this.attributes.unshift(this.attributeForm);

      this.attributeVisiable = false;
      console.log(this.attributeForm);
    },
    //删除商品参数
    handleAttributeDelete(row) {
      const index = this.attributes.indexOf(row);
      this.attributes.splice(index, 1);
      console.log(this.attributeForm);
    }
  }
};
</script>


<style>
.el-card {
  margin-bottom: 10px;
}

.el-tag + .el-tag {
  margin-left: 10px;
}

.input-new-keyword {
  width: 90px;
  margin-left: 10px;
  vertical-align: bottom;
}

.avatar-uploader .el-upload {
  width: 145px;
  height: 145px;
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
.h3 {
  margin: 5px 0 20px;
}

.goodsAttribute {
  display: inline-block;
}
</style>
