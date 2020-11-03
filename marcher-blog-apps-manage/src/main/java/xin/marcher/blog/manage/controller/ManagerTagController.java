package xin.marcher.blog.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.article.client.api.BlogTagApi;
import xin.marcher.blog.article.client.model.request.BlogTagReqs;
import xin.marcher.blog.article.client.model.response.BlogTagResp;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;

import java.util.List;

/**
 * 标签
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/manage/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - ManagerTagController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerTagController {

    private final BlogTagApi blogTagApi;

    @Autowired
    public ManagerTagController(BlogTagApi blogTagApi) {
        this.blogTagApi = blogTagApi;
    }

    /**
     * get标签详情
     *
     * @param id    标签id
     * @return result
     */
    @GetMapping("/get")
    @ApiOperation(httpMethod = "GET", value = "get 标签详情")
    public BaseResult<BlogTagResp> get(Long id) {
        return blogTagApi.get(id);
    }

    /**
     * query 标签
     *
     * @param query query 参数
     */
    @PostMapping("/query")
    @ApiOperation(httpMethod = "POST", value = "query 标签")
    public BaseResult<PageResult<BlogTagResp>> query(@RequestBody BaseQuery query) {
        return blogTagApi.query(query);
    }

    /**
     * 新增标签
     * @param reqs  reqs
     * @return result
     */
    @PostMapping("/save")
    @ApiOperation(httpMethod = "POST", value = "新增标签")
    public BaseResult<Boolean> save(@Validated @RequestBody BlogTagReqs reqs) {
        blogTagApi.save(reqs);

        return BaseResult.success();
    }

    /**
     * 编辑标签
     * @param reqs  reqs
     * @return result
     */
    @PostMapping("/update")
    @ApiOperation(httpMethod = "POST", value = "编辑标签")
    public BaseResult<Boolean> update(@Validated() @RequestBody BlogTagReqs reqs) {
        blogTagApi.update(reqs);

        return BaseResult.success();
    }

    /**
     * 删除标签
     *
     * @param ids   id 数组
     */
    @PostMapping("/remove")
    @ApiOperation(httpMethod = "POST", value = "删除标签")
    public BaseResult<Boolean> remove(@RequestBody List<Long> ids) {
        blogTagApi.remove(ids);

        return BaseResult.success();
    }
}
