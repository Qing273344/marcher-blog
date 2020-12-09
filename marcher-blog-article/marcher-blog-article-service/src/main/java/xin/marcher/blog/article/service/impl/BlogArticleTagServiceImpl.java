package xin.marcher.blog.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.article.domain.BlogArticleTag;
import xin.marcher.blog.article.mapper.BlogArticleTagMapper;
import xin.marcher.blog.article.service.BlogArticleTagService;
import xin.marcher.framework.common.util.CollectionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 博客文章标签关联
 *
 * @author marcher
 */
@Service
public class BlogArticleTagServiceImpl extends ServiceImpl<BlogArticleTagMapper, BlogArticleTag> implements BlogArticleTagService {

    private final BlogArticleTagMapper blogArticleTagMapper;

    @Autowired
    public BlogArticleTagServiceImpl(BlogArticleTagMapper blogArticleTagMapper) {
        this.blogArticleTagMapper = blogArticleTagMapper;
    }

    @Override
    public void addBatch(Long articleId, List<Long> tagIdList) {
        List<BlogArticleTag> blogArticleTagList = toArticleTag(articleId, tagIdList);
        saveBatch(blogArticleTagList);
    }

    @Override
    public void removeByArticleId(Long articleId) {
        blogArticleTagMapper.removeByArticleId(articleId);
    }

    @Override
    public List<Long> getIds(Long articleId) {
        List<BlogArticleTag> blogArticleTags = blogArticleTagMapper.listByArticleId(articleId);
        return CollectionUtil.convertList(blogArticleTags, BlogArticleTag::getTagId);
    }

    @Override
    public void replace(Long articleId, List<Long> tagIdList) {
        // del 旧标签
        removeByArticleId(articleId);
        // add 新标签
        addBatch(articleId, tagIdList);
    }

    private List<BlogArticleTag> toArticleTag(Long articleId, List<Long> tagIdList) {
        List<BlogArticleTag> blogArticleTagList = new ArrayList<>();
        for (Long tagId : tagIdList) {
            BlogArticleTag blogArticleTag = new BlogArticleTag();
            blogArticleTag.setArticleId(articleId);
            blogArticleTag.setTagId(tagId);
            blogArticleTagList.add(blogArticleTag);
        }
        return blogArticleTagList;
    }
}
