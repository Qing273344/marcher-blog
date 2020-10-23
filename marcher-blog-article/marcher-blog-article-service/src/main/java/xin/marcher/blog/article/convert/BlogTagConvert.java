package xin.marcher.blog.article.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xin.marcher.blog.article.client.model.request.BlogTagReqs;
import xin.marcher.blog.article.client.model.response.BlogTagResp;
import xin.marcher.blog.article.domain.BlogTag;

import java.util.List;

/**
 * @author marcher
 */
@Mapper
public interface BlogTagConvert {

    BlogTagConvert INSTANCE = Mappers.getMapper(BlogTagConvert.class);

    BlogTag convert(BlogTagReqs info);

    BlogTagResp convertResp(BlogTag info);

    List<BlogTagResp> convertResp(List<BlogTag> list);
}
