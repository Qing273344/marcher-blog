package xin.marcher.blog.article.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.article.client.api.BlogTagApi;
import xin.marcher.blog.article.client.model.request.BlogTagReqs;
import xin.marcher.blog.article.client.model.response.BlogTagResp;
import xin.marcher.blog.article.service.BlogTagService;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.util.Assert;
import xin.marcher.framework.util.UrlPathUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 博客标签
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/rpc/tag")
@Api(value = "RPC - BlogTagFeignClient", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlogTagApiClient implements BlogTagApi {

    private final BlogTagService blogTagService;

    @Autowired
    public BlogTagApiClient(BlogTagService blogTagService) {
        this.blogTagService = blogTagService;
    }

    @Override
    @GetMapping("/getHotTag")
    public BaseResult<List<String>> getHotTag() {
        List<String> hotTagList = blogTagService.getHotTag();
        return BaseResult.success(hotTagList);
    }

    @Override
    @GetMapping("/get")
    public BaseResult<BlogTagResp> get(@RequestParam("id") Long id) {
        Assert.isNullOrLtZero(id, "请选择标签");

        BlogTagResp resp = blogTagService.get(id);
        return BaseResult.success(resp);
    }

    @Override
    @GetMapping("/listAll")
    public BaseResult<List<BlogTagResp>> listAll() {
        List<BlogTagResp> list = blogTagService.listAll();
        return BaseResult.success(list);
    }

    @Override
    @PostMapping("/query")
    public BaseResult<PageResult<BlogTagResp>> query(@RequestBody BaseQuery query) {
        return blogTagService.query(query);
    }

    @Override
    @PostMapping("/save")
    public BaseResult<Boolean> save(@Validated @RequestBody BlogTagReqs reqs) {
        blogTagService.create(reqs);
        return BaseResult.success();
    }

    @Override
    @PostMapping("/update")
    public BaseResult<Boolean> update(@Validated() @RequestBody BlogTagReqs reqs) {
        blogTagService.update(reqs);

        return BaseResult.success();
    }

    @Override
    @PostMapping("/remove")
    public BaseResult<Boolean> remove(@RequestBody List<Long> ids) {
        Assert.isEmpty(ids, "请至少选择一条记录");

        blogTagService.remove(ids);
        return BaseResult.success();
    }

}
