package org.pjj.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.pjj.gulimall.product.entity.AttrEntity;
import org.pjj.gulimall.product.entity.vo.AttrGroupRelationVo;
import org.pjj.gulimall.product.service.AttrService;
import org.pjj.gulimall.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.pjj.gulimall.product.entity.AttrGroupEntity;
import org.pjj.gulimall.product.service.AttrGroupService;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.R;



/**
 * 属性分组
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 15:18:41
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    /**
     * 获取当前属性分组没有关联得属性
     * @param attrgroupId
     * @param params
     * @return
     */
    @GetMapping("/{attrgroupId}/no/relation/attr")
    public R ajkdjalkf(@PathVariable("attrgroupId") Long attrgroupId, @RequestParam Map<String, Object> params) {
        PageUtils page = attrService.getNoRelationAttr(attrgroupId, params);
        return R.ok().put("page", page);
    }

    /**
     * 根据属性id 与 属性分组id 从属性分组中删除属性 (批量删除)
     * @param attrGroupRelationVoList
     * @return
     */
    @PostMapping("/attr/relation/delete")
    public R attrBatchDelete(@RequestBody List<AttrGroupRelationVo> attrGroupRelationVoList) {
        attrGroupService.deleteRelationBatch(attrGroupRelationVoList);


        return R.ok();
    }

    /**
     * 根据分组id查找关联得所有 基本属性(attr_type 为 1 即是基本属性)
     * @param attrgroupId
     * @return
     */
    @GetMapping("/{attrgroupId}/relation/attr")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> list = attrService.getAttrRelation(attrgroupId);

        return R.ok().put("data", list);
    }

    /**
     * 根据分类id 分页查询
     */
    @RequestMapping("/list/{catelogId}")
    //@RequiresPermissions("product:attrgroup:list")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId){
//        PageUtils page = attrGroupService.queryPage(params); //老的分页查询, 只能查全部attrGroup

        //新的分页查询, 根据catelogId查询attrGroup, catelogId=0 查询全部
        PageUtils page = attrGroupService.queryPage(params, catelogId);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    //@RequiresPermissions("product:attrgroup:info")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);

        Long[] path = categoryService.findCatelogPath(attrGroup.getCatelogId());// 根据 当前分类id 查询分类完整路径
		attrGroup.setCatelogPath(path);//设置 分类的完整路径id [1, 23, 55] 即 当前attr属于55分类, 55分类属于23分类, 23分类属于 1分类

        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrgroup:save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrgroup:update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrgroup:delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

//		TODO 删除时还需将 attr_attrgroup_relation 中该分组关联的属性关系都删掉

        return R.ok();
    }

}
