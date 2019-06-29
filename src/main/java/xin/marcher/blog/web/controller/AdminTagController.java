package xin.marcher.blog.web.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.dto.request.BlogTagReq;
import xin.marcher.blog.service.BlogTagService;
import xin.marcher.blog.utils.*;
import xin.marcher.blog.dto.response.BlogTagResp;
import xin.marcher.blog.validator.group.FirstGroup;

import javax.validation.constraints.NotEmpty;
import javax.validation.groups.Default;
import java.util.List;

/**
 * 博客标签
 *
 * @author marcher
 */
@Validated
@RestController
@RequestMapping(value = "/admin/tag")
public class AdminTagController {


    @Autowired
    private BlogTagService blogTagService;

    /**
     * get标签详情
     *
     * @param id    标签id
     */
    @GetMapping("/get")
    @RequiresPermissions("marcher")
    public Result get(Long id) {
        Assert.isNullOrZero(id, "请选择标签");

        BlogTagResp blogTagResp = blogTagService.get(id);

        Result data = new Result().put("info", blogTagResp);
        return Result.success(data);
    }

    /**
     * list 所有标签
     */
    @GetMapping("/listAll")
    @RequiresPermissions("marcher")
    public Result listAll() {
        List<BlogTagResp> list = blogTagService.listAll();

        Result data = new Result().put("list", list);
        return Result.success(data);
    }

    /**
     * query 标签
     *
     * @param query query参数
     */
    @PostMapping("/query")
    @RequiresPermissions("marcher")
    public Result query(@RequestBody Query<QueryData> query) {
        return blogTagService.query(query);
    }

    /**
     * 新增标签
     */
    @PostMapping("/save")
    @RequiresPermissions("marcher")
    public Result save(@Validated @RequestBody BlogTagReq blogTagReq) {
        blogTagService.create(blogTagReq);

        return Result.success();
    }

    /**
     * 编辑标签
     */
    @PostMapping("/update")
    @RequiresPermissions("marcher")
    public Result update(@Validated({FirstGroup.class, Default.class}) @RequestBody BlogTagReq blogTagReq) {
        blogTagService.update(blogTagReq);

        return Result.success();
    }

    /**
     * 删除标签
     *
     * @param ids   标签id
     */
    @PostMapping("/remove")
    @RequiresPermissions("marcher")
    public Result remove(@NotEmpty(message = "请至少选择一条记录") @RequestBody List<Long> ids) {
        blogTagService.remove(ids);

        return Result.success();
    }

}
