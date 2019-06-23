package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.consts.Constant;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.common.exception.MarcherHintException;
import xin.marcher.blog.dao.BlogTypeDao;
import xin.marcher.blog.entity.BlogType;
import xin.marcher.blog.dto.request.BlogArticleTypeFrom;
import xin.marcher.blog.service.BlogTypeService;
import xin.marcher.blog.utils.*;
import xin.marcher.blog.dto.response.BlogArticleTypeResp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marcher
 */
@Service
public class BlogTypeServiceImpl extends ServiceImpl<BlogTypeDao, BlogType> implements BlogTypeService {

    @Autowired
    private BlogTypeDao blogTypeDao;

    @Override
    public BlogArticleTypeResp get(Long id) {
        BlogType blogType = blogTypeDao.selectById(id);
        if (EmptyUtil.isEmpty(blogType)) {
            return null;
        }

        BlogArticleTypeResp blogArticleTypeResp = new BlogArticleTypeResp();
        ObjectUtil.copyProperties(blogType, blogArticleTypeResp);

        return blogArticleTypeResp;
    }

    @Override
    public List<BlogArticleTypeResp> listAll() {
        List<BlogType> blogTypes = blogTypeDao.selectList(null);
        if (EmptyUtil.isEmpty(blogTypes)) {
            return new ArrayList<>();
        }

        List<BlogArticleTypeResp> blogArticleTypeRespList = new ArrayList<>();
        for (BlogType blogType : blogTypes) {
            BlogArticleTypeResp blogArticleTypeResp = new BlogArticleTypeResp();
            ObjectUtil.copyProperties(blogType, blogArticleTypeResp);
            blogArticleTypeRespList.add(blogArticleTypeResp);
        }

        return blogArticleTypeRespList;
    }

    @Override
    public Result query(Query<QueryData> query) {
        QueryWrapper<BlogType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogType::getName, query.getData().getKeyword());
        IPage<BlogType> queryPage = new Page<>(query.getPage().getCurPage(), query.getPage().getLimit());

        IPage<BlogType> blogArticleTypeIPage = blogTypeDao.selectPage(queryPage, queryWrapper);

        List<BlogType> blogTypes = blogArticleTypeIPage.getRecords();
        List<BlogArticleTypeResp> blogArticleTypeRespList = new ArrayList<>();
        for (BlogType blogTag : blogTypes) {
            BlogArticleTypeResp blogArticleTypeResp = new BlogArticleTypeResp();
            ObjectUtil.copyProperties(blogTag, blogArticleTypeResp);
            blogArticleTypeRespList.add(blogArticleTypeResp);
        }

        PageUtil page = new PageUtil((int) blogArticleTypeIPage.getTotal(), query.getPage());
        Result data = new Result().put("list", blogArticleTypeRespList);

        return Result.successPage(data, page);
    }

    @Override
    public void create(BlogArticleTypeFrom blogArticleTypeFrom) {
        // 校验同名类型
        checkAlikeName(blogArticleTypeFrom.getName());

        BlogType blogType = toBlogArticleType(blogArticleTypeFrom);
        blogTypeDao.insert(blogType);
    }

    @Override
    public void update(BlogArticleTypeFrom blogArticleTypeFrom) {
        // 校验同名标签
        checkAlikeName(blogArticleTypeFrom.getTypeId(), blogArticleTypeFrom.getName());

        BlogType blogType = toBlogArticleType(blogArticleTypeFrom);
        blogType.setTypeId(blogArticleTypeFrom.getTypeId());

        blogTypeDao.updateById(blogType);
    }

    @Override
    public void remove(List<Long> ids) {
        blogTypeDao.deleteBatchIds(ids);
    }

    private BlogType toBlogArticleType(BlogArticleTypeFrom blogArticleTypeFrom) {
        BlogType blogType = new BlogType();
        ObjectUtil.copyProperties(blogArticleTypeFrom, blogType);
        blogType.setTypeId(null);
        blogType.setDeleted(Constant.NO_DELETED);

        return blogType;
    }

    private void checkAlikeName(String name) {
        QueryWrapper<BlogType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogType::getName, name);
        Integer rowNum = blogTypeDao.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new MarcherHintException("已存在该名称类型", RspBaseCodeEnum.PARAM_ILLEGAL.getCode());
        }
    }

    private void checkAlikeName(Long typeId, String name) {
        QueryWrapper<BlogType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogType::getName, name);
        queryWrapper.lambda().ne(BlogType::getTypeId, typeId);
        Integer rowNum = blogTypeDao.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new MarcherHintException("已存在该名称类型", RspBaseCodeEnum.PARAM_ILLEGAL.getCode());
        }
    }
}
