import axios from 'axios';

export function adminLogin(params){
    params._ = new Date().getTime();
    return axios.get('/api/ugoMall/login/doLogin',{params})
}