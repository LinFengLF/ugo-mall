import axios from 'axios';

export function listCollections(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/collectController/getCollectInfo',{params})
}