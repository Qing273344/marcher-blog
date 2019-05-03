package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.consts.Constant;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.common.exception.MarcherHintException;
import xin.marcher.blog.dao.BlogTypeDao;
import xin.marcher.blog.entity.BlogType;
import xin.marcher.blog.from.BlogArticleTypeFrom;
import xin.marcher.blog.service.BlogTypeService;
import xin.marcher.blog.utils.*;
import xin.marcher.blog.vo.BlogArticleTypeVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author marcher
 */
@Service
public class BlogTypeServiceImpl extends ServiceImpl<BlogTypeDao, BlogType> implements BlogTypeService {

    @Resource
    private BlogTypeDao blogTypeDao;

    @Override
    public BlogArticleTypeVo get(Long id) {
        BlogType blogType = blogTypeDao.selectById(id);
        if (EmptyUtil.isEmpty(blogType)) {
            return null;
        }

        BlogArticleTypeVo blogArticleTypeVo = new BlogArticleTypeVo();
        ObjectUtil.copyProperties(blogType, blogArticleTypeVo);

        return blogArticleTypeVo;
    }

    @Override
    public List<BlogArticleTypeVo> listAll() {
        List<BlogType> blogTypes = blogTypeDao.selectList(null);
        if (EmptyUtil.isEmpty(blogTypes)) {
            return new ArrayList<>();
        }

        List<BlogArticleTypeVo> blogArticleTypeVoList = new ArrayList<>();
        for (BlogType blogType : blogTypes) {
            BlogArticleTypeVo blogArticleTypeVo = new BlogArticleTypeVo();
            ObjectUtil.copyProperties(blogType, blogArticleTypeVo);
            blogArticleTypeVoList.add(blogArticleTypeVo);
        }

        return blogArticleTypeVoList;
    }

    @Override
    public Result query(Query<QueryData> query) {
        QueryWrapper<BlogType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogType::getName, query.getData().getKeyword());
        IPage<BlogType> queryPage = new Page<>(query.getPage().getCurPage(), query.getPage().getLimit());

        IPage<BlogType> blogArticleTypeIPage = blogTypeDao.selectPage(queryPage, queryWrapper);

        List<BlogType> blogTypes = blogArticleTypeIPage.getRecords();
        List<BlogArticleTypeVo> blogArticleTypeVoList = new ArrayList<>();
        for (BlogType blogTag : blogTypes) {
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
