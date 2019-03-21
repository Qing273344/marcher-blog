package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.consts.Constant;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.common.exception.MarcherHintException;
import xin.marcher.blog.dao.BlogArticleTypeDao;
import xin.marcher.blog.entity.BlogArticleType;
import xin.marcher.blog.entity.BlogTag;
import xin.marcher.blog.from.BlogArticleTypeFrom;
import xin.marcher.blog.service.BlogArticleTypeService;
import xin.marcher.blog.utils.*;
import xin.marcher.blog.vo.BlogArticleTypeVo;
import xin.marcher.blog.vo.BlogTagVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author marcher
 */
@Service
public class BlogArticleTypeServiceImpl extends ServiceImpl<BlogArticleTypeDao, BlogArticleType> implements BlogArticleTypeService {

    @Resource
    private BlogArticleTypeDao blogArticleTypeDao;

    @Override
    public BlogArticleTypeVo get(Long id) {
        BlogArticleType blogArticleType = blogArticleTypeDao.selectById(id);
        if (EmptyUtil.isEmpty(blogArticleType)) {
            return null;
        }

        BlogArticleTypeVo blogArticleTypeVo = new BlogArticleTypeVo();
        ObjectUtil.copyProperties(blogArticleType, blogArticleTypeVo);

        return blogArticleTypeVo;
    }

    @Override
    public List<BlogArticleTypeVo> listAll() {
        List<BlogArticleType> blogArticleTypes = blogArticleTypeDao.selectList(null);
        if (EmptyUtil.isEmpty(blogArticleTypes)) {
            return new ArrayList<>();
        }

        List<BlogArticleTypeVo> blogArticleTypeVoList = new ArrayList<>();
        for (BlogArticleType blogArticleType : blogArticleTypes) {
            BlogArticleTypeVo blogArticleTypeVo = new BlogArticleTypeVo();
            ObjectUtil.copyProperties(blogArticleType, blogArticleTypeVo);
            blogArticleTypeVoList.add(blogArticleTypeVo);
        }

        return blogArticleTypeVoList;
    }

    @Override
    public Result query(Query<QueryData> query) {
        QueryWrapper<BlogArticleType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogArticleType::getName, query.getData().getKeyword());
        IPage<BlogArticleType> queryPage = new Page<>(query.getPage().getCurPage(), query.getPage().getLimit());

        IPage<BlogArticleType> blogArticleTypeIPage = blogArticleTypeDao.selectPage(queryPage, queryWrapper);

        List<BlogArticleType> blogArticleTypes = blogArticleTypeIPage.getRecords();
        List<BlogArticleTypeVo> blogArticleTypeVoList = new ArrayList<>();
        for (BlogArticleType blogTag : blogArticleTypes) {
            BlogArticleTypeVo blogTagVo = new BlogArticleTypeVo();
            ObjectUtil.copyProperties(blogTag, blogTagVo);
            blogArticleTypeVoList.add(blogTagVo);
        }

        PageUtil page = new PageUtil((int) blogArticleTypeIPage.getTotal(), query.getPage());
        Result data = new Result().put("list", blogArticleTypeVoList);

        return Result.successPage(data, page);
    }

    @Override
    public void create(BlogArticleTypeFrom blogArticleTypeFrom) {
        // 校验同名类型
        checkAlikeName(blogArticleTypeFrom.getName());

        BlogArticleType blogArticleType = toBlogArticleType(blogArticleTypeFrom);
        blogArticleTypeDao.insert(blogArticleType);
    }

    @Override
    public void update(BlogArticleTypeFrom blogArticleTypeFrom) {
        // 校验同名标签
        checkAlikeName(blogArticleTypeFrom.getTypeId(), blogArticleTypeFrom.getName());

        BlogArticleType blogArticleType = toBlogArticleType(blogArticleTypeFrom);
        blogArticleType.setTypeId(blogArticleTypeFrom.getTypeId());

        blogArticleTypeDao.updateById(blogArticleType);
    }

    @Override
    public void remove(List<Long> ids) {
        blogArticleTypeDao.deleteBatchIds(ids);
    }

    private BlogArticleType toBlogArticleType(BlogArticleTypeFrom blogArticleTypeFrom) {
        Long now = System.currentTimeMillis();

        BlogArticleType blogArticleType = new BlogArticleType();
        ObjectUtil.copyProperties(blogArticleTypeFrom, blogArticleType);
        blogArticleType.setTypeId(null);
        blogArticleType.setCreateTime(now);
        blogArticleType.setModifyTime(now);
        blogArticleType.setDeleted(Constant.NO_DELETED);

        return blogArticleType;
    }

    private void checkAlikeName(String name) {
        QueryWrapper<BlogArticleType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogArticleType::getName, name);
        Integer rowNum = blogArticleTypeDao.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new MarcherHintException("已存在该名称类型", RspBaseCodeEnum.PARAM_ILLEGAL.getCode());
        }
    }

    private void checkAlikeName(Long typeId, String name) {
        QueryWrapper<BlogArticleType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogArticleType::getName, name);
        queryWrapper.lambda().ne(BlogArticleType::getTypeId, typeId);
        Integer rowNum = blogArticleTypeDao.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new MarcherHintException("已存在该名称类型", RspBaseCodeEnum.PARAM_ILLEGAL.getCode());
        }
    }
}
