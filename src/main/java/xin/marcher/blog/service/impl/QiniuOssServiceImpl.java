package xin.marcher.blog.service.impl;

import org.springframework.stereotype.Service;
import xin.marcher.blog.service.QiniuOssService;

import java.io.File;

/**
 * @author marcher
 */
@Service
public class QiniuOssServiceImpl implements QiniuOssService {

    @Override
    public String putFile(File file) {
        return file.getName();
    }
}
