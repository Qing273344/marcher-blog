package xin.marcher.blog.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    /**
     * list文件
     *
     * @return
     *      文件osskey
     */
    List<String> listFile(String keyPrefix);

    /**
     * copy oss
     *
     * @param srcFileUrl    源文件url
     * @param directorys    新文件目录参数
     * @return
     *      返回新文件key
     */
    String copyOssObject(String srcFileUrl, String... directorys);

}
