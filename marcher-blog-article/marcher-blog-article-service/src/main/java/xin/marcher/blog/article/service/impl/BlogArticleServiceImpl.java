package xin.marcher.blog.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.article.biz.enums.ArticleStatusEnum;
import xin.marcher.blog.article.client.model.request.BlogArticleReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleDetailsResp;
import xin.marcher.blog.article.client.model.response.BlogArticleListResp;
import xin.marcher.blog.article.client.model.response.BlogArticleResp;
import xin.marcher.blog.article.convert.BlogArticleConvert;
import xin.marcher.blog.article.domain.BlogArticle;
import xin.marcher.blog.article.domain.BlogArticleContent;
import xin.marcher.blog.article.exception.RealmArticleException;
import xin.marcher.blog.article.mapper.BlogArticleMapper;
import xin.marcher.blog.article.service.BlogArticleContentService;
import xin.marcher.blog.article.service.BlogArticleService;
import xin.marcher.blog.article.service.BlogArticleTagService;
import xin.marcher.blog.article.service.BlogArticleTypeService;
import xin.marcher.framework.common.constants.GlobalCodeEnum;
import xin.marcher.framework.common.constants.GlobalConstant;
import xin.marcher.framework.common.exception.BusinessException;
import xin.marcher.framework.common.mvc.request.BaseQuery;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.util.EmptyUtil;
import xin.marcher.framework.common.util.ObjectUtil;
import xin.marcher.framework.common.wrapper.PageWO;

import java.util.List;

