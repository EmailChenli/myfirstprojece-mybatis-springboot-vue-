package com.eastcom.ripple.common.vo.employee;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 批量查询员工姓名的视图包装类
 */
@Getter
@Setter
@ToString
public class BatchQueryEmpVO {
    //员工ID
    private Long employeeId;
    //员工姓名
    private String employeeName;
}
