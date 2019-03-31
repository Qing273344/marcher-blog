package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.entity.BlogArticle;
import xin.marcher.blog.from.BlogArticleFrom;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;

/**
 * 博客文章
 *
 * @author marcher
 */
public interface BlogArticleService extends IService<BlogArticle> {

    /**
     * 发布文章
     *
     * @param blogArticleFrom   文章信息
     * @return 文章id
     */
    Long publish(BlogArticleFrom blogArticleFrom);

    /**
     * 文章详情
     * @param articleId 文章id
     * @return 文章详情
     */
    String details(long articleId);

    /**
     * query文章
     * @param query query参数
     * @return 文章列表
     */
    Result query(Query<QueryData> query);

    /**
     * query 文章
     * @param query query参数
     * @return 文章列表
     */
    Result queryAsAdmin(Query<QueryData> query);

    /**
     * get文章 - 编辑
     *
     * @param id    文章id
     * @return
     *      文章
     */
    BlogArticleFrom getAsEdit(Long id);

    /**
     * update 评论设置
     *
     * @param id    文章id
     */
    void comment(Long id);

    /**
     * update 置顶设置
     *
     * @param id    文章id
     */
    void top(Long id);
}

