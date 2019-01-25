package com.itmuch.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.itmuch.exception.BizException;
import com.itmuch.mapper.NoteDetailMapper;
import com.itmuch.mapper.NoteMapper;
import com.itmuch.model.Note;
import com.itmuch.model.NoteDetail;

@Service
@Transactional
public class NoteService {
	@Autowired
	private NoteMapper noteMapper;
	@Autowired
	private NoteDetailMapper noteDetailMapper;
	
	
	public String create(Note note, List<NoteDetail> noteDetails, List<MultipartFile> files) {
		if(StringUtils.isBlank(note.getName())) {
			throw new BizException("请输入帖子标题");
		}
		noteMapper.insert(note);
		for (NoteDetail detail : noteDetails) {
			noteDetailMapper.insert(detail);
		}
		
		return null;
	}

	public String update(Note note, List<NoteDetail> noteDetails, List<MultipartFile> files) {
		
		
		return null;
	}

}
