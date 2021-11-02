package xin.marcher.blog.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.article.client.api.BlogArticleApi;
import xin.marcher.blog.article.client.model.request.BlogArticleReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleListResp;
import xin.marcher.blog.article.client.model.response.BlogArticleResp;
import xin.marcher.blog.manage.model.dto.PublishDTO;
import xin.marcher.framework.common.mvc.request.BaseQuery;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.mvc.response.PageResult;
import xin.marcher.framework.common.util.OrikaMapperUtil;
import xin.marcher.framework.common.wrapper.BaseWO;
import xin.marcher.framework.common.wrapper.PageWO;

import javax.validation.Valid;

/**
 * 博客文章 apps article
 *
 * @author marcher
 */
@Slf4j
@RequiresRoles("marcher")
@RestController
@RequestMapping(value = "/manage/article", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - ManagerArticleController", tags = "文章", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManageArticleController {

    private final BlogArticleApi blogArticleApi;

    public ManageArticleController(BlogArticleApi blogArticleApi) {
        this.blogArticleApi = blogArticleApi;
    }

    /**
     * 查询文章
     *
     * @param query query
     * @return  result
     */
    @PostMapping("/query")
    @ApiOperation(httpMethod = "POST", value = "查询文章")
    BaseResult<PageResult<BlogArticleListResp>> query(@RequestBody BaseQuery query) {
        BaseResult<PageWO<BlogArticleListResp>> result = blogArticleApi.queryFromManage(query);
        return BaseResult.success(result.getData(), query);
    }

    /**
     * 发布文章
     *
     * @param dto   文章信息
     */
    @PostMapping("/publish")
    @ApiOperation(httpMethod = "POST", value = "发布文章")
    BaseResult<BaseWO> publish(@Valid @RequestBody PublishDTO dto) {
        BlogArticleReqs reqs = OrikaMapperUtil.INSTANCE.map(dto, BlogArticleReqs.class);
        return blogArticleApi.publish(reqs);
    }

    /**
     * 获取文章 - 编辑
     *
     * @param id    文章id
     */
    @GetMapping("/getAsEdit")
    @ApiOperation(httpMethod = "GET", value = "获取文章")
    BaseResult<BlogArticleResp> getAsEdit(@RequestParam("id") Long id) {
        return blogArticleApi.getAsEdit(id);
    }

    /**
     * 文章删除
     *
     * @param id    文章id
     * @return result
     */
    @PostMapping("/remove")
    @ApiOperation(httpMethod = "POST", value = "文章删除")
    BaseResult<Boolean> remove(@RequestParam("id") Long id) {
        return blogArticleApi.remove(id);
    }

    /**
     * 评论设置
     *
     * @param id    文章id
     * @return result
     */
    @PostMapping("/comment")
    @ApiOperation(httpMethod = "POST", value = "评论设置")
    BaseResult<Boolean> comment(@RequestParam("id") Long id) {
        return blogArticleApi.comment(id);
    }

    /**
     * 置顶设置
     *
     * @param id    文章id
     * @return result
     */
    @PostMapping("/top")
    @ApiOperation(httpMethod = "POST", value = "置顶设置")
    BaseResult<Boolean> top(@RequestParam("id") Long id) {
        return blogArticleApi.top(id);
    }
}
