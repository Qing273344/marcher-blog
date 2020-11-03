package xin.marcher.blog.account.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.account.model.cache.BlogUserCO;
import xin.marcher.blog.account.vo.BlogUserVO;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.ObjectUtil;

/**
 * 用户
 *
 * @author marcher
 */
@Slf4j
@RestController
@RequestMapping(value = "/blog/user")
public class BlogUserController extends AbstractBaseController {

    /**
     * 获取用户信息
     *
     * @return
     *      用户信息
     */
    @GetMapping("/info")
    public BaseResult<BlogUserVO> getUserInfo() {
        BlogUserCO blogUserCo = getUser();
        if (EmptyUtil.isEmpty(blogUserCo)) {
            return BaseResult.error();
        }

        BlogUserVO blogUserVO = new BlogUserVO();
        ObjectUtil.copyProperties(blogUserCo, blogUserVO);

        return BaseResult.success(blogUserVO);
    }
}
