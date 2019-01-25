package com.itmuch.service;

import java.util.Date;
import java.util.List;

import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.itmuch.mapper.CategrayMapper;
import com.itmuch.model.Categray;

import tk.mybatis.mapper.entity.Example;

@Service
public class CategrayService {
	@Autowired
	private CategrayMapper categrayMapper;
	
	@Autowired
	private Sid sid;
	public String save(String name) {
		Example example = new Example(Categray.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(name)) {
			criteria.andLike("name", "%"+name+"%");
		}
		List<Categray> result = categrayMapper.selectByExample(example);
		if(result.size() > 0) {
			return "该目录已存在";
		}
		Categray categray = new Categray();
		categray.setName(name);
		categray.setCreateTime(new Date());
		categray.setId(sid.nextShort());
		categrayMapper.insert(categray);
		return "ok";
	}

}
