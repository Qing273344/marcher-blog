package xin.marcher.blog.manage.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import xin.marcher.blog.manage.model.cache.OssSignatureCO;
import xin.marcher.framework.common.exception.BusinessException;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.util.EmptyUtil;
import xin.marcher.framework.common.util.OrikaMapperUtil;
import xin.marcher.framework.redis.RedisService;
import xin.marcher.oss.client.api.OssApi;
import xin.marcher.oss.client.model.request.UploadReqs;
import xin.marcher.oss.client.model.response.OssSignatureResp;

import java.io.IOException;

/**
 * 博客通用 apps article
 *
 * @author marcher
 */
@Slf4j
@RequiresRoles("marcher")
@RestController
@RequestMapping(value = "/manage/common")
@Api(value = "WEB - ManagerCommonController", tags = "通用")
public class ManageCommonController {

    private final OssApi ossApi;
    private final RedisService redisService;

    public ManageCommonController(OssApi ossApi, RedisService redisService) {
        this.ossApi = ossApi;
        this.redisService = redisService;
    }

    @PostMapping("/upload")
    @ApiOperation(httpMethod = "POST", value = "上传文件")
    public BaseResult<String> upload(@RequestParam("file") MultipartFile file) {
        if (EmptyUtil.isEmpty(file)) {
            throw new BusinessException("请上传文件");
        }

        byte[] input;
        try {
            input = file.getBytes();
        } catch (IOException e) {
            throw new BusinessException("文件流获取失败");
        }

        UploadReqs reqs = new UploadReqs();
        reqs.setFileName(file.getOriginalFilename());
        reqs.setInput(input);
        String fileKey = ossApi.upload(reqs);

        return BaseResult.success(fileKey);
    }

    /**
     * oss签名
     */
    @GetMapping("/ossSignature")
    @ApiOperation(httpMethod = "GET", value = "OSS签名")
    public BaseResult<OssSignatureCO> ossSignature() {
        String ossSignatureKey = "oss_signature_key";
        OssSignatureCO ossSignatureCo = redisService.getObj(ossSignatureKey, OssSignatureCO.class);
        if (EmptyUtil.isEmpty(ossSignatureCo)) {
            OssSignatureResp signatureResp = ossApi.signature();
            ossSignatureCo = OrikaMapperUtil.INSTANCE.map(signatureResp, OssSignatureCO.class);
            redisService.setObj(ossSignatureKey, ossSignatureCo, (60L * 60));
        }

        return BaseResult.success(ossSignatureCo);
    }

}
