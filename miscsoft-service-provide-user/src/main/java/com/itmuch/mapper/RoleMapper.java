package com.itmuch.mapper;

import java.util.Set;

import com.itmuch.model.Role;
import com.itmuch.util.MyMapper;

public interface RoleMapper extends MyMapper<Role> {

	Set<String> listRoleByUsername(String username);
}