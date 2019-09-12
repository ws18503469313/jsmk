package com.itmuch.mapper;

import java.util.List;
import java.util.Set;

import com.cloud.dto.NodeDTO;
import com.cloud.model.Access;
import com.itmuch.util.MyMapper;

public interface AccessMapper extends MyMapper<Access> {

	Set<String> getAccessByRole(String ro);
	/**
	 * 通过roleId获取到该角色拥有的权限
	 * @param roleID
	 * @return
	 */
	List<Access> getSysAccessTree(String roleID);
	/**
	 * 获取一级菜单下的二级菜单
	 * @param id
	 * @return
	 */
	List<Access> selectAccessChildren(String id);
	/**
	 * 删除权限时,需要把角色--权限关系也同事删除
	 * @param id
	 */
	void deleteRoleAccessByAccessId(String id);
	/**
	 * 通过parentId删除该一级菜单下的二级菜单
	 * @param id
	 */
	void deleteAccessByParentId(String id);

}