package xin.marcher.blog.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.ToString;
import xin.marcher.framework.mybatis.annotation.CreateTime;
import xin.marcher.framework.mybatis.annotation.ModifyTime;

/**
 * 用户类型-资源
 *
 * @author marcher
 */
@Data
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
    @CreateTime
    private Long createTime;

    /**
     * 修改时间
     */
    @ModifyTime
    private Long modifyTime;

    /**
     * 逻辑删除(0:正常,1:删除)
     */
    @TableLogic
    private Integer deleted;
}
