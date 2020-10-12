package com.eastcom.ripple.controller;

import com.eastcom.ripple.entity.Role;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.PageRole;
import com.eastcom.ripple.util.ResultVO;
import com.eastcom.ripple.serviceinter.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys/role")
@Slf4j
public class RoleController {
	@Autowired
	private RoleService roleService;
	@PostMapping("findall")
	public ResultVO<List<Role>> findRoleAll(@RequestBody Page page){
		try {
			log.info("All_Role!"+page);
			page.setCurrentPage(page.getCurrentPage()-1);
			ResultVO<List<Role>> result = new ResultVO<List<Role>>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(roleService.findAll(page));
			return result;
		}catch(Exception e){
			ResultVO<List<Role>> result = new ResultVO<List<Role>>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}

	@PostMapping("findlike")
	public ResultVO<List<Role>> findRoleLike(@RequestBody PageRole<Role,Page> pageRole){
		try {
			log.info("All_Role!"+pageRole.getPage()+pageRole.getRole());
			Page page = pageRole.getPage();
			Role role = pageRole.getRole();
			page.setCurrentPage(page.getCurrentPage()-1);
			ResultVO<List<Role>> result = new ResultVO<List<Role>>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(roleService.findLike(role,page));
			return result;
		}catch(Exception e){
			ResultVO<List<Role>> result = new ResultVO<List<Role>>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}


	@GetMapping("findbyid/{role_id}")
	public ResultVO<Role> findRoleById(@PathVariable("roleId") Integer roleId){
		log.info("role_id:"+roleId);
		try {
			ResultVO<Role> result = new ResultVO<Role>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(roleService.findById(roleId));
			return result;
		}catch(Exception e){
			ResultVO<Role> result = new ResultVO<Role>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}

	@PostMapping("update")
	public ResultVO<Integer> roleUpdate(@RequestBody Role role){
		log.info("JOB_Update:"+role.getRoleName());
		try {
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(200);
			result.setMsg("更新成功");
			result.setData(roleService.updateRole(role));
			return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("更新失败");
			return result;
		}
	}
}
