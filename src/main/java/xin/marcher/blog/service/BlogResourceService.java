package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.entity.BlogResource;

import java.util.List;
import java.util.Set;

/**
 * 博客资源
 *
 * @author marcher
 */
public interface BlogResourceService extends IService<BlogResource> {

    /**
     * get 资源唯一name
     *
     * @param blogResourceIds 资源id
     * @return
     *      资源name
     */
    Set<String> getNameByIds(List<Long> blogResourceIds);
}
