package xin.marcher.blog.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 账号服务启动类
 *
 * @author marcher
 */
@EnableDiscoveryClient
@ComponentScan(basePackages = {"xin.marcher.blog.account.*", "xin.marcher.framework.*"})
@MapperScan(basePackages = "xin.marcher.blog.account.mapper")
@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class BlogAccountApplication {

    public static void main(String[] args) {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> start <<<<<<<<<<<<<<<<<<<<<<<<<<");
        SpringApplication.run(BlogAccountApplication.class);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>> started <<<<<<<<<<<<<<<<<<<<<<<<<<");
    }

}
