package com.itmuch.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.cloud.model.User;

public class CoreController {
	protected User getCurrentUser() {
		Subject subject = SecurityUtils.getSubject();
		return  (User) subject.getPrincipal();
	}
}
