package com.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author mmzsblog.cn
 * @since 2020-07-30
 */
@RestController
public class UserController {
	@PostMapping("login")
	public String name(String username, String password) {
		String result = "已登录";
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		if (!currentUser.isAuthenticated()) {
			try {
				currentUser.login(token);// 会触发com.shiro.config.MyShiroRealm的doGetAuthenticationInfo方法
				result = "登录成功";
			} catch (UnknownAccountException e) {
				result = "用户名错误";
			} catch (IncorrectCredentialsException e) {
				result = "密码错误";
			}
		}
		return result;
	}

	@GetMapping("logout")
	public void logout() {
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
	}

	@RequiresPermissions("role:update")
	@GetMapping("/role")
	public String name() {
		return "hello";
	}

	@RequiresPermissions("user:select")
	@GetMapping("/role2")
	public String permission() {
		return "hello sel";
	}

}
