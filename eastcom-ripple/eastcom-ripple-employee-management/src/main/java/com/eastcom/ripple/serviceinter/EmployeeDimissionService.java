package com.eastcom.ripple.serviceinter;

import com.eastcom.ripple.entity.EmployeeDimission;
import com.eastcom.ripple.util.Page;

import java.util.List;

public interface EmployeeDimissionService {
	EmployeeDimission findById(Long employeeDimissionId);
	List<EmployeeDimission> findAll(Page page);
	//根据关键字获取对象
	List<EmployeeDimission> findDimissionEmployeeLike(EmployeeDimission employeeDimission);
	//添加删除信息对象
	int addEmployeeDimission(EmployeeDimission employeeDimission);
	//根据id删除
	int deleteDimissionEmployeeById( int employeeDimissionId);
}
