package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.mapper.BlogUserResourceMapper;
import xin.marcher.blog.model.BlogUserResource;
import xin.marcher.blog.service.BlogResourceService;
import xin.marcher.blog.service.BlogUserResourceService;
import xin.marcher.framework.util.EmptyUtil;

import java.util.List;
import java.util.Set;

/**
 * @author marcher
 */
@Service
public class BlogUserResourceServiceImpl extends ServiceImpl<BlogUserResourceMapper, BlogUserResource> implements BlogUserResourceService {

    @Autowired
    private BlogResourceService blogResourceService;

    @Autowired
    private BlogUserResourceMapper blogUserResourceMapper;

    @Override
    public Set<String> getByUserType(Integer userType) {
        // 获取用户权限id
        List<Long> blogResourceIds = getResourceIds(userType);
        if (EmptyUtil.isEmpty(blogResourceIds)) {
            return null;
        }

        // 获取资源唯一name
        return blogResourceService.getNameByIds(blogResourceIds);
    }

    private List<Long> getResourceIds(Integer userType) {
        return blogUserResourceMapper.getResourceIds(userType);
    }
}
