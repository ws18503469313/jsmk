package com.itmuch.mapper;

import java.util.List;
import java.util.Set;

import com.cloud.dto.RoleAccessDTO;
import com.cloud.model.Role;
import com.itmuch.util.MyMapper;

public interface RoleMapper extends MyMapper<Role> {
	
	/**
	 * shiro权限框架需要的,一个用户可以拥有多个校色
	 * @param username
	 * @return
	 */
	Set<String> listRoleByUsername(String username);
	
	/**
	 * 查看用户的角色,本系统中一个用户只能拥有一种角色
	 * @param userID
	 * @return
	 */
	String selectRoleIdByUserId(String userID);
	
	/**
	 * 检查该角色名称是否已存在
	 * @param role
	 * @return
	 */
	Role checkIsExist(Role role);
	/**
	 * 根据roleId查询该校色拥有的权限
	 * @param roleId
	 * @return	权限ids
	 */
	List<String> getHasAccess(String roleId);
	/**
	 * 通过roleId,accessId 把原有然后取消掉的access删掉,
	 * @param roleId
	 * @param accessId
	 */
	void deleteRoleAccess(RoleAccessDTO roleAccessDTO);

	void saveRoleAccess(RoleAccessDTO roleAccessDTO);
}