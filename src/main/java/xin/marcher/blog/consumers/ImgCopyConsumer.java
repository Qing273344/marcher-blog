package xin.marcher.blog.consumers;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

/**
 * oss图片从临时桶拷贝到正式桶
 *
 * @author marcher
 */
@Component
public class ImgCopyConsumer {

    /**
     * 监听队列
     * 参数: 可直接写生产者发送的对象
     *
     * @param str
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "blog_content_queue", durable = "true"),
            exchange = @Exchange(name = "marcher_blog_exchange"),
            key = "blog_content_routekey"
        ), containerFactory = "rabbitListenerContainerFactory"
    )
    @RabbitHandler
    public void handlerMessage(String str) {
        System.out.println("消费者: " + str);
    }

}
