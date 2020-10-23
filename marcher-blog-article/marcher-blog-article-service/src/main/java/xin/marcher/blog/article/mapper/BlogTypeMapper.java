package xin.marcher.blog.article.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.article.domain.BlogType;
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
