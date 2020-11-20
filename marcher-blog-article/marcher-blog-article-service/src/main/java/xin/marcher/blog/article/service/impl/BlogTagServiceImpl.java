package xin.marcher.blog.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.article.client.model.request.BlogTagReqs;
import xin.marcher.blog.article.client.model.response.BlogTagResp;
import xin.marcher.blog.article.convert.BlogTagConvert;
import xin.marcher.blog.article.domain.BlogTag;
import xin.marcher.blog.article.exception.RealmArticleException;
import xin.marcher.blog.article.mapper.BlogTagMapper;
import xin.marcher.blog.article.service.BlogTagService;
import xin.marcher.framework.constants.GlobalCodeEnum;
import xin.marcher.framework.constants.GlobalConstant;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.wrapper.PageWO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author marcher
 */
@Service
public class BlogTagServiceImpl extends ServiceImpl<BlogTagMapper, BlogTag> implements BlogTagService {

    private final BlogTagMapper blogTagMapper;

    @Autowired
    public BlogTagServiceImpl(BlogTagMapper blogTagMapper) {
        this.blogTagMapper = blogTagMapper;
    }

    @Override
    public BlogTagResp get(Long id) {
        BlogTag blogTag = blogTagMapper.selectById(id);
        if (EmptyUtil.isEmpty(blogTag)) {
            return null;
        }
        return BlogTagConvert.INSTANCE.convertResp(blogTag);
    }

    @Override
    public List<BlogTagResp> listAll() {
        List<BlogTag> blogTags = blogTagMapper.selectList(null);
        return BlogTagConvert.INSTANCE.convertResp(blogTags);
    }

    @Override
    public BaseResult<PageWO<BlogTagResp>> query(BaseQuery query) {
        PageWO<BlogTag> blogTagPageWo = blogTagMapper.pageQuery(query);
        if (blogTagPageWo.hashEmpty()) {
            return pageEmpty();
        }

        List<BlogTagResp> respList = BlogTagConvert.INSTANCE.convertResp(blogTagPageWo.getList());
        PageWO<BlogTagResp> pageWO = PageWO.rest(respList, blogTagPageWo.getTotal());
        return BaseResult.success(pageWO);
    }

    @Override
    public void create(BlogTagReqs reqs) {
        // 校验同名标签
        checkAlikeName(reqs.getName());

        BlogTag blogTag = toBlogTag(reqs);
        blogTagMapper.insert(blogTag);
    }

    @Override
    public void update(BlogTagReqs reqs) {
        // 校验同名标签
        checkAlikeName(reqs.getTagId(), reqs.getName());

        BlogTag blogTag = toBlogTag(reqs);
        blogTag.setTagId(reqs.getTagId());

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

    private BlogTag toBlogTag(BlogTagReqs reqs) {
        BlogTag blogTag = BlogTagConvert.INSTANCE.convert(reqs);
        blogTag.setDeleted(GlobalConstant.NO_DELETED);
        return blogTag;
    }

    private void checkAlikeName(String name) {
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogTag::getName, name);
        Integer rowNum = blogTagMapper.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new RealmArticleException(GlobalCodeEnum.GL_PARAMETER_ERROR.getRealCode(), "已存在该名称标签");
        }
    }

    private void checkAlikeName(Long tagId, String name) {
        QueryWrapper<BlogTag> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogTag::getName, name);
        queryWrapper.lambda().ne(BlogTag::getTagId, tagId);
        Integer rowNum = blogTagMapper.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new RealmArticleException(GlobalCodeEnum.GL_PARAMETER_ERROR.getRealCode(), "已存在该名称标签");
        }
    }
}
