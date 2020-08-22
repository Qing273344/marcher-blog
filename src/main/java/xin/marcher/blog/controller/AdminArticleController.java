package xin.marcher.blog.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.dto.BlogArticleDTO;
import xin.marcher.blog.model.BlogArticle;
import xin.marcher.blog.service.BlogArticleService;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.vo.AdminArticleListVO;
import xin.marcher.blog.vo.IdVO;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.util.Assert;
import xin.marcher.framework.util.EmptyUtil;

import javax.validation.constraints.NotNull;

/**
 * 博客文章管理
 *
 * @author marcher
 */
@RestController
@RequestMapping("/admin/article")
public class AdminArticleController {

    @Autowired
    private BlogArticleService blogArticleService;

    /**
     * 发布文章
     *
     * @param blogArticleFrom   文章信息
     */
    @PostMapping("/publish")
    @RequiresRoles("marcher")
    public BaseResult publish(@Validated @RequestBody BlogArticleDTO blogArticleFrom) {

        Long articleId = blogArticleService.publish(blogArticleFrom);
        IdVO idVO = new IdVO();
        idVO.setId(articleId);
        return BaseResult.success(idVO);
    }

    /**
     * get文章 - 编辑
     *
     * @param id    文章id
     */
    @GetMapping("/getAsEdit")
    @RequiresRoles("marcher")
    public BaseResult<BlogArticleDTO> getAsEdit(@NotNull(message = "请选择需要编辑的文章") Long id) {
        BlogArticle blogArticle = blogArticleService.getById(id);
        if (EmptyUtil.isEmpty(blogArticle)) {
            return BaseResult.error(RspBaseCodeEnum.NOT_RESOURCE.getRealDesc());
        }

        BlogArticleDTO blogArticleFrom = blogArticleService.getAsEdit(id);
        return BaseResult.success(blogArticleFrom);
    }

    /**
     * query 文章
     * @param query 参数
     */
    @PostMapping("/query")
    @RequiresRoles("marcher")
    public BaseResult<PageResult<AdminArticleListVO>> query(@RequestBody QueryData query) {
        return blogArticleService.queryAsAdmin(query);
    }

    /**
     * remove 指定文章
     *
     * @param id    文章id
     */
    @PostMapping("/remove")
    @RequiresRoles("marcher")
    public BaseResult remove(Long id) {
        Assert.isNullOrLtZero(id, "请选择需要删除的文章");

        blogArticleService.removeById(id);

        return BaseResult.success();
    }

    /**
     * update 评论设置
     *
     * @param id    文章id
     */
    @PostMapping("/comment")
    @RequiresRoles("marcher")
    public BaseResult comment(Long id) {
        Assert.isNullOrLtZero(id, "请选择需要点赞的文章");

        blogArticleService.comment(id);

        return BaseResult.success();
    }

    /**
     * update 置顶设置
     *
     * @param id    文章id
     */
    @PostMapping("/top")
    @RequiresRoles("marcher")
    public BaseResult top(Long id) {
        Assert.isNullOrLtZero(id, "请选择需要置顶的文章");

        blogArticleService.top(id);

        return BaseResult.success();
    }
}
