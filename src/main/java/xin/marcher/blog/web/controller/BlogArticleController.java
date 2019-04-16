package xin.marcher.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.service.BlogArticleService;
import xin.marcher.blog.utils.EmptyUtil;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;
import xin.marcher.blog.vo.BlogArticleDetailsVo;

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
        if (EmptyUtil.isEmpty(id)) {
            return Result.error(RspBaseCodeEnum.PARAM_MISS, "请选择指定文章");
        }

        BlogArticleDetailsVo blogArticleDetails = blogArticleService.details(id);

        Result data = new Result().put("info", blogArticleDetails);
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
        if (EmptyUtil.isEmpty(id)) {
            return Result.error(RspBaseCodeEnum.PARAM_MISS, "请选择指定文章");
        }

        Integer likedCount = blogArticleService.liked(id);

        Result data = new Result().put("likedCount", likedCount);
        return Result.success(data);
    }
}
