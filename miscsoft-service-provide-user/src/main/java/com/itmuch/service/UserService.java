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

import com.itmuch.dto.UserRoleDTO;
import com.itmuch.exception.BizException;
import com.itmuch.mapper.UserMapper;
import com.itmuch.model.User;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

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
		if (CollectionUtils.isNotEmpty(dbs)) {
			throw new BizException("该用户名已存在");
		}
		if (StringUtils.isNoneBlank(user.getPassword()) && user.getPassword().length() < 6) {
			throw new BizException("密码长度需大于6位");
		}
		Md5Hash password = new Md5Hash(user.getPassword(), user.getUsername());
		user.setPassword(password.toString());
		user.setBirthday(new Date());
		user.setId(sid.nextShort());
		userMapper.insert(user);
		return "操作成功";
	}

	@Transactional
	public User testConcurrent() {
		User user = userMapper.selectByPrimaryKey("1");
		User db = new User();
		db.setId("1");
		db.setAge(user.getAge() + 1);
		userMapper.updateByPrimaryKeySelective(db);
		return user;

	}

	public synchronized User beforTransation() {
		return testConcurrent();
	}

	/**
	 * 更新 || 保存user_role关系
	 * 
	 * @param dto
	 * @return
	 */
	public String saveUserRole(UserRoleDTO dto) {
		if (StringUtils.isNotBlank(dto.getNewRoleID())) {// 前端有传回来新分配的校色ID
			throw new BizException("表单错误!");
		}
		if (StringUtils.isNotBlank(dto.getRoleID())) {// 用户原来就有角色

			if (dto.getNewRoleID().equals(dto.getRoleID())) {// 如果相等,即角色没变
				return "保存成功";
			} else {// 更新信息
				userMapper.saveUserRole(dto);
				return "保存成功";
			}

		} else {// 新建user_role关系
			userMapper.creatUserRole(dto);
			return "保存成功";
		}

	}
	
	public User loadByUserName(String username) {
//		Example example = new Example(User.class);
//		Criteria criteria = example.createCriteria();
//		criteria.andEqualTo("username", username);
//		return userMapper.selectByExample(example).get(0);
		return userMapper.getUserByUsername(username);
	}
}
