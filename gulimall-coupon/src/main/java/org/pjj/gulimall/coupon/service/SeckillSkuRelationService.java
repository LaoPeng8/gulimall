package org.pjj.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.pjj.common.utils.PageUtils;
import org.pjj.gulimall.coupon.entity.SeckillSkuRelationEntity;

import java.util.Map;

/**
 * 秒杀活动商品关联
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 16:51:58
 */
public interface SeckillSkuRelationService extends IService<SeckillSkuRelationEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

