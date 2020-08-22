package xin.marcher.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogArticleBrowse;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

/**
 * 用户浏览文章
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogArticleBrowseMapper extends BaseMapper<BlogArticleBrowse> {
	
}
