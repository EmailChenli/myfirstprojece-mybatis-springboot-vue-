package com.eastcom.ripple.mapper;

import com.eastcom.ripple.entity.Department;
import com.eastcom.ripple.entity.EmployeeDimission;
import com.eastcom.ripple.util.EmployeeDistribute;
import com.eastcom.ripple.util.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmployeeDistributeMapper {
    //根据id获取
    EmployeeDistribute findById(@Param("employeeDistributeId") Long employeeDistributeId);
    //查询所有
    List<EmployeeDistribute> findAll(@Param("page")Page page);
    //根据对象id更新
    int updateEmployeeDistribute(@Param("employeeDistribute") EmployeeDistribute employeeDistribute);
    //根据关键字获取对象
    List<EmployeeDistribute> findEmployeeDistributeLike(@Param("employeeDistribute")EmployeeDistribute employeeDistribute);

}
