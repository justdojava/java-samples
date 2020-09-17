package com.shiro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.mapper.RoleMapper;
import com.shiro.model.Role;
import com.shiro.model.User;
import com.shiro.service.RoleService;
import com.shiro.service.UserRoleService;
import com.shiro.service.UserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mmzsblog.cn
 * @since 2020-07-30
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private UserService userService;

	@Override
	public Role getRoleByUserName(String username) {
		User user = userService.getUserByUsername(username);
		Integer roleId = userRoleService.getRoleIdByUserId(user.getId());
		Role role = roleMapper.selectById(roleId);
		return role;
	}

}
