package xin.marcher.blog.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.model.cache.BlogUserCO;
import xin.marcher.blog.vo.BlogUserVO;
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
        BlogUserCO blogUserCO = getUser();
        if (EmptyUtil.isEmpty(blogUserCO)) {
            return BaseResult.error();
        }

        BlogUserVO blogUserVO = new BlogUserVO();
        ObjectUtil.copyProperties(blogUserCO, blogUserVO);

        return BaseResult.success(blogUserVO);
    }
}
