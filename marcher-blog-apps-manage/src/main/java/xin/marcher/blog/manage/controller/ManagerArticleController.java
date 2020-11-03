package xin.marcher.blog.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.article.client.api.BlogArticleApi;
import xin.marcher.blog.article.client.model.request.BlogArticleReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleListResp;
import xin.marcher.blog.article.client.model.response.BlogArticleResp;
import xin.marcher.blog.manage.model.dto.PublishDTO;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.util.OrikaMapperUtil;
import xin.marcher.framework.wrapper.BaseWO;

import javax.validation.Valid;

/**
 * 博客文章 apps article
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/manage/article", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - ManagerArticleController", tags = "文章", produces = MediaType.APPLICATION_JSON_VALUE)
public class ManagerArticleController {

    @Autowired
    private BlogArticleApi blogArticleApi;

    /**
     * 查询文章
     *
     * @param query query
     * @return  result
     */
    @PostMapping("/query")
    @ApiOperation(httpMethod = "POST", value = "查询文章")
    BaseResult<PageResult<BlogArticleListResp>> query(@RequestBody BaseQuery query) {
        return blogArticleApi.query(query);
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
