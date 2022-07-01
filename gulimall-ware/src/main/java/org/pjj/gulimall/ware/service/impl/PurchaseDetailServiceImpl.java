package org.pjj.gulimall.ware.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.pjj.common.utils.PageUtils;
import org.pjj.common.utils.Query;
import org.pjj.gulimall.ware.dao.PurchaseDao;
import org.pjj.gulimall.ware.dao.PurchaseDetailDao;
import org.pjj.gulimall.ware.entity.PurchaseDetailEntity;
import org.pjj.gulimall.ware.entity.PurchaseEntity;
import org.pjj.gulimall.ware.service.PurchaseDetailService;
import org.pjj.gulimall.ware.service.PurchaseService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;


@Service("purchaseDetailService")
public class PurchaseDetailServiceImpl extends ServiceImpl<PurchaseDetailDao, PurchaseDetailEntity> implements PurchaseDetailService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<PurchaseDetailEntity> purchaseDetailEntityQueryWrapper = new QueryWrapper<>();

        String wareId = (String) params.get("wareId");
        if(!StringUtils.isEmpty(wareId)) {
            purchaseDetailEntityQueryWrapper.eq("ware_id", wareId);
        }

        String status = (String) params.get("status");
        if(!StringUtils.isEmpty(status)) {
            purchaseDetailEntityQueryWrapper.eq("status", status);
        }

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)) {
            purchaseDetailEntityQueryWrapper.and((wrapper) -> {
                wrapper.eq("purchase_id", key).or().eq("sku_id", key);
            });
        }

        IPage<PurchaseDetailEntity> page = this.page(
                new Query<PurchaseDetailEntity>().getPage(params),
                purchaseDetailEntityQueryWrapper
        );

        return new PageUtils(page);
    }

}