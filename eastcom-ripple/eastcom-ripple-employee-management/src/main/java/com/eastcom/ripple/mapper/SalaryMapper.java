package com.eastcom.ripple.mapper;

import com.eastcom.ripple.entity.Salary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SalaryMapper {
	List<Salary> findAll();
	Salary findById(@Param("salaryId") Long salaryId);
}
