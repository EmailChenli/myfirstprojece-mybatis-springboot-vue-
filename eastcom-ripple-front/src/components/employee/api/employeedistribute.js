import request from '../utils/request'

export function employeeDistributefindById(employeeDistributeId){
    return request({
        url: '/sys/employeedistribute/findbyid/'+employeeDistributeId,
        method: 'get'
    })
}



export function  employeeDistributefindall(page){
        return request({
            url: '/sys/employeedistribute/findall',
            method: 'post',
            data:
                page
        })
}

export function  employeeDistributeupdate(employeeDistribute){
    return request({
        url: '/sys/employeedistribute/update',
        method: 'post',
        data:
            employeeDistribute
    })
}

export function  employeeDistributefindlike(employeeDistribute){
    return request({
        url: '/sys/employeedistribute/findlike',
        method: 'post',
        data:
            employeeDistribute
    })
}


