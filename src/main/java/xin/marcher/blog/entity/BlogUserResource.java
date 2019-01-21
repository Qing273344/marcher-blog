package xin.marcher.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 用户类型-资源
 *
 * @author marcher
 */
@Getter
@Setter
@ToString
@TableName("blog_user_resource")
public class BlogUserResource {

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

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 修改时间
     */
    private Long modifyTime;

    /**
     * 逻辑删除(0:正常,1:删除)
     */
    @TableLogic
    private Integer deleted;
}
