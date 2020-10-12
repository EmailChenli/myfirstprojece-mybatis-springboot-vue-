package com.eastcom.ripple.controller;

import com.eastcom.ripple.entity.SysUser;
import com.eastcom.ripple.util.BCrypt;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.PageSysUser;
import com.eastcom.ripple.util.ResultVO;
import com.eastcom.ripple.serviceinter.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sys/sysuser")
@Slf4j
public class SysUserController {
	@Autowired
	private SysUserService sysUserService;
	@PostMapping("findall")
	public ResultVO<List<SysUser>> findSysUserAll(@RequestBody Page page){
		log.info("All_SysUser!"+page);
		try {
			ResultVO<List<SysUser>> result = new ResultVO<List<SysUser>>();
			page.setCurrentPage(page.getCurrentPage()-1);
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(sysUserService.findAll(page));
			return result;
		}catch(Exception e){
			ResultVO<List<SysUser>> result = new ResultVO<List<SysUser>>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}


	@PostMapping("update")
	public ResultVO<Integer> findSysUserAll(@RequestBody SysUser sysUser){
		log.info("Update_SysUser!"+sysUser);

		try {
            ResultVO<Integer> result = new ResultVO<Integer>();
            sysUser.setSysuserPassword(BCrypt.hashpw(sysUser.getSysuserPassword(),BCrypt.gensalt(12)));
            result.setCode(200);
            result.setMsg("更新成功");
            result.setData(sysUserService.update(sysUser));
            return result;
		}catch(Exception e){
			ResultVO<Integer> result = new ResultVO<Integer>();
			result.setCode(444);
			result.setMsg("更新失败");
			return result;
		}
	}

	@PostMapping("findlike")
	public ResultVO<List<SysUser>> findRoleLike(@RequestBody PageSysUser<SysUser,Page> pageSysUser){
		try {
			log.info("All_Role!"+pageSysUser);
			Page page = pageSysUser.getPage();
			SysUser  sysUser = pageSysUser.getSysUser();
			page.setCurrentPage(page.getCurrentPage()-1);
			ResultVO<List<SysUser>> result = new ResultVO<>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(sysUserService.findLike(sysUser,page));
			return result;
		}catch(Exception e){
			ResultVO<List<SysUser>> result = new ResultVO<>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	}
	
	@GetMapping("findbyid/{sysuserId}")
	public ResultVO<?> findSysUserByid(@PathVariable("sysuserId") Long sysuserId){

		try {
			log.info("sysuser_id:"+sysuserId);
			ResultVO<SysUser> result = new ResultVO<SysUser>();
			result.setCode(200);
			result.setMsg("获取成功");
			result.setData(sysUserService.findById(sysuserId));
			return result;
		}catch(Exception e){
			ResultVO<SysUser> result = new ResultVO<SysUser>();
			result.setCode(444);
			result.setMsg("获取失败");
			return result;
		}
	} 
}
