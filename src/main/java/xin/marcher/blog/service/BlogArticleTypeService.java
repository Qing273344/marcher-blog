package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.entity.BlogArticleType;
import xin.marcher.blog.from.BlogArticleTypeFrom;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;
import xin.marcher.blog.vo.BlogArticleTypeVo;

import java.util.List;

/**
 * 博客文章类型
 *
 * @author marcher
 */
public interface BlogArticleTypeService extends IService<BlogArticleType> {

    /**
     * 博客文章类型详情
     *
     * @param id    博客文章类型id
     */
    BlogArticleTypeVo get(Long id);

    /**
     * 获取所有博客文章类型
     */
    List<BlogArticleTypeVo> listAll();

    /**
     * query 文章类型
     *
     * @param query query参数
     */
    Result query(Query<QueryData> query);

    /**
     * save 博客文章分类
     *
     * @param blogArticleTypeFrom   分类信息form
     */
    void create(BlogArticleTypeFrom blogArticleTypeFrom);

    /**
     * update 博客文章分类
     *
     * @param blogArticleTypeFrom   分类信息form
     */
    void update(BlogArticleTypeFrom blogArticleTypeFrom);

    /**
     * remove 博客文章分类
     *
     * @param ids   文章分类id
     */
    void remove(List<Long> ids);

}

