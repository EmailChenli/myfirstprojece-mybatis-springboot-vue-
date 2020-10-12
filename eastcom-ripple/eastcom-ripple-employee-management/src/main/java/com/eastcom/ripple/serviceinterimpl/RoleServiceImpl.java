package com.eastcom.ripple.serviceinterimpl;

import com.eastcom.ripple.entity.Role;
import com.eastcom.ripple.mapper.RoleMapper;
import com.eastcom.ripple.serviceinter.RoleService;
import com.eastcom.ripple.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Override
	public Role findById(Integer roleId) {
		return roleMapper.findById(roleId);
	}

	@Override
	public List<Role> findAll(Page page) {
		return roleMapper.findAll(page);
	}

	@Override
	public List<Role> findLike(Role role, Page page) {
		return roleMapper.findLike(role,page);
	}

	@Override
	public int updateRole(Role role) {
		return roleMapper.updateRole(role);
	}

}
