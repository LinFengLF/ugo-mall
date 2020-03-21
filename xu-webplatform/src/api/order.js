import axios from 'axios';

export function orderList(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/orderController/getList',{params})
}
export function update(params){
    // params._ = new Date().getTime();
    return axios.post('/api/ugoMall/orderController/update',params)
}
export function getChannels(){
    // params._ = new Date().getTime();
    return axios.post('/api/ugoMall/logisticsController/getList')
}
export function ship(params){
    // params._ = new Date().getTime();
    return axios.post('/api/ugoMall/orderController/ship',params)
}
export function selectOne(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/orderController/selectOne',{params})
}
