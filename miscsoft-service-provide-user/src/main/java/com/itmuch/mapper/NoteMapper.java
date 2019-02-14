package com.itmuch.mapper;

import java.util.List;

import com.itmuch.dto.NoteDTO;
import com.itmuch.model.Note;
import com.itmuch.util.MyMapper;
public interface NoteMapper extends MyMapper<Note> {
	
	/**
	 * 列出note不包含详情
	 * @param query
	 * @return
	 */
	List<NoteDTO> listNote(NoteDTO query);
	/**
	 * 根据调教查询note 包含noteDetail
	 * @param query
	 * @return
	 */
	NoteDTO getNoteDetail(NoteDTO query);
}