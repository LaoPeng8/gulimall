package org.pjj.gulimall.coupon.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.pjj.common.utils.PageUtils;
import org.pjj.gulimall.coupon.entity.SkuLadderEntity;

import java.util.Map;

/**
 * 商品阶梯价格
 *
 * @author PengJiaJun
 * @email 2779824672@qq.com
 * @date 2022-05-07 16:51:58
 */
public interface SkuLadderService extends IService<SkuLadderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

