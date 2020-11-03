package xin.marcher.blog.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.account.domain.BlogUserResource;
import xin.marcher.blog.account.mapper.BlogUserResourceMapper;
import xin.marcher.blog.account.service.BlogResourceService;
import xin.marcher.blog.account.service.BlogUserResourceService;
import xin.marcher.framework.util.EmptyUtil;

import java.util.List;
import java.util.Set;

/**
 * @author marcher
 */
@Service
public class BlogUserResourceServiceImpl extends ServiceImpl<BlogUserResourceMapper, BlogUserResource> implements BlogUserResourceService {

    private final BlogResourceService blogResourceService;
    private final BlogUserResourceMapper blogUserResourceMapper;

    @Autowired
    public BlogUserResourceServiceImpl(BlogResourceService blogResourceService,
                                       BlogUserResourceMapper blogUserResourceMapper) {
        this.blogResourceService = blogResourceService;
        this.blogUserResourceMapper = blogUserResourceMapper;
    }

    @Override
    public Set<String> getByUserType(Integer userType) {
        // 获取用户权限 id
        List<Long> blogResourceIds = getResourceIds(userType);
        if (EmptyUtil.isEmpty(blogResourceIds)) {
            return null;
        }

        // 获取资源唯一 name
        return blogResourceService.getNameByIds(blogResourceIds);
    }

    private List<Long> getResourceIds(Integer userType) {
        return blogUserResourceMapper.getResourceIds(userType);
    }
}
