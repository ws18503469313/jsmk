package com.itmuch.controller;

import com.cloud.model.Role;
import com.cloud.model.User;
import com.cloud.util.JSONResult;
import com.github.pagehelper.PageHelper;
import com.cloud.dto.UserDTO;
import com.cloud.dto.UserRoleDTO;
import com.itmuch.mapper.UserMapper;
import com.itmuch.model.Resource;
import com.itmuch.service.RoleService;
import com.itmuch.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


@Controller
@RequestMapping(value="/user")
public class UserController {
	
	
	@Autowired
	private Resource resource;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserService userService;
	@Autowired
	private Sid sid;
	@Autowired
	private RoleService	roleService;
	
	@RequestMapping("/test/{id}")
	public User findById(@PathVariable String id) {
		return userMapper.selectByPrimaryKey("1");
		
	}
	@RequestMapping("/findById")
	@ResponseBody
	public JSONResult findByIdJSON(String id) {
		
		User user = userMapper.selectByPrimaryKey(id);
		return  JSONResult.ok(user);
			
	}
	
	@RequestMapping("/getResource")
	@ResponseBody
	public JSONResult getResource() {
		
		
		Resource source = new Resource();
		BeanUtils.copyProperties(resource, source);
		return  JSONResult.ok(source);
		
	}
	/**
	 *   用户注册
	 * @param user
	 * @return
	 */
	@RequestMapping("/addUser")
	@ResponseBody
	@RequiresPermissions(value= {"user.add"})
	public JSONResult addUser(User user) {
		
//		User user = JSON.parseObject(json, User.class);
		return JSONResult.ok(userService.addUser(user));

	}
	
	
	/**
	 * 
	 *   分页获取用户
	 * @param page
	 * @param user
	 * @return
	 */
	@RequestMapping("/pageUser")
	@ResponseBody
	public JSONResult pageUser(@RequestParam(name="page",defaultValue="0") int page,User user) {
//		System.out.println(resource.getPagesize()+"<"+page);
		PageHelper.startPage(page,resource.getPagesize());
//		Example example = new Example(User.class);
//		Example.Criteria criteria = example.createCriteria();
//		criteria.
//		if(!StringUtils.isEmpty(user.getUsername())) {
//			criteria.andLike("username", "%"+user.getUsername()+"%");
//			criteria.orLike("name", "%"+user.getName()+"%");
//		}
//		List<User> users = userMapper.selectByExample(example);
		List<UserDTO> users = userMapper.listUser(user);
		return JSONResult.ok(users);

	}
	/**
	 * 获取某用户的角色
	 * @param id
	 * @return
	 */
	@RequestMapping("/userRoleManager")
	public String getRole(String id, Map<String, Object> map) {
		UserRoleDTO userRole = userMapper.getRole(id);
		List<Role> roles = roleService.listAllRole();
		map.put("userRole", userRole);
		map.put("roles", roles);
		return "sys/userRoleManager";
	}
	/**
	 *  保存用户角色信息
	 * @param dto
	 * @return
	 */
	@RequestMapping("/setRole")
	@ResponseBody
	public JSONResult setRole(UserRoleDTO dto) {
		String msg = userService.saveUserRole(dto);
		return JSONResult.ok(msg);
	}
	
	
	
	
	
	
	
	
	
	
	
	
}
