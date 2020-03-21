import axios from 'axios';

export function getUserInfo(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/userController/getUserInfo',{params})
}
export function deleteUser(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/userController/deleteByBatch',{params})
}
export function insertUser(params){
    return axios.post('/api/ugoMall/userController/insert',params)
}
export function updateUserInfo(params){
    return axios.post('/api/ugoMall/userController/updateUserInfo',params)
}
export function getAddressInfo(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/addressController/getList',{params})
}
export function indexData(){
    // params._ = new Date().getTime();
    return axios.post('/api/ugoMall/indexController/indexData')
}
export function getFeedBackList(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/feedbackController/getList',{params})
}

