package com.eastcom.ripple.serviceinter;

import com.eastcom.ripple.entity.Salary;

import java.util.List;

public interface SalaryService {
	//发现所有工资信息
	List<Salary> findAll();
	//查找指定工资信息表
	Salary findById(Long salaryId);
}
