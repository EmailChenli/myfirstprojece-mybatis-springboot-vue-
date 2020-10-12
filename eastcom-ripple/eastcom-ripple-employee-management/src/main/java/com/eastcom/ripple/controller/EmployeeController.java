package com.eastcom.ripple.controller;

import com.eastcom.ripple.common.vo.employee.BatchQueryEmpVO;
import com.eastcom.ripple.entity.Employee;
import com.eastcom.ripple.entity.EmployeeDimission;
import com.eastcom.ripple.serviceinter.EmployeeDimissionService;
import com.eastcom.ripple.serviceinter.EmployeeDistributeService;
import com.eastcom.ripple.serviceinter.EmployeeService;
import com.eastcom.ripple.util.EmployeeDistribute;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.PageEmployee;
import com.eastcom.ripple.util.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("sys/employee")
@Slf4j
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private EmployeeDimissionService employeeDimissionService;

	@Autowired
	private EmployeeDistributeService employeeDistributeService;



	@PostMapping("findall")
	public ResultVO<List<Employee>> findEmployeeAll(@RequestBody  Page page){
		try {
			//
			log.info("All_employee!"+page.toString());
			page.setCurrentPage(page.getCurrentPage()-1);
			ResultVO<List<Employee>> result = new ResultVO<List<Employee>>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(employeeService.findAll(page));
			return result;
		}catch(Exception e){
			ResultVO<List<Employee>> result = new ResultVO<List<Employee>>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}

	@GetMapping("findbyid/{employeeId}")
	public ResultVO<?> findEmployeeById(@PathVariable("employeeId") Long employeeId){
		log.info("employee_ID:"+employeeId);
		try {
			ResultVO<Employee> result = new ResultVO<Employee>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(employeeService.findById(employeeId));
			return result;
		}catch(Exception e){
			ResultVO<Employee> result = new ResultVO<Employee>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}

	@GetMapping("delete/{employeeId}")
	@Transactional
	public ResultVO<Integer> deleteEmployeeById(@PathVariable("employeeId") Long employeeId){
		log.info("employee_ID:"+employeeId);

		try {
			EmployeeDistribute employeeDistribute=employeeDistributeService.findById(employeeId);
			EmployeeDimission employeeDimission = new EmployeeDimission();
			employeeDimission.setEmployeeDimissionId(employeeId);
			employeeDimission.setEmployeeDimissionName(employeeDistribute.getEmployeeName());
			employeeDimission.setEmployeeDimissionSex(employeeDistribute.getEmployeeSex());
			employeeDimission.setEmployeeDimissionAddress(employeeDistribute.getEmployeeAddress());
			employeeDimission.setEmployeeDimissionPhone(employeeDistribute.getEmployeePhone());
			employeeDimission.setEmployeeDimissionEmail(employeeDistribute.getEmployeeEmail());
			employeeDimission.setEmployeeDimissionEduSchool(employeeDistribute.getEmployeeEduSchool());
			employeeDimission.setEmployeeDimissionIdcard(employeeDistribute.getEmployeeIdcard());
			employeeDimission.setJobName(employeeDistribute.getJobName());
			employeeDimission.setDepartmentName(employeeDistribute.getDepartmentName());
			employeeDimissionService.addEmployeeDimission(employeeDimission);
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(200);
			result.setMsg("删除成功");
			result.setData(employeeService.deleteEmployeeById(employeeId));
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("删除失败");
			return result;
		}
	}

	@PostMapping("update")
	public ResultVO<Integer> employeeUpdate(@RequestBody Employee employee){
		try {
			log.info("Employee_Update:"+employee.getEmployeeId());
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(200);
			result.setMsg("更新成功");
			result.setData(employeeService.updateEmployeeById(employee));
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("更新失败");
			return result;
		}
	}

	@PostMapping("findlike")
	public ResultVO<List<Employee>> employeeFindlike(@RequestBody PageEmployee<Employee,Page> pageEmployee){
		log.info("Employee_findlike:"+pageEmployee.getEmployee()+pageEmployee.getPage());

		try {
			Employee employee=pageEmployee.getEmployee();
			Page page = pageEmployee.getPage();
			page.setCurrentPage(page.getCurrentPage()-1);
			ResultVO<List<Employee>> result = new ResultVO<List<Employee>>();
			result.setCode(200);
			result.setMsg("查询成功");
			result.setData(employeeService.findEmployeeLike(employee,page));
			return result;
		}catch(Exception e){
			ResultVO<List<Employee>> result = new ResultVO<List<Employee>>();
			result.setCode(444);
			result.setMsg("查询失败");
			return result;
		}
	}


	@PostMapping("add")
	public ResultVO<Integer> DepartmentAdd(@RequestBody Employee employee){
		try {
			log.info("Employee_add"+employee.getEmployeeName());
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(200);
			result.setMsg("添加成功");
			result.setData(employeeService.addEmployee(employee));
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("添加失败");
			return result;
		}
	}

	@PostMapping("batchQueryEmpName")
	public ResultVO<Map<Long, BatchQueryEmpVO>> batchQueryEmpName(@RequestBody List<Long> employeeIds){
		ResultVO<Map<Long, BatchQueryEmpVO>> result = new ResultVO<>();
		if (employeeIds != null && !employeeIds.isEmpty()){
			Map<Long, BatchQueryEmpVO> empVOMap = employeeService.batchQueryEmpName(employeeIds);
			result.setCode(200);
			result.setMsg("查询成功");
			result.setData(empVOMap);
			return result;
		}
		result.setCode(444);
		result.setMsg("查询失败");
		return result;
	}

	@PostMapping("/findReserveTimes/{page}/{rows}")
	public ResultVO<Map<String,Object>> findReserveTimes(@PathVariable int page,
														 @PathVariable int rows){
		log.info("查询预订次数，页数：" + page + "，行数：" + rows);
		return employeeService.findReserveTimes(page, rows);
	}

	@PostMapping("/updateReserveTimes/{employeeId}")
	public ResultVO<Integer> updateReserveTimes(@PathVariable int employeeId){
		log.info("更新预订次数，employeeId：" + employeeId);
		return employeeService.updateReserveTimes(employeeId);
	}
}
