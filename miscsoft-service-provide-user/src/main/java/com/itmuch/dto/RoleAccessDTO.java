package com.itmuch.dto;

public class RoleAccessDTO {
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 角色id
	 */
	private String roleId;
	/**
	 * 权限Id
	 */
	private String accessId;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getAccessId() {
		return accessId;
	}
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	
	public RoleAccessDTO() {
		super();
	}
	public RoleAccessDTO(String roleId, String accessId) {
		super();
		this.roleId = roleId;
		this.accessId = accessId;
	}
	public RoleAccessDTO(String id, String roleId, String accessId) {
		super();
		this.id = id;
		this.roleId = roleId;
		this.accessId = accessId;
	}
	
	
	
}
