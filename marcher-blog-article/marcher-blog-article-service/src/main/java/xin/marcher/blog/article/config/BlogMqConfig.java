package xin.marcher.blog.article.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.blog.article.biz.property.RabbitMqProperties;

import javax.annotation.PostConstruct;

/**
 * blog mq config
 *
 * @author marcher
 */
@Component
public class BlogMqConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitMqProperties rabbitMqProperties;

    /**
     * 初始化队列
     */
    @PostConstruct
    public void init () {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        // 创建direct模式交换器
        Exchange directExchange = new DirectExchange(rabbitMqProperties.getExchange());
        // 创建队列
        Queue queue = new Queue(rabbitMqProperties.getQueue(), true);

        rabbitAdmin.declareExchange(directExchange);
        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareBinding(BindingBuilder.bind(queue)
                .to(directExchange)
                .with(rabbitMqProperties.getRoutekey())
                .noargs()
        );
    }
}
