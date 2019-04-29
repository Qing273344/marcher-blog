package xin.marcher.blog.web.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.from.BlogArticleTypeFrom;
import xin.marcher.blog.service.BlogTypeService;
import xin.marcher.blog.utils.*;
import xin.marcher.blog.vo.BlogArticleTypeVo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 博客文章类型
 *
 * @author marcher
 */
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

        BlogArticleTypeVo blogArticleTypeVo = blogTypeService.get(id);

        Result data = new Result().put("info", blogArticleTypeVo);
        return Result.success(data);
    }

    /**
     * 获取所有博客类型
     */
    @GetMapping("/listAll")
    @RequiresPermissions("marcher")
    public Result listAll() {
        List<BlogArticleTypeVo> blogArticleTypeVoList = blogTypeService.listAll();

        Result data = new Result().put("list", blogArticleTypeVoList);
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
     * @param blogArticleTypeFrom   分类信息form
     */
    @PostMapping("/save")
    @RequiresPermissions("marcher")
    public Result save(@RequestBody BlogArticleTypeFrom blogArticleTypeFrom) {
        blogTypeService.create(blogArticleTypeFrom);

        return Result.success();
    }

    /**
     * update 博客文章分类
     *
     * @param blogArticleTypeFrom   分类信息form
     */
    @PostMapping("/update")
    @RequiresPermissions("marcher")
    public Result update(@RequestBody BlogArticleTypeFrom blogArticleTypeFrom) {
        Assert.isNullOrZero(blogArticleTypeFrom.getTypeId(), "请选择需要修改的分类");

        blogTypeService.update(blogArticleTypeFrom);

        return Result.success();
    }

    /**
     * remove 博客文章分类
     *
     * @param ids   文章分类id
     */
    @PostMapping("/remove")
    @RequiresPermissions("marcher")
    public Result remove(@RequestBody  Long[] ids) {
        Assert.isEmpty(ids, "请至少选择一条记录");

        List<Long> idList = ConvertUtil.arrayToList(ids);
        blogTypeService.remove(idList);

        return Result.success();
    }
}
