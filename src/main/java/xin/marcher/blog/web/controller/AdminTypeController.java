package xin.marcher.blog.web.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.dto.request.BlogArticleTypeReq;
import xin.marcher.blog.dto.response.BlogArticleTypeResp;
import xin.marcher.blog.service.BlogTypeService;
import xin.marcher.blog.utils.Assert;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;
import xin.marcher.blog.validator.group.FirstGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.groups.Default;
import java.util.List;

/**
 * 博客文章类型
 *
 * @author marcher
 */
@Validated
@RestController
@RequestMapping(value = "/admin/type")
public class AdminTypeController {

    @Autowired
    private BlogTypeService blogTypeService;

    /**
     * 博客类型详情
     *
     * @param id    博客类型id
     */
    @GetMapping("/get")
    @RequiresPermissions("marcher")
    public Result get(Long id) {
        Assert.isNullOrZero(id, "请选择分类");

        BlogArticleTypeResp blogArticleTypeResp = blogTypeService.get(id);

        Result data = new Result().put("info", blogArticleTypeResp);
        return Result.success(data);
    }

    /**
     * 获取所有博客类型
     */
    @GetMapping("/listAll")
    @RequiresPermissions("marcher")
    public Result listAll() {
        List<BlogArticleTypeResp> blogArticleTypeRespList = blogTypeService.listAll();

        Result data = new Result().put("list", blogArticleTypeRespList);
        return Result.success(data);
    }

    /**
     * query 文章类型
     *
     * @param query query参数
     */
    @PostMapping("/query")
    @RequiresPermissions("marcher")
    public Result query(@RequestBody Query<QueryData> query) {
        return blogTypeService.query(query);
    }

    /**
     * save 博客文章分类
     *
     * @param blogArticleTypeReq   分类信息form
     */
    @PostMapping("/save")
    @RequiresPermissions("marcher")
    public Result save(@Validated @RequestBody BlogArticleTypeReq blogArticleTypeReq) {
        blogTypeService.create(blogArticleTypeReq);

        return Result.success();
    }

    /**
     * update 博客文章分类
     *
     * @param blogArticleTypeReq   分类信息form
     */
    @PostMapping("/update")
    @RequiresPermissions("marcher")
    public Result update(@Validated({FirstGroup.class, Default.class}) @RequestBody BlogArticleTypeReq blogArticleTypeReq) {
        blogTypeService.update(blogArticleTypeReq);

        return Result.success();
    }

    /**
     * remove 博客文章分类
     *
     * @param ids   文章分类id
     */
    @PostMapping("/remove")
    @RequiresPermissions("marcher")
    public Result remove(@NotEmpty(message = "请至少选择一条记录") @RequestBody  List<Long> ids) {
        blogTypeService.remove(ids);

        return Result.success();
    }
}
