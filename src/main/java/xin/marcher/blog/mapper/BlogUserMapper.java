package xin.marcher.blog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.model.BlogUser;

/**
 * 博客用户
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogUserMapper extends BaseMapper<BlogUser> {


}
