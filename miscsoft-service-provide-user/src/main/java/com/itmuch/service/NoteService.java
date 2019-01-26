package com.itmuch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itmuch.dto.NoteDTO;
import com.itmuch.exception.BizException;
import com.itmuch.mapper.NoteDetailMapper;
import com.itmuch.mapper.NoteMapper;
import com.itmuch.model.Categray;
import com.itmuch.model.Note;
import com.itmuch.model.NoteDetail;
import com.itmuch.model.Resource;

import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class NoteService {
	@Autowired
	private NoteMapper noteMapper;
	@Autowired
	private NoteDetailMapper noteDetailMapper;
	@Autowired
	private Resource resource;
	
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
	/**
	 * 根据条件筛选 帖子
	 * @param query
	 * @param page
	 * @return
	 */
	public Map<String, Object> list(NoteDTO query,  int page) {
		Page<Note> obj = PageHelper.startPage(page,resource.getPagesize());
		Example example = new Example(Note.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(query.getName())) {
			criteria.andLike("name", "%"+query.getName()+"%");
		}
		Map<String, Object> result = new HashMap<>();
		List<NoteDTO> list = noteMapper.listNote(query);
		result.put("list", list);
		result.put("page", obj);
		
		return  result;
		 
	}

	public String delete(String id) {
		try {
//			noteMapper.deleteByPrimaryKey(id);
			Note note = new Note();
			note.setId(id);
			note.setIsDelete(true);
			noteMapper.updateByPrimaryKeySelective(note);
			return "success";
		} catch (Exception e) {
			throw new BizException("操作失败");
		}
	}

}
