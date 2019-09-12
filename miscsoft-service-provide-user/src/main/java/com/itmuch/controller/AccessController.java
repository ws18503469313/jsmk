package com.itmuch.controller;

import com.alibaba.fastjson.JSONArray;
import com.cloud.model.Access;
import com.cloud.util.ExcelUtil;
import com.cloud.util.JSONResult;
import com.google.common.collect.Lists;
import com.itmuch.mapper.AccessMapper;
import com.itmuch.service.AccessService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/access/")
public class AccessController extends CoreController{



	private static final Logger log = LoggerFactory.getLogger(AccessController.class);
	
	@Autowired
	private AccessService accessService;
	@Autowired
	private AccessMapper accessMapper;
	@RequestMapping("save")
	@ResponseBody
	public JSONResult save(Access access) {
		Access msg = accessService.save(access);
		return  JSONResult.ok(msg);
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
//		log.debug("---------------------------------"+name+"---------------------------");
		return JSONResult.ok(accessService.deleteAccess(name));
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
	/**
	 * 获取某一个权限详情
	 * @param id
	 * @return
	 */
	@RequestMapping("detail")
	@ResponseBody
	public JSONResult detail(String id) {
		Access access = accessMapper.selectByPrimaryKey(id);
		return JSONResult.ok(access);
	}
	
	@RequestMapping("fetchBlood")
	@ResponseBody
	public void fetchBlood(String[] dates ,Integer times, HttpServletResponse resp) throws Exception {
        List<String> strs = Lists.newArrayList();
        for (String string : dates) {
        	if(StringUtils.isNotBlank(string))
        		strs.add(string);
		}
        ExcelUtil.convertTOTable(strs, times, resp);
	}
}
