package xin.marcher.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xin.marcher.blog.entity.BlogArticleTag;

/**
 * 博客文章标签关联
 *
 * @author marcher
 */
@Mapper
public interface BlogArticleTagDao extends BaseMapper<BlogArticleTag> {
}
