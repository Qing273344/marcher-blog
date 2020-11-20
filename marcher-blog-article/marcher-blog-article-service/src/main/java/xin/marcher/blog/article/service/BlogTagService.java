package xin.marcher.blog.article.service;

import xin.marcher.blog.article.client.model.request.BlogTagReqs;
import xin.marcher.blog.article.client.model.response.BlogTagResp;
import xin.marcher.blog.article.domain.BlogTag;
import xin.marcher.framework.mvc.request.BaseQuery;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mybatis.service.BaseService;
import xin.marcher.framework.wrapper.PageWO;

import java.util.List;

/**
 * 标签
 *
 * @author marcher
 */
public interface BlogTagService extends BaseService<BlogTag> {

    /**
     * get标签详情
     *
     * @param id    标签id
     * @return
     *      标签
     */
    BlogTagResp get(Long id);

    /**
     * list 所有标签
     * @return
     *      标签
     */
    List<BlogTagResp> listAll();

    /**
     * query
     *
     * @param query query条件
     * @return
     *      tag
     */
    BaseResult<PageWO<BlogTagResp>> query(BaseQuery query);

    /**
     * 创建博客标签
     *
     * @param reqs    reqs
     */
    void create(BlogTagReqs reqs);

    /**
     * 编辑标签
     *
     * @param reqs 新的数据
     */
    void update(BlogTagReqs reqs);

    /**
     * 删除标签
     *
     * @param ids   标签id
     */
    void remove(List<Long> ids);

    /**
     * get 热门标签
     *
     * @return
     *      热门标签
     */
    List<String> getHotTag();
}

