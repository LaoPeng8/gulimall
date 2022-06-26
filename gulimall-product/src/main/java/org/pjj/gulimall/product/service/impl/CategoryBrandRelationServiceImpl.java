package org.pjj.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.pjj.common.exception.BizCodeEnum;
import org.pjj.common.exception.GulimallException;
import org.pjj.common.utils.R;
import org.pjj.gulimall.product.dao.BrandDao;
import org.pjj.gulimall.product.dao.CategoryDao;
import org.pjj.gulimall.product.entity.BrandEntity;
import org.pjj.gulimall.product.entity.CategoryEntity;
import org.pjj.gulimall.product.entity.vo.BrandRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.Query;

import org.pjj.gulimall.product.dao.CategoryBrandRelationDao;
import org.pjj.gulimall.product.entity.CategoryBrandRelationEntity;
import org.pjj.gulimall.product.service.CategoryBrandRelationService;
import org.springframework.util.StringUtils;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {

    @Autowired
    private BrandDao brandDao;

    @Autowired
    private CategoryDao categoryDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        Long brandId = categoryBrandRelation.getBrandId();
        Long catelogId = categoryBrandRelation.getCatelogId();
        //1. 查询详细名字
        BrandEntity brandEntity = brandDao.selectById(brandId);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if(brandEntity != null && categoryEntity != null) {
            if(!StringUtils.isEmpty(brandEntity.getName()) && !StringUtils.isEmpty(categoryEntity.getName())) {
                categoryBrandRelation.setBrandName(brandEntity.getName());
                categoryBrandRelation.setCatelogName(categoryEntity.getName());
                this.baseMapper.insert(categoryBrandRelation);
            }
        }
    }

    @Override
    public void updateBrand(Long brandId, String name) {
        if(null == brandId || StringUtils.isEmpty(name)) {
            throw new GulimallException(BizCodeEnum.PARAMETER_EMPTY_EXCEPTION);
        }

        this.update(new UpdateWrapper<CategoryBrandRelationEntity>().set("brand_name", name).eq("brand_id", brandId));
    }

    @Override
    public void updateCategory(Long catId, String name) {
        if(null == catId || StringUtils.isEmpty(name)) {
            throw new GulimallException(BizCodeEnum.PARAMETER_EMPTY_EXCEPTION);
        }

        this.update(new UpdateWrapper<CategoryBrandRelationEntity>().set("catelog_name", name).eq("catelog_id", catId));
    }

    @Override
    public List<CategoryBrandRelationEntity> getBrandByCatId(String catId) {
        List<CategoryBrandRelationEntity> categoryBrandRelationList = this.baseMapper.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        if(categoryBrandRelationList == null || categoryBrandRelationList.size() <= 0) {
            return new ArrayList<>();//返回空集合
        }

        return categoryBrandRelationList;
    }

}