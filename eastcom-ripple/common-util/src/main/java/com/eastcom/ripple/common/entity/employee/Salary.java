package com.eastcom.ripple.common.entity.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
	private Long salaryId;
	private int salaryNumber;//数目
	private int examineDays; //考勤天数
	private int attendentDays;//出勤天数
	private int salaryAttendent;//出勤天数
	private int salaryTaxable;//税后工资
	private int salaryIncomeTax;//个人所得税
	private int salaryReal; //实发工资
	private Long employeeId;
	private Timestamp createTime;
	private Timestamp modifyTime;
}
