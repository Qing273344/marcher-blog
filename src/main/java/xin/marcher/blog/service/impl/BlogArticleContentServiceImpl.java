package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.consts.Constant;
import xin.marcher.blog.biz.property.OssProperties;
import xin.marcher.blog.biz.property.RabbitMqProperties;
import xin.marcher.blog.dao.BlogArticleContentDao;
import xin.marcher.blog.entity.BlogArticleContent;
import xin.marcher.blog.service.BlogArticleContentService;
import xin.marcher.blog.service.OssService;
import xin.marcher.blog.utils.EmptyUtil;
import xin.marcher.blog.utils.RegexUtil;
import xin.marcher.rabbitmq.send.MqService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author marcher
 */
@Service
public class BlogArticleContentServiceImpl extends ServiceImpl<BlogArticleContentDao, BlogArticleContent> implements BlogArticleContentService {

    @Autowired
    private BlogArticleContentDao blogArticleContentDao;

    @Autowired
    private OssService ossService;

    @Autowired
    private OssProperties ossProperties;

    @Autowired
    private MqService mqService;

    @Autowired
    private RabbitMqProperties rabbitMqProperties;

    @Override
    public void save(Long articleId, String contentMd) {
        BlogArticleContent blogArticleContent = toArticleContent(articleId, contentMd);
        blogArticleContentDao.insert(blogArticleContent);

        producer(articleId);
    }

    @Override
    public BlogArticleContent get(Long id) {
        return blogArticleContentDao.selectById(id);
    }

    @Override
    public void update(Long articleId, String contentMd) {
        updateContent(articleId, contentMd);

        producer(articleId);
    }

    /**
     * 更新文章内容
     *
     * @param articleId     文章id
     * @param contentMd     文章内容
     */
    private void updateContent(Long articleId, String contentMd) {
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

    /**
     * 生产者发送消息
     *
     * @param articleId 文章id
     */
    private void producer(Long articleId) {
        mqService.convertAndSend(rabbitMqProperties.getExchange(), rabbitMqProperties.getRoutekey(), articleId);
    }

    /**
     * 图片url转换
     * 临时桶转移至正式桶
     *
     * @param articleId 文章id
     */
    @Override
    public void convertUrl(Long articleId) {
        BlogArticleContent articleContent = get(articleId);
        if (EmptyUtil.isEmpty(articleContent)) {
            return;
        }

        String content = articleContent.getContentMd();
        List<String> contentImgUrls = RegexUtil.match(content, RegexUtil.URL_REGEXP);

        List<String> newImgUrlList = contentImgUrls.stream()
                .filter(url -> url.contains(ossProperties.getAliyunTempRegion()))
                .collect(Collectors.toList());

        if (EmptyUtil.isEmpty(newImgUrlList)) {
            return;
        }

        Map<String, String> tempNewUrlMap = new HashMap<>();
        newImgUrlList.forEach(newUrl -> {
            String destUrl = ossService.copyOssObject(newUrl, "content", articleId.toString());
            String destImgUrl = ossProperties.getAliyunRegion() + destUrl;
            tempNewUrlMap.put(newUrl, destImgUrl);
        });

        String newContent = content;
        for (Map.Entry<String, String> entry : tempNewUrlMap.entrySet()) {
            String tempUrl = entry.getKey();
            String destUrl = entry.getValue();

            newContent = newContent.replace(tempUrl, destUrl);
        }

        updateContent(articleId, newContent);
    }
}