/**
 * @author marcher
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements BlogArticleService {

    private final BlogArticleContentService blogArticleContentService;
    private final BlogArticleTypeService blogArticleTypeService;
    private final BlogArticleTagService blogArticleTagService;
    private final BlogArticleMapper blogArticleMapper;

    @Autowired
    public BlogArticleServiceImpl(BlogArticleContentService blogArticleContentService,
                                  BlogArticleTypeService blogArticleTypeService,
                                  BlogArticleTagService blogArticleTagService,
                                  BlogArticleMapper blogArticleMapper) {
        this.blogArticleContentService = blogArticleContentService;
        this.blogArticleTypeService = blogArticleTypeService;
        this.blogArticleTagService = blogArticleTagService;
        this.blogArticleMapper = blogArticleMapper;
    }

    @Override
    public Long publish(BlogArticleReqs reqs) {
        // 新增
        if (EmptyUtil.isEmpty(reqs.getArticleId())) {
            return add(reqs);
        }
        return update(reqs);
    }

    private Long add(BlogArticleReqs reqs) {
        // 文章基础信息
        BlogArticle blogArticle = toArticle(reqs);
        blogArticleMapper.insert(blogArticle);
        Long articleId = blogArticle.getArticleId();

        // 文章内容
        blogArticleContentService.save(articleId, reqs.getArticleContent());

        // 文章标签
        blogArticleTagService.addBatch(articleId, reqs.getTagIdList());

        // 文章类型
        blogArticleTypeService.add(articleId, reqs.getTypeId());

        return articleId;
    }

    private Long update(BlogArticleReqs reqs) {
        Long articleId = reqs.getArticleId();
        BlogArticle blogArticle = toArticle(reqs);
        // 文章信息
        blogArticleMapper.updateById(blogArticle);
        // 文章内容
        blogArticleContentService.update(articleId, reqs.getArticleContent());

        // 标签
        blogArticleTagService.replace(articleId, reqs.getTagIdList());

        // 类型
        blogArticleTypeService.replace(articleId, reqs.getTypeId());

        return articleId;
    }

    @Override
    public BlogArticleDetailsResp detail(long articleId) {
        BlogArticle blogArticle = getById(articleId);
        if (EmptyUtil.isEmpty(blogArticle)) {
            throw new RealmArticleException("我真的找不到你想要的这篇文章");
        }

        BlogArticleContent blogArticleContent = blogArticleContentService.getById(articleId);

        BlogArticleDetailsResp resp = BlogArticleConvert.INSTANCE.convertResp(blogArticle);
        resp.setArticleContentMd(blogArticleContent.getContentMd());
        return resp;
    }

    @Override
    public BaseResult<PageWO<BlogArticleListResp>> query(BaseQuery query) {
        PageWO<BlogArticle> pageWO = blogArticleMapper.query(query, ArticleStatusEnum.ARTICLE_STATUS_PUBLISH.getRealCode());
        if (pageWO.hashEmpty()) {
            return pageEmpty();
        }

        List<BlogArticleListResp> respList = BlogArticleConvert.INSTANCE.convertListResp(pageWO.getList());
        PageWO<BlogArticleListResp> respPageWO = PageWO.rest(respList, pageWO.getTotal());
        return BaseResult.success(respPageWO);
    }

    @Override
    public BaseResult<PageWO<BlogArticleListResp>> queryAsAdmin(BaseQuery query) {
        PageWO<BlogArticle> pageWO = blogArticleMapper.query(query, null);
        if (pageWO.hashEmpty()) {
            return pageEmpty();
        }

        List<BlogArticleListResp> respList = BlogArticleConvert.INSTANCE.convertListResp(pageWO.getList());
        PageWO<BlogArticleListResp> respPageWO = PageWO.rest(respList, pageWO.getTotal());
        return BaseResult.success(respPageWO);
    }

    @Override
    public BlogArticleResp getAsEdit(Long id) {
        // 文章信息
        BlogArticle blogArticle = blogArticleMapper.selectById(id);
        if (EmptyUtil.isEmpty(blogArticle)) {
            return null;
        }

        // 文章内容
        BlogArticleContent blogArticleContent = blogArticleContentService.getById(blogArticle.getArticleId());

        // 文章标签id
        List<Long> tagIdList = blogArticleTagService.getIds(id);

        // 文章类型id
        Long typeId = blogArticleTypeService.getId(id);

        BlogArticleResp blogArticleFrom = new BlogArticleResp();
        blogArticleFrom.setArticleId(blogArticle.getArticleId());
        blogArticleFrom.setTitle(blogArticle.getTitle());
        blogArticleFrom.setStatus(blogArticle.getStatus());
        blogArticleFrom.setIsComment(blogArticle.getIsComment());
        blogArticleFrom.setArticleContent(blogArticleContent.getContentMd());
        blogArticleFrom.setTagIdList(tagIdList);
        blogArticleFrom.setTypeId(typeId);

        return blogArticleFrom;
    }

    @Override
    public void comment(Long id) {
        BlogArticle blogArticle = blogArticleMapper.selectById(id);
        if (EmptyUtil.isEmpty(blogArticle)) {
            throw new BusinessException(GlobalCodeEnum.GL_404.getRealDesc());
        }

        Integer isComment = blogArticle.getIsComment();
        isComment = GlobalConstant.YES == isComment ? GlobalConstant.NO : GlobalConstant.YES;

        BlogArticle updateBlogArticle = new BlogArticle();
        updateBlogArticle.setArticleId(id);
        updateBlogArticle.setIsComment(isComment);
        blogArticleMapper.updateById(updateBlogArticle);
    }

    @Override
    public void top(Long id) {
        BlogArticle blogArticle = blogArticleMapper.selectById(id);
        if (EmptyUtil.isEmpty(blogArticle)) {
            throw new BusinessException(GlobalCodeEnum.GL_404.getRealDesc());
        }

        int isTop = GlobalConstant.YES == blogArticle.getIsTop() ? GlobalConstant.NO : GlobalConstant.YES;

        BlogArticle updateBlogArticle = new BlogArticle();
        updateBlogArticle.setArticleId(id);
        updateBlogArticle.setIsTop(isTop);
        blogArticleMapper.updateById(updateBlogArticle);
    }

    @Override
    public Integer liked(Long id) {
        blogArticleMapper.liked(id);

        BlogArticle blogArticle = blogArticleMapper.selectById(id);
        return blogArticle.getLikedCount();
    }

    @Override
    public void viewsIncrease(Long id) {
        blogArticleMapper.viewsIncrease(id);
    }

    private BlogArticle toArticle(BlogArticleReqs reqs) {
        BlogArticle blogArticle = new BlogArticle();
        blogArticle.setIsTop(GlobalConstant.NO);
        blogArticle.setIsComment(GlobalConstant.NO);
        ObjectUtil.copyProperties(reqs, blogArticle);

        return blogArticle;
    }
}
