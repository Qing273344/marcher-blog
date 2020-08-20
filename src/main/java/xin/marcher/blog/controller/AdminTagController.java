package xin.marcher.blog.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.dto.BlogTagDTO;
import xin.marcher.blog.service.BlogTagService;
import xin.marcher.blog.utils.Assert;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;
import xin.marcher.blog.vo.BlogTagVO;
import xin.marcher.framework.mvc.validation.groups.GroupUpdateOrder;

import javax.validation.constraints.NotEmpty;
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
    @RequiresRoles("marcher")
    public Result get(Long id) {
        Assert.isNullOrZero(id, "请选择标签");

        BlogTagVO blogTagVO = blogTagService.get(id);

        Result data = new Result().put("info", blogTagVO);
        return Result.success(data);
    }

    /**
     * list 所有标签
     */
    @GetMapping("/listAll")
    @RequiresRoles("marcher")
    public Result listAll() {
        List<BlogTagVO> list = blogTagService.listAll();

        Result data = new Result().put("list", list);
        return Result.success(data);
    }

    /**
     * query 标签
     *
     * @param query query参数
     */
    @PostMapping("/query")
    @RequiresRoles("marcher")
    public Result query(@RequestBody Query<QueryData> query) {
        return blogTagService.query(query);
    }

    /**
     * 新增标签
     */
    @PostMapping("/save")
    @RequiresRoles("marcher")
    public Result save(@Validated @RequestBody BlogTagDTO blogTagDTO) {
        blogTagService.create(blogTagDTO);

        return Result.success();
    }

    /**
     * 编辑标签
     */
    @PostMapping("/update")
    @RequiresRoles("marcher")
    public Result update(@Validated(GroupUpdateOrder.class) @RequestBody BlogTagDTO blogTagDTO) {
        blogTagService.update(blogTagDTO);

        return Result.success();
    }

    /**
     * 删除标签
     *
     * @param ids   标签id
     */
    @PostMapping("/remove")
    @RequiresRoles("marcher")
    public Result remove(@NotEmpty(message = "请至少选择一条记录") @RequestBody List<Long> ids) {
        blogTagService.remove(ids);

        return Result.success();
    }

}
