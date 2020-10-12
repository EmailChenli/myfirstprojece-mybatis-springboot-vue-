package com.eastcom.ripple.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Job {
	private int jobId;
	private String jobName;
	private String jobMsg;
	private int departmentId;
	private Timestamp createTime;
	private Timestamp modifyTime;
}
