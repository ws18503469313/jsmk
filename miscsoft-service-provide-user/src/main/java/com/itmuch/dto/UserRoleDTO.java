package com.itmuch.dto;

public class UserRoleDTO {
	
	
	private String username;
	
	private String rolename;
	
	private String UID;
	/**
	 * 用户原有的角色ID
	 */
	private String roleID;
	/**
	 * 用户新分配的角色ID
	 */
	private String newRoleID;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getUID() {
		return UID;
	}

	public void setUID(String uID) {
		UID = uID;
	}

	public String getRoleID() {
		return roleID;
	}

	public void setRoleID(String roleID) {
		this.roleID = roleID;
	}

	public String getNewRoleID() {
		return newRoleID;
	}

	public void setNewRoleID(String newRoleID) {
		this.newRoleID = newRoleID;
	}

	

	

	

	
	
}
