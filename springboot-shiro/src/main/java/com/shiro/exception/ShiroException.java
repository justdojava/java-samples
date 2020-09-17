package com.shiro.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ShiroException {
	@ExceptionHandler(value = UnauthorizedException.class)
	@ResponseBody
	public String name() {
		return "没有权限";
	}
}
