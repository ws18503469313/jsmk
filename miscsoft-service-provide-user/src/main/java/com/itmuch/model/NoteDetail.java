package com.itmuch.model;

import javax.persistence.*;

@Table(name = "ss_note_detail")
public class NoteDetail {
    /**
     * id
     */
    @Id
    private Long id;

    /**
     * 帖子id
     */
    @Column(name = "note_id")
    private String noteId;

    /**
     * 一条记录的内容
     */
    private String content;
    
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 帖子详情类型,0:文字,1:img,2:video
     */
    private Integer type;
    
    /**
     * 获取id
     *
     * @return id - id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置id
     *
     * @param id id
     */
    public void setId(Long id) {
        this.id = id;
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

    /**
     * 获取一条记录的内容
     *
     * @return content - 一条记录的内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置一条记录的内容
     *
     * @param content 一条记录的内容
     */
    public void setContent(String content) {
        this.content = content;
    }

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}