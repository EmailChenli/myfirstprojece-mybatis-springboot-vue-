package com.eastcom.ripple.serviceinterimpl;

import com.eastcom.ripple.entity.Department;
import com.eastcom.ripple.mapper.DepartmentMapper;
import com.eastcom.ripple.serviceinter.DepartmentService;
import com.eastcom.ripple.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
	@Autowired
	private DepartmentMapper departmentMapper;
	@Override
	public Department findById(int departmentId) {
		return departmentMapper.findDepartmentById(departmentId);
	}

	@Override
	public List<Department> findAll(Page page) {
		return departmentMapper.findDepartmentAll(page);
	}

	@Override
	public int updateDepartmentById(Department department) {
		return departmentMapper.updateDepartmentById(department);
	}

	@Override
	public List<Department> findDepartmentLike(int departmentId, String departmentName) {
		return departmentMapper.findDepartmentLike(departmentId, departmentName);
	}

	@Override
	public int addDepartment(Department department) {
		return departmentMapper.addDepartment(department);
	}

	@Override
	public int deleteDepartmentById(int departmentId) {
		return departmentMapper.deleteDepartmentById(departmentId);
	}

	@Override
	public Department findByName(String departmentName) {
		return departmentMapper.findByName(departmentName);
	}

}
