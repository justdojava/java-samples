package com.shiro.service;

import com.shiro.model.Permission;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mmzsblog.cn
 * @since 2020-07-30
 */
public interface PermissionService extends IService<Permission> {
	List<Permission> getPermissionsByRoleId(int roleid);
}
