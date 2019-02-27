package com.itmuch.concurrent;

import java.util.Iterator;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.google.gson.Gson;
import com.itmuch.BaseTests;
import com.itmuch.dto.NoteDTO;
import com.itmuch.mapper.NoteMapper;
import com.itmuch.model.Note;
import com.itmuch.service.NoteService;
import com.itmuch.util.JedisUtil;

public class TestSaveVistNum extends BaseTests{

	
	@Autowired
	private JedisUtil jedisUtil;
	@Autowired
	private NoteMapper noteMapper;
	@Autowired
	private NoteService noteService;
	@Test
	@Rollback(true)
	public void saveVisitNum() {
		Set<byte[]> cache = jedisUtil.keys(NoteService.VISITNUM+"*");
		Iterator<byte[]> it = cache.iterator();
		Gson gson = new Gson();
		while (it.hasNext()) {
			byte[] key = (byte[]) it.next();
			byte[] value = jedisUtil.get(key);
			NoteDTO dto = gson.fromJson(value.toString(), NoteDTO.class);//这个是从redis中获取反序列化出来的数据
			NoteDTO db = noteService.getNoteDetail(dto, true);//这个是从mysql中查出来的原始数据
			if(dto.getVisitNum() != db.getVisitNum()) {
				Note temp = new Note(dto.getId(), dto.getVisitNum());//这个是要更新数据库中的visitNum的
				noteMapper.updateByPrimaryKeySelective(temp);//更新数据库中的访问量
			}
			db.setVisitNum(dto.getVisitNum());
			jedisUtil.set(key, gson.toJson(db).getBytes());//更新redis中的帖子细节
			it.remove();
		}
	}
}
