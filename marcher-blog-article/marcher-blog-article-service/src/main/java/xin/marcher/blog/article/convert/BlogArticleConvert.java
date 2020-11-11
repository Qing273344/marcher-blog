package xin.marcher.blog.article.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import xin.marcher.blog.article.client.model.response.BlogArticleDetailsResp;
import xin.marcher.blog.article.client.model.response.BlogArticleListResp;
import xin.marcher.blog.article.client.model.response.BlogArticleResp;
import xin.marcher.blog.article.domain.BlogArticle;

import java.util.List;

/**
 * @author marcher
 */
@Mapper
public interface BlogArticleConvert {

    BlogArticleConvert INSTANCE = Mappers.getMapper(BlogArticleConvert.class);

    BlogArticleDetailsResp convertResp(BlogArticle info);

    @Mappings({
            @Mapping(target = "timeStr", expression = "java(cn.hutool.core.date.DateUtil.formatDate(info.getCreateTime()))")
    })
    BlogArticleListResp convertListResp(BlogArticle info);
    List<BlogArticleListResp> convertListResp(List<BlogArticle> list);

    BlogArticleResp convertRespManage(BlogArticle info);
    List<BlogArticleResp> convertRespManage(List<BlogArticle> list);
}
