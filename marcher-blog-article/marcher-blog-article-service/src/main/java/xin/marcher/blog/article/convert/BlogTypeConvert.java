package xin.marcher.blog.article.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xin.marcher.blog.article.client.model.request.BlogTypeReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleTypeResp;
import xin.marcher.blog.article.domain.BlogType;

import java.util.List;

/**
 * @author marcher
 */
@Mapper
public interface BlogTypeConvert {

    BlogTypeConvert INSTANCE = Mappers.getMapper(BlogTypeConvert.class);

    BlogArticleTypeResp convertResp(BlogType blogType);

    List<BlogArticleTypeResp> convertResp(List<BlogType> blogTypes);

    BlogType convert(BlogTypeReqs reqs);
}
