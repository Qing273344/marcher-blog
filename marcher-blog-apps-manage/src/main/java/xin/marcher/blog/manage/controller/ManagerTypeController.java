package xin.marcher.blog.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.article.client.feign.BlogTagFeign;
import xin.marcher.blog.article.client.model.request.BlogTagReqs;
import xin.marcher.blog.article.client.model.response.BlogTagResp;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;

import java.util.List;

/**
 * 类型
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/article/type", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - ManagerTypeController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerTypeController {

    private final BlogTagFeign blogTagFeign;

    @Autowired
    public ManagerTypeController(BlogTagFeign blogTagFeign) {
        this.blogTagFeign = blogTagFeign;
    }

    /**
     * 博客类型详情
     *
     * @param id    博客类型id
     * @return result
     */
    @GetMapping("/get")
    @ApiOperation(httpMethod = "GET", value = "get 博客类型详情")
    public BaseResult<BlogTagResp> get(Long id) {
        return blogTagFeign.get(id);
    }

    /**
     * query 标签
     *
     * @param query query 参数
     */
    @PostMapping("/query")
    @ApiOperation(httpMethod = "POST", value = "query 标签")
    public BaseResult<PageResult<BlogTagResp>> query(@RequestBody BaseQuery query) {
        return blogTagFeign.query(query);
    }

    /**
     * 新增标签
     * @param reqs  reqs
     * @return result
     */
    @PostMapping("/save")
    @ApiOperation(httpMethod = "POST", value = "新增标签")
    public BaseResult<Boolean> save(@Validated @RequestBody BlogTagReqs reqs) {
        blogTagFeign.save(reqs);

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
        blogTagFeign.update(reqs);

        return BaseResult.success();
    }

    /**
     * 删除标签
     *
     * @param ids   标签id
     * @return result
     */
    @PostMapping("/remove")
    @ApiOperation(httpMethod = "POST", value = "删除标签")
    public BaseResult<Boolean> remove(@RequestBody List<Long> ids) {
        blogTagFeign.remove(ids);

        return BaseResult.success();
    }

}
