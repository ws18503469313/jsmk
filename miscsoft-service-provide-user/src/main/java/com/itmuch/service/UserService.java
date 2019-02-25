package com.itmuch.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itmuch.exception.BizException;
import com.itmuch.mapper.UserMapper;
import com.itmuch.model.User;

import tk.mybatis.mapper.entity.Example;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private Sid sid;
	
	@Transactional
	public String addUser(User user) {
		Example example = new Example(User.class);
		Example.Criteria criteria = example.createCriteria();
		criteria.andEqualTo("username", user.getUsername());
		List<User> dbs = userMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(dbs)) {
			throw new BizException("该用户名已存在");
		}
		if(StringUtils.isNoneBlank(user.getPassword()) && user.getPassword().length() < 6) {
			throw new BizException("密码长度需大于6位");
		}
		Md5Hash password = new Md5Hash(user.getPassword(),user.getUsername());
		user.setPassword(password.toString());
		user.setBirthday(new Date());
		user.setId(sid.nextShort());
		userMapper.insert(user);
		return "操作成功";
	}
}
