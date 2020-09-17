package com.shiro.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.shiro.model.Permission;
import com.shiro.model.Role;
import com.shiro.model.User;
import com.shiro.service.PermissionService;
import com.shiro.service.RoleService;
import com.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyShiroRealm extends AuthorizingRealm {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService permissionService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		// HttpServletRequest request = (HttpServletRequest) ((WebSubject) SecurityUtils
		// .getSubject()).getServletRequest();//这个可以用来获取在登录的时候提交的其他额外的参数信息
		String username = (String) principals.getPrimaryPrincipal();
		// 受理权限
		// 角色
		Set<String> roles = new HashSet<String>();
		Role role = roleService.getRoleByUserName(username);
		System.out.println(role.getRoleName());
		roles.add(role.getRoleName());
		authorizationInfo.setRoles(roles);
		// 权限
		Set<String> permissions = new HashSet<String>();
		List<Permission> querypermissions = permissionService.getPermissionsByRoleId(role.getId());
		for (Permission permission : querypermissions) {
			permissions.add(permission.getPermissionName());
		}
		authorizationInfo.setStringPermissions(permissions);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		String loginName = (String) authcToken.getPrincipal();
		// 获取用户密码
		User user = userService.getOne(new QueryWrapper<User>().eq("username", loginName));
		if (user == null) {
			// 没找到帐号
			throw new UnknownAccountException();
		}
		String password = new String((char[]) authcToken.getCredentials());
		String inpass = (new Md5Hash(password, user.getUsername())).toString();
		if (!user.getPassword().equals(inpass)) {
			throw new IncorrectCredentialsException();
		}
		// 交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(loginName, user.getPassword(),
				ByteSource.Util.bytes(loginName), getName());

		return authenticationInfo;
	}

}
