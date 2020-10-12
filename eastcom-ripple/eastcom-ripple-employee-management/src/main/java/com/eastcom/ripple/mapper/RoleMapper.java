package com.eastcom.ripple.mapper;

import com.eastcom.ripple.entity.Role;
import com.eastcom.ripple.util.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {
	List<Role> findAll(@Param("page")Page page);
	Role findById(@Param("roleId") Integer roleId);
	List<Role> findLike(@Param("role") Role role,@Param("page") Page page);
	int updateRole(@Param("role") Role role);
}
