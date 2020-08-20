package xin.marcher.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogArticleTag;

/**
 * 博客文章标签关联
 *
 * @author marcher
 */
@Mapper
@Component
public interface BlogArticleTagMapper extends BaseMapper<BlogArticleTag> {
}
