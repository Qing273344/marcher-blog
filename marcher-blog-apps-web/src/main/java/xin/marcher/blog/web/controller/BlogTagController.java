package xin.marcher.blog.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.article.client.api.BlogTagApi;
import xin.marcher.framework.mvc.response.BaseResult;

import java.util.List;

/**
 * 博客文章 apps tag
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/article/tag", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = "WEB - BlogTagController", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlogTagController {

    private final BlogTagApi blogTagApi;

    @Autowired
    public BlogTagController(BlogTagApi blogTagApi) {
        this.blogTagApi = blogTagApi;
    }

    /**
     * 获取火热的 tag
     */
    @GetMapping("/getHotTag")
    @ApiOperation("获取火热的 tag")
    public BaseResult<List<String>> getHotTag() {
        return blogTagApi.getHotTag();
    }
}
