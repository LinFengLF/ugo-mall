import axios from 'axios';

export function adList(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/adController/getAdInfo',{params})
}
export function deleteAd(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/adController/deleteByBatch',{params})
}
export function createAd(params){
    return axios.post('/api/ugoMall/adController/insert',params)
}
export function editAd(params){
    return axios.post('/api/ugoMall/adController/update',params)
}