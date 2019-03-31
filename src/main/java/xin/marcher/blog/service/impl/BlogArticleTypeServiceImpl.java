package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.consts.Constant;
import xin.marcher.blog.dao.BlogArticleTypeDao;
import xin.marcher.blog.entity.BlogArticleType;
import xin.marcher.blog.service.BlogArticleTypeService;
import xin.marcher.blog.utils.DateUtil;
import xin.marcher.blog.utils.EmptyUtil;

/**
 * 博客文章类型关联
 *
 * @author marcher
 */
@Service
public class BlogArticleTypeServiceImpl extends ServiceImpl<BlogArticleTypeDao, BlogArticleType> implements BlogArticleTypeService {

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
        Long now = DateUtil.getTimestamp();

        BlogArticleType blogArticleType = new BlogArticleType();
        blogArticleType.setArticleId(articleId);
        blogArticleType.setTypeId(typeId);
        blogArticleType.setCreateTime(now);
        blogArticleType.setModifyTime(now);
        blogArticleType.setDeleted(Constant.NO_DELETED);

        return blogArticleType;
    }
}
