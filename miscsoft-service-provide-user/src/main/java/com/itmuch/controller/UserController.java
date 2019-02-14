package com.itmuch.controller;

import java.util.Date;
import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageHelper;
import com.itmuch.dto.UserRoleDTO;
import com.itmuch.mapper.UserMapper;
import com.itmuch.model.Resource;
import com.itmuch.model.User;
import com.itmuch.util.JSONResult;

import tk.mybatis.mapper.entity.Example;


@RestController
public class UserController {
	
	
	@Autowired
	private Resource resource;
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private Sid sid;
	
//	@RequestMapping("/{id}")
//	public User findById(@PathVariable String id) {
//		return userMapper.selectByPrimaryKey("1");
//		
//	}
	@RequestMapping("/findByIdJSON")
	public JSONResult findByIdJSON(String id) {
		
		User user = userMapper.selectByPrimaryKey(id);
		return  JSONResult.ok(user);
			
	}
	
	@RequestMapping("/getResource")
	public JSONResult getResource() {
		
		
		Resource source = new Resource();
		BeanUtils.copyProperties(resource, source);
		return  JSONResult.ok(source);
		
	}
	@RequestMapping("/addUser")
	public JSONResult addUser() {
		User user = new User(sid.nextShort(), "polunzi", "wangshuai",23, 2000.23, new Date(), "desc");
		System.out.println(user.toString());
		userMapper.insert(user);
		return JSONResult.ok(user);

	}
	
	@RequestMapping("/getUser")
	public JSONResult getUser() {
		User user = userMapper.selectByPrimaryKey("1");
		return JSONResult.ok(user);

	}
	
	
	@RequestMapping("/pageUser")
	public JSONResult pageUser(@RequestParam(name="page",defaultValue="0") int page,User user) {
		System.out.println(resource.getPagesize()+"<"+page);
		PageHelper.startPage(page,resource.getPagesize());
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(user.getUsername())) {
			criteria.andLike("username", "%"+user.getUsername()+"%");
		}
		List<User> users = userMapper.selectByExample(example);
		return JSONResult.ok(users);

	}
	
	@RequestMapping("/getRole")
	public JSONResult getRole(String id) {
		List<UserRoleDTO> roles = userMapper.getRole(id);
		return JSONResult.ok(roles);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
