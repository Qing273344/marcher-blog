package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.property.RabbitMqProperties;
import xin.marcher.blog.mapper.BlogArticleContentMapper;
import xin.marcher.blog.model.BlogArticleContent;
import xin.marcher.blog.service.BlogArticleContentService;
import xin.marcher.blog.service.OssService;
import xin.marcher.framework.constants.GlobalConstant;
import xin.marcher.framework.oss.property.OssProperties;
import xin.marcher.framework.rabbit.send.MqService;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.RegexUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author marcher
 */
@Service
public class BlogArticleContentServiceImpl extends ServiceImpl<BlogArticleContentMapper, BlogArticleContent> implements BlogArticleContentService {

    @Autowired
    private BlogArticleContentMapper blogArticleContentMapper;

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
        blogArticleContentMapper.insert(blogArticleContent);

        producer(articleId);
    }

    @Override
    public BlogArticleContent get(Long id) {
        return blogArticleContentMapper.selectById(id);
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
        blogArticleContentMapper.updateById(blogArticleContent);
    }

    private BlogArticleContent toArticleContent(Long articleId, String contentMd) {
        BlogArticleContent blogArticleContent = new BlogArticleContent();
        blogArticleContent.setArticleId(articleId);
        blogArticleContent.setContentMd(contentMd);
        blogArticleContent.setDeleted(GlobalConstant.NO_DELETED);
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
        List<String> contentImgUrls = RegexUtil.match(content, RegexUtil.REGEX_URL);

        List<String> newImgUrlList = contentImgUrls.stream()
                .filter(url -> url.contains(ossProperties.getTempHost()))
                .collect(Collectors.toList());

        Map<String, String> tempNewUrlMap = new HashMap<>(8);
        newImgUrlList.forEach(newUrl -> {
            String destUrl = ossService.copyOssObject(newUrl, "content", articleId.toString());
            String destImgUrl = ossProperties.getHost() + destUrl;
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
