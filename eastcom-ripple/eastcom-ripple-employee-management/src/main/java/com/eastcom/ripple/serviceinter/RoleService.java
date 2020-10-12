package com.eastcom.ripple.serviceinter;

import com.eastcom.ripple.entity.Role;
import com.eastcom.ripple.util.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleService {
	Role findById(Integer roleId);
	List<Role> findAll(Page page);
	List<Role> findLike(Role role, Page page);
	int updateRole(Role role);
}
