package xin.marcher.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogType;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

/**
 * 博客类型
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogTypeMapper extends BaseMapper<BlogType> {
	
}
