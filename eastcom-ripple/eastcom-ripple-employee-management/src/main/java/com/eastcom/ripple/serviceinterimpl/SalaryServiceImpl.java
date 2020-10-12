package com.eastcom.ripple.serviceinterimpl;

import com.eastcom.ripple.entity.Salary;
import com.eastcom.ripple.mapper.SalaryMapper;
import com.eastcom.ripple.serviceinter.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaryServiceImpl implements SalaryService {
	@Autowired
	private SalaryMapper salaryMapper;
	@Override
	public List<Salary> findAll() {
		return salaryMapper.findAll();
	}

	@Override
	public Salary findById(Long salaryId) {
		return salaryMapper.findById(salaryId);
	}

}
