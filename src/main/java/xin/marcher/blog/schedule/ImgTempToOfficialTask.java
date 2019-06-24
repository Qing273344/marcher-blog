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
 * oss图片临时桶转移至正式桶, 改为mq方式
 * 定时器需在启动类Application加入@EnableScheduling注解
 *
 * @author marcher
 */
@Component
public class ImgTempToOfficialTask {

    /**
     * job
     */
//    @Scheduled(cron = "*/6 * * * * ?")
    private void job() {
        System.out.println("定时器");
    }
}
