package xin.marcher.blog.service;

import java.io.File;

/**
 * 七牛云业务
 *
 * @author marcher
 */
public interface QiniuOssService {

    /**
     * put文件
     * @param file  文件
     * @return
     *      文件url
     */
    String putFile(File file);

}
