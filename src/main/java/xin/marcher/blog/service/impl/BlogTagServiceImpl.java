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
import xin.marcher.blog.entity.BlogTag;
import xin.marcher.blog.from.BlogTagFrom;
import xin.marcher.blog.service.BlogTagService;
import xin.marcher.blog.utils.*;
import xin.marcher.blog.vo.BlogTagVo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author marcher
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagDao, BlogTag> implements BlogTagService {

    @Resource
    private BlogTagDao blogTagDao;

    @Override
    public BlogTagVo get(Long id) {
        BlogTag blogTag = blogTagDao.selectById(id);
        if (EmptyUtil.isEmpty(blogTag)) {
            return null;
        }

        BlogTagVo blogTagVo = new BlogTagVo();
        ObjectUtil.copyProperties(blogTag, blogTagVo);

        return blogTagVo;
    }

    @Override
    public List<BlogTagVo> listAll() {
        List<BlogTag> blogTags = blogTagDao.selectList(null);
        if (EmptyUtil.isEmpty(blogTags)) {
            return new ArrayList<>();
        }

        List<BlogTagVo> blogTagVoList = new ArrayList<>();
        for (BlogTag blogTag : blogTags) {
            BlogTagVo blogTagVo = new BlogTagVo();
            ObjectUtil.copyProperties(blogTag, blogTagVo);
            blogTagVoList.add(blogTagVo);
        }

        return blogTagVoList;
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
        List<BlogTagVo> blogTagVoList = new ArrayList<>();
        for (BlogTag blogTag : blogTags) {
            BlogTagVo blogTagVo = new BlogTagVo();
            ObjectUtil.copyProperties(blogTag, blogTagVo);
            blogTagVoList.add(blogTagVo);
        }

        PageUtil page = new PageUtil((int) blogTagIPage.getTotal(), query.getPage());
        Result data = new Result().put("list", blogTagVoList);

        return Result.successPage(data, page);
    }

    @Override
    public void create(BlogTagFrom blogTagFrom) {
        // 校验同名标签
        checkAlikeName(blogTagFrom.getName());

        BlogTag blogTag = toBlogTag(blogTagFrom);
        blogTagDao.insert(blogTag);
    }

    @Override
    public void update(BlogTagFrom blogTagFrom) {
        // 校验同名标签
        checkAlikeName(blogTagFrom.getTagId(), blogTagFrom.getName());

        BlogTag blogTag = toBlogTag(blogTagFrom);
        blogTag.setTagId(blogTagFrom.getTagId());

        blogTagDao.updateById(blogTag);
    }

    @Override
    public void remove(List<Long> ids) {
        blogTagDao.deleteBatchIds(ids);
    }

    @Override
    public List<String> getHotTag() {
        List<BlogTag> blogTags = list();
        List<String> tagNameList = blogTags.stream().map(BlogTag::getName).distinct().collect(Collectors.toList());
        return tagNameList;
    }

    private BlogTag toBlogTag(BlogTagFrom blogTagFrom) {
        Long now = DateUtil.getTimestamp();

        BlogTag blogTag = new BlogTag();
        ObjectUtil.copyProperties(blogTagFrom, blogTag);
        if (EmptyUtil.isEmpty(blogTagFrom.getTagId())) {
            blogTag.setCreateTime(now);
        }
        blogTag.setModifyTime(now);
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
