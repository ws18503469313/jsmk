package com.itmuch.controller.busyness;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itmuch.mapper.CategrayMapper;
import com.itmuch.model.Categray;
import com.itmuch.model.Resource;
import com.itmuch.service.CategrayService;
import com.itmuch.util.JSONResult;

import tk.mybatis.mapper.entity.Example;

@Controller
@RequestMapping("/categray/")
public class CategrayController {
	@Autowired
	private CategrayMapper categrayMapper;
	
	@Autowired
	private Resource resource;
	@Autowired
	private CategrayService categrayService;
	
	@RequestMapping("list")
	@ResponseBody
	public JSONResult list(@RequestParam(name="page",defaultValue="0") int page, ModelMap map, Categray query) {
		Page<Categray> obj = PageHelper.startPage(page,resource.getPagesize());
		Example example = new Example(Categray.class);
		Example.Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(query.getName())) {
			criteria.andLike("name", "%"+query.getName()+"%");
		}
		List<Categray> result = categrayMapper.selectByExample(example);
//		map.put("query", query);
//		map.put("result", result);
//		map.put("page", page);
		return JSONResult.ok(result, obj.getTotal());
	}
	
	@RequestMapping("save")
	@ResponseBody
	public JSONResult save(String name) {
		String msg = categrayService.save(name);
		return JSONResult.ok(msg);
	}
	@RequestMapping("delete")
	@ResponseBody
	public JSONResult delete(String id) {
		
		categrayMapper.deleteByPrimaryKey(id);
		return JSONResult.ok();
	}
	
	
	@RequestMapping("update")
	@ResponseBody
	public JSONResult update(Categray categray) {
		
		categrayMapper.updateByPrimaryKey(categray);
		return JSONResult.ok();
	}
	
}
