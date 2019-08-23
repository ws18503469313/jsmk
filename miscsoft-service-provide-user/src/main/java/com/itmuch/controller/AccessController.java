package com.itmuch.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.Charsets;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.entity.ContentType;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.itmuch.mapper.AccessMapper;
import com.itmuch.model.Access;
import com.itmuch.service.AccessService;
import com.itmuch.util.ExcelUtil;
import com.itmuch.util.JSONResult;
import org.thymeleaf.util.DateUtils;

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
