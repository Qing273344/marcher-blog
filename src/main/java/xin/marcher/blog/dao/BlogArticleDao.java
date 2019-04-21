package xin.marcher.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import xin.marcher.blog.entity.BlogArticle;

/**
 * 博客文章
 * 
 * @author marcher
 */
@Mapper
public interface BlogArticleDao extends BaseMapper<BlogArticle> {

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

}
