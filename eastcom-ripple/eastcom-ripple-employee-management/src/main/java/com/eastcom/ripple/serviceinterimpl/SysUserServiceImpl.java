package com.eastcom.ripple.serviceinterimpl;

import com.eastcom.ripple.entity.Role;
import com.eastcom.ripple.entity.SysUser;
import com.eastcom.ripple.mapper.SysUserMapper;
import com.eastcom.ripple.serviceinter.SysUserService;
import com.eastcom.ripple.util.Page;
import com.eastcom.ripple.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {
	@Autowired
	private SysUserMapper sysUserMapper;
	@Override
	public List<SysUser> findAll(Page page) {
		return sysUserMapper.findAll(page);
	}

	@Override
	public SysUser findById(Long sysUserId) {
		return sysUserMapper.findById(sysUserId);
	}

	@Override
	public List<SysUser> findLike(SysUser sysUser, Page page) {
		return sysUserMapper.findLike(sysUser,page);
	}

	@Override
	public int update(SysUser sysUser) {
		return sysUserMapper.update(sysUser);
	}

	@Override
	public SysUser findSysUser(SysUser sysUser) {
		return sysUserMapper.findSysUser(sysUser);
	}

	@Override
	public SysUser findSysUserInfo(String sysUserName) {
		return sysUserMapper.findSysUserInfo(sysUserName);
	}

	@Override
	public int updatePassword(PasswordUtil passwordUtil) {
		return sysUserMapper.updatePassword(passwordUtil);
	}

}
