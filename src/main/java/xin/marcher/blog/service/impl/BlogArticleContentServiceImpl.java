package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.consts.Constant;
import xin.marcher.blog.dao.BlogArticleContentDao;
import xin.marcher.blog.entity.BlogArticleContent;
import xin.marcher.blog.service.BlogArticleContentService;
import xin.marcher.blog.utils.EmptyUtil;

import javax.annotation.Resource;

/**
 * @author marcher
 */
@Service
public class BlogArticleContentServiceImpl extends ServiceImpl<BlogArticleContentDao, BlogArticleContent> implements BlogArticleContentService {

    @Resource
    private BlogArticleContentDao blogArticleContentDao;

    @Override
    public void save(Long articleId, String contentMd) {
        BlogArticleContent blogArticleContent = toArticleContent(articleId, contentMd, false);
        blogArticleContentDao.insert(blogArticleContent);
    }

    @Override
    public void update(Long articleId, String contentMd) {
        BlogArticleContent blogArticleContent = toArticleContent(articleId, contentMd, true);
        blogArticleContentDao.updateById(blogArticleContent);
    }

    private BlogArticleContent toArticleContent(Long articleId, String contentMd, boolean isEdit) {
        Long now = System.currentTimeMillis();

        BlogArticleContent blogArticleContent = new BlogArticleContent();
        blogArticleContent.setArticleId(articleId);
        blogArticleContent.setContentMd(contentMd);
        if (isEdit) {
            blogArticleContent.setCreateTime(now);
            blogArticleContent.setDeleted(Constant.NO_DELETED);
        }
        blogArticleContent.setModifyTime(now);
        return blogArticleContent;
    }
}
