package xin.marcher.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogUserResource;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * 用户类型-资源
 *
 * @author marcher
 */
@Mapper
@Component
public interface BlogUserResourceMapper extends BaseMapper<BlogUserResource> {

    /**
     * get用户权限id
     *
     * @param userType  用户类型
     * @return
     *      用户类型对应的资源id
     */
    @Select("SELECT resource_id FROM blog_user_resource WHERE deleted = 0 AND user_type = #{userType};")
    List<Long> getResourceIds(@Param("userType") Integer userType);
}
