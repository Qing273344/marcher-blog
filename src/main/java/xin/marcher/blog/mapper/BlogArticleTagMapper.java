package xin.marcher.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogArticleTag;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

/**
 * 博客文章标签关联
 *
 * @author marcher
 */
@Mapper
@Component
public interface BlogArticleTagMapper extends BaseMapper<BlogArticleTag> {
}
