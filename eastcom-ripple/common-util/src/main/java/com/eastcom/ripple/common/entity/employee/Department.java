package com.eastcom.ripple.common.entity.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
	private int departmentId;
	private String departmentName;
	private String departmentMsg;
	private Timestamp createTime;
	private Timestamp modifyTime;
}
