package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private BlogArticleTypeMapper blogArticleTypeMapper;

    @Override
    public void add(Long articleId, Long typeId) {
        BlogArticleType blogArticleType = toArticleType(articleId, typeId);
        save(blogArticleType);
    }

    @Override
    public void removeByArticleId(Long articleId) {
        blogArticleTypeMapper.removeByArticleId(articleId);
    }

    @Override
    public Long getId(Long articleId) {
        BlogArticleType blogArticleType = blogArticleTypeMapper.getByArticleId(articleId);
        return EmptyUtil.isEmpty(blogArticleType) ? null : blogArticleType.getTypeId();
    }

    @Override
    public void replace(Long articleId, Long typeId) {
        // del 旧类型
        removeByArticleId(articleId);
        // add 新类型
        add(articleId, typeId);
    }

    private BlogArticleType toArticleType(Long articleId, Long typeId) {
        BlogArticleType blogArticleType = new BlogArticleType();
        blogArticleType.setArticleId(articleId);
        blogArticleType.setTypeId(typeId);
        blogArticleType.setDeleted(GlobalConstant.NO_DELETED);

        return blogArticleType;
    }
}
