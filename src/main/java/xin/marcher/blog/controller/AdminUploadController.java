package xin.marcher.blog.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import xin.marcher.blog.service.OssService;
import xin.marcher.framework.mvc.response.BaseResult;

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
    @RequiresRoles("marcher")
    public BaseResult<String> upload(@NotNull(message = "上传文件不能为空") @RequestParam("file") MultipartFile file) {
        String fileUlr = ossService.putFile(file);
        return BaseResult.success(fileUlr);
    }

}
