package com.eastcom.ripple.mapper;


import com.eastcom.ripple.entity.Employee;
import com.eastcom.ripple.entity.EmployeeDimission;
import com.eastcom.ripple.util.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeDimissionMapper {
	//获取所有删除信息
	List<EmployeeDimission> findAll(@Param("page")Page page);
	//获取所有id信息
	EmployeeDimission findById(@Param("employeeDimissionId") Long employeeDimissionId);
	//根据关键字获取对象
	List<EmployeeDimission> findDimissionEmployeeLike(@Param("employeeDimission")EmployeeDimission employeeDimission);
	//添加删除信息对象
	int addEmployeeDimission(@Param("employeeDimission")EmployeeDimission employeeDimission);
	//根据id删除
	int deleteDimissionEmployeeById(@Param("employeeDimissionId") int employeeDimissionId);
}
