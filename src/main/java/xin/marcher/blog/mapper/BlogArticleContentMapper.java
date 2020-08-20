package xin.marcher.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogArticleContent;

/**
 * 博客文章内容
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogArticleContentMapper extends BaseMapper<BlogArticleContent> {
	
}
