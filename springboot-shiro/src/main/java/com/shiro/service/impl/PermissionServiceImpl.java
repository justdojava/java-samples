package com.shiro.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.mapper.PermissionMapper;
import com.shiro.model.Permission;
import com.shiro.service.PermissionService;
import com.shiro.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mmzsblog.cn
 * @since 2020-07-30
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
	@Autowired
	private PermissionMapper permissionMapper;
	@Autowired
	private RolePermissionService rolePermissionService;
	@Override
	public List<Permission> getPermissionsByRoleId(int roleid) {
		List<Integer> permissionIds = rolePermissionService.getPermissionIdsByRoleid(roleid);
		List<Permission> permissions = permissionMapper.selectBatchIds(permissionIds);
		return permissions;
	}

}
