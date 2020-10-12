package com.eastcom.ripple.mapper;

import com.eastcom.ripple.common.vo.employee.BatchQueryEmpVO;
import com.eastcom.ripple.entity.Employee;
import com.eastcom.ripple.util.Page;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface EmployeeMapper {
//	@Select("select *from  tb_employee where employee_id=#{employee_id}")

	//查询所有在职员工信息
	List<Employee> findAll(@Param("page")Page page);
	//根据id获取员工信息
	Employee findById(@Param("employeeId") Long employeeId);

	//根据对象id更新
	int updateEmployeeById(@Param("employee")Employee employee);
	//根据关键字获取对象,@Param("page")Page page
	List<Employee> findEmployeeLike(@Param("employee")Employee employee,@Param("page")Page page);
	//添加部门对象
	int addEmployee(@Param("employee")Employee employee);
	//根据id删除
	int deleteEmployeeById(@Param("employeeId") Long employeeId);

	//批量查询员工姓名
	@MapKey("employeeId")
	Map<Long, BatchQueryEmpVO> batchQueryEmpName(@Param("employeeIds") List<Long> employeeIds);

	//查询预订次数
	List<Employee> findReserveTimes(@Param("start")int start, @Param("rows")int rows);

	int findReserveTimesCount();

	Employee findReserveTimesByEmployeeId(@Param("employeeId") int employeeId);

	int updateReserveTimes(@Param("employeeId") int employeeId, @Param("reserveTimes") int reserveTimes);

}
