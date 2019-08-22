package xin.marcher.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import xin.marcher.blog.entity.BlogTag;

/**
 * 标签
 * 
 * @author marcher
 */
@Mapper
public interface BlogTagDao extends BaseMapper<BlogTag> {

}
