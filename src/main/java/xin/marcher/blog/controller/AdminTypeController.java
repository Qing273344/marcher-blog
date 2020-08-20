package xin.marcher.blog.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.dto.BlogArticleTypeDTO;
import xin.marcher.blog.service.BlogTypeService;
import xin.marcher.blog.utils.Assert;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;
import xin.marcher.blog.vo.BlogArticleTypeVO;
import xin.marcher.framework.mvc.validation.groups.GroupUpdateOrder;

import javax.validation.constraints.NotEmpty;
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
    @RequiresRoles("marcher")
    public Result get(Long id) {
        Assert.isNullOrZero(id, "请选择分类");

        BlogArticleTypeVO blogArticleTypeVO = blogTypeService.get(id);

        Result data = new Result().put("info", blogArticleTypeVO);
        return Result.success(data);
    }

    /**
     * 获取所有博客类型
     */
    @GetMapping("/listAll")
    @RequiresRoles("marcher")
    public Result listAll() {
        List<BlogArticleTypeVO> blogArticleTypeVOList = blogTypeService.listAll();

        Result data = new Result().put("list", blogArticleTypeVOList);
        return Result.success(data);
    }

    /**
     * query 文章类型
     *
     * @param query query参数
     */
    @PostMapping("/query")
    @RequiresRoles("marcher")
    public Result query(@RequestBody Query<QueryData> query) {
        return blogTypeService.query(query);
    }

    /**
     * save 博客文章分类
     *
     * @param blogArticleTypeDTO   分类信息form
     */
    @PostMapping("/save")
    @RequiresRoles("marcher")
    public Result save(@Validated @RequestBody BlogArticleTypeDTO blogArticleTypeDTO) {
        blogTypeService.create(blogArticleTypeDTO);

        return Result.success();
    }

    /**
     * update 博客文章分类
     *
     * @param blogArticleTypeDTO   分类信息form
     */
    @PostMapping("/update")
    @RequiresRoles("marcher")
    public Result update(@Validated(GroupUpdateOrder.class) @RequestBody BlogArticleTypeDTO blogArticleTypeDTO) {
        blogTypeService.update(blogArticleTypeDTO);

        return Result.success();
    }

    /**
     * remove 博客文章分类
     *
     * @param ids   文章分类id
     */
    @PostMapping("/remove")
    @RequiresRoles("marcher")
    public Result remove(@NotEmpty(message = "请至少选择一条记录") @RequestBody  List<Long> ids) {
        blogTypeService.remove(ids);

        return Result.success();
    }
}
