package xin.marcher.blog.web.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.dto.request.BlogArticleReq;
import xin.marcher.blog.entity.BlogArticle;
import xin.marcher.blog.service.BlogArticleService;
import xin.marcher.blog.utils.*;

import javax.validation.constraints.NotNull;

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
    @RequiresPermissions("marcher")
    public Result publish(@Validated @RequestBody BlogArticleReq blogArticleFrom) {

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
    @RequiresPermissions("marcher")
    public Result getAsEdit(@NotNull(message = "请选择需要编辑的文章") Long id) {
        BlogArticle blogArticle = blogArticleService.getById(id);
        if (EmptyUtil.isEmpty(blogArticle)) {
            return Result.error(RspBaseCodeEnum.NOT_RESOURCE.getMsg());
        }

        BlogArticleReq blogArticleFrom = blogArticleService.getAsEdit(id);
        Result data = new Result().put("info", blogArticleFrom);
        return Result.success(data);
    }

    /**
     * query 文章
     * @param query 参数
     */
    @PostMapping("/query")
    @RequiresPermissions("marcher")
    public Result query(@RequestBody Query<QueryData> query) {
        return blogArticleService.queryAsAdmin(query);
    }

    /**
     * remove 指定文章
     *
     * @param id    文章id
     */
    @PostMapping("/remove")
    @RequiresPermissions("marcher")
    public Result remove(Long id) {
        Assert.isNullOrZero(id, "请选择需要删除的文章");

        blogArticleService.removeById(id);

        return Result.success();
    }

    /**
     * update 评论设置
     *
     * @param id    文章id
     */
    @PostMapping("/comment")
    @RequiresPermissions("marcher")
    public Result comment(Long id) {
        Assert.isNullOrZero(id, "请选择需要点赞的文章");

        blogArticleService.comment(id);

        return Result.success();
    }

    /**
     * update 置顶设置
     *
     * @param id    文章id
     */
    @PostMapping("/top")
    @RequiresPermissions("marcher")
    public Result top(Long id) {
        Assert.isNullOrZero(id, "请选择需要置顶的文章");

        blogArticleService.top(id);

        return Result.success();
    }
}
