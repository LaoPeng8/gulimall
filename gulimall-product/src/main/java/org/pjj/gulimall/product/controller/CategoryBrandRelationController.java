package org.pjj.gulimall.product.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.pjj.common.valid.AddGroup;
import org.pjj.gulimall.product.entity.vo.BrandRespVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.pjj.gulimall.product.entity.CategoryBrandRelationEntity;
import org.pjj.gulimall.product.service.CategoryBrandRelationService;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 15:18:41
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    /**
     * 根据分类id获取 该分类下的所有品牌
     *
     * 1. Controller 处理请求, 接收和校验参数
     * 2. Service 接收Controller传来的数据, 进行业务处理
     * 3. Controller 接收Service处理完的数据, 封装为页面指定的Vo
     * @return
     */
    @GetMapping("/brands/list")
    public R getBrandByCatId(@RequestParam("catId") String catId) {

        List<CategoryBrandRelationEntity> CategoryBrandRelationEntityList = categoryBrandRelationService.getBrandByCatId(catId);

        List<BrandRespVo> brandList = CategoryBrandRelationEntityList.stream().map((categoryBrandRelationEntity) -> {
            BrandRespVo brandRespVo = new BrandRespVo();
            brandRespVo.setBrandId(categoryBrandRelationEntity.getBrandId());
            brandRespVo.setBrandName(categoryBrandRelationEntity.getBrandName());
            return brandRespVo;
        }).collect(Collectors.toList());
        return R.ok().put("data", brandList);
    }

    /**
     * 获取当前品牌关联的所有分类 列表
     */
    @GetMapping("/catelog/list/{brandId}")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R list(@PathVariable("brandId") Long brandId){

        List<CategoryBrandRelationEntity> list = categoryBrandRelationService.list(
                new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id", brandId)
        );

        return R.ok().put("data", list);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:categorybrandrelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:categorybrandrelation:info")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:categorybrandrelation:save")
    public R save(@RequestBody @Validated(value = AddGroup.class) CategoryBrandRelationEntity categoryBrandRelation){

        categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:categorybrandrelation:update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:categorybrandrelation:delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
