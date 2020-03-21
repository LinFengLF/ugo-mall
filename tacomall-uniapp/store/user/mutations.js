import * as types from './mutation-types'

export const mutations = {
    [types.SET_IS_LOGIN](state, b) {
        state.isLogin = b
    },
    [types.SET_SYNOPSIS](state, o) {
        state.synopsis = o
    }
}
