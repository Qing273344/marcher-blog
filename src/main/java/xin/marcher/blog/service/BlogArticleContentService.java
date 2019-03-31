package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.entity.BlogArticleContent;

/**
 * 博客文章内容
 *
 * @author marcher
 */
public interface BlogArticleContentService extends IService<BlogArticleContent> {

    /**
     * save 文章内容
     * @param articleId 文章id
     * @param contentMd 内容(md格式)
     */
    void save(Long articleId, String contentMd);

    /**
     * update 文章内容
     *
     * @param articleId         文章id
     * @param articleContent    文章内容
     */
    void update(Long articleId, String articleContent);
}

