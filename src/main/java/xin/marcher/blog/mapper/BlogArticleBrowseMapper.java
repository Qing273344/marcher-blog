package xin.marcher.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogArticleBrowse;

/**
 * 用户浏览文章
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogArticleBrowseMapper extends BaseMapper<BlogArticleBrowse> {
	
}
