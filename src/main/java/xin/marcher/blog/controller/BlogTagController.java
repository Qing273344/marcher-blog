package xin.marcher.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.service.BlogTagService;
import xin.marcher.framework.mvc.response.BaseResult;

import java.util.List;

/**
 * 博客标签
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/blog/tag")
public class BlogTagController {

    @Autowired
    private BlogTagService blogTagService;

    /**
     * get 热门标签
     */
    @GetMapping("/getHotTag")
    public BaseResult<List<String>> getHotTag() {

        List<String> hotTagList = blogTagService.getHotTag();

        return BaseResult.success(hotTagList);
    }

}
