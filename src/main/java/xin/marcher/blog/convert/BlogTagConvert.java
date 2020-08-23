package xin.marcher.blog.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xin.marcher.blog.model.BlogTag;
import xin.marcher.blog.vo.BlogTagVO;

import java.util.List;

/**
 * @author marcher
 */
@Mapper
public interface BlogTagConvert {

    BlogTagConvert INSTANCE = Mappers.getMapper(BlogTagConvert.class);

    BlogTagVO convert(BlogTag info);

    List<BlogTagVO> convert(List<BlogTag> list);
}
