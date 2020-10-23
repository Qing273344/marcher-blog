package xin.marcher.blog.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.account.domain.BlogUserResource;

import java.util.Set;

/**
 * 用户类型-资源
 *
 * @author marcher
 */
public interface BlogUserResourceService extends IService<BlogUserResource> {

    /**
     * get用户权限
     * @param userType  用户类型
     * @return
     *      返回用户权限
     */
    Set<String> getByUserType(Integer userType);
}
