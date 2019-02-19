package com.itmuch.mapper;

import java.util.List;
import java.util.Set;

import com.itmuch.dto.NodeDTO;
import com.itmuch.model.Access;
import com.itmuch.util.MyMapper;

public interface AccessMapper extends MyMapper<Access> {

	Set<String> getAccessByRole(String userID);
	/**
	 * 通过roleId获取到该角色拥有的权限
	 * @param roleID
	 * @return
	 */
	List<Access> getSysAccessTree(String roleID);

}