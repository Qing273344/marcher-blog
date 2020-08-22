package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.dto.BlogArticleTypeDTO;
import xin.marcher.blog.model.BlogType;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.vo.BlogArticleTypeVO;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;

import java.util.List;

/**
 * 博客类型
 *
 * @author marcher
 */
public interface BlogTypeService extends IService<BlogType> {

    /**
     * 博客类型详情
     *
     * @param id    博客类型id
     */
    BlogArticleTypeVO get(Long id);

    /**
     * 获取所有博客类型
     */
    List<BlogArticleTypeVO> listAll();

    /**
     * query 类型
     *
     * @param query query参数
     */
    BaseResult<PageResult<BlogArticleTypeVO>> query(QueryData query);

    /**
     * save 博客文章分类
     *
     * @param blogArticleTypeDTO   分类信息form
     */
    void create(BlogArticleTypeDTO blogArticleTypeDTO);

    /**
     * update 博客文章分类
     *
     * @param blogArticleTypeDTO   分类信息form
     */
    void update(BlogArticleTypeDTO blogArticleTypeDTO);

    /**
     * remove 博客文章分类
     *
     * @param ids   文章分类id
     */
    void remove(List<Long> ids);

}

