package com.itmuch.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itmuch.dto.RoleAccessDTO;
import com.itmuch.exception.BizException;
import com.itmuch.mapper.AccessMapper;
import com.itmuch.mapper.RoleMapper;
import com.itmuch.model.Role;
import com.itmuch.util.ArrayUtil;
@Service
public class RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private AccessMapper accessMapper;
	@Autowired
	private Sid sid;
	
	/**
	 * 给角色赋权限,或重设权限
	 * 1.把原有的权限查出来
	 * 2.和前台传过来的对比
	 * 3.	如过db和传过来的都有,则不处理
	 * 		如果db有,而前台没有,则删掉,
	 * 		如果db没有,而前台有,则添加
	 * 鉴于修改操作比较多,而权限又不会大规模改动,以上算法需要的DB操作最少,效率会更高.
	 * @param roleId
	 * @param ids
	 * @return
	 */
	@Transactional
	public String saveRoleAccess(String roleId, String[] ids) {
		Role role = roleMapper.selectByPrimaryKey(roleId);
		if(role == null) {
			throw new BizException("该角色不存在");
		}
		String [] dbacc =  {};
		dbacc = roleMapper.getHasAccess(roleId).toArray(dbacc);
		String [] delacc = ArrayUtil.substract(dbacc, ids);
		for (int i = 0; i < delacc.length; i++) {
			roleMapper.deleteRoleAccess(new RoleAccessDTO(roleId,delacc[i]));
		}
		String [] addacc = ArrayUtil.substract(ids, dbacc);
		for (int i = 0; i < addacc.length; i++) {
			if(StringUtils.isBlank(addacc[i])) {
				continue;
			}
			roleMapper.saveRoleAccess(new RoleAccessDTO(sid.nextShort(), roleId, addacc[i]));
		}
		return "success";
	}
	
	/**
	 *    系统中获取所有role
	 * @return
	 */
	public List<Role> listAllRole(){
		return roleMapper.selectAll();
	}
}
