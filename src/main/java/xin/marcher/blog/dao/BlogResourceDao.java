package xin.marcher.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xin.marcher.blog.entity.BlogResource;

/**
 * 博客资源
 *
 * @author marcher
 */
@Mapper
public interface BlogResourceDao extends BaseMapper<BlogResource> {
}
