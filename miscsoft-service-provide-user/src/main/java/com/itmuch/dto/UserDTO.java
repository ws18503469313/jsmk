package com.itmuch.dto;

import com.itmuch.model.User;

public class UserDTO extends User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2396835051847444596L;
	
	/**
	 * 	角色名称
	 */
	private String rolename;

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	
	
	
}
