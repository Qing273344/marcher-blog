package xin.marcher.blog.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import xin.marcher.blog.shiro.oauth2.BlogRealm;
import xin.marcher.blog.shiro.oauth2.JwtFilter;

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
    public SecurityManager securityManager(@Qualifier("blogRealm") BlogRealm blogRealm, SessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(blogRealm);
        securityManager.setSessionManager(sessionManager);

        return securityManager;
    }

    /**
     * shiro拦截器
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // oauth过滤
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", new JwtFilter());
        shiroFilterFactoryBean.setFilters(filters);

        // 拦截器.
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // 配置不会被拦截的链接 顺序判断
//        filterChainDefinitionMap.put("/blog/passport/**", "anon");
        filterChainDefinitionMap.put("/blog/article/**", "anon");
//        filterChainDefinitionMap.put("/blog/**", "anon");
//        filterChainDefinitionMap.put("/logout", "logout");

        filterChainDefinitionMap.put("/**", "jwt");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    @Bean
    @DependsOn(value = "shiroFilterFactoryBean")
    public String addFilterChainDefinition(ShiroFilterFactoryBean shiroFilterFactoryBean) {
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        filterChainDefinitionMap.put("xxx", "annon");

        return null;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
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
