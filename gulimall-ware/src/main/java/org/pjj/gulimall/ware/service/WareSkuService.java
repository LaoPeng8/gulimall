package org.pjj.gulimall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.pjj.common.utils.PageUtils;
import org.pjj.gulimall.ware.entity.WareSkuEntity;

import java.util.Map;

/**
 * 商品库存
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 20:47:36
 */
public interface WareSkuService extends IService<WareSkuEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void addStock(Long skuId, Long wareId, Integer skuNum);
}

