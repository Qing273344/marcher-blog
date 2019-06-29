package xin.marcher.blog.web.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xin.marcher.blog.service.OssService;
import xin.marcher.blog.utils.Assert;
import xin.marcher.blog.utils.Result;

import javax.validation.constraints.NotNull;

/**
 * 上传
 *
 * @author marcher
 */
@Validated
@RestController
@RequestMapping(value = "/admin/upload")
public class AdminUploadController {

    @Autowired
    private OssService ossService;

    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件url
     */
    @PostMapping("/uploadFile")
    @RequiresPermissions("marcher")
    public Result upload(@NotNull(message = "上传文件不能为空") @RequestParam("file") MultipartFile file) {
        String fileUlr = ossService.putFile(file);

        Result data = new Result().put("info", fileUlr);
        return Result.success(data);
    }

}
