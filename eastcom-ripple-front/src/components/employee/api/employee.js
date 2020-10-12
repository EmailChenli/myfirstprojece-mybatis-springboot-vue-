import request from '../utils/request'

export function employeefindall(page){
  return request({
        url: '/sys/employee/findall',
        method: 'post',
        data:
            page
    })
}

export function employeefindbyid(employeeId){
    return request({
        url: '/sys/employee/findbyid/'+employeeId,
        method: 'get'
    })
}

export function employeeupdate(employee){
    return request({
        url: '/sys/employee/update',
        method: 'post',
        data:
            employee
    })
}

export function employeeadd(employee){
    return request({
        url: '/sys/employee/add',
        method: 'post',
        data:
            employee
    })
}

export function employeefindlike(employee,page){
    return request({
        url: '/sys/employee/findlike',
        method: 'post',
        data:
            {
                employee,
                page
            }
    })
}

export function employeedelete(employeeId){
    return request({
        url: '/sys/employee/delete/'+employeeId,
        method: 'get'
    })
}
