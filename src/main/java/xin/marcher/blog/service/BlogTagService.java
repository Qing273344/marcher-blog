package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.dto.response.BlogTagResp;
import xin.marcher.blog.entity.BlogTag;
import xin.marcher.blog.dto.request.BlogTagReq;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;

import java.util.List;

/**
 * 标签
 *
 * @author marcher
 */
public interface BlogTagService extends IService<BlogTag> {

    /**
     * get标签详情
     *
     * @param id    标签id
     */
    BlogTagResp get(Long id);

    /**
     * list 所有标签
     */
    List<BlogTagResp> listAll();

    /**
     * query
     *
     * @param query query条件
     */
    Result query(Query<QueryData> query);

    /**
     * 创建博客标签
     */
    void create(BlogTagReq blogTagReq);

    /**
     * 编辑标签
     * @param blogTagReq 新的数据
     */
    void update(BlogTagReq blogTagReq);

    /**
     * 删除标签
     *
     * @param ids   标签id
     */
    void remove(List<Long> ids);

    /**
     * get 热门标签
     * @return
     *      热门标签
     */
    List<String> getHotTag();
}

