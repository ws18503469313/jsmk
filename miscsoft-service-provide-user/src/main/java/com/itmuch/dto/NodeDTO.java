package com.itmuch.dto;

import java.util.List;
/**
 * 	权限Tree
 * @author 86185
 *
 */
public class NodeDTO {
	
	private String id;
	
	private String name;
	
	private String href;
	/**
	 * 该菜单下的二级菜单
	 */
	private List<NodeDTO> children;
	
	/**
	 * 是否拥有该权限
	 */
	private Boolean has;
	public String getUrl() {
		return href;
	}

	public NodeDTO() {
		super();
	}

	public NodeDTO(String id) {
		super();
		this.id = id;
	}

	public NodeDTO(String id, String name, String href, Boolean has) {
		super();
		this.id = id;
		this.name = name;
		this.href = href;
		this.has = has;
	}

	public void setUrl(String url) {
		this.href = url;
	}
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<NodeDTO> getChildren() {
		return children;
	}

	public void setChildren(List<NodeDTO> children) {
		this.children = children;
	}

	public Boolean getHas() {
		return has;
	}

	public void setHas(Boolean has) {
		this.has = has;
	}
	
}
