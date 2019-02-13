package com.itmuch.service;

import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itmuch.mapper.RoleMapper;
import com.itmuch.model.Role;
@Service
public class RoleService {
	
	@Autowired
	private RoleMapper roleMapper;

	@Autowired
	private Sid sid;
	
}
