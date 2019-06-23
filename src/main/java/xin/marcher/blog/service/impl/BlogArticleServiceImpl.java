package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.consts.Constant;
import xin.marcher.blog.biz.enums.ArticleStatusEnum;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.common.exception.MarcherException;
import xin.marcher.blog.common.exception.MarcherHintException;
import xin.marcher.blog.dao.BlogArticleDao;
import xin.marcher.blog.entity.BlogArticle;
import xin.marcher.blog.entity.BlogArticleContent;
import xin.marcher.blog.dto.request.BlogArticleReq;
import xin.marcher.blog.service.BlogArticleContentService;
import xin.marcher.blog.service.BlogArticleService;
import xin.marcher.blog.service.BlogArticleTagService;
import xin.marcher.blog.service.BlogArticleTypeService;
import xin.marcher.blog.utils.*;
import xin.marcher.blog.dto.response.AdminArticleListResp;
import xin.marcher.blog.dto.response.BlogArticleDetailsResp;
import xin.marcher.blog.dto.response.BlogArticleListResp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marcher
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleDao, BlogArticle> implements BlogArticleService {

    @Autowired
    private BlogArticleDao blogArticleDao;

    @Autowired
    private BlogArticleContentService blogArticleContentService;

    @Autowired
    private BlogArticleTypeService blogArticleTypeService;

    @Autowired
    private BlogArticleTagService blogArticleTagService;

    @Override
    public Long publish(BlogArticleReq blogArticleFrom) {
        // 新增
        if (EmptyUtil.isEmpty(blogArticleFrom.getArticleId())) {
            return add(blogArticleFrom);
        }
        return update(blogArticleFrom);
    }

    private Long add(BlogArticleReq blogArticleFrom) {
        // 文章基础信息
        BlogArticle blogArticle = toArticle(blogArticleFrom);
        blogArticleDao.insert(blogArticle);
        Long articleId = blogArticle.getArticleId();

        // 文章内容
        blogArticleContentService.save(articleId, blogArticleFrom.getArticleContent());

        // 文章标签
        blogArticleTagService.addBatch(articleId, blogArticleFrom.getTagIdList());

        // 文章类型
        blogArticleTypeService.add(articleId, blogArticleFrom.getTypeId());

        return articleId;
    }

    private Long update(BlogArticleReq blogArticleFrom) {
        Long articleId = blogArticleFrom.getArticleId();
        BlogArticle blogArticle = toArticle(blogArticleFrom);
        // 文章信息
        blogArticleDao.updateById(blogArticle);
        // 文章内容
        blogArticleContentService.update(articleId, blogArticleFrom.getArticleContent());

        // del 旧标签
        blogArticleTagService.removeByArticleId(articleId);
        // add 新标签
        blogArticleTagService.addBatch(articleId, blogArticleFrom.getTagIdList());

        // del 旧类型
        blogArticleTypeService.removeByArticleId(articleId);
        // add 新类型
        blogArticleTypeService.add(articleId, blogArticleFrom.getTypeId());

        return articleId;
    }

    @Override
    public BlogArticleDetailsResp details(long articleId) {
        BlogArticle blogArticle = getById(articleId);
        if (EmptyUtil.isEmpty(blogArticle)) {
            throw new MarcherHintException("我真的找不到你想要的这篇文章");
        }

        BlogArticleContent blogArticleContent = blogArticleContentService.getById(articleId);

        BlogArticleDetailsResp blogArticleDetailsResp = new BlogArticleDetailsResp();
        ObjectUtil.copyProperties(blogArticle, blogArticleDetailsResp);
        blogArticleDetailsResp.setArticleContentMd(blogArticleContent.getContentMd());

        return blogArticleDetailsResp;
    }

    @Override
    public Result query(Query<QueryData> query) {
        QueryPage queryPage = query.getPage();
        QueryData queryData = query.getData();

        QueryWrapper<BlogArticle> queryWrapper = new QueryWrapper<>();
        if (EmptyUtil.isNotEmpty(queryData)) {
            if (EmptyUtil.isNotEmpty(queryData.getKeyword())) {
                queryWrapper.lambda().like(BlogArticle::getTitle, queryData.getKeyword());
            }
        }
        queryWrapper.lambda().eq(BlogArticle::getStatus, ArticleStatusEnum.ARTICLE_STATUS_PUBLISH.getCode());
        queryWrapper.lambda().orderByDesc(BlogArticle::getArticleId);
        IPage<BlogArticle> pageWrapper = new Page<>(queryPage.getCurPage(), queryPage.getLimit());
        IPage<BlogArticle> blogArticleIPage = blogArticleDao.selectPage(pageWrapper, queryWrapper);

        List<BlogArticle> blogArticles = blogArticleIPage.getRecords();
        List<BlogArticleListResp> blogArticleListRespList = new ArrayList<>();
        for (BlogArticle blogArticle : blogArticles) {
            BlogArticleListResp blogArticleListResp = new BlogArticleListResp();
            ObjectUtil.copyProperties(blogArticle, blogArticleListResp);
            blogArticleListResp.setTimeStr(DateUtil.formatDate(blogArticle.getCreateTime(), DateUtil.PATTERN_HYPHEN_DATE));
            blogArticleListRespList.add(blogArticleListResp);
        }

        PageUtil page = new PageUtil((int) blogArticleIPage.getTotal(), queryPage);
        Result data = new Result().put("list", blogArticleListRespList);

        return Result.successPage(data, page);
    }

    @Override
    public Result queryAsAdmin(Query<QueryData> query) {
        QueryPage queryPage = query.getPage();
        QueryData queryData = query.getData();

        QueryWrapper<BlogArticle> queryWrapper = new QueryWrapper<>();
        if (EmptyUtil.isNotEmpty(queryData)) {
            if (EmptyUtil.isNotEmpty(queryData.getKeyword())) {
                queryWrapper.lambda().like(BlogArticle::getTitle, queryData.getKeyword());
            }
        }
        queryWrapper.lambda().orderByDesc(BlogArticle::getArticleId);
        IPage<BlogArticle> pageWrapper = new Page<>(queryPage.getCurPage(), queryPage.getLimit());
        IPage<BlogArticle> blogArticleIPage = blogArticleDao.selectPage(pageWrapper, queryWrapper);

        List<BlogArticle> blogArticles = blogArticleIPage.getRecords();
        List<AdminArticleListResp> adminArticleListRespList = new ArrayList<>();
        for (BlogArticle blogArticle : blogArticles) {
            AdminArticleListResp adminArticleListResp = new AdminArticleListResp();
            ObjectUtil.copyProperties(blogArticle, adminArticleListResp);
            adminArticleListResp.setTimeStr(DateUtil.formatDate(blogArticle.getCreateTime(), DateUtil.PATTERN_HYPHEN_MINUTE_TIME));
            adminArticleListRespList.add(adminArticleListResp);
        }

        PageUtil page = new PageUtil((int) blogArticleIPage.getTotal(), queryPage);
        Result data = new Result().put("list", adminArticleListRespList);

        return Result.successPage(data, page);
    }

    @Override
    public BlogArticleReq getAsEdit(Long id) {
        // 文章信息
        BlogArticle blogArticle = blogArticleDao.selectById(id);
        if (EmptyUtil.isEmpty(blogArticle)) {
            return null;
        }

        // 文章内容
        BlogArticleContent blogArticleContent = blogArticleContentService.getById(blogArticle.getArticleId());

        // 文章标签id
        List<Long> tagIdList = blogArticleTagService.getIds(id);

        // 文章类型id
        Long typeId = blogArticleTypeService.getId(id);

        BlogArticleReq blogArticleFrom = new BlogArticleReq();
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
        BlogArticle blogArticle = blogArticleDao.selectById(id);
        if (EmptyUtil.isEmpty(blogArticle)) {
            throw new MarcherException(RspBaseCodeEnum.NOT_RESOURCE.getMsg());
        }

        Integer isComment = blogArticle.getIsComment();
        if (Constant.YES.intValue() == isComment) {
            isComment = Constant.NO;
        } else {
            isComment = Constant.YES;
        }

        BlogArticle updateBlogArticle = new BlogArticle();
        updateBlogArticle.setArticleId(id);
        updateBlogArticle.setIsComment(isComment);
        blogArticleDao.updateById(updateBlogArticle);
    }

    @Override
    public void top(Long id) {
        BlogArticle blogArticle = blogArticleDao.selectById(id);
        if (EmptyUtil.isEmpty(blogArticle)) {
            throw new MarcherException(RspBaseCodeEnum.NOT_RESOURCE.getMsg());
        }

        Integer isTop = blogArticle.getIsTop();
        if (Constant.YES.intValue() == isTop) {
            isTop = Constant.NO;
        } else {
            isTop = Constant.YES;
        }

        BlogArticle updateBlogArticle = new BlogArticle();
        updateBlogArticle.setArticleId(id);
        updateBlogArticle.setIsTop(isTop);
        blogArticleDao.updateById(updateBlogArticle);
    }

    @Override
    public Integer liked(Long id) {
        blogArticleDao.liked(id);

        BlogArticle blogArticle = blogArticleDao.selectById(id);
        return blogArticle.getLikedCount();
    }

    @Override
    public void viewsIncrease(Long id) {
        blogArticleDao.viewsIncrease(id);
    }

    private BlogArticle toArticle(BlogArticleReq blogArticleFrom) {
        BlogArticle blogArticle = new BlogArticle();
        blogArticle.setIsTop(Constant.NO);
        blogArticle.setIsComment(Constant.NO);
        ObjectUtil.copyProperties(blogArticleFrom, blogArticle);
        blogArticle.setDeleted(Constant.NO_DELETED);

        return blogArticle;
    }
}
