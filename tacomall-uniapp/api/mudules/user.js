import { request } from '../../libs/request'

export default {
    login: (data) => {
        return request.post('api/user/login', data)
    }
}
