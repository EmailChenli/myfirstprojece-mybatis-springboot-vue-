package com.eastcom.ripple.mapper;

import com.eastcom.ripple.entity.Role;
import com.eastcom.ripple.entity.SysUser;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.PasswordUtil;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysUserMapper {
	SysUser findSysUser(@Param("sysUser")SysUser sysUser);
	List<SysUser> findAll(@Param("page") Page page);
	SysUser findById(@Param("sysuserId") Long sysuserId);
	List<SysUser> findLike(@Param("sysUser") SysUser sysUser, @Param("page") Page page);
	int update(@Param("sysUser") SysUser sysUser);
    SysUser findSysUserInfo(@Param("sysUserName")String sysUserName);
    int updatePassword(@Param("passwordUtil") PasswordUtil passwordUtil);
}
