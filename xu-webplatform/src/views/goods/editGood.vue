<template>
  <div class="app-container">
    <el-card class="box-card">
      <h3>商品介绍</h3>
      <el-form ref="goods" :rules="rules" :model="goods" label-width="150px">
        <el-form-item label="商品ID" prop="id">
          <el-input v-model="goods.id" disabled />
        </el-form-item>
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="goods.name" />
        </el-form-item>
        <el-form-item label="商品编号" prop="goodsSn">
          <el-input v-model="goods.goodsSn" />
        </el-form-item>
        <el-form-item label="市场售价" prop="counterPrice">
          <el-input v-model="goods.counterPrice" placeholder="0.00">
            <template slot="append">元</template>
          </el-input>
        </el-form-item>
        <el-form-item label="是否新品" prop="isNew">
          <el-radio-group v-model="goods.isNew">
            <el-radio :label="1">新品</el-radio>
            <el-radio :label="0">非新品</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否热卖" prop="isHot">
          <el-radio-group v-model="goods.isHot">
            <el-radio :label="0">普通</el-radio>
            <el-radio :label="1">热卖</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否在售" prop="isOnSale">
          <el-radio-group v-model="goods.isOnSale">
            <el-radio :label="1">在售</el-radio>
            <el-radio :label="0">未售</el-radio>
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
            <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过1024kb</div>
          </el-upload>
        </el-form-item>

        <el-form-item label="宣传画廊">
          <el-upload
            action="api/file/upload"
            :limit="5"
            :file-list="galleryFileList"
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
            v-model="categoryIds"
            :options="categoryList"
            clearable
            expand-trigger="hover"
            @change="handleCategoryChange"
          />
        </el-form-item>

        <el-form-item label="商品简介">
          <el-input v-model="goods.brief" />
        </el-form-item>

        <el-form-item label="商品详细介绍">
          <editor v-model="goods.detail" :init="editorInit" />
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="box-card">
      <h3>商品规格</h3>
      <el-table :data="this.specifications">
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
        <el-table-column
          align="center"
          label="操作"
          width="200"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleSpecificationShow(scope.row)">设置</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :visible.sync="specVisiable" title="设置规格">
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
            <el-input v-model="specForm.specification" disabled />
          </el-form-item>
          <el-form-item label="规格值" prop="value">
            <el-input v-model="specForm.value" disabled />
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
          <el-button type="primary" @click="handleSpecificationEdit">确定</el-button>
        </div>
      </el-dialog>
    </el-card>

    <!-- <el-card class="box-card">
      <h3>商品库存</h3>
      <el-table :data="products">
        <el-table-column property="value" label="货品规格">
          <template slot-scope="scope">
            <el-tag v-for="tag in scope.row.specifications" :key="tag">
              {{ tag }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column property="price" label="货品售价" />
        <el-table-column property="number" label="货品数量" />
        <el-table-column property="url" label="货品图片">
          <template slot-scope="scope">
            <img v-if="scope.row.url" :src="scope.row.url" width="40">
          </template>
        </el-table-column>
        <el-table-column align="center" label="操作" width="200" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleProductShow(scope.row)">设置</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :visible.sync="productVisiable" title="编辑货品">
        <el-form ref="productForm" :model="productForm" status-icon label-position="left" label-width="100px" style="width: 400px; margin-left:50px;">
          <el-form-item label="货品规格列" prop="specifications">
            <el-tag v-for="tag in productForm.specifications" :key="tag">
              {{ tag }}
            </el-tag>
          </el-form-item>
          <el-form-item label="货品售价" prop="price">
            <el-input v-model="productForm.price" />
          </el-form-item>
          <el-form-item label="货品数量" prop="number">
            <el-input v-model="productForm.number" />
          </el-form-item>
          <el-form-item label="货品图片" prop="url">
            <el-upload
              :headers="headers"
              :action="uploadPath"
              :show-file-list="false"
              :on-success="uploadProductUrl"
              class="avatar-uploader"
              accept=".jpg,.jpeg,.png,.gif"
            >
              <img v-if="productForm.url" :src="productForm.url" class="avatar">
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
      <h3>商品参数</h3>
      <el-button type="primary" @click="handleAttributeShow">添加</el-button>
      <el-table :data="attributesData">
        <el-table-column property="attribute" label="商品参数名称" />
        <el-table-column property="value" label="商品参数值" />
        <el-table-column
          align="center"
          label="操作"
          width="200"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="handleAttributeShow(scope.row)">设置</el-button>
            <el-button type="danger" size="mini" @click="handleAttributeDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>

      <el-dialog :visible.sync="attributeVisiable" :title="attributeAdd ? '添加商品参数' : '编辑商品参数'">
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
          <el-button v-if="attributeAdd" type="primary" @click="handleAttributeAdd">确定</el-button>
          <el-button v-else type="primary" @click="handleAttributeEdit">确定</el-button>
        </div>
      </el-dialog>
    </el-card>-->
    <div class="op-container">
      <el-button @click="handleCancel">取消</el-button>
      <el-button type="primary" @click="handleEdit">更新商品</el-button>
    </div>
  </div>
</template>


<script>
// import { detailGoods, editGoods, listCatAndBrand } from '@/api/goods'
// import { createStorage, uploadPath } from '@/api/storage'
import Editor from "@tinymce/tinymce-vue";
// import Editor from '../../../static/tinymce4.7.5/tinymce.min.js'
import { MessageBox } from "element-ui";
import * as goodsApi from "@/api/goods";
import * as markApi from "@/api/mark";
// import { getToken } from '@/utils/auth'

export default {
  name: "GoodsEdit",
  components: { Editor },
  data() {
    return {
      newKeywordVisible: false,
      newKeyword: "",
      keywords: [],
      galleryFileList: [],
      categoryList: [],
      brandList: [],
      categoryIds: [],
      goods: { gallery: [] },
      specVisiable: false,
      specForm: { specification: "", value: "", picUrl: "" },
      specifications: [
        { specification: "规格", value: "标准", picUrl: "", price: "", num: "" }
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
      attributeAdd: true,
      attributeForm: { attribute: "", value: "" },
      attributes: [],
      rules: {
        name: [{ required: true, message: "商品名称不能为空", trigger: "blur" }]
      },
      editorInit: {
        language: "zh_CN",
        height: "400px",
        convert_urls: false,
        plugins: [
          "advlist anchor autolink autosave code codesample colorpicker colorpicker contextmenu directionality emoticons fullscreen hr image imagetools importcss insertdatetime link lists media nonbreaking noneditable pagebreak paste preview print save searchreplace spellchecker tabfocus table template textcolor textpattern visualblocks visualchars wordcount"
        ],
        toolbar: [
          "searchreplace bold italic underline strikethrough alignleft aligncenter alignright outdent indent  blockquote undo redo removeformat subscript superscript code codesample",
          "hr bullist numlist link image charmap preview anchor pagebreak insertdatetime media table emoticons forecolor backcolor fullscreen"
        ],
        images_upload_handler: function(blobInfo, success, failure) {
          const formData = new FormData()
          formData.append('file', blobInfo.blob())
          createStorage(formData)
            .then(res => {
              success(res.data.data.url)
            })
            .catch(() => {
              failure('上传失败，请重新上传')
            })
        }
      }
    };
  },
  computed: {
    // headers() {
    //   return {
    //     'X-Litemall-Admin-Token': getToken()
    //   }
    // },
    // attributesData() {
    //   var attributesData = [];
    //   for (var i = 0; i < this.attributes.length; i++) {
    //     if (this.attributes[i].deleted) {
    //       continue;
    //     }
    //     attributesData.push(this.attributes[i]);
    //   }
    //   return attributesData;
    // }
  },
  mounted() {
    this.init();
    this.getCateGory();
  },
  methods: {
    //获取类目信息
    getCateGory() {
      let params = {};
      markApi.getCategoryList(params).then(res => {
        this.categoryList = res.data.data;
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
    //获取信息
    init() {
      // console.log(this.specifications);
      if (this.$route.query.id == null) {
        return;
      }
      let params = {
        id: this.$route.query.id
      };
      // console.log("***************************");
      // console.log(params);
      goodsApi.queryById(params).then(res => {
        console.log(res);
        this.goods = res.data.goods;
        // 稍微调整一下前后端不一致
        if (this.goods.brandId === 0) {
          this.goods.brandId = null;
        }
        if (this.goods.keywords === "") {
          this.goods.keywords = null;
        }
        this.specifications = res.data.specifications;
        this.products = res.data.products;
        this.attributes = res.data.attributes;
        this.categoryIds = res.data.categoryIds;

        this.galleryFileList = [];
        console.log(this.goods.gallery.length);
        if (this.goods.gallery != null) {
          for (var i = 0; i < this.goods.gallery.length; i++) {
            this.galleryFileList.push({
              url: this.goods.gallery[i]
            });
          }
        }

        // const keywords = res.data.keywords;
        // if (keywords !== null) {
        //   this.keywords = keywords.split(",");
        // }
      });

      //   listCatAndBrand().then(response => {
      //     this.categoryList = response.data.data.categoryList;
      //     this.brandList = response.data.data.brandList;
      //   });
    },
    handleCategoryChange(value) {
      debugger
      this.goods.categoryId = value[value.length - 1];
    },
    handleCancel: function() {
      this.$router.push({ path: "/goodsList" });
    },
    //点击更新商品
    handleEdit() {
      this.goods.isOnSale == true ? (this.goods.isOnSale = 1) : (this.goods.isOnSale = 0);
      this.goods.isNew == true ? (this.goods.isNew = 1) : (this.goods.isNew = 0);
      this.goods.isHot == true ? (this.goods.isHot = 1) : (this.goods.isHot = 0);
      let params = {
        goods: this.goods,
        specifications: this.specifications
        // id: this.goods.id,
        // goodsSn: this.goods.goodsSn,
        // name: this.goods.name,
        // categoryId: this.goods.categoryId,
        // gallery: this.goods.gallery,
        // keywords: this.goods.keywords,
        // brief: this.goods.brief,
        // isOnSale:
        //   this.goods.isOnSale == true
        //     ? (this.goods.isOnSale = 1)
        //     : (this.goods.isOnSale = 0),
        // picUrl: this.goods.picUrl,
        // shareUrl: this.goods.shareUrl,
        // isNew:
        //   this.goods.isNew == true
        //     ? (this.goods.isNew = 1)
        //     : (this.goods.isNew = 0),
        // isHot:
        //   this.goods.isHot == true
        //     ? (this.goods.isHot = 1)
        //     : (this.goods.isHot = 0),
        // unit: this.goods.unit,
        // counterPrice: this.goods.counterPrice,
        // retailPrice: this.goods.retailPrice,
        // detail: this.goods.detail,
        // addTime: this.goods.addTime,
        // updateTime: this.goods.updateTime
      };

      const finalGoods = {
        goods: this.goods,
        specifications: this.specifications,
        products: this.products,
        attributes: this.attributes
      };
      goodsApi
        .editGoods(params)
        .then(res => {
          if (res.data.code == 1) {
            this.$notify.success({
              title: "成功",
              message: res.data.msg
            });
          } else {
            this.$notify.error({
              title: "失败",
              message: res.data.msg
            });
          }

          this.$router.push({ path: "/goodsList" });
        })
        .catch(res => {
          MessageBox.alert("业务错误：" + res.data.msg, "警告", {
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
    //图片列表上传成功的回调函数
    handleGalleryUrl(res, file, fileList) {
      if (res.msg == "上传成功") {
        this.goods.gallery.push(res.url);
        console.log(this.goods.gallery);
      }
    },
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
      }
    },
    uploadSpecPicUrl(response) {
      this.specForm.picUrl = response.url;
    },
    handleSpecificationShow(row) {
      debugger;
      this.specForm = Object.assign({}, row);
      this.specVisiable = true;
    },
    //规格确认添加方法
    handleSpecificationEdit() {
      this.specForm.updateTime = "";
      for (var i = 0; i < this.specifications.length; i++) {
        const v = this.specifications[i];
        if (v.id === this.specForm.id) {
          this.specifications.splice(i, 1, this.specForm);
          break;
        }
      }
      this.specVisiable = false;
      console.log(this.specifications);
    },
    handleProductShow(row) {
      this.productForm = Object.assign({}, row);
      this.productVisiable = true;
    },
    uploadProductUrl: function(response) {
      this.productForm.url = response.data.url;
    },
    handleProductEdit() {
      this.productForm.updateTime = "";
      for (var i = 0; i < this.products.length; i++) {
        const v = this.products[i];
        if (v.id === this.productForm.id) {
          this.products.splice(i, 1, this.productForm);
          break;
        }
      }
      this.productVisiable = false;
    },
    handleAttributeShow(row) {
      if (row.id) {
        this.attributeForm = Object.assign({}, row);
        this.attributeAdd = false;
      } else {
        this.attributeForm = {};
        this.attributeAdd = true;
      }
      this.attributeVisiable = true;
    },
    handleAttributeAdd() {
      this.attributes.unshift(this.attributeForm);
      this.attributeVisiable = false;
    },
    handleAttributeEdit() {
      // 这是一个trick，设置updateTime的值为空，告诉后端这个记录已编辑需要更新。
      this.attributeForm.updateTime = "";
      for (var i = 0; i < this.attributes.length; i++) {
        const v = this.attributes[i];
        if (v.id === this.attributeForm.id) {
          this.attributes.splice(i, 1, this.attributeForm);
          break;
        }
      }
      this.attributeVisiable = false;
    },
    handleAttributeDelete(row) {
      row.deleted = true;
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
.op-container {
  display: flex;
  justify-content: center;
}
</style>