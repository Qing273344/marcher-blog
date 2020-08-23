package xin.marcher.blog.controller;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.marcher.blog.dto.BlogArticleTypeDTO;
import xin.marcher.blog.service.BlogTypeService;
import xin.marcher.blog.dto.BaseQuery;
import xin.marcher.blog.vo.BlogArticleTypeVO;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.mvc.validation.groups.GroupUpdateOrder;
import xin.marcher.framework.util.Assert;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 博客文章类型
 *
 * @author marcher
 */
@Validated
@RestController
@RequestMapping(value = "/admin/type")
public class AdminTypeController {

    @Autowired
    private BlogTypeService blogTypeService;

    /**
     * 博客类型详情
     *
     * @param id    博客类型id
     */
    @GetMapping("/get")
    @RequiresRoles("marcher")
    public BaseResult<BlogArticleTypeVO> get(Long id) {
        Assert.isNullOrLtZero(id, "请选择分类");

        BlogArticleTypeVO blogArticleTypeVO = blogTypeService.get(id);
        return BaseResult.success(blogArticleTypeVO);
    }

    /**
     * 获取所有博客类型
     */
    @GetMapping("/listAll")
    @RequiresRoles("marcher")
    public BaseResult<List<BlogArticleTypeVO>> listAll() {
        List<BlogArticleTypeVO> blogArticleTypeVOList = blogTypeService.listAll();

        return BaseResult.success(blogArticleTypeVOList);
    }

    /**
     * query 文章类型
     *
     * @param query query参数
     */
    @PostMapping("/query")
    @RequiresRoles("marcher")
    public BaseResult<PageResult<BlogArticleTypeVO>> query(@RequestBody BaseQuery query) {
        return blogTypeService.query(query);
    }

    /**
     * save 博客文章分类
     *
     * @param blogArticleTypeDTO   分类信息form
     */
    @PostMapping("/save")
    @RequiresRoles("marcher")
    public BaseResult save(@Validated @RequestBody BlogArticleTypeDTO blogArticleTypeDTO) {
        blogTypeService.create(blogArticleTypeDTO);

        return BaseResult.success();
    }

    /**
     * update 博客文章分类
     *
     * @param blogArticleTypeDTO   分类信息form
     */
    @PostMapping("/update")
    @RequiresRoles("marcher")
    public BaseResult update(@Validated(GroupUpdateOrder.class) @RequestBody BlogArticleTypeDTO blogArticleTypeDTO) {
        blogTypeService.update(blogArticleTypeDTO);

        return BaseResult.success();
    }

    /**
     * remove 博客文章分类
     *
     * @param ids   文章分类id
     */
    @PostMapping("/remove")
    @RequiresRoles("marcher")
    public BaseResult remove(@NotEmpty(message = "请至少选择一条记录") @RequestBody  List<Long> ids) {
        blogTypeService.remove(ids);

        return BaseResult.success();
    }
}
