package com.shiro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.mapper.UserRoleMapper;
import com.shiro.model.UserRole;
import com.shiro.service.UserRoleService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mmzsblog.cn
 * @since 2020-07-30
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {
	@Autowired
	private UserRoleMapper userRoleMapper;

	@Override
	public Integer getRoleIdByUserId(Integer userId) {
		UserRole userRole = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("user_id", userId));
		return userRole.getRoleId();
	}

}
