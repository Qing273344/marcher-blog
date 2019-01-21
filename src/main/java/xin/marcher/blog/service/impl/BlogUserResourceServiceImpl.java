package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.marcher.blog.dao.BlogUserResourceDao;
import xin.marcher.blog.entity.BlogUserResource;
import xin.marcher.blog.service.BlogUserResourceService;

/**
 * @author marcher
 */
@Service
public class BlogUserResourceServiceImpl extends ServiceImpl<BlogUserResourceDao, BlogUserResource> implements BlogUserResourceService {
}
