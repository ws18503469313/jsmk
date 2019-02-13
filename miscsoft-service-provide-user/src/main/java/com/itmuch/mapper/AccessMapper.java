package com.itmuch.mapper;

import java.util.List;
import java.util.Set;

import com.itmuch.dto.NodeDTO;
import com.itmuch.model.Access;
import com.itmuch.util.MyMapper;

public interface AccessMapper extends MyMapper<Access> {

	Set<String> getAccessByRole(String userID);

	List<Access> getSysAccessTree(String roleID);
}