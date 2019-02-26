package com.itmuch.model;

import javax.persistence.*;

@Table(name = "ss_collection")
public class Collect {
    /**
     * 主键
     */
    @Id
    private String id;

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 帖子id
     */
    @Column(name = "note_id")
    private String noteId;
    
    
    public Collect() {
		super();
	}

	public Collect(String id, String userId, String noteId) {
		super();
		this.id = id;
		this.userId = userId;
		this.noteId = noteId;
	}

	/**
     * 获取主键
     *
     * @return id - 主键
     */
    public String getId() {
        return id;
    }

    /**
     * 设置主键
     *
     * @param id 主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取用户Id
     *
     * @return user_id - 用户Id
     */
    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户Id
     *
     * @param userId 用户Id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取帖子id
     *
     * @return note_id - 帖子id
     */
    public String getNoteId() {
        return noteId;
    }

    /**
     * 设置帖子id
     *
     * @param noteId 帖子id
     */
    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
}