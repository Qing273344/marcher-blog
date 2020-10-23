package xin.marcher.blog.article.mq.consume;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.blog.article.service.BlogArticleContentService;

import java.io.IOException;

/**
 * oss图片从临时桶拷贝到正式桶
 *
 * @author marcher
 */
@Component
public class ImgMoveConsume {

    @Autowired
    private BlogArticleContentService blogArticleContentService;

    /**
     * 监听队列
     * 参数: 可直接写生产者发送的对象
     */
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "blog_content_queue", durable = "true"),
                    exchange = @Exchange(name = "marcher_blog_exchange"),
                    key = "blog_content_routekey"
            ),
            containerFactory = "rabbitListenerContainerFactory"
    )
    @RabbitHandler
    public void handlerMessage(Long articleId, Channel channel, Message message) throws IOException {
        blogArticleContentService.convertUrl(articleId);

        try {
            /*
             * 告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
             * false: 确认当前一个消息收到, true: 确认所有consumer获得消息
             */
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // ack返回false, 重新回到队列
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            e.printStackTrace();
        }
    }

}
