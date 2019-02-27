package com.itmuch.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.itmuch.dto.NoteDTO;
import com.itmuch.model.NoteDetail;

import redis.clients.jedis.Jedis;

public class GsonUtil {
	
	public static void main(String[] args) {
		NoteDTO dto = new NoteDTO();
		dto.setCategrayId("categray_id");
		dto.setCategrayName("categray_name");
		dto.setLike(1);
		dto.setPublishTime(new Date());
		dto.setVisitNum(200L);
		dto.setShutdown(false);
		dto.setId("dto_id");
		dto.setIsDelete(false);
		dto.setName("dto_name");
		List<NoteDetail> details = new ArrayList<NoteDetail>();
		NoteDetail detail = new NoteDetail();
		detail.setContent("content");
		detail.setId(22L);
		detail.setNoteId("parnet_id");
		detail.setSort(0);
		detail.setType(1);
		details.add(detail);
		dto.setDetails(details);
		
		Gson gson = new Gson();
		String obj_json = gson.toJson(dto);
		
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		jedis.set("obj_json".getBytes(), obj_json.getBytes());
		
		byte [] bt = jedis.get("obj_json".getBytes());
		NoteDTO json_obj = gson.fromJson(new String(bt), NoteDTO.class);
		System.out.println(json_obj.toString());
	}
}
