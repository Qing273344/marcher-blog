package xin.marcher.blog.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xin.marcher.aliyun.oss.factory.OSSFactory;
import xin.marcher.aliyun.oss.util.UrlPathUtil;
import xin.marcher.blog.biz.property.OssProperties;
import xin.marcher.blog.service.OssService;
import xin.marcher.blog.utils.DateUtil;

import java.io.IOException;
import java.util.List;

/**
 * @author marcher
 */
@Service
@Slf4j
public class OssServiceImpl implements OssService {

    @Autowired
    private OssProperties ossProperties;

    /**
     * 上传文件
     *
     * @param file  文件
     * @return
     *      文件url
     */
    @Override
    public String putFile(MultipartFile file) {
        String dateStr = DateUtil.formatDate(DateUtil.getTimestamp(), DateUtil.PATTERN_HYPHEN_DATE);
        String ossKey = UrlPathUtil.createPath(file.getOriginalFilename(), dateStr);

        try {
            byte[] fileByte = file.getBytes();
            ossKey = OSSFactory.build(ossProperties).putObject(ossProperties.getAliyunTempBucketName(), ossKey, fileByte);
        } catch (OSSException | ClientException | IOException e) {
            log.error("applet put oss error!", e);
        }
        return ossProperties.getAliyunTempRegion() + "/" + ossKey;
    }

    /**
     * 列举指定桶内文件
     *
     * @param keyPrefix 文件key前缀(非必填)
     * @return
     *      文件key
     */
    @Override
    public List<String> listFile(String keyPrefix) {
        return OSSFactory.build(ossProperties).listObjects(ossProperties.getAliyunTempBucketName(), keyPrefix);
    }
}
