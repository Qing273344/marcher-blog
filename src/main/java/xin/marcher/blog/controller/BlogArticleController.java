package xin.marcher.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.service.BlogArticleService;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.vo.BlogArticleDetailsVO;
import xin.marcher.blog.vo.BlogArticleListVO;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.util.Assert;

/**
 * 博客类型
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/blog/article")
public class BlogArticleController {

    @Autowired
    private BlogArticleService blogArticleService;

    /**
     * 文章详情
     */
    @GetMapping("/details")
    public BaseResult<BlogArticleDetailsVO> details(Long id) {
        Assert.isNullOrLtZero(id, "请选择指定文章");

        BlogArticleDetailsVO blogArticleDetailsVO = blogArticleService.details(id);

        // 浏览量+1
        blogArticleService.viewsIncrease(id);

        return BaseResult.success(blogArticleDetailsVO);

    }

    /**
     * query文章
     */
    @PostMapping("/query")
    public BaseResult<PageResult<BlogArticleListVO>> query(@RequestBody QueryData query) {
        return blogArticleService.query(query);
    }

    @PostMapping("/liked")
    public BaseResult<Integer> liked(Long id) {
        Assert.isNullOrLtZero(id, "请选择指定文章");

        Integer likedCount = blogArticleService.liked(id);

        return BaseResult.success(likedCount);
    }
}
