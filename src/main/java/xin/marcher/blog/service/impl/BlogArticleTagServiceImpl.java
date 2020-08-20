package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.mapper.BlogArticleTagMapper;
import xin.marcher.blog.model.BlogArticleTag;
import xin.marcher.blog.service.BlogArticleTagService;
import xin.marcher.framework.constants.GlobalConstant;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 博客文章标签关联
 *
 * @author marcher
 */
@Service
public class BlogArticleTagServiceImpl extends ServiceImpl<BlogArticleTagMapper, BlogArticleTag> implements BlogArticleTagService {

    @Autowired
    private BlogArticleTagMapper blogArticleTagMapper;

    @Override
    public void addBatch(Long articleId, List<Long> tagIdList) {
        List<BlogArticleTag> blogArticleTagList = toArticleTag(articleId, tagIdList);
        saveBatch(blogArticleTagList);
    }

    @Override
    public void removeByArticleId(Long articleId) {
        QueryWrapper<BlogArticleTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BlogArticleTag::getArticleId, articleId);

        remove(queryWrapper);
    }

    @Override
    public List<Long> getIds(Long articleId) {
        QueryWrapper<BlogArticleTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BlogArticleTag::getArticleId, articleId);

        List<BlogArticleTag> blogArticleTags = blogArticleTagMapper.selectList(queryWrapper);
        List<Long> tagIdList = blogArticleTags.stream().map(BlogArticleTag::getTagId).distinct().collect(Collectors.toList());

        return tagIdList;
    }

    private List<BlogArticleTag> toArticleTag(Long articleId, List<Long> tagIdList) {
        List<BlogArticleTag> blogArticleTagList = new ArrayList<>();
        for (Long tagId : tagIdList) {
            BlogArticleTag blogArticleTag = new BlogArticleTag();
            blogArticleTag.setArticleId(articleId);
            blogArticleTag.setTagId(tagId);
            blogArticleTag.setDeleted(GlobalConstant.NO_DELETED);
            blogArticleTagList.add(blogArticleTag);
        }

        return blogArticleTagList;
    }
}
