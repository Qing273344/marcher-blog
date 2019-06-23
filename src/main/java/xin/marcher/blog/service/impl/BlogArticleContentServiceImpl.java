package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.consts.Constant;
import xin.marcher.blog.dao.BlogArticleContentDao;
import xin.marcher.blog.entity.BlogArticleContent;
import xin.marcher.blog.service.BlogArticleContentService;
import xin.marcher.blog.utils.EmptyUtil;
import xin.marcher.blog.utils.RegexUtil;
import xin.marcher.rabbitmq.send.MqService;

import java.util.List;

/**
 * @author marcher
 */
@Service
public class BlogArticleContentServiceImpl extends ServiceImpl<BlogArticleContentDao, BlogArticleContent> implements BlogArticleContentService {

    @Autowired
    private BlogArticleContentDao blogArticleContentDao;

    @Override
    public void save(Long articleId, String contentMd) {
        BlogArticleContent blogArticleContent = toArticleContent(articleId, contentMd);
        blogArticleContentDao.insert(blogArticleContent);
    }

    @Override
    public BlogArticleContent get(Long id) {
        return blogArticleContentDao.selectById(id);
    }

    @Override
    public void update(Long articleId, String contentMd) {
        BlogArticleContent blogArticleContent = toArticleContent(articleId, contentMd);
        blogArticleContentDao.updateById(blogArticleContent);
    }

    private BlogArticleContent toArticleContent(Long articleId, String contentMd) {
        BlogArticleContent blogArticleContent = new BlogArticleContent();
        blogArticleContent.setArticleId(articleId);
        blogArticleContent.setContentMd(contentMd);
        blogArticleContent.setDeleted(Constant.NO_DELETED);
        return blogArticleContent;
    }

    private void converUrl(Long id) {
        BlogArticleContent articleContent = get(id);
        if (EmptyUtil.isEmpty(articleContent)) {
            return;
        }

        String content = articleContent.getContentMd();
        List<String> urlList = RegexUtil.match(content, RegexUtil.URL_REGEXP);


    }
}
