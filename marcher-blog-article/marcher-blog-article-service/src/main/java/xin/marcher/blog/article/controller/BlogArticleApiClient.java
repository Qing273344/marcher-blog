package xin.marcher.blog.article.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.article.client.api.BlogArticleApi;
import xin.marcher.blog.article.client.model.request.BlogArticleReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleDetailsResp;
import xin.marcher.blog.article.client.model.response.BlogArticleListResp;
import xin.marcher.blog.article.client.model.response.BlogArticleResp;
import xin.marcher.blog.article.domain.BlogArticle;
import xin.marcher.blog.article.service.BlogArticleService;
import xin.marcher.framework.common.constants.GlobalCodeEnum;
import xin.marcher.framework.common.mvc.request.BaseQuery;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.util.Assert;
import xin.marcher.framework.common.util.EmptyUtil;
import xin.marcher.framework.common.wrapper.BaseWO;
import xin.marcher.framework.common.wrapper.PageWO;

/**
 * 博客类型
 *
 * @author marcher
 */
@Slf4j
@RestController
@RequestMapping(value = "/rpc/article")
@Api(value = "RPC - BlogArticleFeignClient", produces = MediaType.APPLICATION_JSON_VALUE)
public class BlogArticleApiClient implements BlogArticleApi {

    private final BlogArticleService blogArticleService;

    public BlogArticleApiClient(BlogArticleService blogArticleService) {
        this.blogArticleService = blogArticleService;
    }

    /**
     * 文章详情
     */
    @Override
    @GetMapping("/detail")
    public BaseResult<BlogArticleDetailsResp> detail(@RequestParam("id") Long id) {
        Assert.isNullOrLtZero(id, "请选择指定文章");

        BlogArticleDetailsResp resp = blogArticleService.detail(id);

        // 浏览量+1
        blogArticleService.viewsIncrease(id);

        return BaseResult.success(resp);
    }

    /**
     * query 文章
     */
    @Override
    @PostMapping("/query")
    public BaseResult<PageWO<BlogArticleListResp>> query(@RequestBody BaseQuery query) {
        return blogArticleService.query(query);
    }

    @Override
    @PostMapping("/liked")
    public BaseResult<Integer> liked(@RequestParam("id") Long id) {
        Assert.isNullOrLtZero(id, "请选择指定文章");

        Integer likedCount = blogArticleService.liked(id);
        return BaseResult.success(likedCount);
    }

    /**
     * 发布文章
     *
     * @param reqs   文章信息
     */
    @Override
    @PostMapping("/publish")
    public BaseResult<BaseWO> publish(@Validated @RequestBody BlogArticleReqs reqs) {
        Long articleId = blogArticleService.publish(reqs);

        BaseWO singleton = BaseWO.singleton(articleId);
        return BaseResult.success(singleton);
    }

    /**
     * get文章 - 编辑
     *
     * @param id    文章id
     */
    @Override
    @GetMapping("/getAsEdit")
    public BaseResult<BlogArticleResp> getAsEdit(@RequestParam("id") Long id) {
        Assert.isNullOrLtZero(id, "请选择需要编辑的文章");

        BlogArticle blogArticle = blogArticleService.getById(id);
        if (EmptyUtil.isEmpty(blogArticle)) {
            return BaseResult.error(GlobalCodeEnum.GL_OBJECT_NOT_EXISTS.getRealDesc());
        }

        BlogArticleResp blogArticleFrom = blogArticleService.getAsEdit(id);
        return BaseResult.success(blogArticleFrom);
    }

    /**
     * query 文章
     * @param query 参数
     */
    @Override
    @PostMapping("/queryFromManage")
    public BaseResult<PageWO<BlogArticleListResp>> queryFromManage(@RequestBody BaseQuery query) {
        return blogArticleService.queryAsAdmin(query);
    }

    /**
     * remove 指定文章
     *
     * @param id    文章id
     */
    @Override
    @PostMapping("/remove")
    public BaseResult<Boolean> remove(@RequestParam("id") Long id) {
        Assert.isNullOrLtZero(id, "请选择需要删除的文章");

        blogArticleService.removeById(id);

        return BaseResult.success();
    }

    /**
     * update 评论设置
     *
     * @param id    文章id
     */
    @Override
    @PostMapping("/comment")
    public BaseResult<Boolean> comment(@RequestParam("id") Long id) {
        Assert.isNullOrLtZero(id, "请选择需要点赞的文章");

        blogArticleService.comment(id);

        return BaseResult.success();
    }

    /**
     * update 置顶设置
     * @param id    文章id
     */
    @Override
    @PostMapping("/top")
    public BaseResult<Boolean> top(@RequestParam("id") Long id) {
        Assert.isNullOrLtZero(id, "请选择需要置顶的文章");

        blogArticleService.top(id);

        return BaseResult.success();
    }
}
