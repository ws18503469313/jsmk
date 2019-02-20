package com.itmuch.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_user")
public class User implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8340135919288677571L;

	@Id
    private String id;

    private String name;

    private String username;

    private Integer age;
    
    private String password;
    
    private Double balance;

    private Date birthday;

    private String descript;
    
    public User() {
		super();
	}
    
	public User(String id, String name, String username, Integer age, Double balance, Date birthday,
			String descript) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.age = age;
		this.balance = balance;
		this.birthday = birthday;
		this.descript = descript;
	}

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
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return balance
     */
    public Double getBalance() {
        return balance;
    }

    /**
     * @param balance
     */
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    /**
     * @return birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * @param birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * @return descript
     */
    public String getDescript() {
        return descript;
    }

    /**
     * @param descript
     */
    public void setDescript(String descript) {
        this.descript = descript;
    }

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", username=" + username + ", password="
				+ password +"]";
	}
    
}