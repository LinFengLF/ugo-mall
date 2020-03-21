import axios from 'axios';

export function listGoods(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/goodsController/getGoodsList',{params})
}
export function deleteGoods(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/goodsController/deleteByBatch',{params})
}
export function createGood(params){
    return axios.post('/api/ugoMall/goodsController/insert',params)
}
export function queryById(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/goodsController/queryById',{params})
}
export function editGoods(params){
    return axios.post('/api/ugoMall/goodsController/editGoods',params)
}
// 获取商品参数
export function getGoodsAttribute(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/goodsAttributeController/getList',{params})
}
// 添加商品参数
export function createGoodsAttribute(params){
    return axios.post('/api/ugoMall/goodsAttributeController/insert',params)
}
// 删除商品参数
export function deleteGoodsAttribute(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/goodsAttributeController/delete',{params})
}
//获取商品货品
export function getGoodsProduct(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/goodsProductController/getList',{params})
}
// 添加商品货品
export function createGoodsProduct(params){
    return axios.post('/api/ugoMall/goodsProductController/insert',params)
}
// 删除商品货品
export function deleteGoodsProduct(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/goodsProductController/delete',{params})
}
//更新商品货品
export function editGoodsProduct(params){
    return axios.post('/api/ugoMall/goodsProductController/update',params)
}

// 获取商品规格
export function getGoodsSpecification(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/goodsSpecificationController/getList',{params})
}
// 添加商品规格
export function createGoodsSpecification(params){
    return axios.post('/api/ugoMall/goodsSpecificationController/insert',params)
}
// 删除商品规格
export function deleteGoodsSpecification(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/goodsSpecificationController/delete',{params})
}
//商品上架
export function publishGoods(params){
    return axios.post('/api/ugoMall/goodsController/publishGoods',params)
}
//获取商品评论列表
export function getCommentList(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/commentController/getList',{params})
}
// 删除商品评论
export function deleteComment(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/commentController/deleteByBatch',{params})
}

//上传图片
export function createStorage(params){
    return axios.post('/api/file/upload',params)
}