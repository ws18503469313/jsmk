package com.itmuch.mapper;

import java.util.List;

import com.itmuch.dto.UserRoleDTO;
import com.itmuch.model.User;
import com.itmuch.util.MyMapper;

public interface UserMapper extends MyMapper<User> {

	List<UserRoleDTO> getRole(String id);

	User getUserByUsername(String username); 
}