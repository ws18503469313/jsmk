package com.itmuch.mapper;

import java.util.List;

import com.itmuch.dto.NoteDTO;
import com.itmuch.model.Note;
import com.itmuch.util.MyMapper;
public interface NoteMapper extends MyMapper<Note> {

	List<NoteDTO> listNote(NoteDTO query);
}