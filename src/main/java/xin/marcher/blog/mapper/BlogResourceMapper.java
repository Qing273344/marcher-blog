package xin.marcher.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogResource;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

/**
 * 博客资源
 *
 * @author marcher
 */
@Mapper
@Component
public interface BlogResourceMapper extends BaseMapper<BlogResource> {
}
