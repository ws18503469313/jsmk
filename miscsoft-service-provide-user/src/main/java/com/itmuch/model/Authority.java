package com.itmuch.model;

import javax.persistence.*;

@Table(name = "sys_authority")
public class Authority {
    @Id
    private String id;

    /**
     * 权限名
     */
    private String name;

    /**
     * 权限代码
     */
    private String code;

    /**
     * 模块名
     */
    private String module;

    /**
     * 顺序
     */
    private Integer seq;

    /**
     * 对应的父菜单id
     */
    @Column(name = "access_id")
    private String accessId;

    /**
     * 1菜单；2普通
     */
    private Integer type;

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
     * 获取权限名
     *
     * @return name - 权限名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置权限名
     *
     * @param name 权限名
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取权限代码
     *
     * @return code - 权限代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置权限代码
     *
     * @param code 权限代码
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取模块名
     *
     * @return module - 模块名
     */
    public String getModule() {
        return module;
    }

    /**
     * 设置模块名
     *
     * @param module 模块名
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * 获取顺序
     *
     * @return seq - 顺序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置顺序
     *
     * @param seq 顺序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    /**
     * 获取对应的父菜单id
     *
     * @return access_id - 对应的父菜单id
     */
    public String getAccessId() {
        return accessId;
    }

    /**
     * 设置对应的父菜单id
     *
     * @param accessId 对应的父菜单id
     */
    public void setAccessId(String accessId) {
        this.accessId = accessId;
    }

    /**
     * 获取1菜单；2普通
     *
     * @return type - 1菜单；2普通
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1菜单；2普通
     *
     * @param type 1菜单；2普通
     */
    public void setType(Integer type) {
        this.type = type;
    }
}