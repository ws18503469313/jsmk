package com.itmuch.Configuration;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.collect.Maps;
import com.itmuch.cache.RedisCacheManager;
import com.itmuch.shiro.CredentialsMatcher;
import com.itmuch.shiro.UserRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;


@Configuration
public class ShiroConfiguration {
	private static final Logger log = LoggerFactory.getLogger(ShiroConfiguration.class);
//	@Autowired
//	private RedisCacheManager redisCacheManager;
//	@Autowired
//	private RedisSessionDao redisSessionDAO;
	@Bean(name="shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager manager) {
        ShiroFilterFactoryBean bean=new ShiroFilterFactoryBean();
        bean.setSecurityManager(manager);
        //配置登录的url和登录成功的url
        bean.setLoginUrl("/index/");
        bean.setSuccessUrl("/main/index");
        //配置访问权限
        LinkedHashMap<String, String> filterChainDefinitionMap = Maps.newLinkedHashMap();
        //静态资源
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/lay/**", "anon");
        filterChainDefinitionMap.put("/common/**", "anon");//addUser
        //主页
        filterChainDefinitionMap.put("/index", "anon");
        filterChainDefinitionMap.put("/html/*.html", "anon");
        filterChainDefinitionMap.put("/index/*", "anon"); 
        filterChainDefinitionMap.put("/addUser", "anon"); 
        
        //WX
        filterChainDefinitionMap.put("/wx/*", "anon"); 
        
        //登陆
        filterChainDefinitionMap.put("/main/doLogin", "anon"); 
        filterChainDefinitionMap.put("/main/ajaxLogin", "anon");
        filterChainDefinitionMap.put("/main/login", "anon"); //表示可以匿名访问
        filterChainDefinitionMap.put("/logout*","anon");
        //后台
        filterChainDefinitionMap.put("/*", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/**", "authc");//表示需要认证才可以访问
        filterChainDefinitionMap.put("/*.*", "authc");
        bean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return bean;
    }
    /**
     * 配置核心安全事务管理器
     * 并配置缓存
     * @param authRealm
     * @return
     */
    @Bean(name="securityManager")
    public SecurityManager securityManager(@Qualifier("authRealm") UserRealm authRealm) {
//        log.debug("--------------shiro已经加载----------------");
        DefaultWebSecurityManager manager=new DefaultWebSecurityManager();
        manager.setRealm(authRealm);
        manager.setCacheManager(redisCacheManager());
        return manager;
    }
    /**
     * 配置自定义的权限登录器
     * @param matcher
     * @return
     */
    @Bean(name="authRealm")
    public UserRealm authRealm(@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
    	UserRealm authRealm=new UserRealm();
        authRealm.setCredentialsMatcher(matcher);
        return authRealm;
    }
    /**
     * 配置自定义的密码比较器
     * @return
     */
    @Bean(name="credentialsMatcher")
    public CredentialsMatcher credentialsMatcher() {
        return new CredentialsMatcher();
    }
    /**
     * 将shiro 交给spring
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator=new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(@Qualifier("securityManager") SecurityManager manager) {
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(manager);
        return advisor;
    }
    
    @Bean
    public RedisCacheManager redisCacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        return  cacheManager;
    }
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
//    /**
//     * 限制同一账号登录同时登录人数控制
//     *
//     * @return
//     */
//    @Bean
//    public KickoutSessionControlFilter kickoutSessionControlFilter() {
//        KickoutSessionControlFilter kickoutSessionControlFilter = new KickoutSessionControlFilter();
//        kickoutSessionControlFilter.setCacheManager(cacheManager());
//        kickoutSessionControlFilter.setSessionManager(sessionManager());
//        kickoutSessionControlFilter.setKickoutAfter(false);
//        kickoutSessionControlFilter.setMaxSession(1);
//        kickoutSessionControlFilter.setKickoutUrl("/auth/kickout");
//        return kickoutSessionControlFilter;
//    }
}
