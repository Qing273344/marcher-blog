package xin.marcher.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xin.marcher.blog.entity.BlogArticleType;

/**
 * 博客文章类型
 * 
 * @author marcher
 */
@Mapper
public interface BlogArticleTypeDao extends BaseMapper<BlogArticleType> {
	
}
