package org.pjj.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.pjj.gulimall.product.entity.ProductAttrValueEntity;
import org.pjj.gulimall.product.entity.vo.AttrRespVo;
import org.pjj.gulimall.product.entity.vo.AttrVo;
import org.pjj.gulimall.product.service.ProductAttrValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.pjj.gulimall.product.entity.AttrEntity;
import org.pjj.gulimall.product.service.AttrService;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.R;



/**
 * 商品属性
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 15:18:41
 */
@RestController
@RequestMapping("product/attr")
public class AttrController {
    @Autowired
    private AttrService attrService;

    @Autowired
    private ProductAttrValueService productAttrValueService;

    @GetMapping("/base/listforspu/{spuId}")
    public R baseAttrList(@PathVariable("spuId") Long spuId) {
        List<ProductAttrValueEntity> list = productAttrValueService.baseAttrlistforspu(spuId);

        return R.ok().put("data", list);
    }

    @GetMapping("/{attrType}/list/{catelogId}")
    public R baseAttrList(@PathVariable("attrType") String type, @PathVariable("catelogId") Long catelogId,
                          @RequestParam Map<String, Object> params) {

        PageUtils page = attrService.queryBaseAttrPage(catelogId, params, type);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{attrId}")
    //@RequiresPermissions("product:attr:info")
    public R info(@PathVariable("attrId") Long attrId){

        AttrRespVo attr = attrService.getAttrInfo(attrId);

        return R.ok().put("attr", attr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attr:save")
    public R save(@RequestBody AttrVo attr){
		attrService.saveAttr(attr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attr:update")
    public R update(@RequestBody AttrVo attr){
		attrService.updateAttr(attr);

        return R.ok();
    }

    @PostMapping("/update/{spuId}")
    public R updateSpuAttr(@PathVariable("spuId") Long spuId,
                           @RequestBody List<ProductAttrValueEntity> entities) {
        productAttrValueService.updateSpuAttr(spuId, entities);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attr:delete")
    public R delete(@RequestBody Long[] attrIds){
		attrService.removeByIds(Arrays.asList(attrIds));

        return R.ok();
    }

}
