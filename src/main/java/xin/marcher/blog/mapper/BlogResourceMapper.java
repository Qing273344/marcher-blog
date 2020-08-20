package xin.marcher.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogResource;

/**
 * 博客资源
 *
 * @author marcher
 */
@Mapper
@Component
public interface BlogResourceMapper extends BaseMapper<BlogResource> {
}
