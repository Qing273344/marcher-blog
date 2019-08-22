package xin.marcher.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.request.RequestContextListener;

/**
 * 项目启动类
 * 定时器注解 --> @EnableScheduling
 *
 * @author marcher
 */
//@EnableScheduling
@ComponentScan(basePackages = "xin.marcher.*")
@MapperScan(value = "xin.marcher.blog.dao", annotationClass = Repository.class)
@SpringBootApplication
public class BlogApplication  extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BlogApplication.class);
    }

    /**
     * RequestContextHolder.getRequestAttributes() 为null解决方案
     */
    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

}