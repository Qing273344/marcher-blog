package xin.marcher.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xin.marcher.blog.service.BlogTagService;
import xin.marcher.blog.utils.Result;

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
    public Result getHotTag() {

        List<String> hotTagList = blogTagService.getHotTag();

        Result data = new Result().put("list", hotTagList);
        return Result.success(data);
    }

}
