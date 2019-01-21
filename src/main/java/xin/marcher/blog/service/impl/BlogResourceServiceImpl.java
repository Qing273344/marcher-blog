package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import xin.marcher.blog.dao.BlogResourceDao;
import xin.marcher.blog.entity.BlogResource;
import xin.marcher.blog.service.BlogResourceService;

/**
 * @author marcher
 */
@Service
public class BlogResourceServiceImpl extends ServiceImpl<BlogResourceDao, BlogResource> implements BlogResourceService {

}
