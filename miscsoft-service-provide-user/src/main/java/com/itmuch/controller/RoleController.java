package com.itmuch.controller;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itmuch.mapper.RoleMapper;
import com.itmuch.model.Role;
import com.itmuch.util.JSONResult;
@Controller("/role/")
public class RoleController {
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private Sid sid;
	
	@RequestMapping("add")
	@ResponseBody
	public JSONResult add(Role role) {
		String id = sid.nextShort();
		role.setId(id);
		roleMapper.insert(role);
		return JSONResult.ok(role);
	}
}
