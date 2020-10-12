package com.eastcom.ripple.controller;

import com.eastcom.ripple.serviceinter.DepartmentService;
import com.eastcom.ripple.serviceinter.EmployeeDistributeService;
import com.eastcom.ripple.serviceinter.JobService;
import com.eastcom.ripple.util.EmployeeDistribute;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys/employeedistribute")
@Slf4j
public class EmployeeDistributeController {
    @Autowired
    private EmployeeDistributeService employeeDistributeService;
    @Autowired
    private JobService jobService;
    @Autowired
    private DepartmentService departmentService;

    @GetMapping("findbyid/{employeeDistributeId}")
    public ResultVO<EmployeeDistribute> findEmployeeDistributeById(@PathVariable("employeeDistributeId") Long employeeDistributeId){
        try{
            log.info("EmployeeDistributeId::"+employeeDistributeId);
            ResultVO<EmployeeDistribute> result = new ResultVO<EmployeeDistribute>();
            result.setCode(200);
            result.setMsg("查询成功");
            result.setData(employeeDistributeService.findById(employeeDistributeId));
            return result;
        }catch(Exception e){
            ResultVO<EmployeeDistribute> result = new ResultVO<EmployeeDistribute>();
            result.setCode(404);
            result.setMsg("查询失败");
            result.setData(null);
            return result;
        }
    }

    @PostMapping("findall")
    public ResultVO<List<EmployeeDistribute>> findEmployeeDistributeAll(@RequestBody Page page){
        log.info("EmployeeDistributeAll!");
        try{
            log.info("EmployeeDistributeAll!");
            page.setCurrentPage(page.getCurrentPage()-1);
            ResultVO<List<EmployeeDistribute>> result = new ResultVO<List<EmployeeDistribute>>();
            result.setCode(200);
            result.setMsg("查询成功");
            result.setData(employeeDistributeService.findAll(page));
            return result;
        }catch(Exception e){
            ResultVO<List<EmployeeDistribute>> result = new ResultVO<List<EmployeeDistribute>>();
            result.setCode(444);
            result.setMsg("查询失败");
            result.setData(null);
            return result;
        }
    }
    @PostMapping("findlike")
    public ResultVO<List<EmployeeDistribute>> employeeDistributeFindlike(@RequestBody EmployeeDistribute employeeDistribute){
        log.info("EmployeeDimission_findlike:"+employeeDistribute.getEmployeeId());
        try {
            ResultVO<List<EmployeeDistribute>> result = new ResultVO<List<EmployeeDistribute>>();
            result.setCode(200);
            result.setMsg("查询成功");
            result.setData(employeeDistributeService.findEmployeeDistributeLike(employeeDistribute));
            return result;
        }catch(Exception e){
            ResultVO<List<EmployeeDistribute>> result = new ResultVO<List<EmployeeDistribute>>();
            result.setCode(444);
            result.setMsg("查询失败");
            return result;
        }
    }
    @PostMapping("update")
    public ResultVO<Integer> employeeDistributeUpdate(@RequestBody EmployeeDistribute employeeDistribute){
        log.info("Employee_Update:"+employeeDistribute);
        try {
           if(departmentService.findByName(employeeDistribute.getDepartmentName()) != null){
               if(jobService.findByName(employeeDistribute.getJobName(),departmentService.findByName(employeeDistribute.getDepartmentName()).getDepartmentId()) == null){
                   ResultVO<Integer> result = new ResultVO<Integer>();
                   result.setCode(200);
                   result.setMsg("职位不存在，更新失败");
                   return result;
               }else{
                   ResultVO<Integer> result = new ResultVO<Integer>();
                   result.setCode(200);
                   result.setMsg("更新成功");
                   employeeDistribute.setJobId(jobService.findByName(employeeDistribute.getJobName(),departmentService.findByName(employeeDistribute.getDepartmentName()).getDepartmentId()).getJobId());
                   result.setData(employeeDistributeService.updateEmployeeDistribute(employeeDistribute));
                   return result;
               }
           } else{
               ResultVO<Integer> result = new ResultVO<Integer>();
               result.setCode(200);
               result.setMsg("部门不存在，更新失败");
               return result;
           }

        }catch(Exception e){
            ResultVO<Integer> result = new ResultVO<Integer>();
            result.setCode(444);
            result.setMsg("更新失败");
            return result;
        }
    }
}
