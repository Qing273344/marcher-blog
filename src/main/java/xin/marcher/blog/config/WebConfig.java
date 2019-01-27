package xin.marcher.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import xin.marcher.blog.web.interceptor.AuthorizationInterceptor;

/**
 * MVC配置
 *
 * @author marcher
 */
@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private AuthorizationInterceptor authorizationInterceptor;

    /**
     * 配置拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorizationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/blog/passport/**");
    }
}
