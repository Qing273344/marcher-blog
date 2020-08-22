package xin.marcher.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogArticleContent;
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
