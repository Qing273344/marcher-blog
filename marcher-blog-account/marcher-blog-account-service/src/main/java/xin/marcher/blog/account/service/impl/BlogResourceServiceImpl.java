package xin.marcher.blog.account.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.marcher.blog.account.domain.BlogResource;
import xin.marcher.blog.account.mapper.BlogResourceMapper;
import xin.marcher.blog.account.service.BlogResourceService;
import xin.marcher.framework.common.util.CollectionUtil;
import xin.marcher.framework.common.util.EmptyUtil;

import java.util.List;
import java.util.Set;

/**
 * @author marcher
 */
@Service
public class BlogResourceServiceImpl extends ServiceImpl<BlogResourceMapper, BlogResource> implements BlogResourceService {

    @Override
    public Set<String> getNameByIds(List<Long> blogResourceIds) {
        if (EmptyUtil.isEmpty(blogResourceIds)) {
            return null;
        }
        List<BlogResource> blogResources = listByIds(blogResourceIds);
        return CollectionUtil.convertSet(blogResources, BlogResource::getPermission);
    }
}
