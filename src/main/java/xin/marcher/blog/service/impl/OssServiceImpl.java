package xin.marcher.blog.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xin.marcher.aliyun.oss.api.AliyunStorageService;
import xin.marcher.aliyun.oss.config.StorageConfig;
import xin.marcher.aliyun.oss.util.OssManager;
import xin.marcher.blog.biz.property.OssProperties;
import xin.marcher.blog.service.OssService;
import xin.marcher.blog.utils.DateUtil;

import java.io.IOException;

/**
 * @author marcher
 */
@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Autowired
    private OssProperties ossProperties;

    private static StorageConfig config = new StorageConfig();

    private void init() {
        config.setAliyunEndPoint(ossProperties.getAliyunEndPoint())
                .setAliyunAccessKeyId(ossProperties.getAliyunAccessKeyId())
                .setAliyunAccessKeySecret(ossProperties.getAliyunAccessKeySecret())
                .setAliyunBucketName(ossProperties.getAliyunBucketName())
                .setAliyunRegion(ossProperties.getAliyunRegion());
    }

    @Override
    public String putFile(MultipartFile file) {
        init();

        String dateStr = DateUtil.formatDate(DateUtil.getTimestamp(), DateUtil.PATTERN_HYPHEN_DATE);
        String ossKey = AliyunStorageService.getOssKey(file.getOriginalFilename(), dateStr);

        PutObjectResult result;
        try{
            byte[] fileBytes = file.getBytes();
            OssManager ossManager = new OssManager(config);
            result = ossManager.putObject(ossProperties.getAliyunBucketName(), ossKey, fileBytes);
        } catch (OSSException | ClientException | IOException e){
            log.error("applet put oss error!", e);
            result = null;
        }
        if (result != null){
            return ossProperties.getAliyunRegion() + "/" + ossKey;
        }
        return null;
    }
}
