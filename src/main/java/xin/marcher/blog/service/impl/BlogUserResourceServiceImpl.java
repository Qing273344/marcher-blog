package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.marcher.blog.dao.BlogUserResourceDao;
import xin.marcher.blog.entity.BlogUserResource;
import xin.marcher.blog.service.BlogResourceService;
import xin.marcher.blog.service.BlogUserResourceService;
import xin.marcher.blog.utils.EmptyUtil;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author marcher
 */
@Service
public class BlogUserResourceServiceImpl extends ServiceImpl<BlogUserResourceDao, BlogUserResource> implements BlogUserResourceService {

    @Resource
    private BlogResourceService blogResourceService;

    @Resource
    private BlogUserResourceDao blogUserResourceDao;

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
        return blogUserResourceDao.getResourceIds(userType);
    }
}
