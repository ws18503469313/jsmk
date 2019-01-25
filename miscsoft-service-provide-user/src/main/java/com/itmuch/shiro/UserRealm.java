package com.itmuch.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.itmuch.mapper.AccessMapper;
import com.itmuch.mapper.RoleMapper;
import com.itmuch.mapper.UserMapper;
import com.itmuch.model.User;


public class UserRealm extends AuthorizingRealm{

	private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
	@Autowired
	private AccessMapper accessDao;
	@Autowired
	private RoleMapper roleDao;
	@Autowired
	private UserMapper userMapper;
	{
		super.setName("realmName"); 
	}
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("从db获取数据");
		User user = (User)principals.getPrimaryPrincipal();
		Set<String> roles = getRoleByUsername(user.getUsername());
		Set<String> permission = getPermissionByRoles(roles);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		info.setStringPermissions(permission);
		log.info("授权：AuthorizationInfo--------------------------"+info+"-------------------------");
		return info;
	}
	
	
	private Set<String> getPermissionByRoles(Set<String> roles) {
		Set<String> permissions = new HashSet<String>();
		for(String ro :roles) {
			permissions = accessDao.getAccessByRole(ro);
//			for(String p : permission) {
//				permissions.add(p);
//			}
		}
		log.info(permissions.toString());
		return permissions;
	}
	
	private Set<String> getRoleByUsername(String username) {
		log.info("------------------------------------从db获取数据-----------------------------------------");
		Set<String> roles = roleDao.listRoleByUsername(username);
		log.info(roles.toString());
		return roles;
	}
	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
		String username = token.getUsername();
		User user = userMapper.getUserByUsername(username);
		if(user == null) {
			throw new AuthenticationException("user doesn't exist");
		}
		
		Md5Hash hash = new Md5Hash(token.getPassword(),username);
		
//		136fe14a93a03ade69d795b94fca0baa
		
		if(!user.getPassword().equals(hash.toString())) {
			throw new IncorrectCredentialsException("wrong password");
		}
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), "realName");
		info.setCredentialsSalt(ByteSource.Util.bytes(username));
		log.info("认证AuthenticationInfo:---------------------"+info+"-------------------------");
		return info;
	}
	
	public static void main(String[] args) {
		Md5Hash hash = new Md5Hash("1","1");
		System.out.println(hash.toString());
	}
}
