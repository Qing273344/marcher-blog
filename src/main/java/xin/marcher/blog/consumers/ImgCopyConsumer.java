package xin.marcher.blog.consumers;

import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.blog.biz.property.RabbitMqProperties;
import xin.marcher.blog.service.BlogArticleContentService;

/**
 * oss图片从临时桶拷贝到正式桶
 *
 * @author marcher
 */
@Component
public class ImgCopyConsumer {

    @Autowired
    private BlogArticleContentService blogArticleContentService;

    /**
     * 监听队列
     * 参数: 可直接写生产者发送的对象
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "blog_content_queue", durable = "true"),
            exchange = @Exchange(name = "marcher_blog_exchange"),
            key = "blog_content_routekey"
        ), containerFactory = "rabbitListenerContainerFactory"
    )
    @RabbitHandler
    public void handlerMessage(Long articleId) {
        blogArticleContentService.convertUrl(articleId);
    }

}
