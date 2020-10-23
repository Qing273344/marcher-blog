package xin.marcher.blog.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.request.RequestContextListener;

/**
 * 项目启动类
 *
 * @author marcher
 */
@EnableDiscoveryClient
@EnableFeignClients({
        "xin.marcher.blog.article.client.*",
        "xin.marcher.blog.account.client.*"
})
@ComponentScan(basePackages = {"xin.marcher.framework.*", "xin.marcher.*"})
@SpringBootApplication
public class BlogManageApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> start <<<<<<<<<<<<<<<<<<<<<<<<<<");
        SpringApplication.run(BlogManageApplication.class, args);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> started <<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlogManageApplication.class);
    }

    /**
     * RequestContextHolder.getRequestAttributes() 为null解决方案
     */
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

}