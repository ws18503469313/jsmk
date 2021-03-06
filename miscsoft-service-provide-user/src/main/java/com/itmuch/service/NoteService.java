package com.itmuch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cloud.model.Collect;
import com.cloud.model.Note;
import com.cloud.model.NoteDetail;
import com.cloud.model.User;
import com.itmuch.model.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.cloud.dto.NoteDTO;
import com.itmuch.exception.BizException;
import com.itmuch.mapper.CollectionMapper;
import com.itmuch.mapper.NoteDetailMapper;
import com.itmuch.mapper.NoteMapper;
import com.itmuch.util.JedisUtil;
import com.itmuch.util.SerialaizerUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

@Service
@Transactional
public class NoteService {
	
	private static final Logger log = LoggerFactory.getLogger(NoteService.class);
	@Autowired
	private JedisUtil jedisUtil;
	@Autowired
	private NoteMapper noteMapper;
	@Autowired
	private NoteDetailMapper noteDetailMapper;
	@Autowired
	private Resource resource;
	@Autowired
	private CollectionMapper collectionMapper;
	@Autowired
	private Sid sid;
	
	public static final String VISITNUM = "note_visit_num_";
	
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
//		Example example = new Example(Note.class);
//		Example.Criteria criteria = example.createCriteria();
//		if(!StringUtils.isEmpty(query.getName())) {
//			criteria.andLike("name", "%"+query.getName()+"%");
//		}
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
	/**
	 * 根据条件查询
	 * @param id
	 * @param db 是否从mysql中获取
	 * @return
	 */
	public synchronized NoteDTO getNoteDetail(NoteDTO query,boolean db) {
		//先从redis中找
		String key = VISITNUM+query.getId();
		Gson gson = new Gson();
		byte[] value = jedisUtil.get(key.getBytes());
		if(db ||  value == null) {//如果指定从db中获取or redis中没有
			NoteDTO result = noteMapper.getNoteDetail(query);
			jedisUtil.set(key.getBytes(), gson.toJson(result).getBytes());
			return result;
		}else {//如果redis中有数据则返回
			NoteDTO note = gson.fromJson(new String(value), NoteDTO.class);
			note.setVisitNum(note.getVisitNum()+1);
			jedisUtil.set(key.getBytes(), gson.toJson(note).getBytes());
			return note;
		}
		
	}
	
	@Transactional
	public synchronized String like(String id, User user) {
		if(user == null) {
			throw new BizException("请先登陆");
		}
		/**
		 *  检查是否已经收藏过
		 */
		Example example = new Example(Collect.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("userId", user.getId());
		criteria.andEqualTo("noteId", id);
		List<Collect> db = collectionMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(db)) {
			throw new BizException("该帖子已被您收藏");
		}
		/**
		 * 正式收藏
		 */
		Collect collect = new Collect(sid.nextShort(), user.getId(), id);
		collectionMapper.insert(collect);
		return "操作成功";
	}


	public void test(){
		Page<Note> obj =  PageHelper.startPage(1,1);
		Example example = new Example(Note.class);
		Example.Criteria criteria = example.createCriteria();
		Note one = noteMapper.selectOneByExample(example);
		one.setVisitNum(one.getVisitNum() + 1);
		noteMapper.updateByPrimaryKeySelective(one);//更新数据库中的访问量
//		result.get(0).setVisitNum();
	}

}
