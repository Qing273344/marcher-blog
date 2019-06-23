package xin.marcher.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.service.BlogArticleService;
import xin.marcher.blog.utils.*;
import xin.marcher.blog.dto.response.BlogArticleDetailsResp;

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
    public Result details(Long id) {
        Assert.isNullOrZero(id, "请选择指定文章");

        BlogArticleDetailsResp blogArticleDetailsResp = blogArticleService.details(id);

        // 浏览量+1
        blogArticleService.viewsIncrease(id);

        Result data = new Result().put("info", blogArticleDetailsResp);
        return Result.success(data);
    }

    /**
     * query文章
     */
    @PostMapping("/query")
    public Result query(@RequestBody Query<QueryData> query) {
        Result successPage = blogArticleService.query(query);
        return successPage;
    }

    @PostMapping("/liked")
    public Result liked(Long id) {
        Assert.isNullOrZero(id, "请选择指定文章");

        Integer likedCount = blogArticleService.liked(id);

        Result data = new Result().put("likedCount", likedCount);
        return Result.success(data);
    }
}
