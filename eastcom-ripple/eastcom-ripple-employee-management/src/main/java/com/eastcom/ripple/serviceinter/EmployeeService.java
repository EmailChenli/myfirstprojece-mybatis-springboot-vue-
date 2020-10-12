package com.eastcom.ripple.serviceinter;

import com.eastcom.ripple.common.vo.employee.BatchQueryEmpVO;
import com.eastcom.ripple.entity.Employee;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.ResultVO;

import java.util.List;
import java.util.Map;

public interface EmployeeService {
	//根据id获取员工对象
	Employee findById(Long employeeId);
	//获取所有员工对象
	List<Employee> findAll(Page page);
	//根据对象id更新
	int updateEmployeeById(Employee employee);
	//根据关键字获取对象
	List<Employee> findEmployeeLike(Employee employee,Page page);
	//添加员工对象
	int addEmployee(Employee employee);
	//根据id删除
	int deleteEmployeeById(Long employeeId);
	//批量查询员工姓名
	Map<Long, BatchQueryEmpVO> batchQueryEmpName(List<Long> employeeIds);

	ResultVO<Map<String, Object>> findReserveTimes(int page, int rows);

	ResultVO<Integer> updateReserveTimes(int employeeId);
}
