package xin.marcher.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogArticleType;

/**
 * 博客文章类型关联
 *
 * @author marcher
 */
@Mapper
@Component
public interface BlogArticleTypeMapper extends BaseMapper<BlogArticleType> {
}
