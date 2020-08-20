package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.marcher.blog.mapper.BlogResourceMapper;
import xin.marcher.blog.model.BlogResource;
import xin.marcher.blog.service.BlogResourceService;
import xin.marcher.framework.util.EmptyUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author marcher
 */
@Service
public class BlogResourceServiceImpl extends ServiceImpl<BlogResourceMapper, BlogResource> implements BlogResourceService {

    @Override
    public Set<String> getNameByIds(List<Long> blogResourceIds) {
        Collection<BlogResource> blogResources = listByIds(blogResourceIds);
        if (EmptyUtil.isEmpty(blogResources)) {
            return null;
        }

        Set<String> permissionSet = new HashSet<>();
        for (BlogResource blogResource : blogResources) {
            permissionSet.add(blogResource.getPermission());
        }
        return permissionSet;
    }
}
