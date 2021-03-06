package xin.marcher.blog.article.client.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.article.client.model.request.BlogTagReqs;
import xin.marcher.blog.article.client.model.response.BlogTagResp;
import xin.marcher.framework.common.mvc.request.BaseQuery;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.wrapper.PageWO;

import java.util.List;

/**
 * 标签 Feign
 *
 * @author marcher
 */
@FeignClient(name = "marcher-blog-article-service", contextId = "blog-tag-feign")
@RequestMapping(value = "/rpc/tag")
public interface BlogTagApi {

    /**
     * get 热门标签
     * @return result
     */
    @GetMapping("/getHotTag")
    BaseResult<List<String>> getHotTag();

    /**
     * get标签详情
     *
     * @param id    标签id
     * @return result
     */
    @GetMapping("/get")
    BaseResult<BlogTagResp> get(@RequestParam("id") Long id);

    /**
     * list 所有标签
     * @return result
     */
    @GetMapping("/listAll")
    BaseResult<List<BlogTagResp>> listAll();

    /**
     * query 标签
     *
     * @param query query 参数
     */
    @PostMapping("/query")
    BaseResult<PageWO<BlogTagResp>> query(@RequestBody BaseQuery query);

    /**
     * 新增标签
     * @param reqs  reqs
     * @return result
     */
    @PostMapping("/save")
    BaseResult<Boolean> save(@Validated @RequestBody BlogTagReqs reqs);

    /**
     * 编辑标签
     * @param reqs  reqs
     * @return result
     */
    @PostMapping("/update")
    BaseResult<Boolean> update(@Validated() @RequestBody BlogTagReqs reqs);

    /**
     * 删除标签
     *
     * @param ids   标签id
     * @return result
     */
    @PostMapping("/remove")
    BaseResult<Boolean> remove(@RequestBody List<Long> ids);
}
