package xin.marcher.blog.article.service;

import xin.marcher.blog.article.client.model.request.BlogTypeReqs;
import xin.marcher.blog.article.client.model.response.BlogArticleTypeResp;
import xin.marcher.blog.article.domain.BlogType;
import xin.marcher.framework.common.mvc.request.BaseQuery;
import xin.marcher.framework.common.mvc.response.BaseResult;
import xin.marcher.framework.common.wrapper.PageWO;
import xin.marcher.framework.mybatis.service.BaseService;

import java.util.List;

/**
 * 博客类型
 *
 * @author marcher
 */
public interface BlogTypeService extends BaseService<BlogType> {

    /**
     * 博客类型详情
     *
     * @param id    博客类型id
     */
    BlogArticleTypeResp get(Long id);

    /**
     * 获取所有博客类型
     */
    List<BlogArticleTypeResp> listAll();

    /**
     * query 类型
     *
     * @param query query参数
     */
    BaseResult<PageWO<BlogArticleTypeResp>> query(BaseQuery query);

    /**
     * save 博客文章分类
     *
     * @param reqs   分类信息form
     */
    void create(BlogTypeReqs reqs);

    /**
     * update 博客文章分类
     *
     * @param reqs   分类信息form
     */
    void update(BlogTypeReqs reqs);

    /**
     * remove 博客文章分类
     *
     * @param ids   文章分类id
     */
    void remove(List<Long> ids);

}

