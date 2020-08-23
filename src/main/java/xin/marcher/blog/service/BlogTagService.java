package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.dto.BlogTagDTO;
import xin.marcher.blog.model.BlogTag;
import xin.marcher.blog.dto.BaseQuery;
import xin.marcher.blog.vo.BlogTagVO;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;

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
     * @return
     *      标签
     */
    BlogTagVO get(Long id);

    /**
     * list 所有标签
     * @return
     *      标签
     */
    List<BlogTagVO> listAll();

    /**
     * query
     *
     * @param query query条件
     * @return
     *      tag
     */
    BaseResult<PageResult<BlogTagVO>> query(BaseQuery query);

    /**
     * 创建博客标签
     *
     * @param blogTagDTO    blogTagReq
     */
    void create(BlogTagDTO blogTagDTO);

    /**
     * 编辑标签
     *
     * @param blogTagDTO 新的数据
     */
    void update(BlogTagDTO blogTagDTO);

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

