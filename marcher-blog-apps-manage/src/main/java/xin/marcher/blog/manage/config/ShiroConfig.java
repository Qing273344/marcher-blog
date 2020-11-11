package xin.marcher.blog.manage.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xin.marcher.blog.manage.shiro.oauth2.BlogRealm;
import xin.marcher.blog.manage.shiro.oauth2.JwtFilter;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * shiro配置
 *
 * @author marcher
 */
@Configuration
public class ShiroConfig {

    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        return sessionManager;
    }

    @Bean
    public SecurityManager securityManager(BlogRealm blogRealm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(blogRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    /**
     * shiro 拦截器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // oauth过滤
        Map<String, Filter> filters = new HashMap<>(1);
        filters.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
        filterChainDefinitionMap.put("/manage/passport/**", "anon");
//        filterChainDefinitionMap.put("/manage/tag/**", "anon");

        filterChainDefinitionMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * shiro 生命周期
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 自动创建代理类
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 开启 shiro aop 注解支持
     * 使用代理方式, 所以需要开启代码支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    // ---------------------------------------------------------------------------------------------------------------- session-cookie模式使用
//    @Bean(name = "blogRealm")
//    public BlogRealm blogRealm() {
//        BlogRealm blogRealm = new BlogRealm();
//        blogRealm.setCredentialsMatcher(credentialsMatcher());
//        return blogRealm;
//    }

    /**
     * 密码凭证匹配
     */
//    @Bean
//    public CredentialsMatcher credentialsMatcher() {
//        return new CredentialsMatcher();
//    }
}
