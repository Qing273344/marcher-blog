package xin.marcher.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.article.client.feign.BlogTagFeign;
import xin.marcher.framework.mvc.response.BaseResult;

import java.util.List;

/**
 * 博客文章 apps tag
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/article/tag", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlogTagController {

    private final BlogTagFeign blogTagFeign;

    @Autowired
    public BlogTagController(BlogTagFeign blogTagFeign) {
        this.blogTagFeign = blogTagFeign;
    }

    /**
     * 获取火热的 tag
     * @return
     */
    @GetMapping("/getHotTag")
    public BaseResult<List<String>> getHotTag() {
        return blogTagFeign.getHotTag();
    }
}
