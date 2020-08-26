package com.shiro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.mapper.UserMapper;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;

	@Override
	public User getUserByUsername(String username) {
		User user = userMapper.selectOne(new QueryWrapper<User>().eq("username", username));
		return user;
	}

}
