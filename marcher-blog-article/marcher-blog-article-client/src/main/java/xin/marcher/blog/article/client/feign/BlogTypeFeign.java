package xin.marcher.blog.article.client.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import xin.marcher.blog.article.client.model.request.BlogTypeReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleTypeResp;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;

import java.util.List;

/**
 * 类型 Feign
 *
 * @author marcher
 */
@FeignClient(name = "marcher-blog-article-client", contextId = "blog-type-feign")
@RequestMapping(value = "/rpc/type")
public interface BlogTypeFeign {

    /**
     * 博客类型详情
     *
     * @param id    博客类型id
     * @return result
     */
    @GetMapping("/get")
    BaseResult<BlogArticleTypeResp> get(Long id);

    /**
     * 获取所有博客类型
     * @return result
     */
    @GetMapping("/listAll")
    BaseResult<List<BlogArticleTypeResp>> listAll();

    /**
     * query 文章类型
     *
     * @param query query参数
     * @return result
     */
    @PostMapping("/query")
    BaseResult<PageResult<BlogArticleTypeResp>> query(@RequestBody BaseQuery query);

    /**
     * save 博客文章分类
     *
     * @param reqs   分类信息form
     * @return result
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@Validated @RequestBody BlogTypeReqs reqs);

    /**
     * update 博客文章分类
     *
     * @param reqs   分类信息form
     */
    @PostMapping("/update")
    BaseResult<Boolean> update(@Validated @RequestBody BlogTypeReqs reqs);

    /**
     * remove 博客文章分类
     *
     * @param ids   文章分类id
     */
    @PostMapping("/remove")
    BaseResult<Boolean> remove(@RequestBody  List<Long> ids);
}
