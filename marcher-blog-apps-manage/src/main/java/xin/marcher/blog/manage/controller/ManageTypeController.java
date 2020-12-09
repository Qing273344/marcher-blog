package xin.marcher.blog.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.article.client.api.BlogTypeApi;
import xin.marcher.blog.article.client.model.request.BlogTypeReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleTypeResp;
import xin.marcher.framework.common.mvc.request.BaseQuery;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.mvc.response.PageResult;
import xin.marcher.framework.common.wrapper.PageWO;

import java.util.List;

/**
 * 类型
 *
 * @author marcher
 */
@RequiresRoles("marcher")
@RestController
@RequestMapping(value = "/manage/type", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - ManagerTypeController", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageTypeController {

    private final BlogTypeApi blogTypeApi;

    @Autowired
    public ManageTypeController(BlogTypeApi blogTypeApi) {
        this.blogTypeApi = blogTypeApi;
    }

    /**
     * 类型_详情
     *
     * @param id    博客类型id
     * @return result
     */
    @GetMapping("/get")
    @ApiOperation(httpMethod = "GET", value = "get 博客类型详情")
    public BaseResult<BlogArticleTypeResp> get(@RequestParam("id") Long id) {
        return blogTypeApi.get(id);
    }

    /**
     * 类型_查询
     *
     * @param query query 参数
     */
    @PostMapping("/query")
    @ApiOperation(httpMethod = "POST", value = "query 类型")
    public BaseResult<PageResult<BlogArticleTypeResp>> query(@RequestBody BaseQuery query) {
        BaseResult<PageWO<BlogArticleTypeResp>> result = blogTypeApi.query(query);
        return BaseResult.success(result.getData(), query);
    }

    /**
     * 类型_所有
     */
    @GetMapping("/listAll")
    @ApiOperation(httpMethod = "POST", value = "query 类型")
    public BaseResult<List<BlogArticleTypeResp>> listAll() {
        return blogTypeApi.listAll();
    }

    /**
     * 类型_新增
     * @param reqs  reqs
     * @return result
     */
    @PostMapping("/save")
    @ApiOperation(httpMethod = "POST", value = "新增类型")
    public BaseResult<Boolean> save(@Validated @RequestBody BlogTypeReqs reqs) {
        blogTypeApi.save(reqs);

        return BaseResult.success();
    }

    /**
     * 类型_编辑
     * @param reqs  reqs
     * @return result
     */
    @PostMapping("/update")
    @ApiOperation(httpMethod = "POST", value = "编辑类型")
    public BaseResult<Boolean> update(@Validated() @RequestBody BlogTypeReqs reqs) {
        blogTypeApi.update(reqs);

        return BaseResult.success();
    }

    /**
     * 类型_删除
     *
     * @param ids   类型id
     * @return result
     */
    @PostMapping("/remove")
    @ApiOperation(httpMethod = "POST", value = "删除类型")
    public BaseResult<Boolean> remove(@RequestBody List<Long> ids) {
        blogTypeApi.remove(ids);

        return BaseResult.success();
    }

}
