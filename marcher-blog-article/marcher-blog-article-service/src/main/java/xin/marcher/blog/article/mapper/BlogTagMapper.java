package xin.marcher.blog.article.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.article.domain.BlogTag;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mybatis.mapper.BaseMapper;
import xin.marcher.framework.mybatis.query.BaseQueryWrapper;
import xin.marcher.framework.wrapper.PageWO;

/**
 * 标签
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogTagMapper extends BaseMapper<BlogTag> {

    default PageWO<BlogTag> pageQuery(BaseQuery query) {
        BaseQueryWrapper<BlogTag> queryWrapper = new BaseQueryWrapper<>();
        queryWrapper.likeIfPresent(BlogTag::getName, query.getKeyword());
        IPage<BlogTag> iPage = selectPage(pageWrapper(query), queryWrapper);

        return convert(iPage);
    }
}
