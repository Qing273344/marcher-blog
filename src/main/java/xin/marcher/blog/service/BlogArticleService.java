package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.dto.BlogArticleDTO;
import xin.marcher.blog.model.BlogArticle;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.vo.AdminArticleListVO;
import xin.marcher.blog.vo.BlogArticleDetailsVO;
import xin.marcher.blog.vo.BlogArticleListVO;
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
     * @param blogArticleFrom   文章信息
     * @return 文章id
     */
    Long publish(BlogArticleDTO blogArticleFrom);

    /**
     * 文章详情
     *
     * @param articleId 文章id
     * @return 文章详情
     */
    BlogArticleDetailsVO details(long articleId);

    /**
     * query 文章
     *
     * @param query query参数
     * @return 文章列表
     */
    BaseResult<PageResult<BlogArticleListVO>> query(QueryData query);

    /**
     * query 文章
     *
     * @param query query参数
     * @return 文章列表
     */
    BaseResult<PageResult<AdminArticleListVO>> queryAsAdmin(QueryData query);

    /**
     * get文章 - 编辑
     *
     * @param id    文章id
     * @return
     *      文章
     */
    BlogArticleDTO getAsEdit(Long id);

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
     * 浏览量+1
     * @param id    文章id
     */
    void viewsIncrease(Long id);
}

