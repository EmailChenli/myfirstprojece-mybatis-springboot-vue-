package com.eastcom.ripple.util;

import com.eastcom.ripple.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDistribute extends Employee {
    private String jobName;
    private String departmentName;
}
