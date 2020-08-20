package xin.marcher.blog.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xin.marcher.blog.model.BlogUser;
import xin.marcher.blog.model.cache.BlogUserCO;

/**
 * @author marcher
 */
@Mapper
public interface BlogUserConvert {

    BlogUserConvert INSTANCE = Mappers.getMapper(BlogUserConvert.class);

    BlogUserCO convert(BlogUser blogUser);

}
