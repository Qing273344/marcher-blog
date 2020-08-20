package xin.marcher.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogArticle;

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

}
