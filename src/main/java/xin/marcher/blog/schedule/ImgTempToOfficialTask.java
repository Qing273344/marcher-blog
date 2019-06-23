package xin.marcher.blog.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import xin.marcher.blog.biz.property.RabbitMqProperties;
import xin.marcher.blog.config.BlogMqConfig;
import xin.marcher.blog.service.OssService;
import xin.marcher.rabbitmq.send.MqService;

import java.util.List;

/**
 * oss图片临时桶转移至正式桶
 *
 * @author marcher
 */
@Component
public class ImgTempToOfficialTask {

    @Autowired
    private MqService mqService;

    @Autowired
    private RabbitMqProperties rabbitMqProperties;

    /**
     * job
     */
    @Scheduled(cron = "*/6 * * * * ?")
    private void job() {
        System.out.println(111);

        mqService.convertAndSend(rabbitMqProperties.getExchange(), rabbitMqProperties.getRoutekey(), "111");
    }
}
