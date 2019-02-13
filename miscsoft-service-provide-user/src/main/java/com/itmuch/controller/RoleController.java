package com.itmuch.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itmuch.mapper.RoleMapper;
import com.itmuch.model.Resource;
import com.itmuch.model.Role;
import com.itmuch.service.RoleService;
import com.itmuch.util.JSONResult;

import tk.mybatis.mapper.entity.Example;
@Controller
@RequestMapping("/role/")
public class RoleController {
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private Sid sid;
	
	@Autowired
	private RoleService roleSerive;
	
	@Autowired
	private Resource resource;
	
	@RequestMapping("add")
	@RequiresRoles(value="manager")
	@ResponseBody
	public JSONResult add(Role role) {
		String id = sid.nextShort();
		role.setId(id);
		roleMapper.insert(role);
		return JSONResult.ok(role);
	}
	
	@RequestMapping("listRole")
	@ResponseBody
	public JSONResult listRole(@RequestParam(name="page",defaultValue="0") int page,Role role) {
		Page<Role> obj = PageHelper.startPage(page,resource.getPagesize());
		
		Example example = new Example(Role.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(role.getRolename())) {
			criteria.andLike("rolename", "%"+role.getRolename()+"%");
		}
		List<Role> roles = roleMapper.selectByExample(example);
		return JSONResult.ok(roles, obj.getTotal());
	}
}
