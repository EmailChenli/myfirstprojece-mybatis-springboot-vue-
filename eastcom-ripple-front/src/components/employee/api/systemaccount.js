import request from '../utils/request'

export function sysuserfindall(page){
    return request({
        url: '/sys/sysuser/findall',
        method: 'post',
        data:
            page
    })
}


export function sysuserupdate(sysUser){
    return request({
        url: '/sys/sysuser/update',
        method: 'post',
        data:
            sysUser
    })
}


export function sysuserfindlike(sysUser,page){
    return request({
        url: '/sys/sysuser/findlike',
        method: 'post',
        data:
            {
                sysUser,
                page
            }
    })
}
