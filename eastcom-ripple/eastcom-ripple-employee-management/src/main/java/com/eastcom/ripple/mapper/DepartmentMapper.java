package com.eastcom.ripple.mapper;


import com.eastcom.ripple.entity.Department;
import com.eastcom.ripple.entity.Employee;
import com.eastcom.ripple.util.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper 
public interface DepartmentMapper {
	//添加部门
	int addDepartment(@Param("department") Department department);
	//根据id删除
	int deleteDepartmentById(@Param("departmentId") int departmentId);
	//根据对象id更新
	int updateDepartmentById(@Param("department") Department department);
	//根据部门名查找部门
	Department findByName(@Param("departmentName") String departmentName);
	//根据id查询
	Department findDepartmentById(@Param("departmentId") int departmentId);
	//查询所有对象
	List<Department> findDepartmentAll(@Param("page")Page page);
	//根据关键字查询
	List<Department> findDepartmentLike(@Param("departmentId") int departmentId, @Param("departmentName") String departmentName);
}
