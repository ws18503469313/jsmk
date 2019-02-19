package com.itmuch.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
			if(one.getHas() == null) {
				one.setHas(false);
			}
		}
		/**
		 * 将该用户/该角色拥有的access做成一个tree
		 */
		//先找出一级菜单
		for(Access one : allAccess) {
			if(one.getUrl().equals("#")) {
				if(StringUtils.isNotBlank(userID) ) {//如果用户id不为空,则说明是要获取该用户拥有的权限,
					if(one.getHas()) {//如果该用户有该权限,才显示
						result.add(transToNode(one, one.getHas()));
					}
				}else {//如果用户id为空,则说明是给校色分配权限,则要获取所有的权限,
					result.add(transToNode(one, one.getHas()));
				}
			}
		}
		//再找出一级菜单下的二级菜单
		for (NodeDTO parent : result) {
			List<NodeDTO> children = new ArrayList<>();
			for(Access child : allAccess) {
				if(child.getParent().equals(parent.getId())) {
					if(StringUtils.isNotBlank(userID)) {
						if(child.getHas()) {//如果该用户有该权限,才显示
							children.add(transToNode(child, child.getHas()));
						}
					}else {
						children.add(transToNode(child, child.getHas()));
					}
					
				}
			}
			parent.setChildren(children);
		}
		if(StringUtils.isNotBlank(userID)) {
			return transTreeTOJson(result, true);
		}else {
			return transTreeTOJson(result, false);
		}
		
	}
	/**
	 * 将用户/角色拥有的权限转化为tree
	 * 渲染的是jsonnode 的name
	 * 渲染的input框中的value值为value
	 * id 属性不渲染
	 * @param tree
	 * @param isTag 要返回什么样的树:true==前端主页tag树,false==角色管理权限树
	 * @return
	 */
	private JSONArray transTreeTOJson(List<NodeDTO> tree, boolean isTag) {
		JSONArray result = new JSONArray();
		for (NodeDTO parent : tree) {//一级根菜单
			JSONObject first = new JSONObject();
			first.put("name", parent.getName());
			if(isTag) {
				first.put("value", parent.getUrl());
			}else {
				first.put("value", parent.getId());
			}
			
			first.put("checked", parent.getHas());//checked
			first.put("pid", 0);
			JSONArray children = new JSONArray();
			for (NodeDTO child : parent.getChildren()) {//一级根菜单下的二级菜单
				JSONObject second = new JSONObject();
				second.put("name", child.getName());
				if(isTag) {
					second.put("value", child.getUrl());
				}else {
					second.put("value", child.getId());
				}
				
				second.put("checked", child.getHas());
				second.put("pid", parent.getId());
				children.add(second);
			}
			first.put("list", children);
			result.add(first);
		}
		return result;
	}

	private NodeDTO transToNode(Access one, boolean has) {
		return new NodeDTO(one.getId(), one.getTitle(), one.getUrl(), has);
	}

	
}
