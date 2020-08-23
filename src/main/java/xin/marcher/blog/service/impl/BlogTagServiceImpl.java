package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.enums.RealmCodeEnum;
import xin.marcher.blog.convert.BlogTagConvert;
import xin.marcher.blog.dto.BlogTagDTO;
import xin.marcher.blog.mapper.BlogTagMapper;
import xin.marcher.blog.model.BlogTag;
import xin.marcher.blog.service.BlogTagService;
import xin.marcher.blog.dto.BaseQuery;
import xin.marcher.blog.vo.BlogTagVO;
import xin.marcher.framework.constants.GlobalConstant;
import xin.marcher.framework.exception.HintException;
import xin.marcher.framework.mvc.request.PageParam;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.mybatis.page.PageWrapper;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.ObjectUtil;

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
        return BlogTagConvert.INSTANCE.convert(blogTags);
    }

    @Override
    public BaseResult<PageResult<BlogTagVO>> query(BaseQuery query) {
        PageParam pageParam = new PageParam(query.getPageNo(), query.getPageSize());
        PageWrapper<BlogTag> pageWrapper = blogTagMapper.query(query, pageParam);

        List<BlogTagVO> blogTagVOList = BlogTagConvert.INSTANCE.convert(pageWrapper.getList());
        PageResult<BlogTagVO> data = PageResult.rest(blogTagVOList, pageWrapper.getTotal(), pageParam);
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
            throw new HintException(RealmCodeEnum.PARAM_ILLEGAL.getRealCode(), "已存在该名称标签");
        }
    }

    private void checkAlikeName(Long tagId, String name) {
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogTag::getName, name);
        queryWrapper.lambda().ne(BlogTag::getTagId, tagId);
        Integer rowNum = blogTagMapper.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new HintException(RealmCodeEnum.PARAM_ILLEGAL.getRealCode(), "已存在该名称标签");
        }
    }
}
