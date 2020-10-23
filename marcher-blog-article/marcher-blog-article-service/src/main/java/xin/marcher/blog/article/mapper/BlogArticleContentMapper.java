package xin.marcher.blog.article.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.article.domain.BlogArticleContent;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

/**
 * 博客文章内容
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogArticleContentMapper extends BaseMapper<BlogArticleContent> {
	
}
