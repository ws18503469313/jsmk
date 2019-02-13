package com.itmuch.model;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

@Table(name = "sys_access")
public class Access {
    @Id
    private String id;

    private String url;

    private String title;

    @Column(name = "created_time")
    private Date createdTime;
    
    private String parent;
    /**
     * 二级菜单
     */
    private List<Access> children;
    /**
     * 用户是否拥有该权限
     */
    @Transient
    private Boolean has;
    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return created_time
     */
    public Date getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime
     */
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

	public List<Access> getChildren() {
		return children;
	}

	public void setChildren(List<Access> children) {
		this.children = children;
	}

	public Boolean getHas() {
		return has;
	}

	public void setHas(Boolean has) {
		this.has = has;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}
	
   
}