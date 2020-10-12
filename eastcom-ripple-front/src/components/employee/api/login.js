import request from '../utils/request'

export function login(sysUser){
    return request({
        url: '/sys/login/sysuser',
        method: 'post',
        data:
            sysUser
    })
}


export function sysUserInfo(sysUserName){
    return request({
        url: '/sys/login/sysUserInfo',
        method: 'post',
        data:
            sysUserName
    })
}

export function changePassword(passwordUtil){
    return request({
        url: '/sys/login/changePassword',
        method: 'post',
        data:
            passwordUtil
    })
}


