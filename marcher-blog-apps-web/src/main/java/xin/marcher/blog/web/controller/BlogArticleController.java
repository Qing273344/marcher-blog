package xin.marcher.blog.web.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.article.client.feign.BlogArticleFeign;
import xin.marcher.blog.article.client.model.response.BlogArticleDetailsResp;
import xin.marcher.blog.article.client.model.response.BlogArticleListResp;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.util.Assert;

import javax.validation.Valid;

/**
 * 博客文章 apps article
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/article", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - BlogArticleController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlogArticleController {

    private final BlogArticleFeign blogArticleFeign;

    @Autowired
    public BlogArticleController(BlogArticleFeign blogArticleFeign) {
        this.blogArticleFeign = blogArticleFeign;
    }

    /**
     * 文章详情
     * @param id    id
     * @return  result
     */
    @GetMapping("/detail")
    public BaseResult<BlogArticleDetailsResp> detail(@RequestParam("id") Long id) {
        Assert.isNullOrLtZero(id, "请选择指定文章");
        return blogArticleFeign.detail(id);
    }

    /**
     * query 文章
     * @param query query
     * @return  result
     */
    @PostMapping("/query")
    public BaseResult<PageResult<BlogArticleListResp>> query(@Valid @RequestBody BaseQuery query) {
        return blogArticleFeign.query(query);
    }

    /**
     * 喜欢
     * @param id    id
     * @return  result
     */
    @PostMapping("/liked")
    public BaseResult<Integer> liked(@RequestParam("id") Long id) {
        Assert.isNullOrLtZero(id, "请选择指定文章");
        return blogArticleFeign.liked(id);
    }
}
