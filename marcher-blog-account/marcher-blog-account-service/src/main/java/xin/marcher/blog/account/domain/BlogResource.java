package xin.marcher.blog.account.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xin.marcher.framework.mybatis.dao.BaseDO;

/**
 * 博客资源
 *
 * @author marcher
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("blog_resource")
public class BlogResource extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long resourceId;

    /**
     * 资源名
     */
    private String name;

    /**
     * 授权
     */
    private String permission;

}
