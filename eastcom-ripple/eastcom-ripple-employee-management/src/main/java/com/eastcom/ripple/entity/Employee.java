package com.eastcom.ripple.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
	private Long employeeId;
	private String employeeName;
	private String employeeSex;
	private String employeePhone;
	private String employeeEmail;
	private String employeeEduSchool;
	private String employeeIdcard;
	private String employeeAddress;
	private int jobId;
	//预定次数
	private Integer reserveTimes;
	//个人电脑拥有数
	//private int own_com_sum
	private Timestamp createTime;
	private Timestamp modifyTime;
}
