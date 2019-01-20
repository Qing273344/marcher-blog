package xin.marcher.blog.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.marcher.blog.entity.BlogUser;
import xin.marcher.blog.service.BlogUserService;
import xin.marcher.blog.utils.Result;
import xin.marcher.blog.utils.RedisUtil;

/**
 * 上传
 *
 * @author marcher
 */
@RestController
@RequestMapping(value = "/blog/upload")
public class BlogUploadController {

    @Autowired
    private BlogUserService blogUserService;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件url
     */
    @PostMapping("/uploadFile")
    public Result upload(@RequestParam("file") MultipartFile file) {

        return new Result().put("url", "啦啦啦");
    }

    @GetMapping("/setRedisData")
    public Result setRedisData(String str) {
        RedisUtil.setCacheStr("123", str, null);
        return Result.success();
    }

    @GetMapping("/getRedisData")
    @ResponseBody
    public Result getRedisData() {
//        String str = RedisUtil.getCacheStr("123");
//        return new Result().put("str", str);

        BlogUser blogUser = blogUserService.getUserInfoFormCache("1086454802368856066");

        Result data = new Result().put("data", blogUser);
        return Result.success(data);
    }
}
