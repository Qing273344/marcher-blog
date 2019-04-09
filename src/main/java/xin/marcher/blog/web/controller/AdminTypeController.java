package xin.marcher.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.from.BlogArticleTypeFrom;
import xin.marcher.blog.service.BlogTypeService;
import xin.marcher.blog.utils.EmptyUtil;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;
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
    @ResponseBody
    public Result get(Long id) {
        if (EmptyUtil.isEmpty(id)) {
            return Result.error("请选择记录");
        }

        BlogArticleTypeVo blogArticleTypeVo = blogTypeService.get(id);

        Result data = new Result().put("info", blogArticleTypeVo);
        return Result.success(data);
    }

    /**
     * 获取所有博客类型
     */
    @GetMapping("/listAll")
    @ResponseBody
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
    @ResponseBody
    public Result query(@RequestBody Query<QueryData> query) {
        return blogTypeService.query(query);
    }

    /**
     * save 博客文章分类
     *
     * @param blogArticleTypeFrom   分类信息form
     */
    @PostMapping("/save")
    @ResponseBody
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
    @ResponseBody
    public Result update(@RequestBody BlogArticleTypeFrom blogArticleTypeFrom) {
        if (EmptyUtil.isEmpty(blogArticleTypeFrom.getTypeId())) {
            return Result.error("请选择需要修改的分类");
        }
        blogTypeService.update(blogArticleTypeFrom);

        return Result.success();
    }

    /**
     * remove 博客文章分类
     *
     * @param ids   文章分类id
     */
    @PostMapping("/remove")
    @ResponseBody
    public Result remove(@RequestBody  Long[] ids) {
        if (EmptyUtil.isEmpty(ids)) {
            return Result.error("请至少选择一条记录");
        }

        List<Long> idList = new ArrayList<>();
        Collections.addAll(idList, ids);
        blogTypeService.remove(idList);

        return Result.success();
    }
}
