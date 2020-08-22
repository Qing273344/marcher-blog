package xin.marcher.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogArticleType;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

/**
 * 博客文章类型关联
 *
 * @author marcher
 */
@Mapper
@Component
public interface BlogArticleTypeMapper extends BaseMapper<BlogArticleType> {
}
