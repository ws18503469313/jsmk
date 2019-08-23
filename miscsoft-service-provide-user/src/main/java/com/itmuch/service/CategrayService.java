package com.itmuch.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itmuch.exception.BizException;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itmuch.mapper.CategrayMapper;
import com.itmuch.model.Categray;
import com.itmuch.model.Resource;

import tk.mybatis.mapper.entity.Example;

@Service
public class CategrayService {
	
	@Autowired
	private CategrayMapper categrayMapper;
	
	@Autowired
	private Resource resource;
	
	@Autowired
	private Sid sid;
	public String save(String name) {
		Example example = new Example(Categray.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(name)) {
			criteria.andEqualTo("name", name);
		}
		List<Categray> result = categrayMapper.selectByExample(example);
		if(result.size() > 0) {
			throw new BizException("该目录已存在");
		}
		Categray categray = new Categray();
		categray.setName(name);
		categray.setCreateTime(new Date());
		categray.setId(sid.nextShort());
		categray.setIsDelete(0);
		categrayMapper.insert(categray);
		return "ok";
	}
	/**
	 * 获取categray
	 * @param query 查询条件
	 * @param open	是否开启分页
	 * @param page	分页
	 * @return
	 */
	public Map<String, Object> listCategray(Categray query, Boolean open, int page) {
		Page<Categray> obj = null;
		if(open) {
			obj = PageHelper.startPage(page,resource.getPagesize());
		}
		Example example = new Example(Categray.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(query.getName())) {
			criteria.andLike("name", "%"+query.getName()+"%");
		}
		criteria.andEqualTo("isDelete", false);
		List<Categray> result = categrayMapper.selectByExample(example);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		map.put("obj", obj);
		return map;
	}

}
