package org.pjj.gulimall.ware.controller;

import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.R;
import org.pjj.gulimall.ware.entity.PurchaseDetailEntity;
import org.pjj.gulimall.ware.entity.PurchaseEntity;
import org.pjj.gulimall.ware.service.PurchaseDetailService;
import org.pjj.gulimall.ware.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 采购信息
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022/06/30 22:35
 */
@RestController
@RequestMapping("ware/purchase/detail")
public class PurchaseDetailController {

    @Autowired
    private PurchaseDetailService purchaseDetailService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("ware:purchase:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = purchaseDetailService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("ware:purchase:info")
    public R info(@PathVariable("id") Long id){
        PurchaseDetailEntity purchase = purchaseDetailService.getById(id);

        return R.ok().put("purchase", purchase);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("ware:purchase:save")
    public R save(@RequestBody PurchaseDetailEntity purchase){
        purchaseDetailService.save(purchase);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("ware:purchase:update")
    public R update(@RequestBody PurchaseDetailEntity purchase){
        purchaseDetailService.updateById(purchase);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("ware:purchase:delete")
    public R delete(@RequestBody Long[] ids){
        purchaseDetailService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}

