package xin.marcher.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xin.marcher.blog.entity.BlogTags;

/**
 * 标签
 * 
 * @author marcher
 */
@Mapper
public interface BlogTagsDao extends BaseMapper<BlogTags> {
	
}
