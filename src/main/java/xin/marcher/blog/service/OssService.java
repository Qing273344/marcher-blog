package xin.marcher.blog.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 七牛云业务
 *
 * @author marcher
 */
public interface OssService {

    /**
     * put文件
     * @param file  文件
     * @return
     *      文件url
     */
    String putFile(MultipartFile file);

}
