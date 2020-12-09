package xin.marcher.blog.article.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.article.domain.BlogType;
import xin.marcher.framework.common.mvc.request.BaseQuery;
import xin.marcher.framework.common.wrapper.PageWO;
import xin.marcher.framework.mybatis.mapper.BaseMapper;
import xin.marcher.framework.mybatis.query.BaseQueryWrapper;

/**
 * 博客类型
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogTypeMapper extends BaseMapper<BlogType> {

    default PageWO<BlogType> pageQuery(BaseQuery query) {
        BaseQueryWrapper<BlogType> queryWrapper = new BaseQueryWrapper<>();
        queryWrapper.likeIfPresent(BlogType::getName, query.getKeyword());

        IPage<BlogType> iPage = selectPage(pageWrapper(query), queryWrapper);
        return convert(iPage);
    }
}
