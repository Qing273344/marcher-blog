package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.marcher.blog.mapper.BlogArticleTypeMapper;
import xin.marcher.blog.model.BlogArticleType;
import xin.marcher.blog.service.BlogArticleTypeService;
import xin.marcher.framework.constants.GlobalConstant;
import xin.marcher.framework.util.EmptyUtil;

/**
 * 博客文章类型关联
 *
 * @author marcher
 */
@Service
public class BlogArticleTypeServiceImpl extends ServiceImpl<BlogArticleTypeMapper, BlogArticleType> implements BlogArticleTypeService {

    @Override
    public void add(Long articleId, Long typeId) {
        BlogArticleType blogArticleType = toArticleType(articleId, typeId);
        save(blogArticleType);
    }

    @Override
    public void removeByArticleId(Long articleId) {
        QueryWrapper<BlogArticleType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BlogArticleType::getArticleId, articleId);

        remove(queryWrapper);
    }

    @Override
    public Long getId(Long articleId) {
        QueryWrapper<BlogArticleType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BlogArticleType::getArticleId, articleId);

        BlogArticleType blogArticleType = getOne(queryWrapper);
        if (EmptyUtil.isEmpty(blogArticleType)) {
            return null;
        }

        return blogArticleType.getTypeId();
    }

    private BlogArticleType toArticleType(Long articleId, Long typeId) {
        BlogArticleType blogArticleType = new BlogArticleType();
        blogArticleType.setArticleId(articleId);
        blogArticleType.setTypeId(typeId);
        blogArticleType.setDeleted(GlobalConstant.NO_DELETED);

        return blogArticleType;
    }
}
