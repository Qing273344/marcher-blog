package xin.marcher.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.from.BlogTagFrom;
import xin.marcher.blog.service.BlogTagService;
import xin.marcher.blog.utils.EmptyUtil;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;
import xin.marcher.blog.vo.BlogTagVo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 博客标签
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/blog/tag")
public class BlogTagController {

    @Autowired
    private BlogTagService blogTagService;

    /**
     * get标签详情
     *
     * @param id    标签id
     */
    @GetMapping("/get")
    @ResponseBody
    public Result get(Long id) {
        if (EmptyUtil.isEmpty(id)) {
            return Result.error("请选择记录");
        }

        BlogTagVo blogTagVo = blogTagService.get(id);

        Result data = new Result().put("info", blogTagVo);
        return Result.success(data);
    }

    /**
     * list 所有标签
     */
    @GetMapping("/listAll")
    @ResponseBody
    public Result listAll() {
        List<BlogTagVo> list = blogTagService.listAll();

        Result data = new Result().put("list", list);
        return Result.success(data);
    }

    /**
     * query 标签
     *
     * @param query query参数
     */
    @PostMapping("/query")
    @ResponseBody
    public Result query(@RequestBody Query<QueryData> query) {
        return blogTagService.query(query);
    }

    /**
     * 新增标签
     */
    @PostMapping("/save")
    @ResponseBody
    public Result save(@RequestBody BlogTagFrom blogTagFrom) {
        blogTagService.create(blogTagFrom);

        return Result.success();
    }

    /**
     * 编辑标签
     */
    @PostMapping("/update")
    @ResponseBody
    public Result update(@RequestBody BlogTagFrom blogTagFrom) {
        if (EmptyUtil.isEmpty(blogTagFrom.getTagId())) {
            return Result.error("请选择需要修改的标签");
        }

        blogTagService.update(blogTagFrom);

        return Result.success();
    }

    /**
     * 删除标签
     *
     * @param ids   标签id
     */
    @PostMapping("/remove")
    @ResponseBody
    public Result remove(@RequestBody Long[] ids) {
        if (EmptyUtil.isEmpty(ids)) {
            return Result.error("请至少选择一条记录");
        }

        List<Long> idList = new ArrayList<>();
        Collections.addAll(idList, ids);
        blogTagService.remove(idList);

        return Result.success();
    }
}
