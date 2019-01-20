package xin.marcher.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xin.marcher.blog.entity.BlogArticleContent;

/**
 * 博客文章内容
 * 
 * @author marcher
 */
@Mapper
public interface BlogArticleContentDao extends BaseMapper<BlogArticleContent> {
	
}
