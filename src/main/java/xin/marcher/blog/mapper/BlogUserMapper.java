package xin.marcher.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogUser;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

/**
 * 博客用户
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogUserMapper extends BaseMapper<BlogUser> {


}
