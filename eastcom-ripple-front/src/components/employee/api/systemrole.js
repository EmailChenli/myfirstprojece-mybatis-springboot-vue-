import request from '../utils/request'

export function rolefindall(page){
    return request({
        url: '/sys/role/findall',
        method: 'post',
        data:
            page
    })
}

export function roleupdate(role){
    return request({
        url: '/sys/role/update',
        method: 'post',
        data:
            role
    })
}

export function rolefindlike(role,page){
    return request({
        url: '/sys/role/findlike',
        method: 'post',
        data:
            {
                role,
                page
            }
    })
}

