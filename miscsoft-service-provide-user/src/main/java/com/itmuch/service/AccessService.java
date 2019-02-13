package com.itmuch.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.itmuch.dto.NodeDTO;
import com.itmuch.exception.BizException;
import com.itmuch.mapper.AccessMapper;
import com.itmuch.mapper.RoleMapper;
import com.itmuch.model.Access;

import tk.mybatis.mapper.entity.Example;

@Service
public class AccessService {
	
	@Autowired
	private Sid sid;
	
	@Autowired
	private AccessMapper accessMapper;
	@Autowired
	private RoleMapper roleMapper;
	public String save(Access trans) {
		if(trans.getId() ==null) {
			if(StringUtils.isBlank(trans.getUrl())) {
				throw new BizException("请输入url");
			}
			if(StringUtils.isBlank(trans.getTitle())) {
				throw new BizException("请输入标题 ");
			}
			if(!trans.getParent().equals("#")) {//如果是创建二级菜单则要获取parent 的 id
				Example example = new Example(Access.class);
				Example.Criteria criteria = example.createCriteria();
				criteria.andEqualTo("title", trans.getParent());
				List<Access> accesses = accessMapper.selectByExample(example);
				trans.setParent(accesses.get(0).getId());
			}
			trans.setId(sid.nextShort());
			trans.setCreatedTime(new Date());
			accessMapper.insert(trans);
			return trans.getId();
		}else {
			Access db = accessMapper.selectByPrimaryKey(trans.getId());
			if(StringUtils.isNotBlank(trans.getTitle())) {
				db.setTitle(trans.getTitle());
			}
			if(StringUtils.isNotBlank(trans.getUrl())) {
				db.setUrl(trans.getUrl());
			}
			accessMapper.updateByPrimaryKeySelective(db);
			return "ok";
		}
	}
	/**
	 * 系统中权限的管理
	 * @return
	 */
	public JSONArray getSysAccessTree() {
		List<Access> allAccess = accessMapper.selectAll();
		List<NodeDTO> result = new ArrayList<>();
		for(Access first : allAccess) {
			if(first.getUrl().equals("#")) {
				result.add(transToNode(first, false));
			}
		}
		for (NodeDTO parent : result) {
			List<NodeDTO> children = new ArrayList<>();
			for(Access second : allAccess) {
				if(second.getParent().equals(parent.getId())) {
					children.add(transToNode(second, false));
				}
			}
			parent.setChildren(children);
		}
		
		return transTreeTOLayUIJson(result);
	}
	private JSONArray transTreeTOLayUIJson(List<NodeDTO> tree) {
		JSONArray result = new JSONArray();
		for (NodeDTO one : tree) {
			JSONObject first = new JSONObject();
			first.put("name", one.getName());
//			first.put("href", "#");
			first.put("id", one.getId());
			JSONArray children = new JSONArray();
			for (NodeDTO two : one.getChildren()) {
				JSONObject second = new JSONObject();
				second.put("name", two.getName());
				second.put("id", two.getId());
//				second.put("href", two.getUrl());
				JSONObject state = new JSONObject();
				state.put("disabled", false);
				state.put("opened", false);
//				boolean contains = ;
				state.put("selected", two.getHas());
				second.put("state", state);
				children.add(second);
			}
			first.put("children", children);
			result.add(first);
		}
		return result;
	}
	/**
	 * 	根据用户角色生成用户的tab Tree
	 * @param role
	 * @return
	 */
	public JSONArray getSysAccessTree(String userID, String roleID){
		//系统中所有的权限
		List<Access> allAccess = accessMapper.selectAll();
		if(StringUtils.isBlank(roleID)) {
			roleID = roleMapper.selectRoleIdByUserId(userID);
		}
		//该角色拥有的权限
		List<Access> has = accessMapper.getSysAccessTree(roleID);
		//作为一级菜单的结果
		List<NodeDTO> result = new ArrayList<>();
		/**
		 * 将拥有的和全部进行比对,来设置该用户在全部access中拥有那些
		 */
		for(Access one : allAccess) {
			for(Access that : has) {
				if(one.getId().equals(that.getId())) {
					one.setHas(true);
					continue;
				}
			}
			/**
			 * 
			 */
			if(one.getHas() == null) {
				one.setHas(false);
			}
		}
		/**
		 * 将该用户拥有的access做成一个tree
		 */
		for(Access one : allAccess) {
			if(one.getUrl().equals("#")) {//先找出一级菜单
				if(StringUtils.isNotBlank(userID)) {//如果用户id不为空,则说明是要获取该用户拥有的权限,
					result.add(transToNode(one, one.getHas()));
				}else {//如果用户id为空,则说明是给校色分配权限,则要获取所有的权限,
					result.add(transToNode(one, one.getHas()));
				}
//				allAccess.remove(one);
			}
		}
		for (NodeDTO parent : result) {
			List<NodeDTO> children = new ArrayList<>();
			for(Access child : allAccess) {
				if(child.getParent().equals(parent.getId())) {
					children.add(transToNode(child, child.getHas()));
//					allAccess.remove(child);
				}
			}
			parent.setChildren(children);
		}
		return transTreeTOJson(result);
	}
	
	private JSONArray transTreeTOJson(List<NodeDTO> tree) {
		JSONArray result = new JSONArray();
		for (NodeDTO one : tree) {
			JSONObject first = new JSONObject();
			first.put("text", one.getName());
			first.put("id", one.getId());
			JSONArray children = new JSONArray();
			for (NodeDTO two : one.getChildren()) {
				JSONObject second = new JSONObject();
				second.put("text", two.getName());
				second.put("id", two.getId());
				JSONObject state = new JSONObject();
				state.put("disabled", false);
				state.put("opened", false);
				boolean contains = two.getHas();
				state.put("selected", contains);
				second.put("state", state);
				children.add(second);
			}
			first.put("children", children);
			result.add(first);
		}
		return result;
	}

	private NodeDTO transToNode(Access one, boolean has) {
		return new NodeDTO(one.getId(), one.getTitle(), one.getUrl(), has);
	}

	
}
