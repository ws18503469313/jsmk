package com.itmuch.controller.busyness;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public JSONResult list(@RequestParam(name="page",defaultValue="1") int page, 
							@RequestParam(name="open",defaultValue="true") Boolean open,//是否开启分页
							 Categray query) {
/*		Page<Categray> obj = null;
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
*
*///		map.put("query", query);
//		map.put("result", result);
//		map.put("page", page);
		Map<String, Object> map = categrayService.listCategray(query,open,page);
		List<Categray> result = (List<Categray>) map.get("result");
		Page<Categray> obj = (Page<Categray>) map.get("obj");
		return JSONResult.ok(result, obj == null ? null :obj.getTotal());
	}
	
	@RequestMapping("save")
//	@RequiresRoles(value="manager")
	@ResponseBody
	public JSONResult save(String name) {
		String msg = categrayService.save(name);
		return JSONResult.ok(msg);
	}
	@RequestMapping("delete")
	@ResponseBody
//	@RequiresPermissions(value = {"categray.delete"})
	public JSONResult delete(String id) {
		
		categrayMapper.deleteByPrimaryKey(id);
		return JSONResult.ok();
	}
	
	/**
	 * <pre>
	 *	@RequsetParam == request.getParamater()
	 *	@RequestBody 是把传过来的json参数转换为实体，依赖
	 *  dependency
	 *	groupId com.fasterxml.jackson.core
	 *	artifactId jackson-databindd
	 *	dependency
	 *	同时需要严格控制前端参数为json类型，JSON.stringify(）		
	 *	最后需要把ajax的类型设置为contentType: "application/json;charset=UTF-8",
	 *	</pre>
	 * @param categray
	 * @return
	 */
	@PostMapping(value="update")
	@ResponseBody
	public JSONResult update(@RequestBody Categray categray) {
		
		categrayMapper.updateByPrimaryKey(categray);
		return JSONResult.ok();
	}
	
}
