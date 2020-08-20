package xin.marcher.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogTag;

/**
 * 标签
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogTagMapper extends BaseMapper<BlogTag> {

}
