package xin.marcher.blog.article.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.article.domain.BlogArticleType;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

/**
 * 博客文章类型关联
 *
 * @author marcher
 */
@Mapper
@Component
public interface BlogArticleTypeMapper extends BaseMapper<BlogArticleType> {

    default BlogArticleType getByArticleId(Long articleId) {
        QueryWrapper<BlogArticleType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BlogArticleType::getArticleId, articleId);

        return selectOne(queryWrapper);
    }

    default void removeByArticleId(Long articleId) {
        QueryWrapper<BlogArticleType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BlogArticleType::getArticleId, articleId);
        delete(queryWrapper);
    }
}
