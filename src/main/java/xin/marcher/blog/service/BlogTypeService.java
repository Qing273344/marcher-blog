package xin.marcher.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import xin.marcher.blog.entity.BlogType;
import xin.marcher.blog.dto.request.BlogArticleTypeReq;
import xin.marcher.blog.utils.Query;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.utils.Result;
import xin.marcher.blog.dto.response.BlogArticleTypeResp;

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
    Result query(Query<QueryData> query);

    /**
     * save 博客文章分类
     *
     * @param blogArticleTypeReq   分类信息form
     */
    void create(BlogArticleTypeReq blogArticleTypeReq);

    /**
     * update 博客文章分类
     *
     * @param blogArticleTypeReq   分类信息form
     */
    void update(BlogArticleTypeReq blogArticleTypeReq);

    /**
     * remove 博客文章分类
     *
     * @param ids   文章分类id
     */
    void remove(List<Long> ids);

}

