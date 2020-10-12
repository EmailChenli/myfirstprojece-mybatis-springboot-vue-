package com.eastcom.ripple.common.entity.employee;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {
	private int roleId;
	private String roleName;
	private Timestamp createTime;
	private Timestamp modifyTime;
}
