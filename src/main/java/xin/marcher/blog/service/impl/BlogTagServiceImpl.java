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
import xin.marcher.blog.dao.BlogTagDao;
import xin.marcher.blog.dto.request.BlogTagReq;
import xin.marcher.blog.dto.response.BlogTagResp;
import xin.marcher.blog.entity.BlogTag;
import xin.marcher.blog.service.BlogTagService;
import xin.marcher.blog.utils.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author marcher
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagDao, BlogTag> implements BlogTagService {

    @Autowired
    private BlogTagDao blogTagDao;

    @Override
    public BlogTagResp get(Long id) {
        BlogTag blogTag = blogTagDao.selectById(id);
        if (EmptyUtil.isEmpty(blogTag)) {
            return null;
        }

        BlogTagResp blogTagResp = new BlogTagResp();
        ObjectUtil.copyProperties(blogTag, blogTagResp);

        return blogTagResp;
    }

    @Override
    public List<BlogTagResp> listAll() {
        List<BlogTag> blogTags = blogTagDao.selectList(null);
        if (EmptyUtil.isEmpty(blogTags)) {
            return new ArrayList<>();
        }

        List<BlogTagResp> blogTagRespList = new ArrayList<>();
        for (BlogTag blogTag : blogTags) {
            BlogTagResp blogTagResp = new BlogTagResp();
            ObjectUtil.copyProperties(blogTag, blogTagResp);
            blogTagRespList.add(blogTagResp);
        }

        return blogTagRespList;
    }

    @Override
    public Result query(Query<QueryData> query) {
        QueryData queryData = query.getData();

        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        if (EmptyUtil.isNotEmpty(queryData)) {
            queryWrapper.lambda().like(BlogTag::getName, query.getData().getKeyword());
        }
        IPage<BlogTag> queryPage = new Page<>(query.getPage().getCurPage(), query.getPage().getLimit());

        IPage<BlogTag> blogTagIPage = blogTagDao.selectPage(queryPage, queryWrapper);

        List<BlogTag> blogTags = blogTagIPage.getRecords();
        List<BlogTagResp> blogTagRespList = new ArrayList<>();

        blogTags.forEach( blogTag -> {
            BlogTagResp blogTagResp = new BlogTagResp();
            ObjectUtil.copyProperties(blogTag, blogTagResp);
            blogTagRespList.add(blogTagResp);
        });

        PageUtil page = new PageUtil((int) blogTagIPage.getTotal(), query.getPage());
        Result data = new Result().put("list", blogTagRespList);

        return Result.successPage(data, page);
    }

    @Override
    public void create(BlogTagReq blogTagReq) {
        // 校验同名标签
        checkAlikeName(blogTagReq.getName());

        BlogTag blogTag = toBlogTag(blogTagReq);
        blogTagDao.insert(blogTag);
    }

    @Override
    public void update(BlogTagReq blogTagReq) {
        // 校验同名标签
        checkAlikeName(blogTagReq.getTagId(), blogTagReq.getName());

        BlogTag blogTag = toBlogTag(blogTagReq);
        blogTag.setTagId(blogTagReq.getTagId());

        blogTagDao.updateById(blogTag);
    }

    @Override
    public void remove(List<Long> ids) {
        blogTagDao.deleteBatchIds(ids);
    }

    @Override
    public List<String> getHotTag() {
        List<BlogTag> blogTags = list();
        return blogTags.stream().map(BlogTag::getName).distinct().collect(Collectors.toList());
    }

    private BlogTag toBlogTag(BlogTagReq blogTagReq) {
        BlogTag blogTag = new BlogTag();
        ObjectUtil.copyProperties(blogTagReq, blogTag);
        blogTag.setDeleted(Constant.NO_DELETED);
        return blogTag;
    }

    private void checkAlikeName(String name) {
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogTag::getName, name);
        Integer rowNum = blogTagDao.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new MarcherHintException("已存在该名称标签", RspBaseCodeEnum.PARAM_ILLEGAL.getCode());
        }
    }

    private void checkAlikeName(Long tagId, String name) {
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogTag::getName, name);
        queryWrapper.lambda().ne(BlogTag::getTagId, tagId);
        Integer rowNum = blogTagDao.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new MarcherHintException("已存在该名称标签", RspBaseCodeEnum.PARAM_ILLEGAL.getCode());
        }
    }
}
