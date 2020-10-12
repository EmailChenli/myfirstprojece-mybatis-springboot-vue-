package com.eastcom.ripple.serviceinterimpl;

import com.eastcom.ripple.mapper.EmployeeDistributeMapper;
import com.eastcom.ripple.serviceinter.EmployeeDistributeService;
import com.eastcom.ripple.util.EmployeeDistribute;
import com.eastcom.ripple.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeDistributeServiceImpl implements EmployeeDistributeService {
    @Autowired
    private EmployeeDistributeMapper employeeDistributeMapper;
    @Override
    public EmployeeDistribute findById(Long employeeDistributeId) {
        return employeeDistributeMapper.findById(employeeDistributeId);
    }

    @Override
    public List<EmployeeDistribute> findAll(Page page) {
        return employeeDistributeMapper.findAll(page);
    }

    @Override
    public int updateEmployeeDistribute(EmployeeDistribute employeeDistribute) {
        return employeeDistributeMapper.updateEmployeeDistribute(employeeDistribute);
    }

    @Override
    public List<EmployeeDistribute> findEmployeeDistributeLike(EmployeeDistribute employeeDistribute) {
        return employeeDistributeMapper.findEmployeeDistributeLike(employeeDistribute);
    }
}
