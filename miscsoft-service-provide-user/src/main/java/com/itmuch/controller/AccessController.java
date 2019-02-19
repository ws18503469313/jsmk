package com.itmuch.controller;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.itmuch.model.Access;
import com.itmuch.service.AccessService;
import com.itmuch.util.JSONResult;

@Controller
@RequestMapping("/access/")
public class AccessController extends CoreController{
	
	private static final Logger log = LoggerFactory.getLogger(AccessController.class);
	
	@Autowired
	private AccessService accessService;
	
	@RequestMapping("save")
	@ResponseBody
	public JSONResult save(Access access) {
		String msg = accessService.save(access);
		return new JSONResult(0, msg, msg);
	}
	/**
	 * 获取某角色的权限
	 * @param RoleID
	 * @return6
	 */
	@RequestMapping("getAccess")
	@ResponseBody
	public JSONResult getSysAccessTree(String roleId) {
		JSONArray tree = accessService.getSysAccessTree(null, roleId);
		return JSONResult.ok(tree);
	}
	/**
	 * 删除access
	 * @param name
	 * @return
	 */
	@RequestMapping("delete")
	@RequiresRoles(value= {"admin", "manager"},logical = Logical.OR)
	@ResponseBody
	public JSONResult delete(String name) {
		log.debug("---------------------------------"+name+"---------------------------");
		accessService.deleteAccess(name);
		return JSONResult.ok();
	}
	/**
	 * 获取系统的中全部权限
	 * @return
	 */
	@RequestMapping("listAccess")
	@ResponseBody
	public JSONResult listAccess() {
		JSONArray tree = accessService.getSysAccessTree();
		return JSONResult.ok(tree);
	}
}
