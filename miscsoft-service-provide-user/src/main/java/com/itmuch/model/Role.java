package com.itmuch.model;

import javax.persistence.*;

@Table(name = "sys_role")
public class Role {
    @Id
    private String id;

    private String rolename;

    private Integer descprit;

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
     * @return rolename
     */
    public String getRolename() {
        return rolename;
    }

    /**
     * @param rolename
     */
    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

	public Integer getDescprit() {
		return descprit;
	}

	public void setDescprit(Integer descprit) {
		this.descprit = descprit;
	}
    
}