package xin.marcher.blog.biz.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * rabbit 属性
 *
 * @author marcher
 */
@Data
@Component
@ConfigurationProperties(prefix = "rabbitmq.config")
public class RabbitMqProperties {

    /**
     * 交换器
     */
    private String exchange;

    /**
     * 队列
     */
    private String queue;

    /**
     * 路由键
     */
    private String routekey;
}
