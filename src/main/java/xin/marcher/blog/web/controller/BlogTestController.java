package xin.marcher.blog.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/blog/test")
public class BlogTestController {

    @GetMapping("test1")
    public String test1() {

        return "成功了";
    }
}
