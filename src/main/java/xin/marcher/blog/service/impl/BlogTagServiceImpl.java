package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.dto.BlogTagDTO;
import xin.marcher.blog.mapper.BlogTagMapper;
import xin.marcher.blog.model.BlogTag;
import xin.marcher.blog.service.BlogTagService;
import xin.marcher.blog.utils.*;
import xin.marcher.blog.vo.BlogTagVO;
import xin.marcher.framework.constants.GlobalConstant;
import xin.marcher.framework.exception.HintException;
import xin.marcher.framework.mvc.request.PageParam;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author marcher
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    public BlogTagVO get(Long id) {
        BlogTag blogTag = blogTagMapper.selectById(id);
        if (EmptyUtil.isEmpty(blogTag)) {
            return null;
        }

        BlogTagVO blogTagVO = new BlogTagVO();
        ObjectUtil.copyProperties(blogTag, blogTagVO);

        return blogTagVO;
    }

    @Override
    public List<BlogTagVO> listAll() {
        List<BlogTag> blogTags = blogTagMapper.selectList(null);

        List<BlogTagVO> blogTagVOList = new ArrayList<>();
        for (BlogTag blogTag : blogTags) {
            BlogTagVO blogTagVO = new BlogTagVO();
            ObjectUtil.copyProperties(blogTag, blogTagVO);
            blogTagVOList.add(blogTagVO);
        }

        return blogTagVOList;
    }

    @Override
    public BaseResult<PageResult<BlogTagVO>> query(QueryData query) {
        PageParam pageParam = new PageParam(query.getPageNo(), query.getPageSize());

        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        if (EmptyUtil.isNotEmpty(query.getKeyword())) {
            queryWrapper.lambda().like(BlogTag::getName, query.getKeyword());
        }
        IPage<BlogTag> page = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());

        IPage<BlogTag> blogTagIPage = blogTagMapper.selectPage(page, queryWrapper);

        List<BlogTag> blogTags = blogTagIPage.getRecords();
        List<BlogTagVO> blogTagVOList = new ArrayList<>();

        blogTags.forEach( blogTag -> {
            BlogTagVO blogTagVO = new BlogTagVO();
            ObjectUtil.copyProperties(blogTag, blogTagVO);
            blogTagVOList.add(blogTagVO);
        });

        PageResult<BlogTagVO> data = PageResult.rest(blogTagVOList, blogTagIPage.getTotal(), pageParam);
        return BaseResult.success(data);
    }

    @Override
    public void create(BlogTagDTO blogTagDTO) {
        // 校验同名标签
        checkAlikeName(blogTagDTO.getName());

        BlogTag blogTag = toBlogTag(blogTagDTO);
        blogTagMapper.insert(blogTag);
    }

    @Override
    public void update(BlogTagDTO blogTagDTO) {
        // 校验同名标签
        checkAlikeName(blogTagDTO.getTagId(), blogTagDTO.getName());

        BlogTag blogTag = toBlogTag(blogTagDTO);
        blogTag.setTagId(blogTagDTO.getTagId());

        blogTagMapper.updateById(blogTag);
    }

    @Override
    public void remove(List<Long> ids) {
        blogTagMapper.deleteBatchIds(ids);
    }

    @Override
    public List<String> getHotTag() {
        List<BlogTag> blogTags = list();
        return blogTags.stream().map(BlogTag::getName).distinct().collect(Collectors.toList());
    }

    private BlogTag toBlogTag(BlogTagDTO blogTagDTO) {
        BlogTag blogTag = new BlogTag();
        ObjectUtil.copyProperties(blogTagDTO, blogTag);
        blogTag.setDeleted(GlobalConstant.NO_DELETED);
        return blogTag;
    }

    private void checkAlikeName(String name) {
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogTag::getName, name);
        Integer rowNum = blogTagMapper.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new HintException(RspBaseCodeEnum.PARAM_ILLEGAL.getRealCode(), "已存在该名称标签");
        }
    }

    private void checkAlikeName(Long tagId, String name) {
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogTag::getName, name);
        queryWrapper.lambda().ne(BlogTag::getTagId, tagId);
        Integer rowNum = blogTagMapper.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new HintException(RspBaseCodeEnum.PARAM_ILLEGAL.getRealCode(), "已存在该名称标签");
        }
    }
}
