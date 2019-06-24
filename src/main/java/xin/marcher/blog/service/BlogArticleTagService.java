package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.entity.BlogArticleTag;

import java.util.List;

/**
 * 博客文章标签关联
 *
 * @author marcher
 */
public interface BlogArticleTagService extends IService<BlogArticleTag> {

    /**
     * add 文章标签
     *
     * @param articleId 文章id
     * @param tagIdList 文章标签id
     */
    void addBatch(Long articleId, List<Long> tagIdList);

    /**
     * remove 根据文章id
     *
     * @param articleId 文章id
     */
    void removeByArticleId(Long articleId);

    /**
     * get 文章标签id
     *
     * @param articleId 文章id
     * @return
     *      文章标签id
     */
    List<Long> getIds(Long articleId);
}
