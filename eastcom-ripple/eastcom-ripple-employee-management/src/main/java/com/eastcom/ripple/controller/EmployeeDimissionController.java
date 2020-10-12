package com.eastcom.ripple.controller;

import com.eastcom.ripple.entity.EmployeeDimission;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.ResultVO;
import com.eastcom.ripple.serviceinter.EmployeeDimissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys/employeedimission")
@Slf4j
public class EmployeeDimissionController {
	@Autowired
	private EmployeeDimissionService employeeDimissionService;
	
	@PostMapping("findall")
	public ResultVO<List<EmployeeDimission>> findEmployeeDimissionAll(@RequestBody Page page){

		try {
			log.info("All_EmployeeDimission!"+page.toString());
			page.setCurrentPage(page.getCurrentPage()-1);
			ResultVO<List<EmployeeDimission>> result = new ResultVO<List<EmployeeDimission>>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(employeeDimissionService.findAll(page));
			return result;
		}catch(Exception e){
			ResultVO<List<EmployeeDimission>> result = new ResultVO<List<EmployeeDimission>>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	} 
	
	@GetMapping("findbyid/{employeeDimissionId}")
	public ResultVO<EmployeeDimission> findEmployeeDimissionByid(@PathVariable("employeeDimissionId") Long employeeDimissionId){
		try {
			log.info("employeeDimission_ID:"+employeeDimissionId);
			ResultVO<EmployeeDimission> result = new ResultVO<EmployeeDimission>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(employeeDimissionService.findById(employeeDimissionId));
			return result;
		}catch(Exception e){
			ResultVO<EmployeeDimission> result = new ResultVO<EmployeeDimission>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}

	@PostMapping("findlike")
	public ResultVO<List<EmployeeDimission>> EmployeeDimissionFindlike(@RequestBody EmployeeDimission employeeDimission){

		try {
			log.info("EmployeeDimission_findlike:"+employeeDimission.getEmployeeDimissionId());
			ResultVO<List<EmployeeDimission>> result = new ResultVO<List<EmployeeDimission>>();
			result.setCode(200);
			result.setMsg("查询成功");
			result.setData(employeeDimissionService.findDimissionEmployeeLike(employeeDimission));
			return result;
		}catch(Exception e){
			ResultVO<List<EmployeeDimission>> result = new ResultVO<List<EmployeeDimission>>();
			result.setCode(444);
			result.setMsg("查询失败");
			return result;
		}
	}

}
