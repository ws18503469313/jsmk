package com.itmuch.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "ss_note")
public class Note {
    @Id
    private String id;

    /**
     * 帖子标题
     */
    private String name;


    /**
     * 所属分类
     */
    @Column(name = "category_id")
    private String categoryId;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 书否关闭评论
     */
    private Boolean shutdown;

    /**
     * 被喜欢次数
     */
    private Integer like;

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


    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
     * 获取所属分类
     *
     * @return category_id - 所属分类
     */
    public String getCategoryId() {
        return categoryId;
    }

    /**
     * 设置所属分类
     *
     * @param categoryId 所属分类
     */
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 获取发布时间
     *
     * @return publish_time - 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置发布时间
     *
     * @param publishTime 发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取书否关闭评论
     *
     * @return shutdown - 书否关闭评论
     */
    public Boolean getShutdown() {
        return shutdown;
    }

    /**
     * 设置书否关闭评论
     *
     * @param shutdown 书否关闭评论
     */
    public void setShutdown(Boolean shutdown) {
        this.shutdown = shutdown;
    }

    /**
     * 获取被喜欢次数
     *
     * @return like - 被喜欢次数
     */
    public Integer getLike() {
        return like;
    }

    /**
     * 设置被喜欢次数
     *
     * @param like 被喜欢次数
     */
    public void setLike(Integer like) {
        this.like = like;
    }
}