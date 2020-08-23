package xin.marcher.blog.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogArticleTag;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * 博客文章标签关联
 *
 * @author marcher
 */
@Mapper
@Component
public interface BlogArticleTagMapper extends BaseMapper<BlogArticleTag> {

    default void removeByArticleId(Long articleId) {
        QueryWrapper<BlogArticleTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BlogArticleTag::getArticleId, articleId);
        delete(queryWrapper);
    }

    default List<BlogArticleTag> listByArticleId(Long articleId) {
        QueryWrapper<BlogArticleTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BlogArticleTag::getArticleId, articleId);

        return selectList(queryWrapper);
    }
}
