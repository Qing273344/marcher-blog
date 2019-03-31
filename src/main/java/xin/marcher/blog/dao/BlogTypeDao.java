package xin.marcher.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xin.marcher.blog.entity.BlogType;

/**
 * 博客类型
 * 
 * @author marcher
 */
@Mapper
public interface BlogTypeDao extends BaseMapper<BlogType> {
	
}
