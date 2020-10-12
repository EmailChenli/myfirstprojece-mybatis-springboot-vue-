package com.eastcom.ripple.controller;

import com.eastcom.ripple.entity.Department;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.ResultVO;
import com.eastcom.ripple.serviceinter.DepartmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("sys/department")
@RestController
@Slf4j
public class DepartmentController {
	@Autowired
	private DepartmentService departmentService;
	
	@PostMapping("findall")
	public ResultVO<List<Department>> findDepartmentAll(@RequestBody Page page){
		try {
			log.info("findAll_department!");
			page.setCurrentPage(page.getCurrentPage()-1);
			ResultVO<List<Department>> result = new ResultVO<List<Department>>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(departmentService.findAll(page));
			return result;
		}catch(Exception e){
			ResultVO<List<Department>> result = new ResultVO<List<Department>>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	} 
	
	@GetMapping("findbyid/{departmentId}")
	public ResultVO<?> findDepartmentByid(@PathVariable("departmentId") int departmentId){
		try {
			log.info("By_Department_ID:"+departmentId);
			ResultVO<Department> result = new ResultVO<Department>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(departmentService.findById(departmentId));
			return result;
		}catch(Exception e){
			ResultVO<Department> result = new ResultVO<Department>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}

	@GetMapping("delete/{departmentId}")
	public ResultVO<Integer> deleteDepartmentByid(@PathVariable("departmentId") int departmentId){
		try {
			log.info("By_Department_ID:"+departmentId);
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(departmentService.deleteDepartmentById(departmentId));
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}

	@PostMapping("update")
	public ResultVO<Integer> departmentUpdate(@RequestBody Department department){
		try {
			log.info("Department_Update:"+departmentService.updateDepartmentById(department));
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(200);
			result.setMsg("更新成功");
			result.setData(departmentService.updateDepartmentById(department));
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("更新失败");
			return result;
		}
	}

	@PostMapping("findlike")
	public ResultVO<List<Department>> departmentFindlike(@RequestBody Department department){
		try {
			log.info("Department_findlike:"+department.getDepartmentId());
			ResultVO<List<Department>> result = new ResultVO<List<Department>>();
			result.setCode(200);
			result.setMsg("查询成功");
			result.setData(departmentService.findDepartmentLike(department.getDepartmentId(), department.getDepartmentName()));
			return result;
		}catch(Exception e){
			ResultVO<List<Department>> result = new ResultVO<List<Department>>();
			result.setCode(444);
			result.setMsg("查询失败");
			return result;
		}
	}


	@PostMapping("add")
	public ResultVO<Integer> departmentAdd(@RequestBody Department department){
		try {
			log.info("Department_add"+department.getDepartmentName());
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(200);
			result.setMsg("添加成功");
			result.setData(departmentService.addDepartment(department));
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("添加失败");
			return result;
		}
	}

}
