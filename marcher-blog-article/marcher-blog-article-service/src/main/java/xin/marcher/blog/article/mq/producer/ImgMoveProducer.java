package xin.marcher.blog.article.mq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xin.marcher.blog.article.biz.property.RabbitMqProperties;
import xin.marcher.framework.rabbit.send.MqService;

/**
 * 图片迁移消息发送
 *
 * @author marcher
 */
@Component
public class ImgMoveProducer {

    @Autowired
    private MqService mqService;

    @Autowired
    private RabbitMqProperties rabbitMqProperties;

    /**
     * 生产者发送消息
     *
     * @param articleId 文章id
     */
    public void sendMoveImgMessage(Long articleId) {
        mqService.convertAndSend(rabbitMqProperties.getExchange(), rabbitMqProperties.getRoutekey(), articleId);
    }
}
