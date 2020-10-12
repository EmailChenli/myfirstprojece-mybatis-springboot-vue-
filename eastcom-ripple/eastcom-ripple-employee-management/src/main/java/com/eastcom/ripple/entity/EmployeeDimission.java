package com.eastcom.ripple.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDimission {
	// 可以将之前担任的职位和部门添加
	private int jobId;
	private Long employeeDimissionId;
	private String employeeDimissionName;
	private String employeeDimissionSex;
	private String employeeDimissionPhone;
	private String employeeDimissionEmail;
	private String employeeDimissionEduSchool;
	private String employeeDimissionIdcard;
	private String employeeDimissionAddress;
	private String jobName;
	private String departmentName;
	private Timestamp createTime;
	private Timestamp modifyTime;
}
