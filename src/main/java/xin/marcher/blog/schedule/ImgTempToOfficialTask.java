package xin.marcher.blog.schedule;

import org.springframework.stereotype.Component;

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
