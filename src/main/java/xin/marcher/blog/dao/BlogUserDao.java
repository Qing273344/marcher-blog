package xin.marcher.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import xin.marcher.blog.entity.BlogUser;

/**
 * 博客用户
 * 
 * @author marcher
 */
@Mapper
public interface BlogUserDao extends BaseMapper<BlogUser> {


}
