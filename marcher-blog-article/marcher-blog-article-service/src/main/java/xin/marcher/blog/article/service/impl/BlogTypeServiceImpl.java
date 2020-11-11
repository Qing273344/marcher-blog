package xin.marcher.blog.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.article.client.model.request.BlogTypeReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleTypeResp;
import xin.marcher.blog.article.convert.BlogTypeConvert;
import xin.marcher.blog.article.domain.BlogType;
import xin.marcher.blog.article.exception.RealmArticleException;
import xin.marcher.blog.article.mapper.BlogTypeMapper;
import xin.marcher.blog.article.service.BlogTypeService;
import xin.marcher.framework.constants.GlobalConstant;
import xin.marcher.framework.constants.GlobalCodeEnum;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.request.PageParam;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.mybatis.query.BaseQueryWrapper;
import xin.marcher.framework.util.EmptyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marcher
 */
@Service
public class BlogTypeServiceImpl extends ServiceImpl<BlogTypeMapper, BlogType> implements BlogTypeService {

    private final BlogTypeMapper blogTypeMapper;

    @Autowired
    public BlogTypeServiceImpl(BlogTypeMapper blogTypeMapper) {
        this.blogTypeMapper = blogTypeMapper;
    }

    @Override
    public BlogArticleTypeResp get(Long id) {
        BlogType blogType = blogTypeMapper.selectById(id);
        if (EmptyUtil.isEmpty(blogType)) {
            return null;
        }

        return BlogTypeConvert.INSTANCE.convertResp(blogType);
    }

    @Override
    public List<BlogArticleTypeResp> listAll() {
        List<BlogType> blogTypes = blogTypeMapper.selectList(null);
        if (EmptyUtil.isEmpty(blogTypes)) {
            return new ArrayList<>();
        }
        return BlogTypeConvert.INSTANCE.convertResp(blogTypes);
    }

    @Override
    public BaseResult<PageResult<BlogArticleTypeResp>> query(BaseQuery query) {
        BaseQueryWrapper<BlogType> queryWrapper = new BaseQueryWrapper<>();
        queryWrapper.likeIfPresent(BlogType::getName, query.getKeyword());

        PageParam pageParam = new PageParam(query.getPageNo(), query.getPageSize());
        IPage<BlogType> queryPage = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());

        IPage<BlogType> blogArticleTypeIPage = blogTypeMapper.selectPage(queryPage, queryWrapper);
        List<BlogType> blogTypes = blogArticleTypeIPage.getRecords();

        List<BlogArticleTypeResp> respList = BlogTypeConvert.INSTANCE.convertResp(blogTypes);
        PageResult<BlogArticleTypeResp> data = PageResult.rest(respList, blogArticleTypeIPage.getTotal(), pageParam);
        return BaseResult.success(data);
    }

    @Override
    public void create(BlogTypeReqs reqs) {
        // 校验同名类型
        checkAlikeName(reqs.getName());

        BlogType blogType = toBlogArticleType(reqs);
        blogTypeMapper.insert(blogType);
    }

    @Override
    public void update(BlogTypeReqs reqs) {
        // 校验同名标签
        checkAlikeName(reqs.getTypeId(), reqs.getName());

        BlogType blogType = toBlogArticleType(reqs);
        blogType.setTypeId(reqs.getTypeId());

        blogTypeMapper.updateById(blogType);
    }

    @Override
    public void remove(List<Long> ids) {
        blogTypeMapper.deleteBatchIds(ids);
    }

    private BlogType toBlogArticleType(BlogTypeReqs reqs) {
        BlogType blogType = BlogTypeConvert.INSTANCE.convert(reqs);
        blogType.setTypeId(null);
        blogType.setDeleted(GlobalConstant.NO_DELETED);
        return blogType;
    }

    private void checkAlikeName(String name) {
        QueryWrapper<BlogType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogType::getName, name);
        Integer rowNum = blogTypeMapper.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new RealmArticleException(GlobalCodeEnum.GL_PARAMETER_ERROR.getRealCode(), "已存在该名称类型");
        }
    }

    private void checkAlikeName(Long typeId, String name) {
        QueryWrapper<BlogType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogType::getName, name);
        queryWrapper.lambda().ne(BlogType::getTypeId, typeId);
        Integer rowNum = blogTypeMapper.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new RealmArticleException(GlobalCodeEnum.GL_PARAMETER_ERROR.getRealCode(), "已存在该名称类型");
        }
    }
}
