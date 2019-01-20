package xin.marcher.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xin.marcher.blog.entity.BlogArticleBrowse;

/**
 * 用户浏览文章
 * 
 * @author marcher
 */
@Mapper
public interface BlogArticleBrowseDao extends BaseMapper<BlogArticleBrowse> {
	
}
