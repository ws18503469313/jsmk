package com.itmuch.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itmuch.exception.BizException;
import com.itmuch.mapper.RoleMapper;
import com.itmuch.model.Resource;
import com.itmuch.model.Role;
import com.itmuch.service.RoleService;
import com.itmuch.util.JSONResult;

import tk.mybatis.mapper.entity.Example;
@Controller
@RequestMapping("/role/")
public class RoleController extends CoreController{
	
	private static final Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private Sid sid;
	
	@Autowired
	private RoleService roleSerive;
	
	@Autowired
	private Resource resource;
	
	/**
	 * 增加新的角色
	 * @param role
	 * @return
	 */
	@RequestMapping("add")
	@RequiresRoles(value="manager")
	@ResponseBody
	public JSONResult add(Role role) {
		Role exist = roleMapper.checkIsExist(role);
		if(exist != null) {
			throw new BizException("该角色名称已存在");
		}
		if(StringUtils.isBlank(role.getId())) {
			String id = sid.nextShort();
			role.setId(id);
			roleMapper.insert(role);
			return JSONResult.ok(role);
		}else {
			roleMapper.updateByPrimaryKeySelective(role);
			return JSONResult.ok(role);
		}
	}
	/**
	 *	分页列出role
	 * @param page
	 * @param role
	 * @return
	 */
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
	/**
	 * 给角色 赋/重新设定权限,
	 * @param ids
	 * @param roleId
	 * @return
	 */
	@RequestMapping("accessManage")
	@ResponseBody
	public JSONResult roleManager(String []  ids, String roleId ) {
		log.info("--------------------修改角色权限-----------------操作人"+getCurrentUser().getName());
		String result = roleSerive.saveRoleAccess(roleId, ids);
		return JSONResult.ok(result);
	}
	
	@RequestMapping("addRole")
	public String addRole(String id,Map<String, Object> map) {
		if(StringUtils.isNotBlank(id)) {
			Role role = roleMapper.selectByPrimaryKey(id);
			map.put("role", role);
		}
		return "sys/addRole";
	}
}
