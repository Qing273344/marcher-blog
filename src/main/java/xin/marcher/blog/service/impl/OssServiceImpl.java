package xin.marcher.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xin.marcher.blog.biz.property.QiniuProperties;
import xin.marcher.blog.common.exception.MarcherException;
import xin.marcher.blog.service.OssService;
import xin.marcher.qiniu.oss.api.QiniuStorageService;
import xin.marcher.qiniu.oss.config.StorageConfig;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author marcher
 */
@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private QiniuProperties qiniuProperties;

    @Override
    public String putFile(MultipartFile file) {

        StorageConfig storageConfig = new StorageConfig()
                .setQiniuAccessKey(qiniuProperties.getQiniuAccessKey())
                .setQiniuSecreKey(qiniuProperties.getQiniuSecreKey())
                .setQiniuBucketName(qiniuProperties.getQiniuBucketName())
                .setQiniuRegion(qiniuProperties.getQiniuRegion());

        QiniuStorageService qiniuStorageService = new QiniuStorageService();

        String fileUrl;
        try {
            byte[] fileBytes = file.getBytes();
            String fileName = file.getOriginalFilename();
            fileUrl = qiniuStorageService.upload(storageConfig, fileBytes, fileName, "111");
        } catch (IOException e) {
            throw new MarcherException("文件转码byte失败", e);
        }
        return fileUrl;
    }
}
