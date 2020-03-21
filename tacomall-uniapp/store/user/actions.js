import * as types from './mutation-types'
import api from '../../api'
import {token} from '../../utils/token'

export const actions = {
    login: ({dispatch}, o) => {
        return new Promise((resolve) => {
            uni.login({
                provider: 'weixin',
                success: res => {
                    api.user.login({
                        iv: o.iv,
                        code: res.code,
                        encryptedData: o.encryptedData
                    }).then(resp => {
                        if (resp.ok) {
                            uni.showToast({
                                icon: 'success',
                                title: '登陆成功',
                                duration: 2000
                            });
                            token.set(resp.data)
                            dispatch('synopsis')
                            resolve()
                        }
                    })
                }
            })
        })
    },
    synopsis: ({commit}) => {
        commit(types.SET_IS_LOGIN, true)
        api.user.synopsis().then(resp => {
            if (resp.ok) {
                commit(types.SET_SYNOPSIS, resp.data)
            }
        })
    },
    exit: ({commit}) => {
        commit(types.SET_IS_LOGIN, false)
        commit(types.SET_SYNOPSIS, {})
        uni.showToast({
            icon: 'success',
            title: '注销成功',
            duration: 2000
        });
    }
}
