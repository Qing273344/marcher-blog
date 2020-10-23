package xin.marcher.blog.account.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xin.marcher.framework.mybatis.dao.BaseDO;

/**
 * 用户类型-资源
 *
 * @author marcher
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@TableName("blog_user_resource")
public class BlogUserResource extends BaseDO {

    /**
     * 编号
     */
    @TableId
    private Long userResourceId;

    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 资源编号
     */
    private Integer resourceId;

}
