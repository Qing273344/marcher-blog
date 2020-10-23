package xin.marcher.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.article.client.model.request.BlogArticleReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleDetailsResp;
import xin.marcher.blog.article.client.model.response.BlogArticleListResp;
import xin.marcher.blog.article.client.model.response.BlogArticleResp;
import xin.marcher.blog.article.domain.BlogArticle;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;

/**
 * 博客文章
 *
 * @author marcher
 */
public interface BlogArticleService extends IService<BlogArticle> {

    /**
     * 发布文章
     *
     * @param reqs   文章信息
     * @return 文章id
     */
    Long publish(BlogArticleReqs reqs);

    /**
     * 文章详情
     *
     * @param articleId 文章id
     * @return 文章详情
     */
    BlogArticleDetailsResp detail(long articleId);

    /**
     * query 文章
     *
     * @param query query参数
     * @return 文章列表
     */
    BaseResult<PageResult<BlogArticleListResp>> query(BaseQuery query);

    /**
     * query 文章
     *
     * @param query query参数
     * @return 文章列表
     */
    BaseResult<PageResult<BlogArticleResp>> queryAsAdmin(BaseQuery query);

    /**
     * get文章 - 编辑
     *
     * @param id    文章id
     * @return
     *      文章
     */
    BlogArticleResp getAsEdit(Long id);

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

    /**
     * 文章点赞
     *
     * @param id    文章id
     * @return
     *      点赞之后的赞点数
     */
    Integer liked(Long id);

    /**
     * 浏览量 +1
     * @param id    文章id
     */
    void viewsIncrease(Long id);
}

