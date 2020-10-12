package com.eastcom.ripple.serviceinterimpl;

import com.eastcom.ripple.entity.EmployeeDimission;
import com.eastcom.ripple.mapper.EmployeeDimissionMapper;
import com.eastcom.ripple.serviceinter.EmployeeDimissionService;
import com.eastcom.ripple.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDimissionServiceImpl implements EmployeeDimissionService {
	@Autowired
	private EmployeeDimissionMapper employeeDimissionMapper;
	@Override
	public EmployeeDimission findById(Long employeeDimissionId) {
		// TODO Auto-generated method stub
		return employeeDimissionMapper.findById(employeeDimissionId);
	}

	@Override
	public List<EmployeeDimission> findAll(Page page) {
		// TODO Auto-generated method stub
		return employeeDimissionMapper.findAll(page);
	}


	@Override
	public List<EmployeeDimission> findDimissionEmployeeLike(EmployeeDimission employeeDimission) {
		return employeeDimissionMapper.findDimissionEmployeeLike(employeeDimission);
	}

	@Override
	public int addEmployeeDimission(EmployeeDimission employeeDimission) {
		return employeeDimissionMapper.addEmployeeDimission(employeeDimission);
	}

	@Override
	public int deleteDimissionEmployeeById(int employeeDimissionId) {
		return employeeDimissionMapper.deleteDimissionEmployeeById(employeeDimissionId);
	}

}
