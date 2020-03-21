import config from '../config'

export const token = {
    set: (s) => {
        uni.setStorageSync(config.tokenKey, s)
    },
    get: () => {
        return uni.getStorageSync(config.tokenKey)
    },
    clear: () => {
        uni.removeStorageSync(config.tokenKey)
    }
}