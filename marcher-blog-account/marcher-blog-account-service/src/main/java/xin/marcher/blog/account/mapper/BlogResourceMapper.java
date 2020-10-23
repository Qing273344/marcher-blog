package xin.marcher.blog.account.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.account.domain.BlogResource;
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
