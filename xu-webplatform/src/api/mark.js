import axios from 'axios';

export function listRegion(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/regionController/getList',{params})
}

export function listCategory(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/categoryController/getList',{params})
}

export function deleteCategory(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/categoryController/deleteByBatch',{params})
}

export function createCategory(params){
    return axios.post('/api/ugoMall/categoryController/insert',params)
}

export function editCategory(params){
    return axios.post('/api/ugoMall/categoryController/update',params)
}

export function getCategoryList(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/categoryController/getCategoryList',{params})
}