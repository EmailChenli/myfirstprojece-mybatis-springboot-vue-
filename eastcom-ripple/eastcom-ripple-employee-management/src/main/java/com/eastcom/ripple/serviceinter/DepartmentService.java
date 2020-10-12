package com.eastcom.ripple.serviceinter;

import com.eastcom.ripple.entity.Department;
import com.eastcom.ripple.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepartmentService {
	//根据id查询
	Department findById(int departmentId);
	//查询所有部门
	List<Department> findAll(Page page);
	//根据对象id更新
	int updateDepartmentById(Department department);
	//根据关键字获取对象
	List<Department> findDepartmentLike(int departmentId, String departmentName);
	//添加部门对象
	int addDepartment(Department department);
	//根据id删除
	int deleteDepartmentById(int departmentId);
	//根据部门名查找部门
	Department findByName(String departmentName);
}
