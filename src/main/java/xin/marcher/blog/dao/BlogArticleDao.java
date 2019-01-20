package xin.marcher.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xin.marcher.blog.entity.BlogArticle;

/**
 * 博客文章
 * 
 * @author marcher
 */
@Mapper
public interface BlogArticleDao extends BaseMapper<BlogArticle> {
	
}
