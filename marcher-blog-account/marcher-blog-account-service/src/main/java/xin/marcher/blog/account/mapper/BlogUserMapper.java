package xin.marcher.blog.account.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import xin.marcher.blog.account.domain.BlogUser;
import xin.marcher.framework.mybatis.mapper.BaseMapper;

/**
 * 博客用户
 * 
 * @author marcher
 */
@Mapper
@Component
public interface BlogUserMapper extends BaseMapper<BlogUser> {

    default BlogUser getByUsername(String username) {
        QueryWrapper<BlogUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(BlogUser::getUsername, username);
        return selectLimitOne(queryWrapper);
    }
}
