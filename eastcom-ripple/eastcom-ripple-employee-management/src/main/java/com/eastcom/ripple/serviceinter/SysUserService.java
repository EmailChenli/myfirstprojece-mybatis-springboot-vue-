package com.eastcom.ripple.serviceinter;

import com.eastcom.ripple.entity.Role;
import com.eastcom.ripple.entity.SysUser;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.PasswordUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserService {
	//查询所有系统用户
	List<SysUser> findAll(Page page);
	//根据id获取指定系统用户信息
	SysUser findById(Long sysUserId);
	//根据分页对象和用户对象去完成模糊查询
	List<SysUser> findLike(SysUser sysUser, Page page);
	//根据用户对象去进行更新
	int update(SysUser sysUser);
	//根据用户对象进行检索完成登录
	SysUser findSysUser(SysUser sysUser);
	//获取用户信息
	SysUser findSysUserInfo(String sysUserName);
	//修改密码
	int updatePassword(PasswordUtil passwordUtil);
}
