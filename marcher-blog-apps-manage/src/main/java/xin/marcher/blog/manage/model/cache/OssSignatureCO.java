package xin.marcher.blog.manage.model.cache;

import lombok.Data;

import java.io.Serializable;

/**
 * oss 签名 CO
 *
 * @author marcher
 */
@Data
public class OssSignatureCO implements Serializable {

    private static final long serialVersionUID = 3763229422485905114L;

    private String endpoint;
    private String bucket;
    private String accessKeyId;
    private String policy;
    private String signature;
}
