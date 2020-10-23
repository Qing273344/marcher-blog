package xin.marcher.blog.article.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import xin.marcher.blog.article.domain.BlogArticle;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.request.PageParam;
import xin.marcher.framework.mybatis.mapper.BaseMapper;
import xin.marcher.framework.mybatis.page.PageWrapper;
import xin.marcher.framework.mybatis.query.BaseQueryWrapper;

/**
 * 博客文章
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogArticleMapper extends BaseMapper<BlogArticle> {

    /**
     * 文章点赞
     *
     * @param id    文章id
     */
    @Update("UPDATE blog_article SET liked_count = liked_count + 1 WHERE article_id = #{articleId};")
    void liked(@Param("articleId") Long id);

    /**
     * 浏览量+1
     * @param id    文章id
     */
    @Update("UPDATE blog_article SET views_count = views_count + 1 WHERE article_id = #{articleId};")
    void viewsIncrease(@Param("articleId") Long id);

    default PageWrapper<BlogArticle> query(BaseQuery query, Integer status, PageParam pageParam) {
        BaseQueryWrapper<BlogArticle> queryWrapper = new BaseQueryWrapper<>();
        queryWrapper.likeIfPresent(BlogArticle::getTitle, query.getKeyword());
        queryWrapper.eqIfPresent(BlogArticle::getStatus, status);
        queryWrapper.lambda().orderByDesc(BlogArticle::getArticleId);
        IPage<BlogArticle> blogArticlePage = selectPage(pageWrapper(pageParam), queryWrapper);
        return convert(blogArticlePage);
    }
}
