package com.itmuch.mapper;

import java.util.List;

import com.itmuch.dto.UserDTO;
import com.itmuch.dto.UserRoleDTO;
import com.itmuch.model.User;
import com.itmuch.util.MyMapper;

public interface UserMapper extends MyMapper<User> {

	UserRoleDTO getRole(String id);

	User getUserByUsername(String username);

	void saveUserRole(UserRoleDTO dto);

	void creatUserRole(UserRoleDTO dto);

	List<UserDTO> listUser(User user); 
}