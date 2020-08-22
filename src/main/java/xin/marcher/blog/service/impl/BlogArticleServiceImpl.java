package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.enums.ArticleStatusEnum;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.dto.BlogArticleDTO;
import xin.marcher.blog.mapper.BlogArticleMapper;
import xin.marcher.blog.model.BlogArticle;
import xin.marcher.blog.model.BlogArticleContent;
import xin.marcher.blog.service.BlogArticleContentService;
import xin.marcher.blog.service.BlogArticleService;
import xin.marcher.blog.service.BlogArticleTagService;
import xin.marcher.blog.service.BlogArticleTypeService;
import xin.marcher.blog.utils.*;
import xin.marcher.blog.vo.AdminArticleListVO;
import xin.marcher.blog.vo.BlogArticleDetailsVO;
import xin.marcher.blog.vo.BlogArticleListVO;
import xin.marcher.framework.constants.GlobalConstant;
import xin.marcher.framework.exception.HintException;
import xin.marcher.framework.exception.ServiceException;
import xin.marcher.framework.mvc.request.PageParam;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.util.DateUtil;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marcher
 */
@Service
public class BlogArticleServiceImpl extends ServiceImpl<BlogArticleMapper, BlogArticle> implements BlogArticleService {

    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Autowired
    private BlogArticleContentService blogArticleContentService;

    @Autowired
    private BlogArticleTypeService blogArticleTypeService;

    @Autowired
    private BlogArticleTagService blogArticleTagService;

    @Override
    public Long publish(BlogArticleDTO blogArticleFrom) {
        // 新增
        if (EmptyUtil.isEmpty(blogArticleFrom.getArticleId())) {
            return add(blogArticleFrom);
        }
        return update(blogArticleFrom);
    }

    private Long add(BlogArticleDTO blogArticleFrom) {
        // 文章基础信息
        BlogArticle blogArticle = toArticle(blogArticleFrom);
        blogArticleMapper.insert(blogArticle);
        Long articleId = blogArticle.getArticleId();

        // 文章内容
        blogArticleContentService.save(articleId, blogArticleFrom.getArticleContent());

        // 文章标签
        blogArticleTagService.addBatch(articleId, blogArticleFrom.getTagIdList());

        // 文章类型
        blogArticleTypeService.add(articleId, blogArticleFrom.getTypeId());

        return articleId;
    }

