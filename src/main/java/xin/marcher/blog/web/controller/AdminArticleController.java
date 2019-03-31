package xin.marcher.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.entity.BlogArticle;
import xin.marcher.blog.from.BlogArticleFrom;
import xin.marcher.blog.service.BlogArticleService;
import xin.marcher.blog.utils.EmptyUtil;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;

import javax.validation.Valid;

/**
 * 博客文章管理
 *
 * @author marcher
 */
@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {

    @Autowired
    private BlogArticleService blogArticleService;

    /**
     * 发布文章
     *
     * @param blogArticleFrom   文章信息
     */
    @PostMapping("/publish")
    public Result publish(@Valid @RequestBody BlogArticleFrom blogArticleFrom) {

        Long articleId = blogArticleService.publish(blogArticleFrom);

        Result data = new Result().put("id", articleId);
        return Result.success(data);
    }

    /**
     * get文章 - 编辑
     *
     * @param id    文章id
     */
    @GetMapping("/getAsEdit")
    public Result getAsEdit(Long id) {
        if (EmptyUtil.isEmpty(id)) {
            return Result.error(RspBaseCodeEnum.PARAM_MISS, "id");
        }
        BlogArticle blogArticle = blogArticleService.getById(id);
        if (EmptyUtil.isEmpty(blogArticle)) {
            return Result.error("无记录");
        }

        BlogArticleFrom blogArticleFrom = blogArticleService.getAsEdit(id);
        Result data = new Result().put("info", blogArticleFrom);
        return Result.success(data);
    }

    /**
     * query 文章
     * @param query 参数
     */
    @PostMapping("/query")
    public Result query(@RequestBody Query<QueryData> query) {
        return blogArticleService.queryAsAdmin(query);
    }

    /**
     * remove 指定文章
     *
     * @param id    文章id
     */
    @PostMapping("/remove")
    public Result remove(Long id) {
        if (EmptyUtil.isEmpty(id)) {
            return Result.error(RspBaseCodeEnum.PARAM_MISS, "id");
        }

        blogArticleService.removeById(id);

        return Result.success();
    }

    /**
     * update 评论设置
     *
     * @param id    文章id
     */
    @PostMapping("/comment")
    public Result comment(Long id) {
        if (EmptyUtil.isEmpty(id)) {
            return Result.error(RspBaseCodeEnum.PARAM_MISS, "id");
        }

        blogArticleService.comment(id);

        return Result.success();
    }

    /**
     * update 置顶设置
     *
     * @param id    文章id
     */
    @PostMapping("/top")
    public Result top(Long id) {
        if (EmptyUtil.isEmpty(id)) {
            return Result.error(RspBaseCodeEnum.PARAM_MISS, "id");
        }

        blogArticleService.top(id);

        return Result.success();
    }
}
