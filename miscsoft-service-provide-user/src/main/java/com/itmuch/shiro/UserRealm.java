package com.itmuch.shiro;

import java.util.HashSet;
import java.util.Iterator;
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
import com.itmuch.mapper.AuthorityMapper;
import com.itmuch.mapper.RoleMapper;
import com.itmuch.mapper.UserMapper;
import com.cloud.model.User;

/**
 * 用戶自定义实现Realm,完成客户的认证和授权
 * @author 86185
 *
 */
public class UserRealm extends AuthorizingRealm{

	private static final Logger log = LoggerFactory.getLogger(UserRealm.class);
	@Autowired
	private AccessMapper accessDao;
	@Autowired
	private RoleMapper roleDao;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AuthorityMapper authorityMapper;
	{
		super.setName("realmName"); 
	}
	
	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		log.info("------------------------------------从db获取权限数据a-------------------------------");
		User user = (User)principals.getPrimaryPrincipal();
		Set<String> roles = getRoleByUsername(user.getUsername());
		Set<String> authorities = new HashSet<String>();
		authorities = getPermissionByRoles(roles, authorities);
		authorities = getRoleAuthORity(roles, authorities);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setRoles(roles);
		info.setStringPermissions(authorities);
		log.info("-------------------------------授权完成a--------------------------------------");
		return info;
	}
	
	
	private Set<String> getRoleAuthORity(Set<String> roles, Set<String> authorities) {
		for (String role : roles) {
			authorities.addAll(authorityMapper.selectAuthorityByRole(role));
		}
		return authorities;
	}


	private Set<String> getPermissionByRoles(Set<String> roles, Set<String> authorities) {
		for(String ro :roles) {
			authorities.addAll(accessDao.getAccessByRole(ro));
//			for(String p : permission) {
//				permissions.add(p);
//			}
		}
		log.info(authorities.toString());
		return authorities;
	}
	
	private Set<String> getRoleByUsername(String username) {
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
		log.info("----------------------------------token.hash-"+hash+"-----------------");
		if(!user.getPassword().equals(hash.toString())) {
			throw new IncorrectCredentialsException("wrong password");
		}
		
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), "realName");
		info.setCredentialsSalt(ByteSource.Util.bytes(username));
		log.info("---------------------认证完成AuthenticationInfo-------------------------");
		return info;
	}
	
	public static void main(String[] args) {
		Md5Hash hash = new Md5Hash("123123","123123");
		System.out.println(hash.toString());
	}
}
