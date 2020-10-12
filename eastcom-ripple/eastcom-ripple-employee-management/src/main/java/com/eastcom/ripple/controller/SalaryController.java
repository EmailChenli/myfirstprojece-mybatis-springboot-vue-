package com.eastcom.ripple.controller;

import com.eastcom.ripple.entity.Salary;
import com.eastcom.ripple.util.ResultVO;
import com.eastcom.ripple.serviceinter.SalaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("sys/salary")
@Slf4j
public class SalaryController {
	@Autowired
	private SalaryService salaryService;
	@GetMapping("findall")
	public ResultVO<List<Salary>> findSalaryAll(){

		try {
			log.info("All_Salary!");
			ResultVO<List<Salary>> result = new ResultVO<List<Salary>>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(salaryService.findAll());
			return result;
		}catch(Exception e){
			ResultVO<List<Salary>> result = new ResultVO<List<Salary>>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	} 
	
	@GetMapping("findbyid/{salary_id}")
	public ResultVO<?> findSalaryByid(@PathVariable("salaryId") Long salaryId){

		try {
			log.info("salary_ID:"+salaryId);
			ResultVO<Salary> result = new ResultVO<Salary>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(salaryService.findById(salaryId));
			return result;
		}catch(Exception e){
			ResultVO<Salary> result = new ResultVO<Salary>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	} 
}
