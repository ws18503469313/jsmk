package com.itmuch.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "ss_note")
public class Note implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1173274734490147752L;

	@Id
    private String id;

    /**
     * 帖子标题
     */
    private String name;


    /**
     * 所属分类
     */
    @Column(name = "categray_id")
    private String categrayId;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
     * 访问量
     */
    private Long visitNum;
    /**
     * 是否被删除
     */
    @Column(name="is_delete")
    private Boolean isDelete;
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

	

	

	public String getCategrayId() {
		return categrayId;
	}

	public void setCategrayId(String categrayId) {
		this.categrayId = categrayId;
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

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Long getVisitNum() {
		return visitNum;
	}

	public void setVisitNum(Long visitNum) {
		this.visitNum = visitNum;
	}

	public Note() {
		super();
	}

	public Note(String id, Long visitNum) {
		super();
		this.id = id;
		this.visitNum = visitNum;
	}
	
    
}