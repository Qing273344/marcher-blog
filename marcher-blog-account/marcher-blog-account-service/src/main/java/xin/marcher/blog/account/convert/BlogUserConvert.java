package xin.marcher.blog.account.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import xin.marcher.blog.account.domain.BlogUser;
import xin.marcher.blog.account.model.cache.BlogUserCO;

/**
 * @author marcher
 */
@Mapper
public interface BlogUserConvert {

    BlogUserConvert INSTANCE = Mappers.getMapper(BlogUserConvert.class);

    BlogUserCO convert(BlogUser blogUser);

}
