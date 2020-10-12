package com.eastcom.ripple.configurationbean;


import com.eastcom.ripple.serviceinter.*;
import com.eastcom.ripple.serviceinterimpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationBean {
	@Bean
	public EmployeeService employeeService(@Autowired EmployeeServiceImpl bean) {
		return bean;
	}
	
	@Bean
	public DepartmentService departmentService(@Autowired DepartmentServiceImpl bean) {
		return bean;
	}
	
	@Bean
	public JobService jobService(@Autowired JobServiceImpl bean) {
		return bean;
	}
	@Bean
	public SalaryService salaryService(@Autowired SalaryServiceImpl bean) {
		return bean;
	}
	@Bean
	public SysUserService sysUserService(@Autowired SysUserServiceImpl bean) {
		return bean;
	}
	@Bean
	public RoleService roleService(@Autowired RoleServiceImpl bean) {
		return bean;
	}
	@Bean
	public AccountService accountService(@Autowired AccountServiceImpl bean) {
		return bean;
	}

	@Bean
	public EmployeeDistributeService employeeDistributeService(@Autowired EmployeeDistributeServiceImpl bean) {
		return bean;
	}

}
