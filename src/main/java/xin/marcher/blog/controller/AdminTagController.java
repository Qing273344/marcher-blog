package xin.marcher.blog.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.dto.BlogTagDTO;
import xin.marcher.blog.service.BlogTagService;
import xin.marcher.blog.dto.BaseQuery;
import xin.marcher.blog.vo.BlogTagVO;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.mvc.validation.groups.GroupUpdateOrder;
import xin.marcher.framework.util.Assert;

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
    public BaseResult<BlogTagVO> get(Long id) {
        Assert.isNullOrLtZero(id, "请选择标签");

        BlogTagVO blogTagVO = blogTagService.get(id);
        return BaseResult.success(blogTagVO);
    }

    /**
     * list 所有标签
     */
    @GetMapping("/listAll")
    @RequiresRoles("marcher")
    public BaseResult<List<BlogTagVO>> listAll() {
        List<BlogTagVO> list = blogTagService.listAll();
        return BaseResult.success(list);
    }

    /**
     * query 标签
     *
     * @param query query参数
     */
    @PostMapping("/query")
    @RequiresRoles("marcher")
    public BaseResult<PageResult<BlogTagVO>> query(@RequestBody BaseQuery query) {
        return blogTagService.query(query);
    }

    /**
     * 新增标签
     */
    @PostMapping("/save")
    @RequiresRoles("marcher")
    public BaseResult save(@Validated @RequestBody BlogTagDTO blogTagDTO) {
        blogTagService.create(blogTagDTO);

        return BaseResult.success();
    }

    /**
     * 编辑标签
     */
    @PostMapping("/update")
    @RequiresRoles("marcher")
    public BaseResult update(@Validated(GroupUpdateOrder.class) @RequestBody BlogTagDTO blogTagDTO) {
        blogTagService.update(blogTagDTO);

        return BaseResult.success();
    }

    /**
     * 删除标签
     *
     * @param ids   标签id
     */
    @PostMapping("/remove")
    @RequiresRoles("marcher")
    public BaseResult remove(@NotEmpty(message = "请至少选择一条记录") @RequestBody List<Long> ids) {
        blogTagService.remove(ids);

        return BaseResult.success();
    }

}
