package com.eastcom.ripple.common.entity.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	private Long accountId;
	private String accountName;
	private String accountPassword;
	private Long employeeId;
	private String employeeName;
	private Timestamp createTime;
	private Timestamp modifyTime;
}
