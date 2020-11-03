package xin.marcher.blog.article.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.article.client.model.request.BlogArticleReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleDetailsResp;
import xin.marcher.blog.article.client.model.response.BlogArticleListResp;
import xin.marcher.blog.article.client.model.response.BlogArticleResp;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.wrapper.BaseWO;

/**
 * 文章 Feign
 *
 * @author marcher
 */
@FeignClient(name = "marcher-blog-article-client", contextId = "blog-article-feign")
@RequestMapping(value = "/rpc/article")
public interface BlogArticleApi {

    /**
     * 文章详情
     *
     * @param id    id
     * @return  result
     */
    @GetMapping("/detail")
    BaseResult<BlogArticleDetailsResp> detail(@RequestParam("id") Long id);

    /**
     * 查询文章
     *
     * @param query query
     * @return  result
     */
    @PostMapping("/query")
    BaseResult<PageResult<BlogArticleListResp>> query(@RequestBody BaseQuery query);

    /**
     * 喜欢
     *
     * @param id    id
     * @return  result
     */
    @PostMapping("/liked")
    BaseResult<Integer> liked(@RequestParam("id") Long id);

    /**
     * 发布文章
     *
     * @param reqs   文章信息
     */
    @PostMapping("/publish")
    BaseResult<BaseWO> publish(@Validated @RequestBody BlogArticleReqs reqs);

    /**
     * get文章 - 编辑
     *
     * @param id    文章id
     */
    @GetMapping("/getAsEdit")
    BaseResult<BlogArticleResp> getAsEdit(Long id);

    /**
     * query 文章
     * @param query 参数
     */
    @PostMapping("/queryFromManage")
    BaseResult<PageResult<BlogArticleResp>> queryFromManage(@RequestBody BaseQuery query);

    /**
     * remove 指定文章
     *
     * @param id    文章id
     * @return result
     */
    @PostMapping("/remove")
    BaseResult<Boolean> remove(Long id);

    /**
     * update 评论设置
     *
     * @param id    文章id
     * @return result
     */
    @PostMapping("/comment")
    BaseResult<Boolean> comment(Long id);

    /**
     * update 置顶设置
     *
     * @param id    文章id
     * @return result
     */
    @PostMapping("/top")
    BaseResult<Boolean> top(Long id);
}
