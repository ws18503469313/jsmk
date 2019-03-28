package com.itmuch.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itmuch.mapper.WXResultMapper;
import com.itmuch.model.WXResult;

@Service
public class WXResultService {
	
	@Autowired
	private WXResultMapper wxResultMapper;
	
	public void save(WXResult result) {
		wxResultMapper.insert(result);
	}
	
	public WXResult load(String id) {
		return wxResultMapper.selectByPrimaryKey(id);
	}
}
