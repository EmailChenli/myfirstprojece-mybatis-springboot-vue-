package com.eastcom.ripple.serviceinter;

import com.eastcom.ripple.util.EmployeeDistribute;
import com.eastcom.ripple.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeDistributeService {
    //根据id获取
    EmployeeDistribute findById(Long employeeDistributeId);
    //获取所有分配对象
    List<EmployeeDistribute> findAll(Page page);
    //根据对象id更新
    int updateEmployeeDistribute(EmployeeDistribute employeeDistribute);
    //根据关键字获取对象
    List<EmployeeDistribute> findEmployeeDistributeLike(EmployeeDistribute employeeDistribute);



}
