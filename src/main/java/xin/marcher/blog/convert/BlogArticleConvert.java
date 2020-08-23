package xin.marcher.blog.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import xin.marcher.blog.model.BlogArticle;
import xin.marcher.blog.vo.AdminArticleListVO;
import xin.marcher.blog.vo.BlogArticleDetailsVO;
import xin.marcher.blog.vo.BlogArticleListVO;

import java.util.List;

/**
 * @author marcher
 */
@Mapper
public interface BlogArticleConvert {

    BlogArticleConvert INSTANCE = Mappers.getMapper(BlogArticleConvert.class);

    BlogArticleDetailsVO convertVO(BlogArticle blogArticle);

    @Mappings({
            @Mapping(target = "timeStr",
                    expression = "java(xin.marcher.framework.util.DateUtil.formatDate(info.getCreateTime()," +
                            " xin.marcher.framework.util.DateUtil.PATTERN_HYPHEN_DATE))")
    })
    BlogArticleListVO convert(BlogArticle info);

    List<BlogArticleListVO> convert(List<BlogArticle> list);

    @Mappings({
            @Mapping(target = "timeStr",
                    expression = "java(xin.marcher.framework.util.DateUtil.formatDate(info.getCreateTime()," +
                            " xin.marcher.framework.util.DateUtil.PATTERN_HYPHEN_DATE))")
    })
    AdminArticleListVO convertAdmin(BlogArticle info);

    List<AdminArticleListVO> convertAdmin(List<BlogArticle> list);
}