    private Long update(BlogArticleDTO blogArticleFrom) {
        Long articleId = blogArticleFrom.getArticleId();
        BlogArticle blogArticle = toArticle(blogArticleFrom);
        // 文章信息
        blogArticleMapper.updateById(blogArticle);
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
    public BlogArticleDetailsVO details(long articleId) {
        BlogArticle blogArticle = getById(articleId);
        if (EmptyUtil.isEmpty(blogArticle)) {
            throw new HintException("我真的找不到你想要的这篇文章");
        }

        BlogArticleContent blogArticleContent = blogArticleContentService.getById(articleId);

        BlogArticleDetailsVO blogArticleDetailsVO = new BlogArticleDetailsVO();
        ObjectUtil.copyProperties(blogArticle, blogArticleDetailsVO);
        blogArticleDetailsVO.setArticleContentMd(blogArticleContent.getContentMd());

        return blogArticleDetailsVO;
    }

    @Override
    public BaseResult<PageResult<BlogArticleListVO>> query(QueryData query) {
        PageParam pageParam = new PageParam(query.getPageNo(), query.getPageSize());

        QueryWrapper<BlogArticle> queryWrapper = new QueryWrapper<>();
        if (EmptyUtil.isNotEmpty(query.getKeyword())) {
            queryWrapper.lambda().like(BlogArticle::getTitle, query.getKeyword());
        }
        queryWrapper.lambda().eq(BlogArticle::getStatus, ArticleStatusEnum.ARTICLE_STATUS_PUBLISH.getRealCode());
        queryWrapper.lambda().orderByDesc(BlogArticle::getArticleId);
        IPage<BlogArticle> pageWrapper = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        IPage<BlogArticle> blogArticlePage = blogArticleMapper.selectPage(pageWrapper, queryWrapper);

        List<BlogArticleListVO> blogArticleListVoList = new ArrayList<>();
        for (BlogArticle blogArticle : blogArticlePage.getRecords()) {
            BlogArticleListVO blogArticleListVO = new BlogArticleListVO();
            ObjectUtil.copyProperties(blogArticle, blogArticleListVO);
            blogArticleListVO.setTimeStr(DateUtil.formatDate(blogArticle.getCreateTime(), DateUtil.PATTERN_HYPHEN_DATE));
            blogArticleListVoList.add(blogArticleListVO);
        }

        PageResult<BlogArticleListVO> rest = PageResult.rest(blogArticleListVoList, blogArticlePage.getTotal(), pageParam);
        return BaseResult.success(rest);
    }

    @Override
    public BaseResult<PageResult<AdminArticleListVO>> queryAsAdmin(QueryData query) {
        PageParam pageParam = new PageParam(query.getPageNo(), query.getPageSize());

        QueryWrapper<BlogArticle> queryWrapper = new QueryWrapper<>();
        if (EmptyUtil.isNotEmpty(query.getKeyword())) {
            queryWrapper.lambda().like(BlogArticle::getTitle, query.getKeyword());
        }
        queryWrapper.lambda().orderByDesc(BlogArticle::getArticleId);
        IPage<BlogArticle> pageWrapper = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());
        IPage<BlogArticle> blogArticleIPage = blogArticleMapper.selectPage(pageWrapper, queryWrapper);

        List<BlogArticle> blogArticles = blogArticleIPage.getRecords();
        List<AdminArticleListVO> adminArticleListRespList = new ArrayList<>();
        for (BlogArticle blogArticle : blogArticles) {
            AdminArticleListVO adminArticleListVO = new AdminArticleListVO();
            ObjectUtil.copyProperties(blogArticle, adminArticleListVO);
            adminArticleListVO.setTimeStr(DateUtil.formatDate(blogArticle.getCreateTime(), DateUtil.PATTERN_HYPHEN_MINUTE_TIME));
            adminArticleListRespList.add(adminArticleListVO);
        }

        PageResult<AdminArticleListVO> rest = PageResult.rest(adminArticleListRespList, blogArticleIPage.getTotal(), pageParam);
        return BaseResult.success(rest);
    }

    @Override
    public BlogArticleDTO getAsEdit(Long id) {
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

        BlogArticleDTO blogArticleFrom = new BlogArticleDTO();
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
            throw new ServiceException(RspBaseCodeEnum.NOT_RESOURCE.getRealDesc());
        }

        Integer isComment = blogArticle.getIsComment();
        if (GlobalConstant.YES == isComment) {
            isComment = GlobalConstant.NO;
        } else {
            isComment = GlobalConstant.YES;
        }

        BlogArticle updateBlogArticle = new BlogArticle();
        updateBlogArticle.setArticleId(id);
        updateBlogArticle.setIsComment(isComment);
        blogArticleMapper.updateById(updateBlogArticle);
    }

    @Override
    public void top(Long id) {
        BlogArticle blogArticle = blogArticleMapper.selectById(id);
        if (EmptyUtil.isEmpty(blogArticle)) {
            throw new ServiceException(RspBaseCodeEnum.NOT_RESOURCE.getRealDesc());
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

    private BlogArticle toArticle(BlogArticleDTO blogArticleFrom) {
        BlogArticle blogArticle = new BlogArticle();
        blogArticle.setIsTop(GlobalConstant.NO);
        blogArticle.setIsComment(GlobalConstant.NO);
        ObjectUtil.copyProperties(blogArticleFrom, blogArticle);
        blogArticle.setDeleted(GlobalConstant.NO_DELETED);

        return blogArticle;
    }
}
