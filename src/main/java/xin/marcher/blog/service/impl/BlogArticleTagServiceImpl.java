package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.consts.Constant;
import xin.marcher.blog.dao.BlogArticleTagDao;
import xin.marcher.blog.entity.BlogArticleTag;
import xin.marcher.blog.service.BlogArticleTagService;
import xin.marcher.blog.utils.DateUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 博客文章标签关联
 *
 * @author marcher
 */
@Service
public class BlogArticleTagServiceImpl extends ServiceImpl<BlogArticleTagDao, BlogArticleTag> implements BlogArticleTagService {

    @Resource
    private BlogArticleTagDao blogArticleTagDao;

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

        List<BlogArticleTag> blogArticleTags = blogArticleTagDao.selectList(queryWrapper);
        List<Long> tagIdList = blogArticleTags.stream().map(BlogArticleTag::getTagId).distinct().collect(Collectors.toList());

        return tagIdList;
    }

    private List<BlogArticleTag> toArticleTag(Long articleId, List<Long> tagIdList) {
        List<BlogArticleTag> blogArticleTagList = new ArrayList<>();
        for (Long tagId : tagIdList) {
            BlogArticleTag blogArticleTag = new BlogArticleTag();
            blogArticleTag.setArticleId(articleId);
            blogArticleTag.setTagId(tagId);
            blogArticleTag.setDeleted(Constant.NO_DELETED);
            blogArticleTagList.add(blogArticleTag);
        }

        return blogArticleTagList;
    }
}
