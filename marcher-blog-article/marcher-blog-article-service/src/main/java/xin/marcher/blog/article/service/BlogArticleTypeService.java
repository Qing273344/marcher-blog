package xin.marcher.blog.article.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.article.domain.BlogArticleType;

/**
 * 博客文章类型关联
 *
 * @author marcher
 */
public interface BlogArticleTypeService extends IService<BlogArticleType> {

    /**
     * add 文章类型
     *
     * @param articleId 文章id
     * @param typeId    类型id
     */
    void add(Long articleId, Long typeId);

    /**
     * remove 根据文章id
     *
     * @param articleId 文章id
     */
    void removeByArticleId(Long articleId);

    /**
     * get 文章类型id
     *
     * @param articleId    文章id
     * @return
     *      文章类型id
     */
    Long getId(Long articleId);

    void replace(Long articleId, Long typeId);
}
