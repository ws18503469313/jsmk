package com.itmuch.service;

import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.itmuch.dto.NoteDTO;
import com.itmuch.mapper.NoteMapper;
import com.itmuch.model.Note;
import com.itmuch.util.JedisUtil;
import com.itmuch.util.SerialaizerUtils;

//@Component
public class ScheduledService {
	
	
	@Autowired
	private JedisUtil jedisUtil;
	@Autowired
	private NoteMapper noteMapper;
	@Autowired
	private NoteService noteService;
	/**
	 * 每10分钟保存redis数据到数据库一次
	 */
	@Scheduled(cron = "0 0/10 * * * ? ")
	@Transactional
	public void saveVisitNum() {
		Set<byte[]> cache = jedisUtil.keys(NoteService.VISITNUM+"*");
		Iterator<byte[]> it = cache.iterator();
		Gson gson = new Gson();
		while (it.hasNext()) {
			byte[] key = (byte[]) it.next();
			byte[] value = jedisUtil.get(key);
			NoteDTO dto = gson.fromJson(new String(value), NoteDTO.class);//这个是从redis中获取反序列化出来的数据
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
