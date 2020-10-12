package com.eastcom.ripple.serviceinterimpl;

import com.eastcom.ripple.common.vo.employee.BatchQueryEmpVO;
import com.eastcom.ripple.entity.Employee;
import com.eastcom.ripple.mapper.EmployeeMapper;
import com.eastcom.ripple.serviceinter.EmployeeService;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("employeeServiceImpl")
public class EmployeeServiceImpl implements EmployeeService {
	//没有注入bean对象获取空指针
	@Autowired
	private EmployeeMapper employeeMapper;
	@Override
	public Employee findById(Long employeeId) {
		return employeeMapper.findById(employeeId);
	}

	@Override
	public List<Employee> findAll(Page page) {
		return employeeMapper.findAll(page);
	}

	@Override
	public int updateEmployeeById(Employee employee) {
		return employeeMapper.updateEmployeeById(employee);
	}

	@Override
	public List<Employee> findEmployeeLike(Employee employee,Page page) {
		return employeeMapper.findEmployeeLike(employee,page);
	}

	@Override
	public int addEmployee(Employee employee) {
		return employeeMapper.addEmployee(employee);
	}

	@Override
	public int deleteEmployeeById(Long employeeId) {
		return employeeMapper.deleteEmployeeById(employeeId);
	}

	/**
	 * 根据员工id集合批量查询员工姓名
	 * @param employeeIds
	 * @return
	 */
	@Override
	public Map<Long, BatchQueryEmpVO> batchQueryEmpName(List<Long> employeeIds) {
		return employeeMapper.batchQueryEmpName(employeeIds);
	}

	@Override
	public ResultVO<Map<String, Object>> findReserveTimes(int page, int rows) {
		try{
			int count = employeeMapper.findReserveTimesCount();
			//分页操作
			int start=(page-1)*rows;//起始位置
			List<Employee> list = employeeMapper.findReserveTimes(start, rows);
			ResultVO<Map<String, Object>> result = new ResultVO<>();
			result.setCode(200);
			result.setMsg("查询成功");
			Map<String,Object> map = new HashMap<>();
			map.put("count",count);
			map.put("list",list);
			result.setData(map);
			return result;
		}catch(Exception e){
			ResultVO<Map<String, Object>> result = new ResultVO<>();
			result.setCode(444);
			result.setMsg("查询失败");
			return result;
		}

	}

	@Override
	public ResultVO<Integer> updateReserveTimes(int employeeId) {
		try{
			Employee employee = employeeMapper.findReserveTimesByEmployeeId(employeeId);
			int reserveTimes = employee.getReserveTimes();
			reserveTimes++;
			int count = employeeMapper.updateReserveTimes(employeeId,reserveTimes);
			ResultVO<Integer> result = new ResultVO<>();
			result.setCode(200);
			result.setMsg("查询成功");
			result.setData(count);
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<>();
			result.setCode(444);
			result.setMsg("查询失败");
			return result;
		}
	}

}
