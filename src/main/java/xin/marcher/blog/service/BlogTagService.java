package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.entity.BlogTag;
import xin.marcher.blog.from.BlogTagFrom;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;
import xin.marcher.blog.vo.BlogTagVo;

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
    BlogTagVo get(Long id);

    /**
     * list 所有标签
     */
    List<BlogTagVo> listAll();

    /**
     * query
     * @param query query条件
     */
    Result query(Query<QueryData> query);

    /**
     * 创建博客标签
     */
    void create(BlogTagFrom blogTagFrom);

    /**
     * 编辑标签
     */
    void update(BlogTagFrom blogTagFrom);

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

