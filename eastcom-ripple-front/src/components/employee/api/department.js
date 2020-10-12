import request from '../utils/request'

export function departmentfindall(page){
    return request({
        url: '/sys/department/findall',
        method: 'post',
        data:
            page
    })
}

export function departmentfindbyid(departmentId){
    return request({
        url: '/sys/department/findbyid/'+departmentId,
        method: 'get'
    })
}

export function departmentdeletebyid(departmentId){
    return request({
        url: '/sys/department/delete/'+departmentId,
        method: 'get'
    })
}

export function departmentupdate(department){
    return request({
        url: '/sys/department/update',
        method: 'post',
        data:
            department
    })
}

export function departmentadd(department){
    return request({
        url: '/sys/department/add',
        method: 'post',
        data:
            department
    })
}

export function departmentfindlike(department){
    return request({
        url: '/sys/department/findlike',
        method: 'post',
        data:
            department
    })
}




