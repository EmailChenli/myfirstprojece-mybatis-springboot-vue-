package com.eastcom.ripple.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUser {
	private Long sysuserId;
	private String sysuserAccount;
	private String sysuserPassword;
	private int sysuserStatus;
	private int roleId ;
	private String roleName;
	private Timestamp createTime;
	private Timestamp modifyTime;
}
