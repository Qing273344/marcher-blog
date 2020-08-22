package xin.marcher.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import xin.marcher.blog.biz.enums.RspBaseCodeEnum;
import xin.marcher.blog.dto.BlogArticleTypeDTO;
import xin.marcher.blog.mapper.BlogTypeMapper;
import xin.marcher.blog.model.BlogType;
import xin.marcher.blog.service.BlogTypeService;
import xin.marcher.blog.utils.QueryData;
import xin.marcher.blog.vo.BlogArticleTypeVO;
import xin.marcher.framework.constants.GlobalConstant;
import xin.marcher.framework.exception.HintException;
import xin.marcher.framework.mvc.request.PageParam;
import xin.marcher.framework.mvc.response.BaseResult;
import xin.marcher.framework.mvc.response.PageResult;
import xin.marcher.framework.util.EmptyUtil;
import xin.marcher.framework.util.ObjectUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author marcher
 */
@Service
public class BlogTypeServiceImpl extends ServiceImpl<BlogTypeMapper, BlogType> implements BlogTypeService {

    @Autowired
    private BlogTypeMapper blogTypeMapper;

    @Override
    public BlogArticleTypeVO get(Long id) {
        BlogType blogType = blogTypeMapper.selectById(id);
        if (EmptyUtil.isEmpty(blogType)) {
            return null;
        }

        BlogArticleTypeVO blogArticleTypeVO = new BlogArticleTypeVO();
        ObjectUtil.copyProperties(blogType, blogArticleTypeVO);

        return blogArticleTypeVO;
    }

    @Override
    public List<BlogArticleTypeVO> listAll() {
        List<BlogType> blogTypes = blogTypeMapper.selectList(null);
        if (EmptyUtil.isEmpty(blogTypes)) {
            return new ArrayList<>();
        }

        List<BlogArticleTypeVO> blogArticleTypeVOList = new ArrayList<>();
        for (BlogType blogType : blogTypes) {
            BlogArticleTypeVO blogArticleTypeVO = new BlogArticleTypeVO();
            ObjectUtil.copyProperties(blogType, blogArticleTypeVO);
            blogArticleTypeVOList.add(blogArticleTypeVO);
        }

        return blogArticleTypeVOList;
    }

    @Override
    public BaseResult<PageResult<BlogArticleTypeVO>> query(QueryData query) {
        QueryWrapper<BlogType> queryWrapper = new QueryWrapper<>();
        if (EmptyUtil.isNotEmpty(query.getKeyword())) {
            queryWrapper.lambda().like(BlogType::getName, query.getKeyword());
        }

        PageParam pageParam = new PageParam(query.getPageNo(), query.getPageSize());
        IPage<BlogType> queryPage = new Page<>(pageParam.getPageNo(), pageParam.getPageSize());

        IPage<BlogType> blogArticleTypeIPage = blogTypeMapper.selectPage(queryPage, queryWrapper);

        List<BlogType> blogTypes = blogArticleTypeIPage.getRecords();
        List<BlogArticleTypeVO> blogArticleTypeVOList = new ArrayList<>();
        for (BlogType blogTag : blogTypes) {
            BlogArticleTypeVO blogArticleTypeVO = new BlogArticleTypeVO();
            ObjectUtil.copyProperties(blogTag, blogArticleTypeVO);
            blogArticleTypeVOList.add(blogArticleTypeVO);
        }

        PageResult<BlogArticleTypeVO> data = PageResult.rest(blogArticleTypeVOList, blogArticleTypeIPage.getTotal(), pageParam);
        return BaseResult.success(data);
    }

    @Override
    public void create(BlogArticleTypeDTO blogArticleTypeDTO) {
        // 校验同名类型
        checkAlikeName(blogArticleTypeDTO.getName());

        BlogType blogType = toBlogArticleType(blogArticleTypeDTO);
        blogTypeMapper.insert(blogType);
    }

    @Override
    public void update(BlogArticleTypeDTO blogArticleTypeDTO) {
        // 校验同名标签
        checkAlikeName(blogArticleTypeDTO.getTypeId(), blogArticleTypeDTO.getName());

        BlogType blogType = toBlogArticleType(blogArticleTypeDTO);
        blogType.setTypeId(blogArticleTypeDTO.getTypeId());

        blogTypeMapper.updateById(blogType);
    }

    @Override
    public void remove(List<Long> ids) {
        blogTypeMapper.deleteBatchIds(ids);
    }

    private BlogType toBlogArticleType(BlogArticleTypeDTO blogArticleTypeDTO) {
        BlogType blogType = new BlogType();
        ObjectUtil.copyProperties(blogArticleTypeDTO, blogType);
        blogType.setTypeId(null);
        blogType.setDeleted(GlobalConstant.NO_DELETED);

        return blogType;
    }

    private void checkAlikeName(String name) {
        QueryWrapper<BlogType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogType::getName, name);
        Integer rowNum = blogTypeMapper.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new HintException(RspBaseCodeEnum.PARAM_ILLEGAL.getRealCode(), "已存在该名称类型");
        }
    }

    private void checkAlikeName(Long typeId, String name) {
        QueryWrapper<BlogType> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(BlogType::getName, name);
        queryWrapper.lambda().ne(BlogType::getTypeId, typeId);
        Integer rowNum = blogTypeMapper.selectCount(queryWrapper);
        if (rowNum > 0) {
            throw new HintException(RspBaseCodeEnum.PARAM_ILLEGAL.getRealCode(), "已存在该名称类型");
        }
    }
}
