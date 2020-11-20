package xin.marcher.blog.article.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.article.client.api.BlogTypeApi;
import xin.marcher.blog.article.client.model.request.BlogTypeReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleTypeResp;
import xin.marcher.blog.article.service.BlogTypeService;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.validation.groups.GroupUpdateOrder;
import xin.marcher.framework.util.Assert;
import xin.marcher.framework.wrapper.PageWO;

import java.util.List;

/**
 * 博客文章类型
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/rpc/type")
@Api(value = "RPC - BlogTypeFeignClient", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlogTypeApiClient implements BlogTypeApi {

    private final BlogTypeService blogTypeService;

    @Autowired
    public BlogTypeApiClient(BlogTypeService blogTypeService) {
        this.blogTypeService = blogTypeService;
    }

    /**
     * 博客类型详情
     *
     * @param id    博客类型id
     */
    @Override
    @GetMapping("/get")
    public BaseResult<BlogArticleTypeResp> get(@RequestParam("id") Long id) {
        Assert.isNullOrLtZero(id, "请选择分类");

        BlogArticleTypeResp blogArticleTypeVO = blogTypeService.get(id);
        return BaseResult.success(blogArticleTypeVO);
    }

    /**
     * 获取所有博客类型
     */
    @Override
    @GetMapping("/listAll")
    public BaseResult<List<BlogArticleTypeResp>> listAll() {
        List<BlogArticleTypeResp> blogArticleTypeVOList = blogTypeService.listAll();

        return BaseResult.success(blogArticleTypeVOList);
    }

    /**
     * query 文章类型
     *
     * @param query query参数
     */
    @Override
    @PostMapping("/query")
    public BaseResult<PageWO<BlogArticleTypeResp>> query(@RequestBody BaseQuery query) {
        return blogTypeService.query(query);
    }

    /**
     * save 博客文章分类
     *
     * @param reqs   分类信息form
     */
    @Override
    @PostMapping("/save")
    public BaseResult<Boolean> save(@Validated @RequestBody BlogTypeReqs reqs) {
        blogTypeService.create(reqs);

        return BaseResult.success();
    }

    /**
     * update 博客文章分类
     *
     * @param reqs   分类信息form
     */
    @Override
    @PostMapping("/update")
    public BaseResult<Boolean> update(@Validated(GroupUpdateOrder.class) @RequestBody BlogTypeReqs reqs) {
        blogTypeService.update(reqs);

        return BaseResult.success();
    }

    /**
     * remove 博客文章分类
     *
     * @param ids   文章分类id
     */
    @Override
    @PostMapping("/remove")
    public BaseResult<Boolean> remove(@RequestBody List<Long> ids) {
        Assert.isEmpty(ids, "请至少选择一条记录");

        blogTypeService.remove(ids);
        return BaseResult.success();
    }
}
