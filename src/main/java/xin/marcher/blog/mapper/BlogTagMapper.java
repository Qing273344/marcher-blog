package xin.marcher.blog.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogTag;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

/**
 * 标签
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogTagMapper extends BaseMapper<BlogTag> {

}
