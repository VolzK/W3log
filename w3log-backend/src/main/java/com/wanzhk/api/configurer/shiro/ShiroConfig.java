package com.wanzhk.api.configurer.shiro;

import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro
 *
 * @author wangzhiheng
 * <p>
 * 2020-05-14
 */
@Configuration
public class ShiroConfig {

    /**
     * lifecycleBeanPostProcessor是负责生命周期的 , 初始化和销毁的类
     * (可选)
     */
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        shiroFilterFactoryBean.setLoginUrl("/notLogin");
        shiroFilterFactoryBean.setUnauthorizedUrl("/noauth");


        // 设置拦截器
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 开放登陆接口
        filterChainDefinitionMap.put("/user/login", "anon");
        filterChainDefinitionMap.put("/user/register", "anon");
        filterChainDefinitionMap.put("/user/forget", "anon");
        filterChainDefinitionMap.put("/user/logout", "anon");


        // //用户，需要角色权限 “user”
        // filterChainDefinitionMap.put("/api/article/**", "roles[user]");
        // //管理员，需要角色权限 “admin”
        // filterChainDefinitionMap.put("/admin/**", "roles[admin]");

        // filterChainDefinitionMap.put("/article/list", "roles[user]");

        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        // filterChainDefinitionMap.put("/**", "authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        System.out.println("Shiro拦截器工厂类注入成功");
        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     *
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //自定义session管理
        securityManager.setSessionManager(sessionManager());
        //自定义缓存实现
        // securityManager.setCacheManager(ehCacheManager());

        // 设置realm.
        securityManager.setRealm(customRealm());
        return securityManager;
    }


    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public CustomRealm customRealm() {
        return new CustomRealm();
    }

    @Bean
    public SessionManager sessionManager() {
        ShiroSessionManager shiroSessionManager = new ShiroSessionManager();
        //这里可以不设置。Shiro有默认的session管理。如果缓存为Redis则需改用Redis的管理
        shiroSessionManager.setSessionDAO(new EnterpriseCacheSessionDAO());
        return shiroSessionManager;
    }

}
