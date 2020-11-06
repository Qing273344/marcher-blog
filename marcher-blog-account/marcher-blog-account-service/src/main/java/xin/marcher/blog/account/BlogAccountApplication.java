package xin.marcher.blog.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.context.request.RequestContextListener;

/**
 * 账号服务启动类
 *
 * @author marcher
 */
@EnableDiscoveryClient
@ComponentScan(basePackages = {"xin.marcher.framework.*", "xin.marcher.*"})
@MapperScan(basePackages = "xin.marcher.blog.account.mapper")
@SpringBootApplication
public class BlogAccountApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> start <<<<<<<<<<<<<<<<<<<<<<<<<<");
        SpringApplication.run(BlogAccountApplication.class);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> started <<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlogAccountApplication.class);
    }

    /**
     * RequestContextHolder.getRequestAttributes() 为null解决方案
     */
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

}
