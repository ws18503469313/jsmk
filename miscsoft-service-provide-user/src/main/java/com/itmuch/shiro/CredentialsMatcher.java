package com.itmuch.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;

public class CredentialsMatcher extends SimpleCredentialsMatcher{
	
	@Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        UsernamePasswordToken utoken=(UsernamePasswordToken) token;
        //获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
        String in = new String(utoken.getPassword());
        String inPassword = new Md5Hash(in, utoken.getUsername()).toString();
        //获得数据库中的密码
        String dbPassword= info.getCredentials().toString();
        //进行密码的比对
        return this.equals(inPassword, dbPassword);
    }
}
