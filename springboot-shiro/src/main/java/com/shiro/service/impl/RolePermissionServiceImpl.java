package com.shiro.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shiro.mapper.RolePermissionMapper;
import com.shiro.model.RolePermission;
import com.shiro.service.RolePermissionService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mmzsblog.cn
 * @since 2020-07-30
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
	@Autowired
	private RolePermissionMapper rolePermissionMapper;

	@Override
	public List<Integer> getPermissionIdsByRoleid(int roleid) {
		List<RolePermission> rolePermissions = rolePermissionMapper
				.selectList(new QueryWrapper<RolePermission>().eq("role_id", roleid));
		List<Integer> permissionIds = new ArrayList<>();
		for (RolePermission rolePermission : rolePermissions) {
			Integer permissionId = rolePermission.getId();
			permissionIds.add(permissionId);
		}
		return permissionIds;
	}

}
